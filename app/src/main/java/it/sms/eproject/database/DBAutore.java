package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;

import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.data.classes.Museo;

/**
 * Gestisce le operazioni per gli stati nel database
 */
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    al.add(new Autore(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2).equals("null") ? null : LocalDate.parse(c.getString(2)),
                            c.getString(3).equals("null") ? null : LocalDate.parse(c.getString(3)),
                            c.getString(4)
                    ));
                }

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }
}
