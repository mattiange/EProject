package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Autore;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBAggiornamento extends DbManager{
    public DBAggiornamento(Context context) {
        super(context);
    }

    /**
     * Inserisce l'aggiornamento
     *
     * @param controllo_aggiornamento Versione dell'aggiornamento
     * @return
     */
    public boolean inserisci(int controllo_aggiornamento) throws SQLException{
        String insert = "INSERT INTO aggiornamenti (codice, controllo_aggiornamento) "
                + "VALUES (NULL," +
                "'"+ controllo_aggiornamento +"'" +
                ");";

        SQLiteDatabase db= helper.getWritableDatabase();
        db.execSQL(insert);

        return true;
    }
}
