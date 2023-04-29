package it.sms.eproject.database.helper.insert;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Inserisce:
 * - Stati
 * - Regioni
 * - Province
 * - Città
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class InsertZone {
    private SQLiteDatabase db;

    public InsertZone(SQLiteDatabase db){
        this.db = db;
    }

    public void execute() throws SQLException{
        this.insert();
    }

    private void insert() throws SQLException {
        //Inserimento dei dati su stato e citta
        String insert_stati="INSERT INTO stati (nome) " +
                "VALUES ('Italia')";
        String insert_regioni="INSERT INTO regioni (nome, stato_codice) " +
                "VALUES ('Puglia', 1)," +
                "('Basilicata', 1)";
        String insert_province="INSERT INTO province (nome, sigla, regione_codice) " +
                "VALUES ('Bari', 'BA', 1)," +//1
                "('Taranto', 'TA', 1)," +//2
                "('Potenza', 'PZ', 2)," +//3
                "('Matera', 'MT', 2)";//4
        String insert_citta="INSERT INTO citta (nome, cap, provincia_codice) " +
                "VALUES ('Gioia del Colle', '70023', 1), " +//1
                "('Bari', '70122', 1), " +//2
                "('Potenza', '85100', 3)," +//3
                "('Massafra', '74016', 2)," + //4
                "('Matera', '75100', 4)";//5

        db.execSQL(insert_stati);
        db.execSQL(insert_regioni);
        db.execSQL(insert_province);
        db.execSQL(insert_citta);
    }
}
