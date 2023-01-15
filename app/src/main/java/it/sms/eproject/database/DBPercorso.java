package it.sms.eproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.data.classes.Percorso;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBPercorso extends DbManager{
    public DBPercorso(Context context) {
        super(context);
    }

    /**
     * Inserisce un nuovo percorso nel database
     *
     * @param p Percorso da inserire
     * @param m Musei da legare al percorso
     * @param o Oggetti da legare al percorso
     * @return true|null Restituisce true se l'inserimento è andato a buon fine, false altrimenti.
     */
    public boolean inserisciPercorso(Percorso p, List<Museo> m, List<Oggetto> o){
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            ContentValues cv_percorso = new ContentValues();
            cv_percorso.put("nome", p.getNome());
            cv_percorso.put("descrizione", p.getDescrizione());
            cv_percorso.put("durata", p.getDurata());
            cv_percorso.put("codice_utente", p.getCodiceUtente());

            //db.execSQL(insert_percorso);
            long percorso_id = db.insert("percorsi", null, cv_percorso);

            //inserimento dei musei
            if(m != null){
                for(Museo museo : m){
                    String insert_musei_percorso = "INSERT INTO musei_has_percorsi (museo_codice, percorso_codice) " +
                            "VALUES (" +
                            museo.getID() + ", " +
                            percorso_id +
                            ")";
                    db.execSQL(insert_musei_percorso);
                }
            }

            //inserimento degli oggetti
            if(o != null){
                for(Museo oggetto : m){
                    String insert_oggetti_percorso = "INSERT INTO oggetti_has_percorsi (oggetto_codice, percorso_codice) " +
                            "VALUES (" +
                            oggetto.getID() + ", " +
                            percorso_id +
                            ")";
                    db.execSQL(insert_oggetti_percorso);
                }
            }

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }finally {
            db.close();
        }
    }
}
