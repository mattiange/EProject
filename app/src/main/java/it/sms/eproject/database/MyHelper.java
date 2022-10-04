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
        String insert1="INSERT INTO Utente_Registrato (ID, Nome, Cognome, Username, Email, Password, tipo) " + "VALUES (NULL,'Alessandro','Manzoni', 'test', 'email@gmail.com', 'test', 0)";
        try{
            db.execSQL(insert1);
        }catch(SQLException ex){
            Toast.makeText(this.context , "inizializza() => " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String utente="CREATE TABLE Utente_Registrato (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "Cognome TEXT NOT NULL," +
                "Email TEXT NOT NULL UNIQUE," +
                "Password TEXT NOT NULL," +
                "tipo INTEGER NOT NULL)";

        String zona = "CREATE TABLE Zone (" +
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

        String permessi = "CREATE TABLE Permessi (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "permesso varchar" +
                ");";

        String permessi_has_utente = "CREATE TABLE Permessi_has_Utente (" +
                "Permessi_id INTEGER," +
                "Utente_ID tINTEGER," +
                "FOREIGN KEY(Utente_ID) REFERENCES Utente_Registrato(ID)," +
                "FOREIGN KEY(Permessi_id) REFERENCES Permessi(id)," +
                "PRIMARY KEY(Permessi_id,Utente_ID)" +
                ");";


        try{
            db.execSQL(utente);
            db.execSQL(zona);
            db.execSQL(oggetto);
            db.execSQL(museo);
            db.execSQL(permessi);
            db.execSQL(permessi_has_utente);
        }catch(SQLException ex){
            Toast.makeText(this.context , "onCreate() => " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Eseguo la query
        //inizializza(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}