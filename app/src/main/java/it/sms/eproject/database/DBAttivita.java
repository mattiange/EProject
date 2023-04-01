package it.sms.eproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Attivita;
import it.sms.eproject.data.classes.Autore;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBAttivita extends DbManager{
    private final String TABLE_NAME;
    private final String TABLE_NAME_MUSEI_HAS_ATTIVITA;
    private final String TABLE_NAME_OGGETTI_HAS_ATTIVITA;

    public DBAttivita(Context context) {
        super(context);
        TABLE_NAME                      = "attivita";
        TABLE_NAME_MUSEI_HAS_ATTIVITA   = "attivita_has_musei";
        TABLE_NAME_OGGETTI_HAS_ATTIVITA = "oggetti_has_attivita";
    }

    /**
     * Inserisce una nuova attivita
     *
     * @param a Attività da inserire
     * @return true se l'inserimento ha avuto successo, false altrimenti
     */
    public long inserisciAttivita(Attivita a){
        SQLiteDatabase db= helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("attivita", a.getNome());
        values.put("ultima_modifica", new Date().getTime());
        values.put("data_creazione", new Date().getTime());
        values.put("descrizione", a.getDescrizione());
        values.put("utente_codice", a.getUtenteCodice());
        values.put("citta_codice", a.getCittaCodice());

        long lastId = db.insert(TABLE_NAME, null, values);

        return lastId;
    }

    public long inserisciMuseoAttivita(long codiceAttivita, int codiceMuseo){
        ContentValues values = new ContentValues();
        values.put("attivita_codice", codiceAttivita);
        values.put("musei_codice", codiceMuseo);

        long lastId = helper.getWritableDatabase().insert(TABLE_NAME_MUSEI_HAS_ATTIVITA, "", values);

        return lastId;
    }

    public long inserisciOggettoAttivita(long codiceAttivita, int codiceOggetto){
        ContentValues values = new ContentValues();
        values.put("attivita_codice", codiceAttivita);
        values.put("oggetti_codice", codiceOggetto);

        long lastId = helper.getWritableDatabase().insert(TABLE_NAME_OGGETTI_HAS_ATTIVITA, "", values);

        return lastId;
    }

    public boolean aggiornaAttivita(Attivita a){
        ContentValues values = new ContentValues();
        values.put("attivita", a.getNome());
        values.put("ultima_modifica", new Date().getTime());
        values.put("descrizione", a.getDescrizione());

        helper.getWritableDatabase().update(TABLE_NAME, values, " codice = ? ", new String[]{String.valueOf(a.getCodice())});

        return true;
    }

    public Attivita getAttivita(long codice){
        String query="SELECT * FROM attivita WHERE codice = " + codice;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);
        Attivita a = null;

        if(c.moveToFirst()){
            a = new Attivita(
                    c.getInt(0),//Codice
                    c.getInt(5),//Codice utente
                    c.getLong(6),//Città codice
                    c.getString(1),//Nome attività
                    c.getString(4)//Descrizione attività
            );
        }

        return a;
    }
    public ArrayList<Attivita> getAttivita(String cerca){
        String query="SELECT * FROM attivita WHERE attivita LIKE \"%"+cerca+"%\"";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);
        ArrayList<Attivita> a = null;

        if(c.moveToFirst()){
            a = new ArrayList<>();

            a.add(new Attivita(
                    c.getInt(0),//Codice
                    c.getInt(5),//Codice utente
                    c.getLong(6),//Città codice
                    c.getString(1),//Nome attività
                    c.getString(4)//Descrizione attività
            ));
        }

        return a;
    }
}
