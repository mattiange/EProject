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
    public ArrayList<Citta> elencoCitta(){
        String query="SELECT * FROM citta ORDER BY nome";
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


}
