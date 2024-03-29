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
import it.sms.eproject.data.classes.AttivitaMuseo;
import it.sms.eproject.data.classes.AttivitaOggetto;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Oggetto;

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

    public boolean cancellaAttivita(long codice){
        helper.getWritableDatabase().delete(TABLE_NAME," codice = ? ", new String[]{String.valueOf(codice)});

        return true;
    }

    public AttivitaMuseo getAttivitaMuseo(int codiceMuseo){
        String query="SELECT * " +
                "FROM attivita as a, attivita_has_musei as am, musei as m " +
                "WHERE am.musei_codice = m.codice AND a.codice = am.attivita_codice AND m.codice = " + codiceMuseo;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);
        AttivitaMuseo a = null;

        if(c.moveToFirst()){
            a = new AttivitaMuseo(
                    new Attivita(
                            c.getInt(0),//Codice
                            c.getInt(5),//Codice utente
                            c.getLong(6),//Città codice
                            c.getString(1),//Nome attività
                            c.getString(4)//Descrizione attività
                    ),
                    new Museo(
                            c.getInt(8),//Codice
                            c.getString(10),//Nome museo
                            c.getString(11),//Numero telefono
                            c.getString(12),//Indirizzo
                            c.getLong(17),//Codice città
                            c.getString(13),//Email
                            c.getString(14),//Sito internet
                            c.getString(15),//Orario
                            new byte[]{},//immagine museo
                            c.getInt(18)//Durata visita
                    )
            );
        }


        return a;
    }

    public AttivitaOggetto getAttivitaOggetto(int codiceOggetto){
        String query="SELECT * " +
                "FROM attivita as a, oggetti_has_attivita as am, oggetti as m " +
                "WHERE am.oggetti_codice = m.codice AND a.codice = am.attivita_codice AND m.codice = " + codiceOggetto;

        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);
        AttivitaOggetto a = null;

        if(c.moveToFirst()){
            a = new AttivitaOggetto(
                    new Attivita(
                            c.getInt(0),//Codice
                            c.getInt(5),//Codice utente
                            c.getLong(6),//Città codice
                            c.getString(1),//Nome attività
                            c.getString(4)//Descrizione attività
                    ),
                    new Oggetto(
                            c.getInt(8),//Codice
                            c.getString(10),//Nome
                            c.getInt(12),//Anno
                            c.getInt(14),//Autore
                            c.getString(13),//Descrizione
                            c.getInt(15),//Citta
                            c.getInt(16)//Durata
                    )
            );
        }


        return a;
    }
}
