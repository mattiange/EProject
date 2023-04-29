package it.sms.eproject.database.helper.insert;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Inserisce i valori di default per gli autori
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class InsertAutori {
    private SQLiteDatabase db;

    public InsertAutori(SQLiteDatabase db){
        this.db = db;
    }

    public void execute() throws SQLException {
        this.insert();
    }

    private void insert() throws SQLException {
        String insert_autori="INSERT INTO autori (nome, data_di_nascita, data_di_morte) " +
                "VALUES " +
                "('Autore sconosciuto', '0000-0-0', '0000-0-0'), " +
                "('Donatello', '1386-0-0', '1466-12-13'), " +
                "('Michelangelo Buonarotti', '1475-03-06', '1564-03-06'), " +
                "('Gian Lorenzo Bernini', '1598-12-07', '1680-11-28')," +
                "('Antonio Canova', '1757-11-1', '1822-10-13')," +
                "('Vincenzo Gemito', '1852-07-16', '1929-03-01')," +
                "('Amedeo Modigliani', '1884-07-12', '1920-01-24')," +
                "('Paul Cézanne', '1839-01-19', '1906-10-22')," +
                "('Pierre-Auguste Renoir', '1841-02-25', '1919-10-03')," +
                "('Claude Monet', '1840-11-14', '1926-12-05')," +
                "('Sandro Botticelli', '1445-03-01', '1510-05-17')," +
                "('Raffaello Sanzio', '1483-03-28', '1520-04-06')," +
                "('Leonardo da Vinci', '1452-04-15', '1519-05-02')," +
                "('Antoon van Dyck', '1599-03-22', '1641-12-09')," +
                "('Marc Chagall', '1887-07-07', '1985-03-28')," +
                "('Caravaggio', '1571-09-29', '1610-07-18')";

        db.execSQL(insert_autori);
    }
}
