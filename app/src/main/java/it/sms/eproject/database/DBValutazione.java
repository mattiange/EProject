package it.sms.eproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Autore;

/**
 * Gestisce le operazioni per le valutazioni
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBValutazione extends DbManager{
    private static String TABLE_NAME_VALUTAZIONI;
    private static String TABLE_NAME_VALUTAZIONI_PERCORSI;

    static {
        TABLE_NAME_VALUTAZIONI          = "valutazioni";
        TABLE_NAME_VALUTAZIONI_PERCORSI = "percorsi_has_valutazioni";
    }

    public DBValutazione(Context context) {
        super(context);
    }

    /**
     * Inserisce una nuova valutazione per un percorso
     *
     * @param idUtente Id dell'utente che ha recensito
     * @param valutazione Valutazione da registrare
     * @param idPercorso Codice del percorso su cui salvare la valutazione
     * @return true
     */
    public boolean inserisciValutazionePercorso(long idUtente, float valutazione, long idPercorso){
        //String insert1= "INSERT INTO valutazioni (valutazione, utenti_codice) VALUES (null, "+valutazione+", "+idUtente+");";

        //Inserisce una valutazione SOLO SE non ne è già
        //stata inserita una per lo stesso percorso
        if(!controlloVotazionePercorso(idUtente, idPercorso)) {
            long last_id = inserisciValutazione(idUtente, valutazione);

            if (last_id == -1) return false;

            ContentValues contentValues = new ContentValues();
            contentValues.put("valutazione_codice", last_id);
            contentValues.put("percorso_codice", idPercorso);

            SQLiteDatabase db = helper.getWritableDatabase();

            last_id = db.insert(TABLE_NAME_VALUTAZIONI_PERCORSI, null, contentValues);

            if (last_id == -1) return false;

            db.close();

            return true;
        }else{//se già presente la aggiorno
            long codiceValutazione = getValutazioneIDByUtente(idUtente);

            Log.d("CODICE VALUTAZIONE", String.valueOf(codiceValutazione));

            if(codiceValutazione != -1) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("valutazione", valutazione);

                SQLiteDatabase db = helper.getWritableDatabase();
                long row = db.update(TABLE_NAME_VALUTAZIONI, contentValues, "codice = ?", new String[]{String.valueOf(codiceValutazione)});

                Log.d("CODICE UPDATE", String.valueOf(row));

                db.close();
            }


        }

        return false;
    }

    /***
     * Restituisce l'ID della votazione in base all'ID
     * dell'utente
     *
     * @param codiceUtente ID dell'utente
     * @return
     */
    public long getValutazioneIDByUtente(long codiceUtente){
        String query = "SELECT v.codice " +
                "FROM valutazioni v " +
                "LEFT JOIN percorsi_has_valutazioni phv ON v.codice = phv.valutazione_codice " +
                "WHERE phv.percorso_codice IS NOT NULL AND v.utenti_codice = "+codiceUtente;

        Log.d("QUERY", query);

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()){
            do {
                return c.getLong(0);
            } while(c.moveToNext());
        }
        c.close();


        return -1;
    }

    /**
     * Calcola la media delle valutazioni di un percorso
     * @param codicePercorso
     * @return
     */
    public float calcolaMediaValutazionePercorso(long codicePercorso){
        String query =
                "SELECT AVG(v.valutazione) " +
                "FROM valutazioni v " +
                "LEFT JOIN percorsi_has_valutazioni phv ON v.codice = phv.valutazione_codice " +
                "WHERE phv.percorso_codice IS NOT NULL AND phv.percorso_codice = " + codicePercorso;

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()){
            do {
                return c.getFloat(0);
            } while(c.moveToNext());
        }
        c.close();


        return 0.0f;
    }

    /**
     * Controlla se un utente ha già votato per un
     * determinato percorso
     *
     * @param codiceUtente  Codice dell'utente
     * @param codicePercorso Codice del percorso
     * @return true se è già stato inserita una votazione, false altrimenti
     */
    private boolean controlloVotazionePercorso(long codiceUtente, long codicePercorso){
        String query = "SELECT COUNT(*) " +
                "FROM valutazioni v " +
                "LEFT JOIN percorsi_has_valutazioni phv ON v.codice = phv.valutazione_codice " +
                "WHERE phv.percorso_codice IS NOT NULL AND v.utenti_codice = "+codiceUtente+" AND phv.percorso_codice = " + codicePercorso;

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()){
            do {
                if(c.getLong(0) > 0) return true;
            } while(c.moveToNext());
        }
        c.close();


        return false;
    }

    /**
     * Inserisce la valutazione all'interno del percorso
     *
     * @param idUtente ID Utente
     * @param valutazione Valutazione da inserire
     * @return
     */
    private long inserisciValutazione(long idUtente, float valutazione){
        ContentValues contentValues = new ContentValues();
        contentValues.put("valutazione", valutazione);
        contentValues.put("utenti_codice", idUtente);

        SQLiteDatabase db= helper.getWritableDatabase();

        long id = db.insert(TABLE_NAME_VALUTAZIONI, null, contentValues);

        db.close();

        return id;
    }
}
