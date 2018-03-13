/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShareManager;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 * @author opossum
 */
public class Config {
    final static String DBURL = "jdbc:mysql://localhost/shareman?useUnicode=true&characterEncoding=UTF-8";
    final static String DBNAME = "shareman";
    final static String USER = "root";
    final static String PASS = "";
    static boolean FullScreen = false;  //teljes kpernyőssé teszi a ShareManager-t    
    
    /**
     * A paraméterben megadott combobox-ot feltölti a paraméterben megadott JTable elemeivel
     * @param cbx - JComboBox 
     * @param t - JTable 
     */
    public static void addItemToComboBox(JComboBox cbx, JTable t) {
        cbx.removeAllItems();
        for (int i=0; i<t.getRowCount(); i++) {
            cbx.addItem(t.getValueAt(i, 1));
        }  
    }
    
     /**
     * A combobox-ot feltölti a JTable c oszlop elemeivel és a "Mind" elemmel
     * @param cbx - JComboBox 
     * @param t - JTable 
     * @param c - int - oszlop index 
     */
    public static void addColumnAndAllToCBX(JComboBox cbx, JTable t, int c) {
        cbx.removeAllItems();
        cbx.addItem("Mind");
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<t.getRowCount(); i++) {
            list.add(t.getValueAt(i, c).toString());
        }
        Collections.sort(list);
        int i = 0;
        cbx.addItem(list.get(0));
        while (i<list.size()-1){
            if (list.get(i).equals(list.get(i+1))) list.remove(i+1);
            else {
                cbx.addItem(list.get(i+1));
                i++;
            }
        }
    }
    
    /**
     * A combobox-ot feltölti a paraméterben megadott JTable elemeivel és a "Mind" elemmel
     * @param cbx - JComboBox 
     * @param s - String[] 
     */
    public static void addItemAndAllToComboBoxFromString(JComboBox cbx, String[] s) {
        cbx.removeAllItems();
        cbx.addItem("Mind");
        for (int i=0; i<s.length; i++) {
            cbx.addItem(s[i]);
        }  
        cbx.setSelectedIndex(0);
    }
    
    /**
     * A paraméterben megadott combobox-ot feltölti a JTable azon elemeivel,
     * amelyek megfelelnek a szűrőnek
     * @param cbx - JComboBox 
     * @param t - JTable 
     * @param filter - String
     */
    public static void addItemToComboBox(JComboBox cbx, JTable t, Object filter) {
        cbx.removeAllItems();
        for (int i=0; i<t.getRowCount(); i++) {
            if (t.getValueAt(i, 3).equals(filter))
                cbx.addItem(t.getValueAt(i, 1));
        }  
    }
    
    /**
     * A megadott JTable-ben megkeresi a megadott Object-et és
     * annak ID-jét (0.oszlopát) adja eredményül. Ha nincs találat -1.
     * @param t - JTable
     * @param o - Object
     * @return - String
     */
    public static String getID(JTable t, Object o){
        String s = "-1";
        for (int i=0; i<t.getRowCount(); i++) {
            if (t.getValueAt(i, 1).equals(o)){
                s= t.getValueAt(i, 0).toString();
                break;
            }
        }
        return s;
    }
    
    /**
     * A megadott JTable-ben megkeresi a megadott Object-et és
     * annak a rekordnak egy megadott elemét adja eredményül.
     * Ha nincs találat -1.
     * @param t - JTable - tábla
     * @param o - Object - keresett elem
     * @param c - int - oszlop
     * @return - String
     */
    public static String getObject(JTable t, Object o, int c){
        String s = "-1";
        for (int i=0; i<t.getRowCount(); i++) {
            if (t.getValueAt(i, 1).equals(o)){
                s= t.getValueAt(i, c).toString();
                break;
            }
        }
        return s;
    }
    
    public static void main(String[] args) {
        Config c = new Config();
       
        
    }
}

