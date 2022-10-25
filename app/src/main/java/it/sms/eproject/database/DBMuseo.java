package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     * Restituisce un museo selezionandolo in base al suo codice.
     *
     * @param codice Codice del museo da cercare
     * @return Museo trovato o null se non ci sono musei con quel codice
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public Museo getMuseo(int codice){
        String query="SELECT * FROM musei WHERE codice = " + codice;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        Museo m = null;

        if(c.moveToFirst()){
            System.out.println("======================= OK =========================");

            m = new Museo(
                    c.getInt(0),//Codice
                    c.getString(1),//Nome
                    c.getString(2),//Telefono
                    c.getString(3),//Indirizzo
                    c.getInt(8),//codice_citta
                    c.getString(4),//Email
                    c.getString(5),//Sito web
                    c.getString(6),//Orario
                    c.getBlob(7)//Immagine del museo
            );
        }

        System.out.println();

        return m;
    }


    /**
     * Seleziona tutti i musei presenti nel database
     *
     * @return ArrayList<Museo>
     *     Restituisce un ArrayList contenenente tutti i musei presenti nel database
     *
     * @return
     */
    @Autore(autore = "Mattia")
    public ArrayList<Museo> elencoMusei(){
        String query="SELECT * FROM musei";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<Museo> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                al.add(new Museo(
                        c.getInt(0),//Codice
                        c.getString(1),//Nome
                        c.getString(2),//Telefono
                        c.getString(3),//Indirizzo
                        c.getInt(8),//codice_citta
                        c.getString(4),//Email
                        c.getString(5),//Sito web
                        c.getString(6),//Orario
                        c.getBlob(7)//Immagine del museo
                ));

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }
}