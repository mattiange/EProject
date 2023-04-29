package it.sms.eproject.database.helper.insert;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Inserisce nel database i valori
 * di default per gli Utenti e i Permessi
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class InsertUtentiPermessi {
    private SQLiteDatabase db;

    public InsertUtentiPermessi(SQLiteDatabase db){
        this.db = db;
    }

    public void execute() throws SQLException{
        this.insert();
    }

    private void insert() throws SQLException {
        String insert1="INSERT INTO utenti (codice, nome, cognome, codice_fiscale, data_di_nascita, Email, Password) " +
                "VALUES (NULL,'Alessandro','Manzoni', 'MNZSS80C18H098L', '1980-03-18', 'a.manzoni@gmail.com', 'test')," + //id 1
                "(NULL,'Mario','Rossi', 'MRRSSIZSS80D18H098L', '1980-04-18', 'm.rossi@gmail.com', 'test')," +//id 2
                "(NULL,'Mattia Leonardo','Angelillo', 'NGLMTL93A25H096J', '1993-01-25', 'm.angelillo@gmail.com', 'test')";//id 3
        String insert2 = "INSERT INTO permessi(codice, permesso)" +
                "VALUES (NULL, 'Curatore')," +//id 1
                "(NULL, 'Guida turistica')," +//id 2
                "(NULL, 'Visitatore')";//id 3
        String insert3 = "INSERT INTO permesso_has_utente(codice_utente, codice_permesso)" +
                "VALUES (1, 3), (2, 2), (3, 1)";

        db.execSQL(insert1);
        db.execSQL(insert2);
        db.execSQL(insert3);
    }
}
