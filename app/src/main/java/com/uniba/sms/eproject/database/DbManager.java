package com.uniba.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Museo;
import com.uniba.sms.eproject.data.classes.Oggetto;
import com.uniba.sms.eproject.data.classes.Zona;

import java.util.ArrayList;
import java.util.HashMap;

public class DbManager {
    MyHelper helper=null;
    private final static String DATABASE="ProgettoSMS.db";
    private final static int VERSIONE_DATABASE=1;

    public DbManager(Context context)      {
        helper=new MyHelper(context, DATABASE,  null, VERSIONE_DATABASE);
    }

    /**
     * Visualizza tutti gli utenti registrati
     *
     * @return
     */
    @Autore(autore = "Mattia, Giandomenico")
    public Cursor elencoUtenti()      {
        String query="SELECT * FROM Utente_Registrato";
        SQLiteDatabase db= helper.getReadableDatabase();

        return db.rawQuery(query, null);
    }

    /**
     * Registra un nuovo utente
     *
     * @param nome
     * @param cognome
     * @param username
     * @param email
     * @param password
     * @param tipo
     * @return
     */
    @Autore(autore = "Mattia, Giandomenico")
    public boolean registrazione(String nome, String cognome, String username, String email, String password, int tipo){
        String insert1="INSERT INTO Utente_Registrato (ID, Nome, Cognome, Username, Email, Password, tipo) "
                + "VALUES (NULL,'"+nome+"','"+cognome+"', '"+username+"', '"+email+"', '"+password+"', "+tipo+")";
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            return false;
        }
    }

    /**
     * Effettua il login di un utente
     *
     * @param username  Nome utente
     * @param password  Password di login
     * @return HashMap<String, String>
     *     Restituisce un HashMap dove la chiave è il nome della colonna della tabella Utente_Registrato
     *     mentre il valore è il valore associato a quella colonna
     *
     *     ESEMPIO:
     *          ID => 1
     *          Nome => Mario
     *          Cognome => Rossi
     *          Email => mariorossi@email.com
     *          ....
     */
    @Autore(autore = "Mattia, Giandomenico")
    public HashMap<String, String> login(String username, String password){
        String query="SELECT * FROM Utente_Registrato WHERE email = '"+username+"' AND password = '"+password+"';";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        HashMap<String, String> utente = null;

        if (c.moveToFirst()){
            utente = new HashMap<>();

            do {

                utente.put("ID", c.getString(0));
                utente.put("Nome", c.getString(1));
                utente.put("Cognome", c.getString(2));
                utente.put("Username", c.getString(3));
                utente.put("Email", c.getString(4));
                utente.put("tipo", c.getString(6));

            } while(c.moveToNext());
        }

        c.close();

        return utente;
    }

    //////////////////////////// GESTIONE ZONE

    /**
     * Inserisce una nuova zona all'interno del database
     *
     * @param z Zona da inserire
     * @return
     */
    @Autore(autore = "Mattia")
    public boolean inserisciZona(Zona z){
        String insert1="INSERT INTO Zone (ID, Nome, Provincia, Regione, CAP) "
                + "VALUES (NULL," +
                "'"+z.getNome()+"','"+
                z.getProvincia()+"', '"+
                z.getRegione()+"', '"+
                z.getCAP()+"'"+
                ");";

        System.out.println("---->" + insert1);
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.out.println( ex.getMessage() );

            return false;
        }
    }

    /**
     * Seleziona tutte le zone presenti nel database
     *
     * @return HashMap<String, String>
     *     Restituisce un ArrayList contenenente un HashMap con le zone.
     *
     *     L'HashMap ha una coppia chiave/valore che rispettivamente
     *     sono il nome del campo e il suo valore
     *
     * @return
     */
    @Autore(autore = "Mattia")
    public ArrayList<HashMap<String, String>> visualizzaTutteLeZone(){
        String query="SELECT * FROM Zone";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<HashMap<String, String>> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                HashMap<String, String> hm = new HashMap<>();

                hm.put("ID", c.getString(0));
                hm.put("Nome", c.getString(1));
                hm.put("Provincia", c.getString(2));
                hm.put("Regione", c.getString(3));
                hm.put("CAP", c.getString(4));

                al.add(hm);

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }

    /**
     * Recupera tutte le regioni presenti all'interno delle zone
     *
     * @return
     */
    @Autore(autore = "Mattia")
    public ArrayList<HashMap<String, String>> visualizzaTutteLeRegioniDelleZone(){
        String query="SELECT Regione FROM Zone GROUP BY Regione";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<HashMap<String, String>> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                HashMap<String, String> hm = new HashMap<>();

                hm.put("Regione", c.getString(0));

                al.add(hm);

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }

    /////////////////////////////// GESTIONE OGGETTO

    /**
     * Inserisce un nuovo oggetto all'interno del database
     *
     * @param o Oggetto da inserire
     * @return
     */
    @Autore(autore = "Mattia")
    public boolean inserisciOggetto(Oggetto o){
        String insert1="INSERT INTO Oggetto (ID, Nome, Anno, Autore, Descrizione) "
                + "VALUES (NULL," +
                "'"+o.getNome()+"','"+
                o.getAnno()+"', '"+
                o.getAutore()+"', '"+
                o.getDescrizione()+"', '"+
                ");";

        System.out.println("---->" + insert1);
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.out.println( ex.getMessage() );

            return false;
        }
    }

    ///////////////////////////// GESTIONE MUSEO

    /**
     * Inserisce un nuovo museo all'interno del database
     *
     * @param m Museo da inserire
     * @return
     */
    @Autore(autore = "Mattia")
    public boolean inserisciMuseo(Museo m){
        String insert1="INSERT INTO Museo (ID, Nome, Numero_Telefono, Indirizzo, Citta, Provincia, CAP, Regione, Email_contatti, Sito_Web, Orario_Apertura, Immagine_Museo) "
                + "VALUES (NULL," +
                            "'"+m.getNome()+"','"+
                            m.getTelefono()+"', '"+
                            m.getIndirizzo()+"', '"+
                            m.getCitta()+"', '"+
                            m.getProvincia()+"', '"+
                            m.getCap()+"', '"+
                            m.getRegione()+"', '"+
                            m.getEmail()+"', '"+
                            m.getSito_web()+"', '"+
                            m.getOrario()+"', '"+
                            m.getImmagine()+"'"+
                            ");";

        System.out.println("---->" + insert1);
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.out.println( ex.getMessage() );

            return false;
        }
    }

    /**
     * Aggiorna un museo
     *
     * @param m Nuovi dati del museo da aggiornare
     * @return
     */
    @Autore(autore = "Mattia")
    public boolean aggiornaMuseo(Museo m){
        String insert1="UPDATE Museo SET " +
                "Nome = '" + m.getNome() + "', "+
                "Numero_Telefono = '" + m.getTelefono() + "', "+
                "Indirizzo = '" + m.getIndirizzo() + "', "+
                "Citta = '" + m.getCitta() + "', "+
                "Provincia = '" + m.getProvincia() + "', "+
                "CAP = '" + m.getCap() + "', "+
                "Regione = '" + m.getRegione() + "', "+
                "Email_Contatti = '" + m.getEmail() + "', "+
                "Sito_Web = '" + m.getSito_web() + "', "+
                "Orario_Apertura = '" + m.getOrario() + "', "+
                "Immagine_Museo = '" + m.getImmagine() + "' " +
                "WHERE ID = " + m.getID();

        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.out.println( ex.getMessage() );

            return false;
        }
    }

    /**
     * Cancella un museo
     *
     * @param id ID del museo
     * @return
     */
    @Autore(autore = "Mattia")
    public boolean eliminaMuseo(int id){
        String insert1="DELETE FROM Museo " +
                "WHERE ID = " + id;
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.out.println( ex.getMessage() );

            return false;
        }
    }

    /**
     * Seleziona tutti i musei presenti nel database
     *
     * @return HashMap<String, String>
     *     Restituisce un ArrayList contenenente un HashMap con i musei
     *     L'HashMap ha una coppia chiave/valore che rispettivamente
     *     sono il nome del campo e il suo valore
     *
     * @return
     */
    @Autore(autore = "Mattia")
    public ArrayList<HashMap<String, String>> visualizzaTuttiIMusei(){
        String query="SELECT * FROM Museo";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<HashMap<String, String>> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                HashMap<String, String> hm = new HashMap<>();

                hm.put("ID", c.getString(0));
                hm.put("Nome", c.getString(1));
                hm.put("Numero_Telefono", c.getString(2));
                hm.put("Indirizzo", c.getString(3));
                hm.put("Citta", c.getString(4));
                hm.put("Provincia", c.getString(5));
                hm.put("CAP", c.getString(6));
                hm.put("Regione", c.getString(7));
                hm.put("Email_Contatti", c.getString(8));
                hm.put("Sito_Web", c.getString(9));
                hm.put("Orario_Apertura", c.getString(10));
                hm.put("Immagine_Museo", c.getString(11));

                al.add(hm);

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }
}
