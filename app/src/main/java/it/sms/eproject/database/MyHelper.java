package it.sms.eproject.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyHelper  extends SQLiteOpenHelper {

    private Context context;
    /**
     *
     * @param context
     * @param name      Nome del database
     * @param factory
     * @param version   Versione del database
     */
    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     *
     * @param db Database reference
     */
    private void inizializza(SQLiteDatabase db)      {
        String insert1="INSERT INTO utenti (codice, nome, cognome, codice_fiscale, data_di_nascita, Email, Password) " +
                "VALUES (NULL,'Alessandro','Manzoni', 'MNZSS80C18H098L', '1980-03-18', 'a.manzoni@gmail.com', 'test')," +
                "(NULL,'Mario','Rossi', 'MRRSSIZSS80D18H098L', '1980-04-18', 'm.rossi@gmail.com', 'test')," +
                "(NULL,'Mattia Leonardo','Angelillo', 'NGLMTL93A25H096J', '1993-01-25', 'm.angelillo@gmail.com', 'test')";
        try{
            db.execSQL(insert1);
        }catch(SQLException ex){
            Toast.makeText(this.context , "inizializza() => " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String utente="CREATE TABLE utenti (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "cognome TEXT NOT NULL," +
                "codice_fiscale VARCHAR NOT NULL," +
                "data_di_nascita DATE NOT NULL," +
                "data_inserimento DATE DEFAULT CURRENT_TIMESTAMP," +
                "ultima_modifica DATE DEFAULT CURRENT_TIMESTAMP," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL" +
                ")";

        String permessi = "CREATE TABLE permessi (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "permesso varchar" +
                ");";

        String permessi_has_utente = "CREATE TABLE Permesso_has_Utente (" +
                "codice_utente INTEGER," +
                "codice_permesso tINTEGER," +
                "FOREIGN KEY(Utente_ID) REFERENCES permessi(codice)," +
                "FOREIGN KEY(codice_permesso) REFERENCES utenti(codice)," +
                "PRIMARY KEY(codice_utente,codice_permesso)" +
                ");";

        /*String zona = "CREATE TABLE Zone (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "Provincia TEXT," +
                "Regione TEXT," +
                "CAP TEXT)";

        String oggetto = "CREATE TABLE Oggetto (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "Anno TEXT," +
                "Autore TEXT," +
                "id_zona INTEGER," +
                "Descrizione TEXT)";

        String museo = "CREATE TABLE Museo (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "Numero_Telefono TEXT," +
                "Indirizzo TEXT," +
                "Citta TEXT," +
                "Provincia TEXT," +
                "CAP TEXT," +
                "Regione TEXT," +
                "Email_Contatti TEXT UNIQUE," +
                "Sito_Web TEXT," +
                "Orario_Apertura TEXT," +
                "Immagine_Museo TEXT)";

        String permessi_has_utente = "CREATE TABLE Permessi_has_Utente (" +
                "Permessi_id INTEGER," +
                "Utente_ID tINTEGER," +
                "FOREIGN KEY(Utente_ID) REFERENCES Utente_Registrato(ID)," +
                "FOREIGN KEY(Permessi_id) REFERENCES Permessi(id)," +
                "PRIMARY KEY(Permessi_id,Utente_ID)" +
                ");";*/


        try{
            db.execSQL(utente);
            db.execSQL(permessi);
            db.execSQL(permessi_has_utente);
            /*db.execSQL(zona);
            db.execSQL(oggetto);
            db.execSQL(museo);
            db.execSQL(permessi);
            db.execSQL(permessi_has_utente);*/
        }catch(SQLException ex){
            Toast.makeText(this.context , "onCreate() => " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Eseguo la query di inizizializzazione dei valori di default
        inizializza(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}