/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import ShareManager.Config;
import ShareManager.DB;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author atna
 */
public class Kimutatas extends Panel {
    ListSelectionModel selectionModel;
    ArrayList<TickerStock> listOfTickerStock;
    boolean filterFlag = false; //a szűrőknél kapcsolja a táblázat frissítését
    int x; //frissítés paramétere
    
    /**
     * Egy ügylet (értékpapír) adatai nyereség számításhoz (FIFO) módszerrel
     */
    private class Ticker{
        int quantity;
        double price;
        double currencyPrice;
        
        /**
         * A Ticker konstruktora a szükséges paraméterekkel
         * @param q - int - mennyiség
         * @param p - double - árfolyam (értékpapír)
         * @param cp - double - deviza árfolyam (forintnál 1)
         */
        private Ticker(int q, double p, double cp) {
            this.quantity = q;
            this.price = p;
            this.currencyPrice = cp;
        }
    }
    
    /**
     * A TickerStock osztály lényegében egy LinkedList egy értékpapír ügyleteiről.
     * Ezen felül nyilvántartja az aktuális készletet.
     */
    private class TickerStock{
        String tickerStockName;
        int totalProfit;
        int sumQ;
        int sumValue;
        LinkedList<Ticker> tickers = new LinkedList<>(); 
        
        /**
         * A TickerStock konstruktora, a paraméter az értékpapír neve
         * @param t - String - értékpapír neve
         */
        private TickerStock(String t){
            this.tickerStockName = t;
            sumQ = 0;   //az aktuális készlet
            sumValue = 0; //a teljes érték
        }
        
        /**
         * A TickerStock nevét adja eredményül
         * @return - String - tickerstock neve
         */
        private String getTickerName(){
            return tickerStockName;
        }
        
        private void buy(Ticker t){
            tickers.add(t);
            sumQ += t.quantity;
        }
        
        private int sell(Ticker t){
            int q1 = t.quantity;
            double p1 = t.price;
            double cp1 = t.currencyPrice;
            int q0 = tickers.getFirst().quantity;
            double p0 = tickers.getFirst().price;
            double cp0 = tickers.getFirst().currencyPrice;
            int profit = 0;
            
            while (q1!=0) {
                if (q0>q1){  //ha az első elem bőven fedezi az értékesítést (marad is belőle)
                    profit += (int) Math.round((p1*cp1 - p0*cp0) * q1);
                    tickers.getFirst().quantity -= q1;  //az eredeti mennyiségét módosítjuk NEM a változóét
                    sumQ -= q1;
                    q1 -= q1;
                } else {                            //ha az első elem nem fedezi a teljes értékesítést
                    profit += (int) Math.round((p1*cp1 - p0*cp0) * q0);
                    tickers.poll();                 //eltávolítja az első elemet, ami "kifogyott"
                    sumQ -=q0;
                    q1 -= q0;                       //ennyi marad, NULLA is lehet
                }
            }
            return profit;
        }
    }
    
    private TickerStock getTickerStockFromList(String t) {
        for (int i=0; i<listOfTickerStock.size(); i++){
            if (listOfTickerStock.get(i).getTickerName().equals(t))
                return listOfTickerStock.get(i);
        }
        TickerStock last = new TickerStock(t);
        listOfTickerStock.add(last);
        return last;
    }
    
    
    /**
     * Creates new form Ertekpapirok
     */
    public Kimutatas() {
        initComponents();
        
        DB db = new DB();
        db.sqlLekerdezKimutatas(tbKimutatas, "Mind", "Mind", "Mind", "Mind");
        nyeresegszamitas();
        Config.addColumnAndAllToCBX(cbxAccount, tbKimutatas, 1);
        Config.addColumnAndAllToCBX(cbxShare, tbKimutatas, 3);
        filterFlag = true;
        
        setTableListener();  
    }

    /**
     * Kitölti a kimutatás számolandó (HUF érték, nyereség/veszteség) értékeit és összegzi azt
     */
    private void nyeresegszamitas(){
        listOfTickerStock = new ArrayList<>();
        TickerStock ts;
        int totalProfit = 0;
        int totalCommision = 0;
        int tax = 0;
        
        for (int i=0; i<tbKimutatas.getRowCount(); i++){
            ts = getTickerStockFromList(tbKimutatas.getValueAt(i, 3).toString());
            int profit = 0;
            int q = Integer.parseInt(tbKimutatas.getValueAt(i, 4).toString());
            double p = Double.parseDouble(tbKimutatas.getValueAt(i, 5).toString());
            double com = Double.parseDouble(tbKimutatas.getValueAt(i, 6).toString());
            double cp = Double.parseDouble(tbKimutatas.getValueAt(i, 7).toString());
            int comHUF = (int) Math.round(com*cp);
            tbKimutatas.setValueAt(""+comHUF, i, 9);
            int value = (int) Math.round(p*q*cp);
            tbKimutatas.setValueAt(""+value, i, 8);
            if (q>0){   //ha vétel van
                ts.buy(new Ticker(q,p,cp));
            } else {        //ha eladás van
                profit = ts.sell(new Ticker(-q, p,cp));
            }
            tbKimutatas.setValueAt(""+profit, i, 10);
            totalProfit += profit;
            totalCommision += comHUF;
        }
        lbBrtNyereseg.setText(String.format("%1$,d", totalProfit));
        lbKoltseg.setText(String.format("%1$,d", totalCommision));
        totalProfit -= totalCommision;
        lbNettoNyereseg.setText(String.format("%1$,d", totalProfit));
    }
    
