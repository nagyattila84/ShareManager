/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import ShareManager.DB;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author atna
 */
public class Szamlak extends Panel {
    ListSelectionModel selectionModel;

    /**
     * Creates new form Penznemek
     */
    public Szamlak() {
        initComponents();
        frissites();
    }
    
    public void frissites(){
        DB db = new DB();
        db.sqlLekerdezSzamlak(tbSzamlak);
        setTableListener();
    }
    
    private void setTableListener(){
        selectionModel = tbSzamlak.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!selectionModel.isSelectionEmpty()) {
                    btUpdate.setEnabled(true);
                    btDel.setEnabled(true);
                    int i = selectionModel.getMinSelectionIndex();
                    txtRovidNev.setText(tbSzamlak.getValueAt(i, 1).toString());
                    if (Integer.parseInt(tbSzamlak.getValueAt(i, 2).toString())==0)
                        chbTax.setSelected(false);
                    else if (Integer.parseInt(tbSzamlak.getValueAt(i, 2).toString())==1)
                        chbTax.setSelected(true);
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSzamlak = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtRovidNev = new javax.swing.JTextField();
        btAdd = new javax.swing.JButton();
        btUpdate = new javax.swing.JButton();
        btDel = new javax.swing.JButton();
        chbTax = new javax.swing.JCheckBox();

        setPreferredSize(new java.awt.Dimension(540, 480));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Számlák");

        tbSzamlak.setAutoCreateRowSorter(true);
        tbSzamlak.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Pénznem ID", "Típus név", "Adómentes"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbSzamlak);
        if (tbSzamlak.getColumnModel().getColumnCount() > 0) {
            tbSzamlak.getColumnModel().getColumn(0).setMinWidth(0);
            tbSzamlak.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbSzamlak.getColumnModel().getColumn(0).setMaxWidth(0);
            tbSzamlak.getColumnModel().getColumn(2).setPreferredWidth(40);
        }

        jLabel2.setText("Számla név");

        txtRovidNev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRovidNevActionPerformed(evt);
            }
        });

        btAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/013-add.png"))); // NOI18N
        btAdd.setToolTipText("Hozzáad");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        btUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/010-draw.png"))); // NOI18N
        btUpdate.setToolTipText("Módosít");
        btUpdate.setEnabled(false);
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });

        btDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/012-delete.png"))); // NOI18N
        btDel.setToolTipText("Töröl");
        btDel.setEnabled(false);
        btDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDelActionPerformed(evt);
            }
        });

        chbTax.setText("Adómentes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btAdd)
                                    .addComponent(jLabel2))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(btUpdate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btDel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtRovidNev))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(chbTax)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAdd, btDel, btUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtRovidNev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(chbTax)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAdd)
                            .addComponent(btUpdate)
                            .addComponent(btDel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtRovidNevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRovidNevActionPerformed
        
        
    }//GEN-LAST:event_txtRovidNevActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        if (txtRovidNev.getText().isEmpty()) return;
        String b = "0";
        if (chbTax.isSelected()) b = "1";
        DB db = new DB();
        db.sqlUjSzamla(txtRovidNev.getText(), b);
        frissites();
    }//GEN-LAST:event_btAddActionPerformed

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        if (txtRovidNev.getText().isEmpty()) return;
        int id = Integer.parseInt(tbSzamlak.getValueAt(selectionModel.getMinSelectionIndex(), 0).toString());
        String b = "0";
        if (chbTax.isSelected()) b = "1";
        DB db = new DB();
        db.sqlModositSzamla(id, txtRovidNev.getText(), b);
        frissites();
    }//GEN-LAST:event_btUpdateActionPerformed

    private void btDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDelActionPerformed
        int id = Integer.parseInt(tbSzamlak.getValueAt(selectionModel.getMinSelectionIndex(), 0).toString());
        DB db = new DB();
        db.sqlTorol("szamlak", id);
        frissites();
    }//GEN-LAST:event_btDelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btDel;
    private javax.swing.JButton btUpdate;
    private javax.swing.JCheckBox chbTax;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tbSzamlak;
    private javax.swing.JTextField txtRovidNev;
    // End of variables declaration//GEN-END:variables
}
