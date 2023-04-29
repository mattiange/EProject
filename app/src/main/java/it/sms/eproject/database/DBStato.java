package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Stato;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBStato extends DbManager{
    public DBStato(Context context) {
        super(context);
    }

    /**
     * Restituisce tutti gli stati presenti nel database
     *
     * @return ArrayList degli stati
     */
    public ArrayList<Stato> elencoStati()      {
        String query="SELECT * FROM stati ORDER BY nome";
        SQLiteDatabase db= helper.getReadableDatabase();

        ArrayList<Stato> stati = null;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            stati = new ArrayList<>();

            do {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    stati.add(new Stato(
                            Integer.parseInt(c.getString(0)),
                            c.getString(1)
                    ));
                }

            } while(c.moveToNext());
        }
        c.close();

        return stati;
    }
}
