package it.sms.eproject.database.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.database.helper.create.CreateTable;
import it.sms.eproject.database.helper.insert.InsertAutori;
import it.sms.eproject.database.helper.insert.InsertMusei;
import it.sms.eproject.database.helper.insert.InsertOggetti;
import it.sms.eproject.database.helper.insert.InsertPercorsi;
import it.sms.eproject.database.helper.insert.InsertUtentiPermessi;
import it.sms.eproject.database.helper.insert.InsertZone;

/**
 * Crea il database e inserisce i valori di default
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
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
    private void inizializza(SQLiteDatabase db) {
        try{
            new InsertUtentiPermessi(db).execute();
            new InsertZone(db).execute();
            new InsertAutori(db).execute();
            new InsertMusei(db).execute();
            new InsertOggetti(db).execute();
            new InsertPercorsi(db).execute();
        }catch(SQLException ex){
            Toast.makeText(this.context , "inizializza() => " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            new CreateTable(db).execute();
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