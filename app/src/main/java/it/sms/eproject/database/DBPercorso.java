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
     * @return true|null Restituisce true se l'inserimento è andato a buon fine, false altrimenti.
     */
    public boolean inserisciPercorso(Percorso p){
        String insert1="INSERT INTO percorsi (codice, Nome, descrizione, durata, codice_utente) "
                + "VALUES (NULL," +
                "'"+p.getNome()+"','"+
                p.getDescrizione()+"', '"+
                p.getDurata()+"', '"+
                p.getCodiceUtente() +"'" +
                ");";

        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            //INSERIRE QUI SALVATAGGIO PER I COLLEGAMENTI DEL PERCORSO (COME I MUSEI)

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }
}
