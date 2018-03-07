/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShareManager;

import javax.swing.JComboBox;

/**
 *
 * @author opossum
 * 
 * alap beállítások
 * 
 * közös metódusok pl adat ellenőrzők
 */
public class Config {
    final static String DBURL = "jdbc:mysql://localhost/shareman?useUnicode=true&characterEncoding=UTF-8";
    final static String DBNAME = "shareman";
    final static String USER = "root";
    final static String PASS = "";
    static boolean FullScreen = false;  //teljes kpernyőssé teszi a ShareManager-t
    
    
    //Adatbázis tábláinak eredeti és a programban megjelenő (ékezetes) nevi
    final static String[] tablesNameSQL = {"ertekpapir_tipusok", "ertekpapirok", "penznemek", "szamlak", "tozsdek", "ugyletek"};
    final static String[] tablesNameForm = {"Értékpapír típusok", "Értékpapírok", "Devizák", "Számlák", "Tőzsdék", "Ügyletek"};
    
    //Táblák eredeti és a programban megjelenő mezőnevei
    final static String[][] fieldsNameSQLQuery = {{"id", "nev"},                    //értékpapír típusok
        {"ertekpapirok.id", "ertekpapirok.nev", "ertekpapirok.ceg_nev", "tozsdek.nev", "ertekpapir_tipusok.nev", "isin_kod", "penznemek.nev"},  //értékpapírok
        {"id", "nev", "teljes_nev"},                                           //pénznemek
        {"id", "nev"},                                                         //számlák
        {"id", "nev", "teljes_nev"},                                           //tozsdek
        {}};                                                        //ügyletek
    final static String[][] fieldsNameSQLUpdate = {{"id", "nev"},                    //értékpapír típusok
        {"id", "nev", "ceg_nev", "tozsdeID", "tipusID", "isin_kod", "penznemID"},  //értékpapírok
        {"id", "nev", "teljes_nev"},                                           //pénznemek
        {"id", "nev"},                                                         //számlák
        {"id", "nev", "teljes_nev"},                                           //tozsdek
        {}};                                                        //ügyletek
    final static String[][] fieldsNameForm = {{"Az", "Név"},
        {"Az", "Név", "Cég név", "Tőzsde", "Típus", "ISIN kód", "Deviza"},
        {"Az", "Név", "Teljes név"},
        {"Az", "Név"},
        {"Az", "Név", "Teljes név"},
        {}}; //ügylet
    final static String[] joins = {"",                    //értékpapír típusok
        " JOIN tozsdek ON ertekpapirok.tozsdeID=tozsdek.id JOIN ertekpapir_tipusok ON ertekpapirok.tipusID=ertekpapir_tipusok.id JOIN penznemek ON ertekpapirok.penznemID=penznemek.id",  //értékpapírok
        "",                                           //pénznemek
        "",                                                         //számlák
        "",                                           //tozsdek
        ""}; //ügyletek
    
    
    public static boolean checkDate(String date) throws ClassCastException { 
    //ellenőrzi a megadott dátum formátumát és érvényességét 1900-2050
    //elfogadható formátumok 2017/03/03  2017.03.03  2017-03-03 20170303

        String c = ""; //split character
        int year, month, day;
        if (date.length()<6) return false;
        if (date.indexOf('-')>=0) c = "-";
        else if (date.indexOf('.')>=0) c = ".";
        else if (date.indexOf('/')>=0) c = "/";
        try {
            if (!c.equals("")) {
                String[] d = date.split(c);
                year = Integer.parseInt(d[0]);
                month = Integer.parseInt(d[1]);
                day = Integer.parseInt(d[2]);
            } else {
                year = Integer.parseInt(date.substring(0, 4));
                month = Integer.parseInt(date.substring(4, 6));
                day = Integer.parseInt(date.substring(6));
            }
            if (year>1900 && year<2050 && month>0 && month<13 && day>0 && day<32) return true;
            else return false;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {return false;}
    }
    
    /**
     * A paraméterben megadott combobox-ot feltölti a paraméterben megadott String tömb elemeivel
     * @param cbx - JComboBox 
     * @param s - String[]
     */
    public static void addItemToComboBox(JComboBox cbx, String[] s) {
        for (String item : s) {
            cbx.addItem(item);        
        }
    }
    
    public static void main(String[] args) {
        Config c = new Config();
       
        
    }
}

