/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShareManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author opossum
 */
public class DB {
    
    /**
     * Az SQL adatbázisban szereplő táblák vagy egy tábla mezőinek a neveit adja vissza
     * @param x: 0 = a táblák neve (ebben az esetben a table paraméter tetszőleges)
     *           1 = a tábla mezőinek a neve
     * @param table: x=1 esetén a tábla neve, amelynek a mező neveit akarjuk lekérdezni
     * @return egy String tömböt ad eredményül a táblák nevével
     */
    public String[] sqlTablakVagyMezokNeve(int x, String table) {
        String lekerdezoParancs = "";
        if (x==1) {
            lekerdezoParancs = "SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` " +
                "WHERE `TABLE_SCHEMA`='" + Config.DBNAME + "' AND `TABLE_NAME`='" + table + "';";
        } else if (x==0) {
            lekerdezoParancs = "SHOW TABLES FROM " + Config.DBNAME;
        }
             
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(lekerdezoParancs);
                ResultSet eredmeny = parancs.executeQuery()){
            ArrayList<String> sList = new ArrayList<>();
            int i = 0;
            while (eredmeny.next()) {
                String sor = eredmeny.getString(1);
                sList.add(sor);
            }
            if (sList==null) return null; //ha nincs eredménye a lekérdezésnek null értéket ad és befejezi a metódust
            String[] s = new String[sList.size()];
            for (int j=0; j<sList.size(); j++) {
                s[j]= sList.get(j);
            }
            return s;
             
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        return null;
    }
    
    /**
     * Az adatbázis (Config.BDName) tábláinak a nevét adja eredményül
     * @return - String[] 
     */
    public String[] sqlTablakNeve(){
        return sqlTablakVagyMezokNeve(0, "");
    }
    
    /**
     * A megadott tábla mezőinek a nevét adja eredményül
     * @param s String - a tábla neve
     * @return - String[]
     */
    public String[] sqlMezokNeve(String s){
        return sqlTablakVagyMezokNeve(1, s);
    }
    
