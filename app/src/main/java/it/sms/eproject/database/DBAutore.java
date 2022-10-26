package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.data.classes.Museo;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBAutore extends DbManager{
    public DBAutore(Context context) {
        super(context);
    }

    /**
     * Inserisce un nuovo autore all'interno del database
     *
     * @param a Autore da inserire
     * @return
     */
    public boolean inserisciAutore(Autore a){
        String insert1="INSERT INTO autori (codice, nome, data_di_nascita, data_di_morte, descrizione) "
                + "VALUES (NULL," +
                "'"+a.getNome()+"','"+
                a.getDataDiNascita()+"', '"+
                a.getDataDiMorte()+"', '"+
                a.getDescrizione() +"'" +
                ");";

        System.out.println("===================> " + insert1);

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
     * Restituisce un autore selezionandolo in base al suo codice.
     *
     * @param codice Codice dell'autore da cercare
     * @return Autore trovato o null se non ci sono autori con quel codice
     */
    public Autore getAutore(int codice){
        String query="SELECT * FROM autori WHERE codice = " + codice;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        Autore a = null;

        if(c.moveToFirst()){
            System.out.println("======================= OK =========================");

            LocalDate data_di_nascita   = null;
            LocalDate data_di_morte     = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                try {
                    data_di_nascita = LocalDate.parse(c.getString(2));
                    data_di_morte   = LocalDate.parse(c.getString(3));
                }catch (DateTimeParseException e){

                }
            }
            a = new Autore(
                    c.getInt(0),
                    c.getString(1),
                    data_di_nascita,
                    data_di_morte,
                    c.getString(4)
            );
        }

        System.out.println();

        return a;
    }

    /**
     * Visualizza un elenco con tutti gli autori presenti nel database
     *
     * @return
     */
    public ArrayList<Autore> elencoAutori(){
        String query="SELECT * FROM autori";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<Autore> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                LocalDate data_di_nascita   = null;
                LocalDate data_di_morte     = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    try {
                        data_di_nascita = LocalDate.parse(c.getString(2));
                        data_di_morte   = LocalDate.parse(c.getString(3));
                    }catch (DateTimeParseException e){}
                }

                al.add(new Autore(
                        c.getInt(0),
                        c.getString(1),
                        data_di_nascita,
                        data_di_morte,
                        c.getString(4)
                ));

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }
}
