package it.sms.eproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    public boolean inserisciPercorso(Percorso p, List<Museo> m, List<Oggetto> o){
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            ContentValues cv_percorso = new ContentValues();
            cv_percorso.put("nome", p.getNome());
            cv_percorso.put("descrizione", p.getDescrizione());
            cv_percorso.put("durata", p.getDurata());
            cv_percorso.put("codice_utente", p.getCodiceUtente());

            //db.execSQL(insert_percorso);
            long percorso_id = db.insert("percorsi", null, cv_percorso);

            //inserimento dei musei
            if(m != null){
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
            if(o != null){
                for(Museo oggetto : m){
                    String insert_oggetti_percorso = "INSERT INTO oggetti_has_percorsi (oggetto_codice, percorso_codice) " +
                            "VALUES (" +
                            oggetto.getID() + ", " +
                            percorso_id +
                            ")";
                    db.execSQL(insert_oggetti_percorso);
                }
            }

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
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
    public Percorso get(int codice_percorso){
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
                        c.getInt(4)//Codice utente
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
    public OggettiMuseoHasPercorsi getElementiPercorso(int codice_percorso){
        String query_oggetti="SELECT * FROM oggetti_has_percorsi WHERE percorso_codice = " + codice_percorso;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query_oggetti, null);

        ArrayList<Oggetto> po = new ArrayList<>();

        if (c.moveToFirst()){
            do {
                po.add(new DBOggetto(this.c).getOggetto(c.getInt(1)));
            } while(c.moveToNext());
        }

        c.close();

        String query_musei = "SELECT * FROM musei_has_percorsi WHERE percorso_codice = " + codice_percorso;
        db = helper.getReadableDatabase();

        c = db.rawQuery(query_musei, null);

        ArrayList<Museo> pm = new ArrayList<>();;

        if (c.moveToFirst()){
            do {
                pm.add(new DBMuseo(this.c).getMuseo(c.getInt(1)));
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
    public boolean eliminaPercorso(int codice){
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
                        c.getInt(4)//Codice utente
                ));

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }
}