    /**
     * A megadot paraméterek szerint elkészíti az SQL paracsot és végrehajtja azt
     * @param tabla - Az SQL tábla neve ahova az adatokat rögzíti
     * @param mezok - A rekord változói. Az azonosítót magától adja. A többi adatot egy String tömbben kell megadni.
     */
    public void sqlInsertRow(String tabla, String[] mezok) {
        
        String s = "INSERT INTO " + tabla + " VALUES (?";       //az id helye
        for (int i=0; i<mezok.length; i++) {                  //a String tömb helyei
            s+=",?";
        }
        s+=");";                                                //lezárás
        
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, null);                   //az id
            for (int i=0; i<mezok.length; i++) {    //String tömb
                ekpar.setString(i+2, mezok[i]);
            }
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * A megadot paraméterek szerint elkészíti az SQL paracsot és végrehajtja azt
     * @param tabla - int - Az SQL tábla sorszáma, ahova az adatokat rögzíti
     * @param id - int - A módosítandó rekord azonosítója
     * @param data - String[] - Az új adatok
     */
    public void sqlUpdateRow(int tabla, int id, String[] data) {
        
        String[] columnsSQLName = Config.fieldsNameSQLUpdate[tabla];
        String s = "UPDATE "+Config.tablesNameSQL[tabla]+" SET nev = ?";
        for (int i=1; i<data.length; i++) { 
            s+= ", " + columnsSQLName[i+1] + " = ? ";
        }
        s+=" WHERE " +Config.tablesNameSQL[tabla]+".id ="+id;
        
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            //ekpar.setString(1, null);                   //az id
            for (int i=0; i<data.length; i++) {    //String tömb
                ekpar.setString(i+1, data[i]);
            }
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * A megadott táblából a keresett érték azonosító számát adja eredményül
     * @param table - int - az SQL tábla sorszáma
     * @param s - String - a keresett érték (szöveg)
     * @return - String - a keresett érték azonosító száma
     */
    public String sqlBeolvasMezot(int table, String s){
        String lekerdezoParancs = "SELECT id FROM " + Config.tablesNameSQL[table] + " WHERE nev = '" + s + "'";
        String er = "";
        
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(lekerdezoParancs);
                ResultSet eredmeny = parancs.executeQuery()){
            while (eredmeny.next()) {
                er = eredmeny.getString(1);
            }
            return er;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }
    
    /**
     * Az adott tábla első oszlopát adja eredményül egy String típusú tömbben 
     * @param tabla a beolvasandó tábla
     * @return 
     */
    public String[] sqlBeolvasOszlopot(String tabla) {
        
        String lekerdezoParancs = "SELECT nev FROM " + tabla;
        ArrayList<String> sList = new ArrayList<>();
        int i = 0;
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(lekerdezoParancs);
                ResultSet eredmeny = parancs.executeQuery()){
            while (eredmeny.next()) {
                String sor = 
                    eredmeny.getString(1)
//                    eredmeny.getString("ido"),
//                    eredmeny.getString("nev"),
//                    eredmeny.getString("allapot"),
//                    eredmeny.getString("osztaly"),
//                    eredmeny.getString("iskola"),
                ;
                sList.add(sor);
            }
            if (sList==null) return null; //ha nincs eredménye a lekérdezésnek null értéket ad és befejezi a metódust
            String[] s = new String[sList.size()];
            for (int j=0; j<sList.size(); j++) {
                s[j]= sList.get(j);
            }
            return s;
             
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return null;
    }

    
    /**
     * A megadott SQL mezőket lekéri egy megadott JAVA JTable-be
     * @param table - JTable - a JTable, ahová betölti az adatokat
     * @param currentTable - int - a tábla sorszáma
     */
    public void sqlBeolvasTablat(JTable table, int currentTable) {
        DefaultTableModel tm = (DefaultTableModel)table.getModel();
        String[] param = Config.fieldsNameSQLQuery[currentTable];
        tm.setColumnCount(param.length);
        tm.setColumnIdentifiers(param);
        
        String s = "SELECT " + param[0];
        for (int i=1; i<param.length; i++) {
            s+= ", " + param[i] ;
        }
        s+= " FROM " + Config.tablesNameSQL[currentTable];
        s+= Config.joins[currentTable];
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()){
            tm.setRowCount(0);
            Object[] sor = new Object[param.length];
            while (eredmeny.next()) {
                for (int i=0; i<param.length; i++) {
                    sor[i] = eredmeny.getString(param[i]);
                };
                tm.addRow(sor);
            }
             
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        } 
    }    
    
    /**
     * A paraméterben megadott táblából törli a megadott azonosító számú sort
     * @param sqlTable String - a tábla neve
     * @param id int - a sor (rekord) azonosító száma
     */
    public void sqlTorol(String sqlTable, int id) {
        String s = "DELETE FROM " + sqlTable +" WHERE " + sqlTable + ".id = '" + id + "'";
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s)) {
            parancs.executeUpdate(s);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    } 
        
//    private String lekerdez(){
//        String q = "";
//        if (!txtGepszuro.getText().isEmpty()) {
//            q += " felhasznalo LIKE '" + txtGepszuro.getText() + "' AND";
//        }
//        switch (cbxIdo.getSelectedIndex()) {
//            case 0: //ezen az órán
//                q += " TIMEDIFF(NOW(),ido)<'00:45' AND";
//                break;
//            case 1: //ma
//                q += " DATE(ido)=DATE(NOW()) AND";
//                break;
//            case 2: // 7 napja
//                q += " DATEDIFF(NOW(), ido)<=7 AND";
//                break;
//            case 3: // 30 napja
//                q += " DATEDIFF(NOW(), ido)<=30 AND";
//                break;
//        }
//        if (!txtNev.getText().isEmpty()) {
//            q += " nev LIKE '" + txtNev.getText() + "' AND";
//        }
//        if (chkProb.isSelected()) {
//            q += " allapot NOT LIKE 'Rendben%'";
//        } else {
//            q += " allapot LIKE '%'";
//        }
//        return "SELECT felhasznalo, iskola, osztaly, nev, ido, allapot "
//                + "FROM gepek WHERE" + q + " ORDER BY ido DESC";
//    }
    
    public static void main(String[] args) throws IOException {
        DB db = new DB();
        System.out.println(db.sqlBeolvasMezot(4, "BET"));
    }
}
