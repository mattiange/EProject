package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;

import it.sms.eproject.data.classes.Citta;
import it.sms.eproject.data.classes.Provincia;

/**
 * Gestisce le operazioni per gli stati nel database
 */
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
                            Integer.parseInt(c.getString(2))
                    ));
                }

            } while(c.moveToNext());
        }
        c.close();

        return province;
    }


}
