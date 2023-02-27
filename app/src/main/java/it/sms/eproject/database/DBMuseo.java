package it.sms.eproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Stato;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
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
    public boolean inserisciMuseo(Museo m){
        SQLiteDatabase db= helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nome", m.getNome());
        values.put("numero_telefono", m.getTelefono());
        values.put("indirizzo", m.getIndirizzo());
        values.put("email_contatti", m.getEmail());
        values.put("immagine_museo", m.getImmagine());
        values.put("durata_visita", m.getDurata_visita());
        values.put("citta_codice", m.getCitta());


        try{
            long res = db.insert("musei", null, values);

            if(res ==-1) return false;
            else return true;

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
    public Museo getMuseo(long codice){
        String query="SELECT * FROM musei WHERE codice = " + codice;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        Museo m = null;

        if(c.moveToFirst()){
            m = new Museo(
                    c.getInt(0),//Codice
                    c.getString(1),//Nome
                    c.getString(2),//Telefono
                    c.getString(3),//Indirizzo
                    c.getInt(8),//codice_citta
                    c.getString(4),//Email
                    c.getString(5),//Sito web
                    c.getString(6),//Orario
                    c.getBlob(7),//Immagine del museo
                    c.getInt(9)//Durata della visita
            );
        }

        System.out.println();

        return m;
    }

    /**
     * Aggiorna un museo
     *
     * @param m Nuovi dati del museo da aggiornare
     * @return
     */
    @AutoreCodice(autore = "Mattia")
    public boolean aggiornaMuseo(Museo m){
        SQLiteDatabase db= helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nome", m.getNome());
        values.put("numero_telefono", m.getTelefono());
        values.put("indirizzo", m.getIndirizzo());
        values.put("email_contatti", m.getEmail());
        values.put("immagine_museo", m.getImmagine());
        values.put("citta_codice", m.getCitta());

        try{
            long res = db.update("musei", values, "codice = ?", new String[]{String.valueOf(m.getID())});

            if(res == -1) return false;
            else return true;

        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }
    }


    /**
     * Seleziona tutti i musei presenti nel database
     *
     * @return ArrayList<Museo>
     *     Restituisce un ArrayList contenenente tutti i musei presenti nel database
     *
     * @return
     */
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
                        c.getBlob(7),//Immagine del museo
                        c.getInt(9)//Durata della visita
                ));

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }

    /**
     * Seleziona tutti i musei presenti nel database
     *
     * @return ArrayList<Museo>
     *     Restituisce un ArrayList contenenente tutti i musei presenti nel database
     *
     * @param citta Codice della città
     *
     * @return
     */
    public ArrayList<Museo> elencoMuseiByCitta(long citta){
        String query="SELECT * FROM musei WHERE citta_codice=" + citta;
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
                        //c.getBlob(7),//Immagine del museo
                        new byte[]{},
                        c.getInt(9)//Durata della visita
                ));

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }

    /**
     * Cancella un museo
     *
     * @param codice Codice del museo
     * @return true se il museo è stato cancellato, false altrimenti
     */
    @AutoreCodice(autore = "Mattia")
    public boolean eliminaMuseo(int codice){
        String insert1="DELETE FROM musei WHERE codice = " + codice;
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
