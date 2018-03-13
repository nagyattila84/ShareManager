/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShareManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author opossum
 */
public class DB {
    
    private String levag(String s, int i){
        s = s.trim();
        s = s.substring(0, i);
        return s;
    }
    
    /**
    * A tőzsdék táblából beolvassa az adatokat
    * @param table - JTable - a JTable, ahová betölti az adatokat
    */
    public void sqlLekerdezTozsdek(JTable table) {
        DefaultTableModel tm = (DefaultTableModel)table.getModel();
        
        String s = "SELECT * FROM tozsdek";
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()){
            tm.setRowCount(0);
            while (eredmeny.next()) {
                String[] sor = {eredmeny.getString("tozsde_id"), eredmeny.getString("nev"),  eredmeny.getString("teljes_nev")};
                tm.addRow(sor);
            }
             
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        } 
    }
    /**
     * Egy új tőzsdét ad a tőzsdék táblához
     * @param s1 - String - a rövid név
     * @param s2 - String - a teljes név
     */
    public void sqlUjTozsde(String s1, String s2) {
        
        String s = "INSERT INTO tozsdek VALUES (?,?,?);";
        
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, null);                 
            ekpar.setString(2, s1);                   
            ekpar.setString(3, s2);                   
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    /**
    * A kijelölt pénznem adatait módosítja
    * @param id - int - a pénznem id-ja
    * @param s1 - String - rövid név
    * @param s2 - String - teljes név
    */
    public void sqlModositTozsde(int id, String s1, String s2) {
        
        String s = "UPDATE tozsdek SET nev = ?, teljes_nev = ? WHERE tozsde_id = "+id;
        
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, s1);
            ekpar.setString(2, s2);
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * A típusok táblából beolvassa az adatokat
     * @param table - JTable - a JTable, ahová betölti az adatokat
     */
    public void sqlLekerdezTipusok(JTable table) {
        DefaultTableModel tm = (DefaultTableModel)table.getModel();
        
        String s = "SELECT * FROM tipusok";
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()){
            tm.setRowCount(0);
            while (eredmeny.next()) {
                String[] sor = {eredmeny.getString("tipus_id"),
                                eredmeny.getString("nev")};
                tm.addRow(sor);
            }  
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        } 
    }
    /**
    * Egy új típust ad a típusok táblához
    * @param s1 - String - a rövid név
    */
    public void sqlUjTipus(String s1) {
        String s = "INSERT INTO tipusok VALUES (?,?);";
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, null);                 
            ekpar.setString(2, s1);                 
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    /**
     * A kijelölt típus adatait módosítja
     * @param id - int - a pénznem id-ja
     * @param s1 - String - rövid név
     */
    public void sqlModositTipus(int id, String s1) {
        String s = "UPDATE tipusok SET nev = ? WHERE tipus_id = "+id;
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, s1);
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * A pénznemek táblából beolvassa az adatokat
     * @param table - JTable - a JTable, ahová betölti az adatokat
     */
    public void sqlLekerdezPenznemek(JTable table) {
        DefaultTableModel tm = (DefaultTableModel)table.getModel();
       
        String s = "SELECT * FROM penznemek";
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()){
            tm.setRowCount(0);
            while (eredmeny.next()) {
                String[] sor = {eredmeny.getString("penznem_id"), eredmeny.getString("nev"),  eredmeny.getString("teljes_nev")};
                tm.addRow(sor);
            }
             
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        } 
    }
    /**
    * Egy új pénznemet ad a pénznemek táblához
    * @param s1 - String - a rövid név
    * @param s2 - String - a teljes név
    */
    public void sqlUjPenznem(String s1, String s2) {
        
        String s = "INSERT INTO penznemek VALUES (?,?,?);";
        
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, null);                 
            ekpar.setString(2, s1);                   
            ekpar.setString(3, s2);                   
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    /**
     * A kijelölt pénznem adatait módosítja
     * @param id - int - a pénznem id-ja
     * @param s1 - String - rövid név
     * @param s2 - String - teljes név
     */
    public void sqlModositPenznem(int id, String s1, String s2) {
        
        String s = "UPDATE penznemek SET nev = ?, teljes_nev = ? WHERE penznem_id = "+id;
        
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, s1);
            ekpar.setString(2, s2);
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    
    
    /**
     * A számlák táblából beolvassa az adatokat
     * @param table - JTable - a JTable, ahová betölti az adatokat
     */
    public void sqlLekerdezSzamlak(JTable table) {
        DefaultTableModel tm = (DefaultTableModel)table.getModel();
        
        String s = "SELECT * FROM szamlak";
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()){
            tm.setRowCount(0);
            while (eredmeny.next()) {
                String[] sor = {eredmeny.getString("szamla_id"),
                                eredmeny.getString("nev"),
                                eredmeny.getString("adomentes")};
                tm.addRow(sor);
            }  
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        } 
    }
    /**
     * Egy új számlát ad a számlák táblához
     * @param s1 - String - a rövid név
     * @param s2 - String - adómentes-e 0-nem, 1-igen
     */
    public void sqlUjSzamla(String s1, String s2) {
        String s = "INSERT INTO szamlak VALUES (?,?,?);";
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, null);                 
            ekpar.setString(2, s1);                 
            ekpar.setString(3, s2);                 
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    /**
    * A kijelölt számla adatait módosítja
    * @param id - int - a pénznem id-ja
    * @param s1 - String - rövid név
    * @param s2 - String - adómentes, 0-nem, 1-igen
    */
    public void sqlModositSzamla(int id, String s1, String s2) {
        String s = "UPDATE szamlak SET nev = ?, adomentes = ? WHERE szamla_id = "+id;
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(s);            
            ekpar.setString(1, s1);
            ekpar.setString(2, s2);
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Az értékpapírok táblából beolvassa az adatokat
     * @param table - JTable - a JTable, ahová betölti az adatokat
     */
    public void sqlLekerdezErtekpapirok(JTable table) {
        DefaultTableModel tm = (DefaultTableModel)table.getModel();
        
        String s = "SELECT ertekpapir_id, ticker, ceg_nev, tozsdek.nev, tipusok.nev, isin, penznemek.nev FROM ertekpapirok " +
                "JOIN tozsdek ON ertekpapirok.tozsde_id=tozsdek.tozsde_id " +
                "JOIN tipusok ON ertekpapirok.tipus_id=tipusok.tipus_id " +
                "JOIN penznemek ON ertekpapirok.penznem_id=penznemek.penznem_id";
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()){
            tm.setRowCount(0);
            while (eredmeny.next()) {
                String[] sor = {eredmeny.getString("ertekpapir_id"),
                                eredmeny.getString("ticker"),  
                                eredmeny.getString("ceg_nev"),
                                eredmeny.getString("tozsdek.nev"),
                                eredmeny.getString("tipusok.nev"),
                                eredmeny.getString("isin"),
                                eredmeny.getString("penznemek.nev")};
                tm.addRow(sor);
            }
             
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        } 
    }
    /**
     * Egy új értékpapírt ad az értékpapírok táblához
     * @param s
     */
    public void sqlUjErtekpapir(String[] s) {
        
        String p = "INSERT INTO ertekpapirok VALUES (?,?,?,?,?,?,?);";
        
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(p);            
            ekpar.setString(1, null);     
            for (int i=0; i<6; i++) {
                ekpar.setString(i+2, s[i]);                   
            }              
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(p);
            System.out.println(ex.getMessage());
        }
    }
    /**
    * A kijelölt értékpapír adatait módosítja
    * @param id - int - az értékpapír id-ja
    * @param s - String[]
    */
    public void sqlModositErtekpapir(int id, String s[]) {
        String p = "UPDATE ertekpapirok SET ticker = ?, ceg_nev = ?, tozsde_id = ?, tipus_id = ?, "+
                "isin = ?, penznem_id = ? WHERE ertekpapir_id = "+id;
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(p);            
            for (int i=0; i<6; i++) {
                ekpar.setString(i+1, s[i]);
            }
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(p);
            System.out.println(ex.getMessage());
        }
    }
    

    /**
     * Az ügyletek táblából beolvassa az adatokat
     * @param table - JTable - a JTable, ahová betölti az adatokat
     */
    public void sqlLekerdezUgyletek(JTable table) {
        DefaultTableModel tm = (DefaultTableModel)table.getModel();
        
        String s = "SELECT ugylet_id, ertekpapirok.ticker, szamlak.nev, mennyiseg, arfolyam, datum, jutalek, dev_arfolyam FROM ugyletek " +
                "JOIN ertekpapirok ON ugyletek.ertekpapir_id=ertekpapirok.ertekpapir_id " +
                "JOIN szamlak ON ugyletek.szamla_id=szamlak.szamla_id";
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()){
            tm.setRowCount(0);
            while (eredmeny.next()) {
                String[] sor = {eredmeny.getString("ugylet_id"),
                                eredmeny.getString("ertekpapirok.ticker"),  
                                eredmeny.getString("szamlak.nev"),
                                eredmeny.getString("mennyiseg"),
                                eredmeny.getString("arfolyam"),
                                eredmeny.getString("datum"),
                                eredmeny.getString("jutalek"),
                                eredmeny.getString("dev_arfolyam")};
                tm.addRow(sor);
            }
             
        } catch (SQLException ex) {
            System.out.println(s);
            System.out.println(ex.getMessage());
        } 
    }
    /**
     * Egy új ügyletet ad az ügyletek táblához
     * @param s
     */
    public void sqlUjUgylet(String[] s) {
        
        String p = "INSERT INTO ugyletek VALUES (?,?,?,?,?,?,?,?);";
        
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(p);            
            ekpar.setString(1, null);            
            for (int i=0; i<7; i++) {
                ekpar.setString(i+2, s[i]);                   
            }              
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(p);
            System.out.println(ex.getMessage());
        }
    }
    /**
    * A kijelölt ügylet adatait módosítja
    * @param id - int - az ügylet id-ja
    * @param s - String[]
    */
    public void sqlModositUgylet(int id, String s[]) {
        String p = "UPDATE ugyletek SET ugyletek.ertekpapir_id = ?, ugyletek.szamla_id = ?, mennyiseg = ?, arfolyam = ?, "+
                "datum = ?, jutalek = ?, dev_arfolyam = ? WHERE ugylet_id = "+id;
        try (java.sql.Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS)) {
            PreparedStatement ekpar = kapcs.prepareStatement(p);            
            for (int i=0; i<7; i++) {
                ekpar.setString(i+1, s[i]);
            }
            ekpar.executeUpdate(); //ha egyet ad vissza akkor sikerül
        } catch (SQLException ex) {
            System.out.println(p);
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Az ügyletek táblából beolvassa az adatokat a kimutatáshoz, a paraméterben megadott szűrők alkalmazásával
     * @param table - JTable - a JTable, ahová betölti az adatokat
     * @param account - String - a számla (Mind esetén nem szűr rá)
     * @param share - String - a értékpapír (Mind esetén nem szűr rá)
     */
    public void sqlLekerdezKimutatas(JTable table, String account, String share) {
        DefaultTableModel tm = (DefaultTableModel)table.getModel();
        
        String s = "SELECT ugylet_id, ertekpapirok.ticker, szamlak.nev, mennyiseg, arfolyam, datum, jutalek, dev_arfolyam FROM ugyletek " +
                "JOIN ertekpapirok ON ugyletek.ertekpapir_id=ertekpapirok.ertekpapir_id " +
                "JOIN szamlak ON ugyletek.szamla_id=szamlak.szamla_id";
        
        if (!account.equals("Mind")) {
            s += " WHERE szamlak.nev = '" + account + "'";
        }
        
        if (!share.equals("Mind")) {
            s += " WHERE ertekpapirok.ticker = '" + share + "'";
        }
         
        try (Connection kapcs = DriverManager.getConnection(Config.DBURL, Config.USER, Config.PASS);
                PreparedStatement parancs = kapcs.prepareStatement(s);
                ResultSet eredmeny = parancs.executeQuery()){
            tm.setRowCount(0);
            while (eredmeny.next()) {
                String[] sor = {eredmeny.getString("ugylet_id"),
                                eredmeny.getString("szamlak.nev"),
                                eredmeny.getString("datum"),
                                eredmeny.getString("ertekpapirok.ticker"),  
                                eredmeny.getString("mennyiseg"),
                                eredmeny.getString("arfolyam"),
                                eredmeny.getString("jutalek"),
                                eredmeny.getString("dev_arfolyam")};
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
        String t;
        if (sqlTable.equals("szamlak") || sqlTable.equals("tozsdek")) t =sqlTable.substring(0, sqlTable.length()-1);
        else t = sqlTable.substring(0,sqlTable.length()-2);
        String s = "DELETE FROM " + sqlTable +" WHERE " + t + "_id = '" + id + "'";
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
}