    @Override
    public void frissites(){
        String s1 = cbxAccount.getSelectedItem().toString();
        String s2 = cbxShare.getSelectedItem().toString();
        String d1 = "Mind";
        String d2 = "Mind";
        if (chbPeriod.isSelected()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            d1 = sdf.format(spDate1.getValue());
            d2 = sdf.format(spDate2.getValue());
        }

        DB db = new DB();
        db.sqlLekerdezKimutatas(tbKimutatas, s1, s2, d1, d2);
        nyeresegszamitas();
    }
    
    /**
     * A kimutatásban szereplő évszámokat adja eredményül rendezett sorrendben, egy String tömbben
     * @return
     */
    public String[] getYear(){
        ArrayList<String> years = new ArrayList<>();
        for (int i=0; i<tbKimutatas.getRowCount(); i++){
            String year = tbKimutatas.getValueAt(i, 2).toString();
            if (!year.isEmpty()) {
                year = year.substring(0, 4);
                if (years.indexOf(year)<0) years.add(year);
            }
        }
        Collections.sort(years);
        String[] s = new String[years.size()];
        for (int i=0; i<years.size(); i++) {
            s[i] += years.get(i);
        }
        return s;
    }
    
    
    private void setTableListener(){
        selectionModel = tbKimutatas.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!selectionModel.isSelectionEmpty()) {
                    int i = selectionModel.getMinSelectionIndex();
                    
                } else {
                    
                }
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbKimutatas = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxAccount = new javax.swing.JComboBox<>();
        cbxShare = new javax.swing.JComboBox<>();
        chbPeriod = new javax.swing.JCheckBox();
        spDate2 = new javax.swing.JSpinner();
        spDate1 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbBrtNyereseg = new javax.swing.JLabel();
        lbKoltseg = new javax.swing.JLabel();
        lbNettoNyereseg = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(780, 560));
        setPreferredSize(new java.awt.Dimension(780, 530));

        tbKimutatas.setAutoCreateRowSorter(true);
        tbKimutatas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ügylet ID", "Számla", "Dátum", "Értékpapír", "Mennyiség", "Árfolyam", "Jutalék", "Dev.árf", "HUF érték", "HUF jutalék", "Nyereség/veszteség"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbKimutatas.setSelectionBackground(new java.awt.Color(0, 153, 255));
        jScrollPane1.setViewportView(tbKimutatas);
        if (tbKimutatas.getColumnModel().getColumnCount() > 0) {
            tbKimutatas.getColumnModel().getColumn(0).setMinWidth(0);
            tbKimutatas.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbKimutatas.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Kimutatás");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Szűrők"));

        jLabel1.setText("Számla");

        jLabel2.setText("Értékpapír");

        cbxAccount.setSelectedItem("Mind");
        cbxAccount.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxAccountItemStateChanged(evt);
            }
        });

        cbxShare.setSelectedItem("Mind");
        cbxShare.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxShareItemStateChanged(evt);
            }
        });

        chbPeriod.setText("Időszak");
        chbPeriod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chbPeriodStateChanged(evt);
            }
        });

        spDate2.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_WEEK));
        spDate2.setEditor(new javax.swing.JSpinner.DateEditor(spDate2, "yyyy-MM-dd"));
        spDate2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spDate2StateChanged(evt);
            }
        });

        spDate1.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1514841960000L), null, null, java.util.Calendar.DAY_OF_WEEK));
        spDate1.setEditor(new javax.swing.JSpinner.DateEditor(spDate1, "yyyy-MM-dd"));
        spDate1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spDate1StateChanged(evt);
            }
        });

        jLabel3.setText("-tól");

        jLabel5.setText("-ig");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxShare, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chbPeriod)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(cbxAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxShare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbPeriod)
                    .addComponent(spDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Bruttó nyereség");

        jLabel7.setText("Költség");

        jLabel8.setText("Nettó nyereség");

        lbBrtNyereseg.setText("jLabel9");

        lbKoltseg.setText("jLabel9");

        lbNettoNyereseg.setText("jLabel9");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(lbBrtNyereseg)
                        .addGap(198, 198, 198)
                        .addComponent(jLabel7)
                        .addGap(21, 21, 21)
                        .addComponent(lbKoltseg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(21, 21, 21)
                        .addComponent(lbNettoNyereseg)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel4)
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbBrtNyereseg)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(lbKoltseg)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lbNettoNyereseg))))
                .addGap(55, 55, 55))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxAccountItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAccountItemStateChanged
        
        if (filterFlag) frissites();
    }//GEN-LAST:event_cbxAccountItemStateChanged

    private void cbxShareItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxShareItemStateChanged

        if (filterFlag) frissites();
    }//GEN-LAST:event_cbxShareItemStateChanged

    private void chbPeriodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chbPeriodStateChanged
        frissites();
    }//GEN-LAST:event_chbPeriodStateChanged

    private void spDate1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spDate1StateChanged
        frissites();
    }//GEN-LAST:event_spDate1StateChanged

    private void spDate2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spDate2StateChanged
         frissites();
    }//GEN-LAST:event_spDate2StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxAccount;
    private javax.swing.JComboBox<String> cbxShare;
    private javax.swing.JCheckBox chbPeriod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbBrtNyereseg;
    private javax.swing.JLabel lbKoltseg;
    private javax.swing.JLabel lbNettoNyereseg;
    private javax.swing.JSpinner spDate1;
    private javax.swing.JSpinner spDate2;
    private javax.swing.JTable tbKimutatas;
    // End of variables declaration//GEN-END:variables
}
