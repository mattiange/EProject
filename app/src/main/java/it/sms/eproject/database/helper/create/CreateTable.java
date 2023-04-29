package it.sms.eproject.database.helper.create;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Crea le tabelle nel database
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CreateTable {
    private SQLiteDatabase db;

    public CreateTable(SQLiteDatabase db){
        this.db = db;
    }

    /**
     * Esegue le creazioni delle tabelle
     *
     * @throws SQLException
     */
    public void execute() throws SQLException{
        this.createUtentiPermessi();
        this.createZone();
        this.createMusei();
        this.createOggetti();
        this.createPercorsi();
        this.createValutazioni();
        this.createAutori();
        this.createAttivita();
        this.createAggiornamento();
    }

    /**
     * Crea le tabelle per la gestione degli utenti
     * e dei permessi a lui associati
     *
     * @throws SQLException
     */
    private void createUtentiPermessi() throws SQLException {
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

        db.execSQL(utenti);
        db.execSQL(permessi);
        db.execSQL(permessi_has_utente);
    }

    /**
     * Crea le zone per i percorsi
     *
     * @throws SQLException
     */
    private void createZone() throws SQLException {
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
                "sigla VARCHAR NOT NULL," +
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

        db.execSQL(stati);
        db.execSQL(regioni);
        db.execSQL(province);
        db.execSQL(citta);
    }

    /**
     * Crea i musei
     *
     * @throws SQLException
     */
    private void createMusei() throws SQLException {
        String musei = "CREATE TABLE musei (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR NOT NULL," +
                "numero_telefono VARCHAR," +
                "indirizzo VARCHAR," +
                "email_contatti VARCHAR," +
                "sito_web VARCHAR," +
                "orario_apertura VARCHAR," +
                "immagine_museo BLOB," +
                "citta_codice INTEGER," +
                "durata_visita INTEGER DEFAULT 0," + //Durata della visita in minuti
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";

        db.execSQL(musei);
    }

    /**
     * Crea gli oggetti
     *
     * @throws SQLException
     */
    private void createOggetti() throws SQLException {

        String oggetti = "CREATE TABLE oggetti (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "indirizzo TEXT NOT NULL," +
                "anno INTEGER," +
                "descrizione TEXT," +
                "autore_codice INTEGER," +
                "citta_codice INTEGER," +
                "durata_visita INTEGER DEFAULT 0," + //Durata della visita in minuti
                "FOREIGN KEY(autore_codice) REFERENCES autori(codice)," +
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";

        db.execSQL(oggetti);
    }

    /**
     * Crea i percorsi
     *
     * @throws SQLException
     */
    private void createPercorsi() throws SQLException {
        String percorsi = "CREATE TABLE percorsi (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "descrizione TEXT," +
                "durata INTEGER," +
                "codice_utente INTEGER," +
                "codice_citta INTEGER, " +
                "FOREIGN KEY(codice_citta) REFERENCES citta(codice), " +
                "FOREIGN KEY(codice_utente) REFERENCES utenti(codice)" +
                ")";
        String oggetti_has_percorsi = "CREATE TABLE oggetti_has_percorsi (" +
                "percorso_codice INTEGER NOT NULL," +
                "oggetto_codice INTEGER NOT NULL," +
                "PRIMARY KEY(percorso_codice, oggetto_codice)" +
                ")";
        String musei_has_percorsi = "CREATE TABLE musei_has_percorsi (" +
                "museo_codice INTEGER NOT NULL," +
                "percorso_codice INTEGER NOT NULL," +
                "PRIMARY KEY(museo_codice, percorso_codice)" +
                ")";

        db.execSQL(percorsi);
        db.execSQL(oggetti_has_percorsi);
        db.execSQL(musei_has_percorsi);
    }

    /**
     * Crea le valutazioni
     *
     * @throws SQLException
     */
    private void createValutazioni() throws SQLException {
        String valutazioni = "CREATE TABLE valutazioni (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "valutazione INTEGER NOT NULL," +
                "utenti_codice INTEGER NOT NULL," +
                "FOREIGN KEY(utenti_codice) REFERENCES utenti(codice)" +
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
        String percorsi_has_valutazioni = "CREATE TABLE percorsi_has_valutazioni (" +
                "percorso_codice INTEGER NOT NULL ," +
                "valutazione_codice INTEGER NOT NULL," +
                "PRIMARY KEY(percorso_codice, valutazione_codice)," +
                "FOREIGN KEY(percorso_codice) REFERENCES percorsi(codice)," +
                "FOREIGN KEY(valutazione_codice) REFERENCES valutazioni(codice)" +
                ")";

        db.execSQL(valutazioni);
        db.execSQL(musei_has_valutazioni);
        db.execSQL(oggetti_has_valutazioni);
        db.execSQL(percorsi_has_valutazioni);
    }

    /**
     * Crea gli autori
     *
     * @throws SQLException
     */
    private void createAutori() throws SQLException {
        String autori = "CREATE TABLE autori (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "data_di_nascita INTEGER," +
                "data_di_morte INTEGER," +
                "descrizione TEXT," +
                "citta_codice INTEGER," +
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";

        db.execSQL(autori);
    }

    /**
     * Crea le attività
     *
     * @throws SQLException
     */
    private void createAttivita() throws SQLException {
        String attivita = "CREATE TABLE attivita (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "attivita VARCHAR NOT NULL," +
                "ultima_modifica INTEGER," +
                "data_creazione INTEGER," +
                "descrizione TEXT," +
                "utente_codice INTEGER," +
                "citta_codice INTEGER," +
                "FOREIGN KEY(utente_codice) REFERENCES utenti(codice)," +
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

        String attivita_has_valutazioni = "CREATE TABLE attivita_has_valutazioni (" +
                "attivita_codice INTEGER NOT NULL ," +
                "valutazioni_codice INTEGER NOT NULL," +
                "PRIMARY KEY(attivita_codice, valutazioni_codice)," +
                "FOREIGN KEY(attivita_codice) REFERENCES attivita(codice)," +
                "FOREIGN KEY(valutazioni_codice) REFERENCES valutazioni(codice)" +
                ")";

        db.execSQL(attivita);
        db.execSQL(oggetti_has_attivita);
        db.execSQL(attivita_has_musei);
        db.execSQL(attivita_has_valutazioni);
    }

    /**
     * Tabella utile a verificare se un aggiornamento è già
     * stato effettuato
     *
     * @throws SQLException
     */
    private void createAggiornamento() throws SQLException {
        String aggiornamenti = "CREATE TABLE aggiornamenti (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "controllo_aggiornamento INTEGER NOT NULL UNIQUE" +
                ")";

        db.execSQL(aggiornamenti);
    }

}
