package it.sms.eproject.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Crea il database e inserisce i valori di default
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class MyHelper  extends SQLiteOpenHelper {

    private Context context;
    /**
     *
     * @param context
     * @param name      Nome del database
     * @param factory
     * @param version   Versione del database
     */
    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     *
     * @param db Database reference
     */
    private void inizializza(SQLiteDatabase db)      {
        String insert1="INSERT INTO utenti (codice, nome, cognome, codice_fiscale, data_di_nascita, Email, Password) " +
                "VALUES (NULL,'Alessandro','Manzoni', 'MNZSS80C18H098L', '1980-03-18', 'a.manzoni@gmail.com', 'test')," + //id 1
                "(NULL,'Mario','Rossi', 'MRRSSIZSS80D18H098L', '1980-04-18', 'm.rossi@gmail.com', 'test')," +//id 2
                "(NULL,'Mattia Leonardo','Angelillo', 'NGLMTL93A25H096J', '1993-01-25', 'm.angelillo@gmail.com', 'test')";//id 3
        String insert2 = "INSERT INTO permessi(codice, permesso)" +
                "VALUES (NULL, 'Curatore')," +//id 1
                "(NULL, 'Guida turistica')," +//id 2
                "(NULL, 'Visitatore')";//id 3
        String insert3 = "INSERT INTO permesso_has_utente(codice_utente, codice_permesso)" +
                "VALUES (1, 3), (2, 2), (3, 1)";

        //Inserimento dei dati su stato e citta
        String insert_stati="INSERT INTO stati (nome) " +
                "VALUES ('Italia')";
        String insert_regioni="INSERT INTO regioni (nome, stato_codice) " +
                "VALUES ('Puglia', 1)," +
                        "('Basilicata', 1)";
        String insert_province="INSERT INTO province (nome, sigla, regione_codice) " +
                "VALUES ('Bari', 'BA', 1)," +
                        "('Taranto', 'TA', 1)," +
                        "('Potenza', 'PZ', 2)," +
                        "('Matera', 'MT', 2)";
        String insert_citta="INSERT INTO citta (nome, cap, provincia_codice) " +
                "VALUES ('Gioia del Colle', '70023', 1), " +//1
                "('Bari centro', '70122', 1), " +//2
                "('Bari Mungivacca', '70126', 1)," +//3
                "('Abriola', '85010', 3)," +//4
                "('Potenza centro', '85100', 3)," +//5
                "('Massafra', '74016', 1)";//6

        //Inserimento degli autori
        String insert_autori="INSERT INTO autori (nome, data_di_nascita, data_di_morte) " +
                "VALUES ('Donatello', '1386-0-0', '1466-12-13'), " +
                "('Michelangelo Buonarotti', '1475-03-06', '1564-03-06'), " +
                "('Gian Lorenzo Bernini', '1598-12-07', '1680-11-28')," +
                "('Antonio Canova', '1757-11-1', '1822-10-13')";
        //---------------------------------------------------------------------------

        //Inserimento musei
        String insert_musei = "INSERT INTO musei (nome, numero_telefono, indirizzo, email_contatti, sito_web, orario_apertura, citta_codice, durata_visita) " +
                "VALUES ('Museo archeologico', '080 5285231', 'via Dante, 1', NULL, 'https://musei.puglia.beniculturali.it/musei/museo-archeologico-nazionale-castello-di-gioia-del-colle/', '15:00', 1, 60), " +
                        "(\"Sergio Gatti Bottega d'Arte\", '333 297 4435', 'via Michele Petrera, 42', NULL, '', '15:00', 1, 60), " +
                        "(\"Filippo Maria Cazzolla Bottega D'Arte\", '347 918 3227', 'Via Piottola, 12', NULL, '', '18:00', 1, 60), " +
                        "(\"DUMEST | Museo - Museum\", '360 260 046', 'Via Giuseppe di Vittorio, 115', NULL, '', '10:00', 1, 60), " +
                        "(\"Antonella Lozito Bottega d'arte\", '331 600 7451', 'Via Lagomagno, 22', NULL, '', '10:00', 1, 30)";

        // "('Castello normanno-svevo', '080 348 1305', 'Piazza dei Martiri del 1799, 1', NULL, '', '15:00', 1, 60), " +
        //---------------------------------------------------------------------------

        //Inserimento oggetti
        String insert_oggetti = "INSERT INTO oggetti (Nome, indirizzo, anno, descrizione, autore_codice, citta_codice, durata_visita) " +
                "VALUES ('Monumento dei caduti', 'Piazza Cesare Battisti, 12', 0, 'Monumento in ricordo dei caduti di tutte le guerre', 0, 1, 10), " +
                "('Arco Costantinopoli', 'Arco Costantinopoli', '1600', 'L’arco, intitolato alla Madonna di Costantinopoli nel XVII secolo, consente l’accesso ad uno spiazzo, anticamente comunitario, con al centro un pozzo di acqua sorgiva del secolo XVI e con scala esterna in pietra per l’accesso al vano di cui molte volte l’abitazione risulta unicamente costituita.', 0, 1, 10), " +
                "('Arco di Cimone', 'Arco Cimone', 0, 'L’arco con le imposte poco al di sopra del piano stradale introduce nella corte, sede in età normanna del rappresentante dell’abate della Chiesa di S.Nicola di Bari, dopo che a questa Riccardo Siniscalco aveva donato il Castello. Tracce rilevanti dell’antica residenza sono tuttora visibili nella costruzione che si erge al disopra dell’arco. Ancora visibili sono anche l’arco ogivale e la bifora che lo sormonta.', 0, 1, 10)," +
                "('Distilleria Cassano', 'Ex Distilleria Cassano', '1859', \"La Distilleria Cassano è annoverata fra i monumenti dell'archeologia industriale di maggior rilievo in Puglia. Di proprietà di Paolo Cassano dal 1859, si collocava tra le quattro più importanti del nostro territorio assieme a quelle di Castellana, Bari e Barletta, esportando i propri prodotti anche all’estero. L'attività dell'opificio continuò a gonfie vele fino al 1914, quando la società fu messa in liquidazione. La distilleria, passata alla famiglia Taranto e progressivamente lasciata in stato di abbandono, venne poi ceduta alla USL (oggi Azienda sanitaria locale) nel 1970 per farne un ospedale. La costruzione fu nuovamente ceduta al comune di Gioia del Colle nel 1997. L'ex distilleria rappresenta un esempio pionieristico dell'industria pugliese, in ragione di queste considerazioni il Ministero dei Beni Culturali e Ambientali ne ha sancito l'importanza storica con l'iscrizione nell'elenco dei beni monumentali e ambientali. Nel 2006 vengono avviati dei lavori di restauro ed attualmente la distilleria ospita sagre e concerti.\", 0, 1, 30);";
        //---------------------------------------------------------------------------

        //Inserimento percorsi
        String insert_percorsi = "INSERT INTO percorsi (Nome, descrizione, durata, codice_utente, codice_citta) " +
                "VALUES ('Terra di peucezia', 'Visita le origini di Gioia del Colle', 250, 2, 1)";
        //---------------------------------------------------------------------------

        //Inserimento musei has percorsi
        String insert_musei_has_percorsi = "INSERT INTO musei_has_percorsi (museo_codice, percorso_codice) " +
                                            "VALUES (1, 1), (2, 1), (3, 1), (4, 1), (5, 1)";
        //---------------------------------------------------------------------------

        //Inserimento oggetti has percorsi
        String insert_oggetti_has_percorsi = "INSERT INTO oggetti_has_percorsi (oggetto_codice, percorso_codice) " +
                "VALUES (1, 1),(2, 1),(3, 1), (4,1)";
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
            db.execSQL(insert_autori);
            db.execSQL(insert_oggetti);
            db.execSQL(insert_percorsi);
            db.execSQL(insert_musei_has_percorsi);
            db.execSQL(insert_oggetti_has_percorsi);
        }catch(SQLException ex){
            Toast.makeText(this.context , "inizializza() => " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

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
                "codice_utente INTEGER," +
                "codice_citta INTEGER, " +
                "FOREIGN KEY(codice_citta) REFERENCES citta(codice), " +
                "FOREIGN KEY(codice_utente) REFERENCES utenti(codice)" +
                ")";

        String autori = "CREATE TABLE autori (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT NOT NULL," +
                "data_di_nascita INTEGER," +
                "data_di_morte INTEGER," +
                "descrizione TEXT," +
                "citta_codice INTEGER," +
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";

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

        String oggetti_has_percorsi = "CREATE TABLE oggetti_has_percorsi (" +
                "percorso_codice INTEGER NOT NULL," +
                "oggetto_codice INTEGER NOT NULL," +
                "PRIMARY KEY(percorso_codice, oggetto_codice)" +
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
                "immagine_museo BLOB," +
                "citta_codice INTEGER," +
                "durata_visita INTEGER DEFAULT 0," + //Durata della visita in minuti
                "FOREIGN KEY(citta_codice) REFERENCES citta(codice)" +
                ")";

        String musei_has_percorsi = "CREATE TABLE musei_has_percorsi (" +
                "museo_codice INTEGER NOT NULL," +
                "percorso_codice INTEGER NOT NULL," +
                "PRIMARY KEY(museo_codice, percorso_codice)" +
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
        String percorsi_has_valutazioni = "CREATE TABLE percorsi_has_valutazioni (" +
                "percorso_codice INTEGER NOT NULL ," +
                "valutazione_codice INTEGER NOT NULL," +
                "PRIMARY KEY(percorso_codice, valutazione_codice)," +
                "FOREIGN KEY(percorso_codice) REFERENCES percorsi(codice)," +
                "FOREIGN KEY(valutazione_codice) REFERENCES valutazioni(codice)" +
                ")";

        /**
         * Tabella utile a verificare se un aggiornamento è già
         * stato effettuato
         */
        String aggiornamenti = "CREATE TABLE aggiornamenti (" +
                "codice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "controllo_aggiornamento INTEGER NOT NULL UNIQUE" +
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
            db.execSQL(musei_has_percorsi);
            db.execSQL(oggetti_has_attivita);
            db.execSQL(attivita_has_musei);
            db.execSQL(valutazioni);
            db.execSQL(attivita_has_valutazioni);
            db.execSQL(musei_has_valutazioni);
            db.execSQL(oggetti_has_valutazioni);
            db.execSQL(aggiornamenti);
            db.execSQL(percorsi_has_valutazioni);
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