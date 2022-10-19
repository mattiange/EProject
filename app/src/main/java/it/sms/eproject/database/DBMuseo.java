package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;

import it.sms.eproject.annotazioni.Autore;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Stato;

/**
 * Gestisce le operazioni per gli stati nel database
 */
public class DBMuseo extends DbManager{
    public DBMuseo(Context context) {
        super(context);
    }

    /**
     * Inserisce un nuovo museo all'interno del database
     *
     * @param m Museo da inserire
     * @return
     */
    @Autore(autore = "Mattia")
    public boolean inserisciMuseo(Museo m){
        String insert1="INSERT INTO musei (codice, nome, numero_telefono, indirizzo, email_contatti, sito_web, orario_apertura, immagine_museo, citta_codice) "
                + "VALUES (NULL," +
                "'"+m.getNome()+"','"+
                m.getTelefono()+"', '"+
                m.getIndirizzo()+"', '"+
                m.getEmail()+"', '"+
                m.getSito_web()+"', '"+
                m.getOrario()+"', '"+
                m.getImmagine()+"', '"+
                m.getImmagine() +"'" +
                ");";

        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }
}
