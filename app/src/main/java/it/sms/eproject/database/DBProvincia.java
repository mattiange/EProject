package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Citta;
import it.sms.eproject.data.classes.Provincia;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBProvincia extends DbManager{
    public DBProvincia(Context context) {
        super(context);
    }

    /**
     * Restituisce tutte le città presenti nel database
     *
     * @return ArrayList delle città
     */
    public ArrayList<Provincia> elencoProvince(int codice_regione){
        String query="SELECT * FROM province WHERE regione_codice = "+codice_regione+" ORDER BY nome";
        SQLiteDatabase db= helper.getReadableDatabase();

        ArrayList<Provincia> province = null;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            province = new ArrayList<>();

            do {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    province.add(new Provincia(
                            Integer.parseInt(c.getString(0)),
                            c.getString(1),
                            c.getString(2),
                            Integer.parseInt(c.getString(3))
                    ));
                }

            } while(c.moveToNext());
        }
        c.close();

        return province;
    }

    /**
     * Restituisce la siglia di una provincia
     *
     * @param codice Codice della provincia
     * @return
     */
    public String getSiglaProvincia(long codice){
        String query="SELECT sigla FROM province WHERE codice = "+codice;
        System.out.println(query);
        SQLiteDatabase db= helper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        String provincia = "";
        if (c.moveToFirst()){
            do {
                provincia = c.getString(0);
            } while(c.moveToNext());
        }
        c.close();

        return provincia;
    }

}
