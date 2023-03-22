package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.time.LocalDate;

import it.sms.eproject.eccezioni.EmailGiaEsistenteException;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Permesso;
import it.sms.eproject.data.classes.Utente;

/**
 * Tabella di gestione del database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DbManager {
    static MyHelper helper=null;
    private final static String DATABASE="ProgettoSMS.db";
    private final static int VERSIONE_DATABASE=1;
    Context c;

    public DbManager(Context context)      {
        this.c = context;
        helper=new MyHelper(context, DATABASE,  null, VERSIONE_DATABASE);
    }



    /*
     * Visualizza tutti gli utenti registrati e i permessi a loro associati
     *
     * @return Restituisce tutti gli utenti registrato con i relativi permessi
     */
    /*@AutoreCodice(autore = "Mattia, Giandomenico")
    public ArrayList<Utente> elencoUtenti()      {
        String query="SELECT * FROM utenti AS u, permessi AS p, permesso_has_utente AS pu WHERE u.codice = pu.codice_utente AND p.codice = pu.codice_permesso";
        SQLiteDatabase db= helper.getReadableDatabase();

        ArrayList<Utente> utenti = null;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            utenti = new ArrayList<>();

            do {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    utenti.add(new Utente(
                            Integer.parseInt(c.getString(0)),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            LocalDate.parse(c.getString(4)),
                            c.getString(7),
                            //Permesso associato all'utente
                            new Permesso(Integer.parseInt(c.getString(9)), c.getString(10))
                    ));
                }

            } while(c.moveToNext());
        }
        c.close();

        return utenti;
    }*/

    /**
     * Registra un nuovo utente
     *
     * @param u Utente da registrare
     * @return Restituisce l'esito della registrazione
     */
    @AutoreCodice(autore = "Mattia, Giandomenico")
    public boolean registrazione(Utente u) throws EmailGiaEsistenteException{
        String insert1="INSERT INTO utenti(codice, nome, cognome, codice_fiscale, data_di_nascita, email, password) "
                + "VALUES (NULL,'"+u.getNome()+"','"+u.getCognome()+"', '"+u.getCodice_fiscale()+"', '"+u.getData_di_nascita()+"', '"+u.getEmail()+"', '"+u.getPassword()+"')";
        SQLiteDatabase db= helper.getWritableDatabase();


        try{
            db.execSQL(insert1);

            //Leggo l'id inserito e aggiungo il permesso all'utente
            String last_id_query = "SELECT last_insert_rowid() as last_id FROM utenti;";
            db= helper.getReadableDatabase();
            Cursor c = db.rawQuery(last_id_query, null);
            if(c.moveToFirst()){
                int last_id = Integer.parseInt(c.getString(0));

                //inserisco il permesso dell'utente
                String insertPermessoUtente = "INSERT INTO permesso_has_utente(codice_utente, codice_permesso)" +
                        "VALUES ("+last_id+","+u.getPermesso().getCodice()+");";

                db= helper.getWritableDatabase();
                db.execSQL(insertPermessoUtente);
                //------------
            }
            //----------------------------------------------------

            c.close();

            return true;
        }catch(SQLException ex){
            if(ex instanceof SQLiteConstraintException){
                throw new EmailGiaEsistenteException(this.c);
            }

            return false;
        }
    }

    /**
     * Effettua il login di un utente
     *
     * @param email  Nome utente
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
    @AutoreCodice(autore = "Mattia, Giandomenico")
    public Utente login(String email, String password){
        String query="SELECT * FROM utenti AS u, permessi AS p, permesso_has_utente AS pu " +
                "WHERE email = '"+email+"' " +
                "AND password = '"+password+"' " +
                "AND u.codice = pu.codice_utente " +
                "AND p.codice = pu.codice_permesso;";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        Utente utente = null;

        if (c.moveToFirst()){
            do {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    utente = new Utente(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            LocalDate.parse(c.getString(4)),
                            c.getString(7),
                            new Permesso(c.getInt(9), c.getString(10))
                    );
                }

            } while(c.moveToNext());
        }

        c.close();

        return utente;
    }


    /**
     * AGGIORNA DATI UTENTE
     */
    public boolean aggiornaProfilo(Utente utente){

        String insert1 = "UPDATE Utente" + "SET" + "Nome = '"+utente.getNome()+"', " +
                "Cognome ='"+utente.getCognome()+"', Password = '"+utente.getPassword()+"'" +
                "WHERE id ='"+utente.getCodice()+"';";

        SQLiteDatabase db= helper.getReadableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }

    }


    //////////////////////////// GESTIONE ZONE

    /*
     * Restituisce una singola zona in base al suo ID
     *
     * @param id ID della zona
     * @return Restituisce la zona ottenuta. Restituisce <strong>null</strong> se non
     *         trova nessuna zona.
     */
    /*public Zona getZona(int id){
        Zona ogg = null;

        String query="SELECT * FROM Zone WHERE id = " + id + "";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            do {
                ogg = new Zona(
                        Integer.parseInt(c.getString(0)),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4)
                );

            } while(c.moveToNext());
        }

        c.close();

        return ogg;
    }*/

    /*
     * Elimina un oggetto dal database selezionandolo in base al suo ID.
     *
     * @param id ID dell'oggetto
     * @return <strong>true</strong> se l'oggetto viene cancellato, <strong>false</strong> altrimenti
     */
    /*public boolean deleteZona(int id){
        String query = "DELETE FROM Zone WHERE id = " + id;

        SQLiteDatabase db = helper.getWritableDatabase();

        try{
            db.execSQL(query);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }*/

    /*
     * Aggiorna una zona esistente.
     * La zona da modificare viene identificato mediante
     * il campo ID.
     *
     * @param id ID della zona
     * @param z Dati della nuova zona
     * @return Restituisce <string>true</string> se la modifica ha successo
     *          <string>false</string> altrimenti.
     */
    /*public boolean updateZona(int id, @NonNull Zona z){
        String query = "UPDATE Zone "
                + "SET " +
                "Nome = '"+z.getNome()+"', Provincia = '"+
                z.getProvincia()+"', Regione = '"+
                z.getRegione()+"', CAP = '"+
                z.getCAP()+"' " +
                "WHERE id = "+id+";";

        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(query);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }*/

    /*
     *
     * Seleziona tutte le zone presenti nel database, per una specifica provincia
     *
     * @return HashMap<String, String>
     *     Restituisce un ArrayList contenenente un HashMap con le zone.
     *
     *     L'HashMap ha una coppia chiave/valore che rispettivamente
     *     sono il nome del campo e il suo valore
     *
     * @param provincia
     * @return
     */
    /*@AutoreCodice(autore = "Mattia")
    public ArrayList<Zona> visualizzaTutteLeZoneByProvincia(String provincia){
        String query="SELECT * FROM Zone WHERE Provincia = '" + provincia + "'";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        //ArrayList<HashMap<String, String>> al = null;
        ArrayList<Zona> al = new ArrayList<>();

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                al.add(new Zona(
                                Integer.parseInt(c.getString(0)),
                                c.getString(1),
                                c.getString(2),
                                c.getString(3),
                                c.getString(4)
                        )
                );

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }*/

    /*
     * Recupera tutte le regioni presenti all'interno delle zone
     *
     * @return
     */
    /*@AutoreCodice(autore = "Mattia")
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
    }*/

    /*
     * Recupera tutte le regioni presenti all'interno delle zone
     *
     * @return
     */
    /*@AutoreCodice(autore = "Mattia")
    public ArrayList<HashMap<String, String>> visualizzaTutteLeProvinceDiUnaRegione(String regione){
        String query="SELECT provincia FROM Zone WHERE regione = '"+regione+"' GROUP BY provincia";

        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<HashMap<String, String>> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                HashMap<String, String> hm = new HashMap<>();

                hm.put("Provincia", c.getString(0));

                al.add(hm);

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }*/

    /////////////////////////////// GESTIONE OGGETTO

    /*
     * Elimina un oggetto dal database selezionandolo in base al suo ID.
     *
     * @param id ID dell'oggetto
     * @return <strong>true</strong> se l'oggetto viene cancellato, <strong>false</strong> altrimenti
     */
    /*public boolean deleteOggetto(int id){
        String query = "DELETE FROM Oggetto WHERE id = " + id;

        SQLiteDatabase db = helper.getWritableDatabase();

        try{
            db.execSQL(query);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }*/

    /*
     * Aggiorna un oggetto esistente.
     * L'oggetto da modificare viene identificato mediante
     * il campo ID.
     *
     * @param id ID dell'oggetto
     * @param o Dati del nuovo oggetto
     * @return Restituisce <string>true</string> se la modifica ha successo
     *          <string>false</string> altrimenti.
     */
    /*public boolean updateOggetto(int id, @NonNull Oggetto o){
        String insert1="UPDATE Oggetto "
                + "SET " +
                "Nome = '"+o.getNome()+"', Anno = '"+
                o.getAnno()+"', Autore = '"+
                o.getAutore()+"', Descrizione = '"+
                o.getDescrizione()+"' " +
                "WHERE id = "+id+";";

        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }*/

}