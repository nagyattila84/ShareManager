/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import ShareManager.Config;
import ShareManager.DB;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author atna
 */
public class Ertekpapirok extends Panel {
    ListSelectionModel selectionModel;
    
    /**
     * Creates new form Ertekpapirok
     */
    public Ertekpapirok() {
        initComponents();
        frissites();
        setTableListener();
    }

    public void frissites(){
        DB db = new DB();
        db.sqlLekerdezErtekpapirok(tbErtekpapirok);
        Config.addItemToComboBox(cbxType, Tipusok.tbTipusok);
        Config.addItemToComboBox(cbxExchange, Tozsdek.tbTozsdek);
        Config.addItemToComboBox(cbxCurrency, Penznemek.tbPenznemek);
    }
    
    private void setTableListener(){
        selectionModel = tbErtekpapirok.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!selectionModel.isSelectionEmpty()) {
                    btUpdate.setEnabled(true);
                    btDel.setEnabled(true);
                    int i = selectionModel.getMinSelectionIndex();
                    txtTicker.setText(tbErtekpapirok.getValueAt(i, 1).toString());
                    txtCompanyName.setText(tbErtekpapirok.getValueAt(i, 2).toString());
                    txtISINcode.setText(tbErtekpapirok.getValueAt(i, 5).toString());
                    cbxExchange.setSelectedItem(tbErtekpapirok.getValueAt(i, 3));
                    cbxType.setSelectedItem(tbErtekpapirok.getValueAt(i, 4));
                    cbxCurrency.setSelectedItem(tbErtekpapirok.getValueAt(i, 6));
                } else {
                    btUpdate.setEnabled(false);
                    btDel.setEnabled(false);
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

        jLabel6 = new javax.swing.JLabel();
        txtTicker = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCompanyName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbxExchange = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbxType = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtISINcode = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbxCurrency = new javax.swing.JComboBox<>();
        btAdd = new javax.swing.JButton();
        btUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbErtekpapirok = new javax.swing.JTable();
        btDel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jLabel6.setText("Rövid név (Ticker)");

        txtTicker.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setText("Vállalat neve");

        txtCompanyName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCompanyName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompanyNameActionPerformed(evt);
            }
        });

        jLabel7.setText("Tőzsde");

        jLabel9.setText("Típus");

        jLabel11.setText("ISIN kód");

        txtISINcode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtISINcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtISINcodeActionPerformed(evt);
            }
        });

        jLabel10.setText("Pénznem");

        cbxCurrency.setToolTipText("");

        btAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/013-add.png"))); // NOI18N
        btAdd.setMnemonic('o');
        btAdd.setToolTipText("Hozzáad");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        btUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/010-draw.png"))); // NOI18N
        btUpdate.setMnemonic('m');
        btUpdate.setToolTipText("Módosít");
        btUpdate.setEnabled(false);
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });

        tbErtekpapirok.setAutoCreateRowSorter(true);
        tbErtekpapirok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Értékpapír ID", "TICKER", "Vállalat neve", "Tőzsde", "Típus", "ISIN", "Pénznem"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbErtekpapirok);
        if (tbErtekpapirok.getColumnModel().getColumnCount() > 0) {
            tbErtekpapirok.getColumnModel().getColumn(0).setMinWidth(0);
            tbErtekpapirok.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbErtekpapirok.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/012-delete.png"))); // NOI18N
        btDel.setToolTipText("Töröl");
        btDel.setEnabled(false);
        btDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDelActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Értékpapírok");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCompanyName, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                    .addComponent(txtTicker)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbxType, javax.swing.GroupLayout.Alignment.LEADING, 0, 100, Short.MAX_VALUE)
                                    .addComponent(txtISINcode, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxCurrency, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxExchange, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btDel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAdd, btDel, btUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbxExchange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtISINcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbxCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAdd)
                            .addComponent(btUpdate)
                            .addComponent(btDel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        if (txtTicker.getText().isEmpty()) return;
        int id = Integer.parseInt(tbErtekpapirok.getValueAt(selectionModel.getMinSelectionIndex(), 0).toString());
        String[] s = {txtTicker.getText(),
                    txtCompanyName.getText(),
                    Config.getID(Tozsdek.tbTozsdek, cbxExchange.getSelectedItem()),
                    Config.getID(Tipusok.tbTipusok, cbxType.getSelectedItem()),
                    txtISINcode.getText(),
                    Config.getID(Penznemek.tbPenznemek, cbxCurrency.getSelectedItem())                       
        };
        DB db = new DB();
        db.sqlModositErtekpapir(id, s);
        frissites();     
    }//GEN-LAST:event_btUpdateActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        if (txtTicker.getText().isEmpty()) return;
        
        String[] s = {txtTicker.getText(),
                    txtCompanyName.getText(),
                    Config.getID(Tozsdek.tbTozsdek, cbxExchange.getSelectedItem()),
                    Config.getID(Tipusok.tbTipusok, cbxType.getSelectedItem()),
                    txtISINcode.getText(),
                    Config.getID(Penznemek.tbPenznemek, cbxCurrency.getSelectedItem())                       
        };
        DB db = new DB();
        db.sqlUjErtekpapir(s);
        frissites();     
    }//GEN-LAST:event_btAddActionPerformed

    private void txtCompanyNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompanyNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyNameActionPerformed

    private void txtISINcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtISINcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtISINcodeActionPerformed

    private void btDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDelActionPerformed
        int id = Integer.parseInt(tbErtekpapirok.getValueAt(selectionModel.getMinSelectionIndex(), 0).toString());
        DB db = new DB();
        db.sqlTorol("ertekpapirok", id);
        frissites();
    }//GEN-LAST:event_btDelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btDel;
    private javax.swing.JButton btUpdate;
    private javax.swing.JComboBox<String> cbxCurrency;
    private javax.swing.JComboBox<String> cbxExchange;
    private javax.swing.JComboBox<String> cbxType;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tbErtekpapirok;
    private javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtISINcode;
    private javax.swing.JTextField txtTicker;
    // End of variables declaration//GEN-END:variables
}
