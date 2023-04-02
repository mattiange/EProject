package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.time.LocalDate;

import it.sms.eproject.data.classes.Permesso;
import it.sms.eproject.data.classes.Utente;

public class DBUtente extends DbManager{

    public DBUtente(Context context) {
        super(context);
    }

    /**
     * Restituisce i dati dell'utente
     *
     * @param codice ID dell'utente
     * @return Utente trovato
     */
    public Utente getUtente(int codice){
        String query="SELECT utenti.codice as u_codice, nome, cognome, codice_fiscale, data_di_nascita, email, " +
                "permessi.codice as p_codice, permesso " +
                "FROM utenti, permessi, permesso_has_utente " +
                "WHERE permessi.codice = permesso_has_utente.codice_permesso AND utenti.codice = permesso_has_utente.codice_utente AND utenti.codice = " + codice;
        SQLiteDatabase db= helper.getReadableDatabase();

        Log.d("QUERY", query);

        Utente utente = null;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){

            do {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    utente = new Utente(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        LocalDate.parse(c.getString(4)),
                        c.getString(5),
                        new Permesso(
                                c.getInt(6),
                                c.getString(7)
                        )
                    );
                }

            } while(c.moveToNext());
        }
        c.close();

        return utente;
    }
}
