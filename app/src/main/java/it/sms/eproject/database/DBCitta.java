package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;

import it.sms.eproject.data.classes.Citta;
import it.sms.eproject.data.classes.Stato;

/**
 * Gestisce le operazioni per gli stati nel database
 */
public class DBCitta extends DbManager{
    public DBCitta(Context context) {
        super(context);
    }

    /**
     * Restituisce tutte le città presenti nel database
     *
     * @return ArrayList delle città
     */
    public ArrayList<Citta> elencoCitta(int codice_provincia){
        String query="SELECT * FROM citta WHERE provincia_codice = "+codice_provincia+" ORDER BY nome";
        SQLiteDatabase db= helper.getReadableDatabase();

        ArrayList<Citta> citta = null;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            citta = new ArrayList<>();

            do {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    citta.add(new Citta(
                            Integer.parseInt(c.getString(0)),
                            c.getString(1),
                            c.getString(2),
                            Integer.parseInt(c.getString(3))
                    ));
                }

            } while(c.moveToNext());
        }
        c.close();

        return citta;
    }

    /**
     * Restituisce il nome della città
     * selezionandolo attraverso il
     * suo codice
     *
     * @param codice Codice della cittò
     * @return Nome della cittò se c'è un riscontro, altrimenti
     *          restituisce una stringa vuota
     */
    public String getNomeCitta(int codice){
        String query="SELECT nome FROM citta WHERE codice = "+codice;
        System.out.println("==================> " + query);
        SQLiteDatabase db= helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        String citta = "";
        if (c.moveToFirst()){
            do {

                citta = c.getString(0);

            } while(c.moveToNext());
        }
        c.close();

        return citta;
    }

}
