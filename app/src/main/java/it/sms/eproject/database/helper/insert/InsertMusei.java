package it.sms.eproject.database.helper.insert;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class InsertMusei{
    private SQLiteDatabase db;

    public InsertMusei(SQLiteDatabase db){
        this.db = db;
    }

    public void execute() throws SQLException {
        this.insert();
    }

    private void insert() throws SQLException {
        String insert_musei = "INSERT INTO musei (nome, numero_telefono, indirizzo, email_contatti, sito_web, orario_apertura, citta_codice, durata_visita) " +
                "VALUES " +
                //Gioia del Colle
                "('Museo archeologico', '080 5285231', 'via Dante, 1', NULL, 'https://musei.puglia.beniculturali.it/musei/museo-archeologico-nazionale-castello-di-gioia-del-colle/', '15:00', 1, 60), " +
                "(\"Sergio Gatti Bottega d'Arte\", '333 297 4435', 'via Michele Petrera, 42', NULL, '', '15:00', 1, 60), " +
                "(\"Filippo Maria Cazzolla Bottega D'Arte\", '347 918 3227', 'Via Piottola, 12', NULL, '', '18:00', 1, 60), " +
                "(\"DUMEST | Museo - Museum\", '360 260 046', 'Via Giuseppe di Vittorio, 115', NULL, '', '10:00', 1, 60), " +
                "(\"Antonella Lozito Bottega d'arte\", '331 600 7451', 'Via Lagomagno, 22', NULL, '', '10:00', 1, 30)," +

                //Bari
                "('Pinacoteca Corrado Giaquinto', '080 5412420', 'Via Spalato 19', 'pinacoteca@cittametropolitana.ba.it', 'https://www.pinacotecabari.it/index.php/contatti', '17:00', 2, 120), " +
                "('Museo Archeologico di Santa Scolastica', '', 'via Venezia 73', 'museoarcheologico@cittametropolitana.ba.it', 'https://www.museoarcheologicosantascolastica.it/', '09:00', 2, 120), " +
                "('Museo Diocesano - Sezione Bari', '080 5210064', 'via dei Dottula', 'museobari@odegitria.bari.it', 'https://www.arcidiocesibaribitonto.it/curia/museo-diocesano', '10:00', 2, 60), " +

                //Potenza
                "('Museo Archeologico Nazionale della Basilicata “Dinu Adamesteanu”', '0835 256211', 'Via Serrao', 'pm-bas.museopotenza@beniculturali.it', 'http://musei.basilicata.beniculturali.it/musei/?mid=76&nome=museo-archeologico-nazionale-della-basilicata-dinu-adamesteanu', '09-00', 3, 60), " +
                "('Museo Archeologico Provinciale', '0971 444833', 'Via Lazio', 'museo.provinciale@provinciapotenza.it', 'http://www.provincia.potenza.it/provincia/home.jsp', '08:30', 3, 50)," +

                //Massafra
                "('Castello di Massafra', '099 8858111', 'VIA LO PIZZO', '', 'https://www.museionline.info/puglia-musei-monumenti/castello-di-massafra', '10:00', 4, 100), " +

                //Matera
                "('Museo Laboratorio della Civiltà Contadina', '0835 344057', 'Via San Giovanni Vecchio, 60', 'info@museolaboratorio.it', 'https://museolaboratorio.it/', '09:00', 5, 60), " +
                "('Via San Giovanni Vecchio, 60', '0835 310118', 'Vicinato di vico Solitario, 11', 'info@casagrotta.it', 'http://www.casagrotta.it/', '10:00', 5, 30)";

        db.execSQL(insert_musei);
    }
}
