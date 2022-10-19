package it.sms.eproject.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import it.sms.eproject.annotazioni.Autore;

/**
 * Crea il database e inserisce i valori di default
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class MyHelper  extends SQLiteOpenHelper {

    private Context context;
    /**
     *
     * @param context
     * @param name      Nome del database
     * @param factory
     * @param version   Versione del database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     *
     * @param db Database reference
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void inizializza(SQLiteDatabase db)      {
        String insert1="INSERT INTO utenti (codice, nome, cognome, codice_fiscale, data_di_nascita, Email, Password) " +
                "VALUES (NULL,'Alessandro','Manzoni', 'MNZSS80C18H098L', '1980-03-18', 'a.manzoni@gmail.com', 'test')," +
                "(NULL,'Mario','Rossi', 'MRRSSIZSS80D18H098L', '1980-04-18', 'm.rossi@gmail.com', 'test')," +
                "(NULL,'Mattia Leonardo','Angelillo', 'NGLMTL93A25H096J', '1993-01-25', 'm.angelillo@gmail.com', 'test')";
        String insert2 = "INSERT INTO permessi(codice, permesso)" +
                "VALUES (NULL, 'Curatore')," +
                "(NULL, 'Guida turistica')," +
                "(NULL, 'Visitatore')";
        String insert3 = "INSERT INTO permesso_has_utente(codice_utente, codice_permesso)" +
                "VALUES (1, 3), (2, 2), (3, 1)";

        //Inserimento dei dati su stato e citta
        String insert_stati="INSERT INTO stati (nome) " +
                "VALUES ('Italia')";
        String insert_regioni="INSERT INTO regioni (nome, stato_codice) " +
                "VALUES ('Puglia', 1)," +
                        "('Basilicata', 1)";
        String insert_province="INSERT INTO province (nome, regione_codice) " +
                "VALUES ('Bari', 1)," +
                        "('Taranto', 1)," +
                        "('Potenza', 2)," +
                        "('Matera', 2)";
        String insert_citta="INSERT INTO citta (nome, cap, provincia_codice) " +
                "VALUES ('Gioia del Colle', '70023', 1), " +
                "('Bari centro', '70122', 1), " +
                "('Bari Mungivacca', '70126', 1)," +
                "('Abriola', '85010', 3)," +
                "('Potenza centro', '85100', 3)," +
                "('Massafra', '74016', 1)";
        //---------------------------------------------------------------------------

        //Inserimento musei
        String insert_musei = "INSERT INTO musei (nome, numero_telefono, indirizzo, email_contatti, sito_web, orario_apertura, citta_codice) " +
                "VALUES ('Museo archeologico nazionale', ' 080 5285231', 'Piazza dei Martiri del 1799, n.1', NULL, 'https://musei.puglia.beniculturali.it/musei/museo-archeologico-nazionale-castello-di-gioia-del-colle/', '15:00', 1)";
        //---------------------------------------------------------------------------

        try{
            db.execSQL(insert1);
            db.execSQL(insert2);
            db.execSQL(insert3);
            db.execSQL(insert_stati);
            db.execSQL(insert_regioni);
            db.execSQL(insert_province);
            db.execSQL(insert_citta);
            db.execSQL(insert_musei);
        }catch(SQLException ex){
            Toast.makeText(this.context , "inizializza() => " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Autore(autore = "Mattia Leonardo Angelillo")
    @Override
    public void onCreate(SQLiteDatabase db) {
        String utenti = "CREATE TABLE utenti (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "cognome TEXT NOT NULL," +
                "codice_fiscale VARCHAR NOT NULL," +
                "data_di_nascita DATE NOT NULL," +
                "data_inserimento DATE DEFAULT CURRENT_TIMESTAMP," +
                "ultima_modifica DATE DEFAULT CURRENT_TIMESTAMP," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL" +
                ")";

        String permessi = "CREATE TABLE permessi (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "permesso varchar" +
                ");";

        String permessi_has_utente = "CREATE TABLE permesso_has_utente (" +
                "codice_utente INTEGER," +
                "codice_permesso INTEGER," +
                "FOREIGN KEY(codice_utente) REFERENCES permessi(codice)," +
                "FOREIGN KEY(codice_permesso) REFERENCES utenti(codice)," +
                "PRIMARY KEY(codice_utente,codice_permesso)" +
                ");";

        String stati = "CREATE TABLE stati(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR NOT NULL" +
                ")";

        String regioni = "CREATE TABLE regioni(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR NOT NULL," +
                "stato_codice INTEGER," +
                "FOREIGN KEY(stato_codice) REFERENCES stati(codice)" +
                ")";

        String province = "CREATE TABLE province(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR NOT NULL," +
                "regione_codice INTEGER," +
                "FOREIGN KEY(regione_codice) REFERENCES regioni(codice)" +
                ")";

        String citta = "CREATE TABLE citta(" +
                "codice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR NOT NULL," +
                "cap VARCHAR NOT NULL," +
                "provincia_codice INTEGER," +
                "FOREIGN KEY(provincia_codice) REFERENCES province(codice)" +
                ")";

        /*String zone = "CREATE TABLE zone (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "citta_codice INTEGER," +
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";*/

        String percorsi = "CREATE TABLE percorsi (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "descrizione TEXT," +
                "durata INTEGER," +
                "codice_percorso INTEGER," +
                "FOREIGN KEY(codice_percorso) REFERENCES utenti(codice)" +
                ")";

        String oggetti_has_percorsi = "CREATE TABLE oggetti_has_percorsi (" +
                "percorso_codice INTEGER NOT NULL," +
                "oggetto_codice INTEGER NOT NULL," +
                "PRIMARY KEY(percorso_codice, oggetto_codice)" +
                ")";

        String autori = "CREATE TABLE autori (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "dato_di_nascita INTEGER," +
                "dato_di_morte INTEGER," +
                "descrizione TEXT," +
                "citta_codice INTEGER," +
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";

        String oggetti = "CREATE TABLE oggetti (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "anno INTEGER," +
                "descrizione TEXT," +
                "autore_codice INTEGER," +
                "citta_codice INTEGER," +
                "FOREIGN KEY(autore_codice) REFERENCES autori(codice)," +
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";

        String attivita = "CREATE TABLE attivita (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "attivita VARCHAR NOT NULL," +
                "ultima_modifica INTEGER," +
                "data_creazione INTEGER," +
                "descrizione TEXT," +
                "autore_codice INTEGER," +
                "zona_codice INTEGER," +
                "FOREIGN KEY(autore_codice) REFERENCES autori(codice)," +
                "FOREIGN KEY(zona_codice) REFERENCES zone(codice)" +
                ")";

        String musei = "CREATE TABLE musei (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR NOT NULL," +
                "numero_telefono VARCHAR," +
                "indirizzo VARCHAR," +
                "email_contatti VARCHAR," +
                "sito_web VARCHAR," +
                "orario_apertura VARCHAR," +
                "immagine_museo VARCHAR," +
                "citta_codice INTEGER," +
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";

        String oggetti_has_attivita = "CREATE TABLE oggetti_has_attivita (" +
                "attivita_codice INTEGER NOT NULL," +
                "oggetti_codice INTEGER NOT NULL," +
                "PRIMARY KEY(attivita_codice, oggetti_codice)," +
                "FOREIGN KEY(attivita_codice) REFERENCES attivita(codice)," +
                "FOREIGN KEY(oggetti_codice) REFERENCES oggetti(codice)" +
                ")";

        String attivita_has_musei = "CREATE TABLE attivita_has_musei (" +
                "attivita_codice INTEGER NOT NULL," +
                "musei_codice INTEGER NOT NULL," +
                "PRIMARY KEY(attivita_codice, musei_codice)," +
                "FOREIGN KEY(attivita_codice) REFERENCES attivita(codice)," +
                "FOREIGN KEY(musei_codice) REFERENCES oggetti(musei)" +
                ")";

        String valutazioni = "CREATE TABLE valutazioni (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "valutazione INTEGER NOT NULL," +
                "utenti_codice INTEGER NOT NULL," +
                "FOREIGN KEY(utenti_codice) REFERENCES utenti(codice)" +
                ")";

        String attivita_has_valutazioni = "CREATE TABLE attivita_has_valutazioni (" +
                "attivita_codice INTEGER NOT NULL ," +
                "valutazioni_codice INTEGER NOT NULL," +
                "PRIMARY KEY(attivita_codice, valutazioni_codice)," +
                "FOREIGN KEY(attivita_codice) REFERENCES attivita(codice)," +
                "FOREIGN KEY(valutazioni_codice) REFERENCES valutazioni(codice)" +
                ")";

        String musei_has_valutazioni = "CREATE TABLE musei_has_valutazioni (" +
                "valutazioni_codice INTEGER NOT NULL ," +
                "museo_codice INTEGER NOT NULL," +
                "PRIMARY KEY(museo_codice, valutazioni_codice)," +
                "FOREIGN KEY(museo_codice) REFERENCES musei(codice)," +
                "FOREIGN KEY(valutazioni_codice) REFERENCES valutazioni(codice)" +
                ")";
        String oggetti_has_valutazioni = "CREATE TABLE oggetti_has_valutazioni (" +
                "oggetto_codice INTEGER NOT NULL ," +
                "valutazioni_codice INTEGER NOT NULL," +
                "PRIMARY KEY(oggetto_codice, valutazioni_codice)," +
                "FOREIGN KEY(oggetto_codice) REFERENCES oggetti(codice)," +
                "FOREIGN KEY(valutazioni_codice) REFERENCES valutazioni(codice)" +
                ")";

        try{
            db.execSQL(utenti);
            db.execSQL(permessi);
            db.execSQL(permessi_has_utente);
            db.execSQL(stati);
            db.execSQL(regioni);
            db.execSQL(province);
            db.execSQL(citta);
            //db.execSQL(zone);
            db.execSQL(percorsi);
            db.execSQL(oggetti_has_percorsi);
            db.execSQL(autori);
            db.execSQL(oggetti);
            db.execSQL(attivita);
            db.execSQL(musei);
            db.execSQL(oggetti_has_attivita);
            db.execSQL(attivita_has_musei);
            db.execSQL(valutazioni);
            db.execSQL(attivita_has_valutazioni);
            db.execSQL(musei_has_valutazioni);
            db.execSQL(oggetti_has_valutazioni);
        }catch(SQLException ex){
            Toast.makeText(this.context , "onCreate() => " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Eseguo la query di inizizializzazione dei valori di default
        inizializza(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}