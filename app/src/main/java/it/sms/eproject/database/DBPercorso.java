package it.sms.eproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.OggettiMuseoHasPercorsi;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.data.classes.Percorso;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBPercorso extends DbManager{
    public DBPercorso(Context context) {
        super(context);
    }

    /**
     * Inserisce un nuovo percorso nel database
     *
     * @param p Percorso da inserire
     * @param m Musei da legare al percorso
     * @param o Oggetti da legare al percorso
     * @return true|null Restituisce true se l'inserimento è andato a buon fine, false altrimenti.
     */
    public long inserisciPercorso(Percorso p, List<Museo> m, List<Oggetto> o){
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            ContentValues cv_percorso = new ContentValues();
            cv_percorso.put("nome", p.getNome());
            cv_percorso.put("descrizione", p.getDescrizione());
            cv_percorso.put("durata", p.getDurata());
            cv_percorso.put("codice_utente", p.getCodiceUtente());
            cv_percorso.put("codice_citta", p.getCodice_citta());

            //db.execSQL(insert_percorso);
            long percorso_id = db.insert("percorsi", null, cv_percorso);

            //inserimento dei musei
            if(m.size() > 0){
                for(Museo museo : m){
                    String insert_musei_percorso = "INSERT INTO musei_has_percorsi (museo_codice, percorso_codice) " +
                            "VALUES (" +
                            museo.getID() + ", " +
                            percorso_id +
                            ")";
                    db.execSQL(insert_musei_percorso);
                }
            }

            //inserimento degli oggetti
            if(o.size() > 0){
                for(Oggetto oggetto : o){
                    String insert_oggetti_percorso = "INSERT INTO oggetti_has_percorsi (oggetto_codice, percorso_codice) " +
                            "VALUES (" +
                            oggetto.getId() + ", " +
                            percorso_id +
                            ")";
                    db.execSQL(insert_oggetti_percorso);
                }
            }

            return percorso_id;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return -1;
        }finally {
            db.close();
        }
    }

    /**
     * Restituisce un percorso in base al suo ID
     *
     * @param codice_percorso Codice del percorso da restituire
     * @return Percorso ottenuto. NULL se non c'è nessun percorso con quell'ID.
     */
    public Percorso get(long codice_percorso){
        String query_oggetti="SELECT * FROM percorsi WHERE codice = " + codice_percorso;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query_oggetti, null);

        if (c.moveToFirst()){
            do {
                return new Percorso(
                        c.getInt(0),//Codice
                        c.getString(1),//Nome
                        c.getString(2),//Descrizione
                        c.getInt(3),//Durata
                        c.getInt(4),//Codice utente
                        c.getInt(5)//Codice citta
                );
            } while(c.moveToNext());
        }

        return null;
    }

    /**
     * Restituisce gli oggetti
     *
     * @param codice_percorso Codice del Percorso
     * @return Musei e oggetti del percorso
     */
    public OggettiMuseoHasPercorsi getElementiPercorso(long codice_percorso){
        String query_oggetti="SELECT oggetto_codice FROM oggetti_has_percorsi WHERE percorso_codice = " + codice_percorso;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query_oggetti, null);

        ArrayList<Oggetto> po = new ArrayList<>();

        if (c.moveToFirst()){
            do {
                po.add(new DBOggetto(this.c).getOggetto(c.getLong(0)));
            } while(c.moveToNext());
        }

        c.close();

        String query_musei = "SELECT museo_codice FROM musei_has_percorsi WHERE percorso_codice = " + codice_percorso;
        db = helper.getReadableDatabase();

        c = db.rawQuery(query_musei, null);

        ArrayList<Museo> pm = new ArrayList<>();;

        if (c.moveToFirst()){
            do {
                pm.add(new DBMuseo(this.c).getMuseo(c.getLong(0)));
            } while(c.moveToNext());
        }

        c.close();

        return new OggettiMuseoHasPercorsi(pm, po);
    }

    /**
     * Cancella un percorso
     *
     * @param codice Codice del percorso
     * @return true se il percorso è stato cancellato, false altrimenti
     */
    @AutoreCodice(autore = "Mattia")
    public boolean eliminaPercorso(long codice){
        String insert1 = "DELETE FROM percorsi WHERE codice = " + codice;
        SQLiteDatabase db = helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }

    /**
     * Seleziona tutti i percorsi presenti nel database
     *
     * @return ArrayList<Percorso>
     *     Restituisce un ArrayList contenenente tutti i percorsi presenti nel database
     *
     * @return
     */
    public ArrayList<Percorso> elencoPercorsi(){
        String query="SELECT * FROM percorsi";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<Percorso> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                al.add(new Percorso(
                        c.getInt(0),//Codice
                        c.getString(1),//Nome
                        c.getString(2),//Descrizione
                        c.getInt(3),//Durata
                        c.getInt(4),//Codice utente
                        c.getInt(5)//Codice citta
                ));

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }

    /**
     * Restituisce il codice della città a cui il percorso fa riferimento
     *
     * @param percorso_codice Codice del percorso
     * @return Codice della città
     */
    public long getCodiceCitta(long percorso_codice){
        String query = "select codice_citta " +
                "from percorsi " +
                "where codice = " + percorso_codice;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        long codice   = -1;
        if (c.moveToFirst()){
            do {
                codice = c.getLong(0);

            } while(c.moveToNext());
        }
        c.close();

        return codice;
    }


    /**
     * Restituisce il codice della città a cui il percorso fa riferimento
     *
     * @param percorso_codice Codice del percorso
     * @return Codice della città
     */
    public String getNomeCitta(long percorso_codice){
        String query = "select citta.nome " +
                "from oggetti_has_percorsi, musei_has_percorsi, musei, oggetti, citta " +
                "where oggetti_has_percorsi.oggetto_codice = oggetti.codice AND musei.codice = musei_has_percorsi.museo_codice " +
                "and (oggetti_has_percorsi.percorso_codice = "+ percorso_codice + " AND musei_has_percorsi.percorso_codice = " + percorso_codice + ") " +
                "AND (citta.codice = oggetti.citta_codice OR musei.citta_codice = citta.codice) " +
                "LIMIT 1";
        SQLiteDatabase db= helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        String nomeCitta = null;
        if (c.moveToFirst()){
            do {
                nomeCitta = c.getString(0);

            } while(c.moveToNext());
        }
        c.close();

        return nomeCitta;
    }

    /**
     * Elimina un museo dal percorso
     *
     * @param m
     * @return
     */
    public boolean deleteMuseo(Museo m){
        String sql = "DELETE FROM musei_has_percorsi WHERE museo_codice = " + m.getID();
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(sql);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }

    /**
     * Elimina un oggetto dal percorso
     *
     * @param o
     * @return
     */
    public boolean deleteOggetto(Oggetto o){
        String sql = "DELETE FROM oggetti_has_percorsi WHERE oggetto_codice = " + o.getId();
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(sql);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }

    /**
     * Inserisce un nuovo oggetto all'interno del percorso
     *
     * @param codice_percorso
     * @param codice_oggetto
     * @return
     */
    public boolean insertOggetto(long codice_percorso, int codice_oggetto){
        SQLiteDatabase db= helper.getWritableDatabase();

        String insert_oggetti_percorso = "INSERT INTO oggetti_has_percorsi (oggetto_codice, percorso_codice) " +
                "VALUES (" +
                codice_oggetto + ", " +
                codice_percorso +
                ")";
        try {
            db.execSQL(insert_oggetti_percorso);

            return true;
        }catch (SQLException e){
            return false;
        }

    }

    /**
     * Inserisce un nuovo museo all'interno del percorso
     *
     * @param codice_percorso
     * @param codice_percorso
     * @return
     */
    public boolean insertMuseo(long codice_percorso, int codice_museo){
        SQLiteDatabase db= helper.getWritableDatabase();

        String insert_musei_percorso = "INSERT INTO musei_has_percorsi (museo_codice, percorso_codice) " +
                "VALUES (" +
                codice_museo + ", " +
                codice_percorso +
                ")";
        try {
            db.execSQL(insert_musei_percorso);

            return true;
        }catch (SQLException e){
            return false;
        }

    }

    /**
     * Restituisce un percorso cercandolo atteaverso il nome di un museo
     * o di un oggetto
     *
     * @param nome Nome del museo o dell'oggetto
     * @return Percorsi trovati, null altrimenti
     */
    public ArrayList<Percorso> getPercorsoFromMuseoOrOggetto(String nome){
        String query = "SELECT p.* " +
                        "FROM musei as m " +
                        "INNER JOIN musei_has_percorsi as mhp ON m.codice = mhp.museo_codice " +
                        "INNER JOIN percorsi as p ON p.codice = mhp.percorso_codice " +
                        "INNER JOIN oggetti_has_percorsi ohp ON o.codice = ohp.percorso_codice " +
                        "INNER JOIN oggetti as o ON o.codice = ohp.oggetto_codice " +
                        "WHERE o.nome LIKE '%"+nome+"%' OR m.nome LIKE '%"+nome+"%' " +
                        "GROUP BY p.codice";
        //String query = "SELECT * FROM percorsi";

        //Log.d("Query", query);

        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<Percorso> arr = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                arr.add(new Percorso(
                        c.getInt(0),//Codice
                        c.getString(1),//Nome
                        c.getString(2),//Descrizione
                        c.getInt(3),//Durata
                        c.getInt(4),//Codice utente
                        c.getInt(5)//Codice citta
                ));
            } while(c.moveToNext());

            return arr;
        }

        return null;
    }

    /**
     * Restituisce tutti i percorsi creati da una guida
     * o da un curatore
     *
     * @return Percorsi trovati, null altrimenti
     */
    public ArrayList<Percorso> getPercorsoByGuidaOrCuratore(){
        /*String query = "SELECT * " +
                "FROM (" +
                        "SELECT * " +
                        "FROM percorsi " +
                        "WHERE percorsi.codice_utente IN ( " +
                                                            "SELECT utenti.codice " +
                                                            "FROM utenti, permesso_has_utente, permessi " +
                                                            "WHERE utenti.codice=permesso_has_utente.codice_utente " +
                                                                    "AND permessi.codice=permesso_has_utente.codice_permesso " +
                                                                    "AND permessi.codice IN (1, 2)" +
                                                        ") " +
                    ") pgc, " +
                "musei,  musei_has_percorsi, oggetti, oggetti_has_percorsi " +
                "WHERE ((musei.codice = musei_has_percorsi.museo_codice AND pgc.codice = musei_has_percorsi.museo_codice) " +
                        "OR (oggetti.codice = oggetti_has_percorsi.oggetto_codice AND pgc.codice = oggetti_has_percorsi.oggetto_codice)) " +
                        "AND (pgc.codice =  musei_has_percorsi.percorso_codice AND pgc.codice = oggetti_has_percorsi.percorso_codice)" +
                "GROUP BY pgc.codice";*/
        String query = "SELECT * FROM percorsi WHERE percorsi.codice_utente IN ( SELECT utenti.codice FROM utenti, permesso_has_utente, permessi WHERE utenti.codice=permesso_has_utente.codice_utente AND permessi.codice=permesso_has_utente.codice_permesso AND permessi.codice IN (1, 2)) ";

        SQLiteDatabase db= helper.getReadableDatabase();

        Log.d("QUERY", query);

        Cursor c = db.rawQuery(query, null);
        Log.d("COUNT_CURSOR", String.valueOf(c.getCount()));

        ArrayList<Percorso> arr = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                arr.add(new Percorso(
                        c.getInt(0),//Codice
                        c.getString(1),//Nome
                        c.getString(2),//Descrizione
                        c.getInt(3),//Durata
                        c.getInt(4),//Codice utente
                        c.getInt(5)//Codice citta
                ));
            } while(c.moveToNext());

            return arr;
        }

        return null;
    }

    /**
     * Restituisce tutti i percorsi creati da una guida
     * o da un curatore
     *
     * @return Percorsi trovati, null altrimenti
     */
    public ArrayList<Percorso> getPercorsoByGuidaOrCuratore(String cerca){
        String query = "SELECT * " +
                "FROM (" +
                        "SELECT * " +
                        "FROM percorsi " +
                        "WHERE percorsi.codice_utente IN ( " +
                                                        "SELECT utenti.codice " +
                                                        "FROM utenti, permesso_has_utente, permessi " +
                                                        "WHERE utenti.codice=permesso_has_utente.codice_utente " +
                                                                "AND permessi.codice=permesso_has_utente.codice_permesso " +
                                                                "AND permessi.codice IN (1, 2)" +
                                                        ") " +
                ") pgc, " +
                "musei,  musei_has_percorsi, oggetti, oggetti_has_percorsi, citta " +
                "WHERE (" +
                            "(" +
                                "(musei.codice = musei_has_percorsi.museo_codice AND pgc.codice = musei_has_percorsi.museo_codice AND musei.nome LIKE \"%"+cerca+"%\") " +
                                "OR (oggetti.codice = oggetti_has_percorsi.oggetto_codice AND pgc.codice = oggetti_has_percorsi.oggetto_codice AND oggetti.Nome LIKE \"%"+cerca+"%\") " +
                                " OR (citta.nome LIKE \"%"+cerca+"%\" AND citta.codice = pgc.codice_citta ) " +
                                ") " +
                            " AND (pgc.codice =  musei_has_percorsi.percorso_codice AND pgc.codice = oggetti_has_percorsi.percorso_codice) " +
                        ") " +
                        " OR (pgc.nome LIKE \"%" + cerca + "%\") " +
                "GROUP BY pgc.codice";

        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        Log.d("QUERY_CERCA", query);

        ArrayList<Percorso> arr = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                Percorso p = new Percorso(
                        c.getInt(0),//Codice
                        c.getString(1),//Nome
                        c.getString(2),//Descrizione
                        c.getInt(3),//Durata
                        c.getInt(4),//Codice utente
                        c.getInt(5)//Codice citta
                );
                arr.add(p);
            } while(c.moveToNext());

            return arr;
        }
        return null;
    }

    /**
     * Restituisce tutti i percorsi creati dall'utente
     *
     * @param codice Codice dell'utente
     * @return Percorsi trovati, null altrimenti
     */
    public ArrayList<Percorso> getPercorsoByUtente(long codice){
        String query = "SELECT * " +
                "FROM (SELECT * FROM percorsi WHERE percorsi.codice_utente = " + codice + " ) pgc, " +
                "musei,  musei_has_percorsi, oggetti, oggetti_has_percorsi, citta " +
                "WHERE (musei.codice = musei_has_percorsi.museo_codice AND pgc.codice = musei_has_percorsi.museo_codice) " +
                        "OR (oggetti.codice = oggetti_has_percorsi.oggetto_codice AND pgc.codice = oggetti_has_percorsi.oggetto_codice) " +
                "GROUP BY pgc.codice;";

        Log.d("QUERY", query);

        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<Percorso> arr = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                arr.add(new Percorso(
                        c.getInt(0),//Codice
                        c.getString(1),//Nome
                        c.getString(2),//Descrizione
                        c.getInt(3),//Durata
                        c.getInt(4),//Codice utente
                        c.getInt(5)//Codice citta
                ));
            } while(c.moveToNext());

            return arr;
        }

        return null;
    }


    /**
     * Restituisce tutti i percorsi creati dall'utente
     *
     * @param codice Codice dell'utente
     * @param cerca Stringa da cercare
     * @return Percorsi trovati, null altrimenti
     */
    public ArrayList<Percorso> getPercorsoByUtente(long codice, String cerca){
        String query = "SELECT * " +
                "FROM (SELECT * FROM percorsi WHERE percorsi.codice_utente = " + codice + " ) pgc, " +
                        " musei,  musei_has_percorsi, oggetti, oggetti_has_percorsi, citta " +
                "WHERE (musei.codice = musei_has_percorsi.museo_codice AND pgc.codice = musei_has_percorsi.museo_codice AND musei.nome LIKE \"%" + cerca + "%\") " +
                        "OR (oggetti.codice = oggetti_has_percorsi.oggetto_codice AND pgc.codice = oggetti_has_percorsi.oggetto_codice AND oggetti.Nome LIKE \"%" + cerca + "%\") " +
                        "OR (citta.nome LIKE \"%" + cerca + "%\" AND citta.codice = pgc.codice_citta ) " +
                        "OR (pgc.nome LIKE \"%" + cerca + "%\") " +
                "GROUP BY pgc.codice";

        Log.d("QUERY", query);

        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<Percorso> arr = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                arr.add(new Percorso(
                        c.getInt(0),//Codice
                        c.getString(1),//Nome
                        c.getString(2),//Descrizione
                        c.getInt(3),//Durata
                        c.getInt(4),//Codice utente
                        c.getInt(5)//Codice citta
                ));
            } while(c.moveToNext());

            return arr;
        }

        return null;
    }
}
