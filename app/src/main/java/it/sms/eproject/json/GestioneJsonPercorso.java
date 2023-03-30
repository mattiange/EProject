package it.sms.eproject.json;


import static it.sms.eproject.database.DbManager.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.sms.eproject.data.classes.Percorso;

public class GestioneJsonPercorso{

    public String getPercorsoAsJson(long codice_percorso) {
        String query_percorso= "SELECT * FROM percorsi WHERE codice = " + codice_percorso;
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(query_percorso, null);

        if (c.moveToFirst()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("codice", c.getInt(0));
                jsonObject.put("nome", c.getString(1));
                jsonObject.put("descrizione", c.getString(2));
                jsonObject.put("durata", c.getInt(3));
                jsonObject.put("codice_utente", c.getInt(4));
                jsonObject.put("codice_citta", c.getInt(5));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            c.close();
            db.close();
            return jsonObject.toString();
        }

        c.close();
        db.close();
        return null;
    }


}
