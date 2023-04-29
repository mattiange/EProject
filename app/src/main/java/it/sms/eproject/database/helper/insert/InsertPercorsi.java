package it.sms.eproject.database.helper.insert;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Inserisce i valori di default per i percorsi
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class InsertPercorsi {
    private SQLiteDatabase db;

    public InsertPercorsi(SQLiteDatabase db){
        this.db = db;
    }

    public void execute() throws SQLException {
        this.insert();
    }

    private void insert() throws SQLException {
        //Inserimento percorsi
        String insert_percorsi = "INSERT INTO percorsi (Nome, descrizione, durata, codice_utente, codice_citta) " +
                "VALUES " +
                "('Terra di peucezia', 'Visita le origini di Gioia del Colle', 250, 2, 1), " +

                "('Città di Bari', 'Visita le meraviglie baresi', 330, 2, 2)";
        //---------------------------------------------------------------------------

        //Inserimento musei has percorsi
        String insert_musei_has_percorsi = "INSERT INTO musei_has_percorsi (museo_codice, percorso_codice) " +
                "VALUES " +
                //Terra di peucezia
                "(1, 1), (2, 1), (3, 1), (4, 1), (5, 1)," +

                //Città di Bari
                "(6, 2), (7, 2), (8, 2)";
        //---------------------------------------------------------------------------

        //Inserimento oggetti has percorsi
        String insert_oggetti_has_percorsi = "INSERT INTO oggetti_has_percorsi (oggetto_codice, percorso_codice) " +
                "VALUES " +
                //Terra di peucezia
                "(1, 1),(2, 1),(3, 1), (4,1)," +

                //Città di Bari
                "(5, 1)";
        //---------------------------------------------------------------------------

        db.execSQL(insert_percorsi);
        db.execSQL(insert_musei_has_percorsi);
        db.execSQL(insert_oggetti_has_percorsi);
    }
}
