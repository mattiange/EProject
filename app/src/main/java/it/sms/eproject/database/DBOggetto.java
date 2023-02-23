package it.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Oggetto;

/**
 * Gestisce le operazioni per gli stati nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class DBOggetto extends DbManager{
    public DBOggetto(Context context) {
        super(context);
    }

    /**
     * Inserisce un nuovo oggetto all'interno del database
     *
     * @param o Oggetto da inserire
     * @return true se l'inserimento ha avuto successo, false altrimenti
     */
    public boolean inserisciOggetto(Oggetto o){
        String insert1="INSERT INTO oggetti (codice, Nome, anno, descrizione, autore_codice, citta_codice) "
                + "VALUES (NULL," +
                "'"+o.getNome()+"','"+
                o.getAnno()+"', '"+
                o.getDescrizione()+"', '"+
                o.getAutore() +"', '" +
                o.getCodice_citta() + "'" +
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
     * Visualizza un elenco con tutti gli oggetti presenti nel database
     *
     * @return ArrayList contenente tutti gli oggetti
     */
    public ArrayList<Oggetto> elencoOggetti(){
        String query="SELECT * FROM oggetti ORDER BY nome";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<Oggetto> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {

                al.add(new Oggetto(
                        c.getInt(0),//id
                        c.getString(1),//nome
                        c.getInt(2),//anno
                        c.getInt(4),//id autore
                        c.getString(3),//descrizione
                        c.getInt(5)//id città
                ));

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }
    /**
     * Seleziona tutti gli oggetti presenti nel database per una data città
     *
     * @return ArrayList<Oggetto>
     *     Restituisce un ArrayList contenenente tutti gli oggetti presenti nel database
     *     per una data cittò
     *
     * @param citta Codice della città
     *
     * @return
     */
    public ArrayList<Oggetto> elencoOggettiByCitta(long citta){
        String query="SELECT * FROM oggetti WHERE citta_codice=" + citta;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        ArrayList<Oggetto> al = null;

        if (c.moveToFirst()){
            al = new ArrayList<>();

            do {
                al.add(new Oggetto(
                        c.getInt(0),//id
                        c.getString(1),//nome
                        c.getInt(2),//anno
                        c.getInt(4),//codice dell'autore
                        c.getString(3),//descrizione
                        c.getInt(5)//codice della città
                ));

            } while(c.moveToNext());
        }

        c.close();

        return al;
    }

    /**
     * Restituisce un oggetto selezionandolo in base al suo codice.
     *
     * @param codice Codice dell'oggetto da cercare
     * @return Oggetto trovato o null se non ce ne sono con quel codice
     */
    public Oggetto getOggetto(int codice){
        String query="SELECT * FROM oggetti WHERE codice = " + codice;
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        Oggetto o = null;

        if(c.moveToFirst()){
            o = new Oggetto(
                    c.getInt(0),//id
                    c.getString(1),//nome
                    c.getInt(3),//anno
                    c.getInt(5),//autore
                    c.getString(4),//descrizione
                    c.getInt(6)//citta
            );
            o.setIndirizzo(c.getString(2));
        }

        c.close();

        return o;
    }

    /**
     * Aggiorna i dati di un autore
     *
     * @param o Oggetto da aggiornare
     * @return true se i dati sono stati aggiornati, false altrimenti
     */
    public boolean aggiornaOggetto(Oggetto o){
        String update = "UPDATE oggetti SET " +
                "Nome = '" + o.getNome() + "', " +
                "anno = '" + o.getAnno() + "', " +
                "descrizione = '" + o.getDescrizione() + "', " +
                "autore_codice = '" + o.getAutore() + "', " +
                "citta_codice = '" + o.getCodice_citta() + "' " +
                "WHERE codice = " + o.getId();

        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(update);

            return true;
        }catch(SQLException ex){
            System.err.println( ex.getMessage() );

            return false;
        }

    }
    /**
     * Cancella un oggetto
     *
     * @param codice Codice dell'oggetto
     * @return true se l'oggetto è stato cancellato, false altrimenti
     */
    @AutoreCodice(autore = "Mattia")
    public boolean eliminaOggetto(int codice){
        String insert1="DELETE FROM oggetti WHERE codice = " + codice;
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
     * Restituisce il nome dell'autore
     *
     * @param codice Codice
     * @return
     */
    public String getNomeAutore(int codice){
        DBAutore db = new DBAutore(this.c);

        return db.getAutore(codice).getNome();
    }

    /**
     * Restituisce il nome della città
     *
     * @param codice Codice
     * @return
     */
    public String getNomeCitta(int codice){
        DBCitta db = new DBCitta(this.c);

        return db.getNomeCitta(codice);
    }
}
