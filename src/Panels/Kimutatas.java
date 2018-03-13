/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import ShareManager.Config;
import ShareManager.DB;
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
        
//        Config.addColumnAndAllToCBX(cbxAccount, tbKimutatas, 1);
        Config.addColumnAndAllToCBX(cbxShare, tbKimutatas, 3);
        frissites();
        
        setTableListener();  
    }

    private void nyeresegszamitas(){
        listOfTickerStock = new ArrayList<>();
        TickerStock ts;
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
        }
    }
    
    @Override
    public void frissites(){
        DB db = new DB();
        
        db.sqlLekerdezKimutatas(tbKimutatas, "Mind", cbxShare.getSelectedItem().toString());
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
        cbxYear = new javax.swing.JComboBox<>();
        rbtYear = new javax.swing.JRadioButton();
        rbtPeriod = new javax.swing.JRadioButton();
        spPeriodBegin = new javax.swing.JSpinner();
        spPeriodEnd = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxAccount = new javax.swing.JComboBox<>();
        cbxShare = new javax.swing.JComboBox<>();

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

        cbxYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mind" }));

        buttonGroup1.add(rbtYear);
        rbtYear.setSelected(true);
        rbtYear.setText("Év");

        buttonGroup1.add(rbtPeriod);
        rbtPeriod.setText("Időszak");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(rbtYear))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtPeriod)
                        .addGap(18, 18, 18)
                        .addComponent(spPeriodBegin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(spPeriodEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cbxShare, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(425, 425, 425))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(cbxAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxShare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtPeriod)
                    .addComponent(spPeriodBegin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spPeriodEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtYear)
                    .addComponent(cbxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel4)
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxAccountItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAccountItemStateChanged
        
        frissites();
    }//GEN-LAST:event_cbxAccountItemStateChanged

    private void cbxShareItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxShareItemStateChanged

        frissites();
    }//GEN-LAST:event_cbxShareItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxAccount;
    private javax.swing.JComboBox<String> cbxShare;
    private javax.swing.JComboBox<String> cbxYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtPeriod;
    private javax.swing.JRadioButton rbtYear;
    private javax.swing.JSpinner spPeriodBegin;
    private javax.swing.JSpinner spPeriodEnd;
    private javax.swing.JTable tbKimutatas;
    // End of variables declaration//GEN-END:variables
}
