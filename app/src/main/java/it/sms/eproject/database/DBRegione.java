package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;

import it.sms.eproject.data.classes.Regione;
import it.sms.eproject.data.classes.Stato;

/**
 * Gestisce le operazioni per gli stati nel database
 */
public class DBRegione extends DbManager{
    public DBRegione(Context context) {
        super(context);
    }

    /**
     * Restituisce tutte le regioni presenti nel database
     *
     * @return ArrayList delle regioni
     */
    public ArrayList<Regione> elencoRegioni()      {
        String query="SELECT * FROM regioni ORDER BY nome";
        SQLiteDatabase db= helper.getReadableDatabase();

        ArrayList<Regione> regioni = null;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            regioni = new ArrayList<>();

            do {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    regioni.add(new Regione(
                            Integer.parseInt(c.getString(0)),
                            c.getString(1),
                            Integer.parseInt(c.getString(2))
                    ));
                }

            } while(c.moveToNext());
        }
        c.close();

        return regioni;
    }
}
