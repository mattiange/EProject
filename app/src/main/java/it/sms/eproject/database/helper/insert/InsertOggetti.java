package it.sms.eproject.database.helper.insert;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Inserisce i valori di default per gli oggeti
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class InsertOggetti {
    private SQLiteDatabase db;

    public InsertOggetti(SQLiteDatabase db){
        this.db = db;
    }

    public void execute() throws SQLException {
        this.insert();
    }

    private void insert() throws SQLException {
        String insert_oggetti = "INSERT INTO oggetti (Nome, indirizzo, anno, descrizione, autore_codice, citta_codice, durata_visita) " +
                "VALUES " +
                //Gioia del Colle
                "('Monumento dei caduti', 'Piazza Cesare Battisti, 12', 0, 'Monumento in ricordo dei caduti di tutte le guerre', 0, 1, 10), " +
                "('Arco Costantinopoli', 'Arco Costantinopoli', '1600', 'L’arco, intitolato alla Madonna di Costantinopoli nel XVII secolo, consente l’accesso ad uno spiazzo, anticamente comunitario, con al centro un pozzo di acqua sorgiva del secolo XVI e con scala esterna in pietra per l’accesso al vano di cui molte volte l’abitazione risulta unicamente costituita.', 0, 1, 10), " +
                "('Arco di Cimone', 'Arco Cimone', 0, 'L’arco con le imposte poco al di sopra del piano stradale introduce nella corte, sede in età normanna del rappresentante dell’abate della Chiesa di S.Nicola di Bari, dopo che a questa Riccardo Siniscalco aveva donato il Castello. Tracce rilevanti dell’antica residenza sono tuttora visibili nella costruzione che si erge al disopra dell’arco. Ancora visibili sono anche l’arco ogivale e la bifora che lo sormonta.', 0, 1, 10)," +
                "('Distilleria Cassano', 'Ex Distilleria Cassano', '1859', \"La Distilleria Cassano è annoverata fra i monumenti dell'archeologia industriale di maggior rilievo in Puglia. Di proprietà di Paolo Cassano dal 1859, si collocava tra le quattro più importanti del nostro territorio assieme a quelle di Castellana, Bari e Barletta, esportando i propri prodotti anche all’estero. L'attività dell'opificio continuò a gonfie vele fino al 1914, quando la società fu messa in liquidazione. La distilleria, passata alla famiglia Taranto e progressivamente lasciata in stato di abbandono, venne poi ceduta alla USL (oggi Azienda sanitaria locale) nel 1970 per farne un ospedale. La costruzione fu nuovamente ceduta al comune di Gioia del Colle nel 1997. L'ex distilleria rappresenta un esempio pionieristico dell'industria pugliese, in ragione di queste considerazioni il Ministero dei Beni Culturali e Ambientali ne ha sancito l'importanza storica con l'iscrizione nell'elenco dei beni monumentali e ambientali. Nel 2006 vengono avviati dei lavori di restauro ed attualmente la distilleria ospita sagre e concerti.\", 0, 1, 30), " +

                //Bari
                "('Basilica San Nicola', 'Largo Abate Elia 13', '1087', 'La Basilica di San Nicola a Bari è uno degli edifici religiosi più importanti e suggestivi d’Italia.\n" +
                "Patrono di Bari, santo caro sia ai cattolici che agli ortodossi, San Nicola di Myra è il vescovo che secondo la leggenda ha dato le origini a Santa Claus, il nostro Babbo Natale.\n" +
                "Le sue spoglie riposano proprio nella cripta di questa basilica, e perciò è meta di pellegrinaggio da parte di fedeli cattolici e russi ortodossi: si tratta infatti di uno dei pochi luoghi di culto italiani in cui si celebrano le funzioni di entrambi i riti.\n" +
                "La basilica di San Nicola fu edificata proprio per custodire i resti del Santo, nel 1089: oggi è uno dei luoghi più visitati della Puglia e merita una visita per molti motivi. Si tratta di un mirabile esempio di stile romanico e all’interno potrete ammirare i soffitti di legno dorato con dipinti del ‘600, l’altare d’argento, un ciborio (il baldacchino di marmo sopra l’altare) più antico della Puglia, che risale al 1150, e la Cattedra di Elia, una delle sculture romaniche più rappresentative della Puglia.\n" +
                "Il tesoro di San Nicola, che continua ad accrescersi grazie ai doni e agli ex voto dei fedeli, è custodito nel Museo Nicolaiano adiacente alla Basilica e raccoglie tra l’altro argenti, codici miniati e preziose pergamene.'," +
                "0, 2, 45), " +

                /*
                //Potenza
                "(), " +

                //Matera
                "(), " +
                */

                //Massafra
                "('Chiesa ipogea di San Leonardo', 'VIA FRAPPIETRI, 70-72', '1600', 'La chiesa ipogea ha origine con ogni probabilità da una tomba a camera d’età classica, riadattata a luogo sacro in età altomedioevale. Vi si accede attraverso un corridoio o dromos in discesa ed attualmente presenta una vasta aula rettangolare con un solo pilastro quadrangolare.\n" +
                "\n" +
                "La volta è piana e parzialmente crollata; ad est vi è una semi-iconostasi e quindi il bema, leggermente rialzato, che presenta l’abside semi-circolare, ed il solo diaconicon alla sua sinistra. Infatti, l’escavazione, alla destra dell’abside, della prothesis – l’altro elemento costante dell’architettura bizantina del bema – sembra essere stata interrotta per dissesti statici nel banco di roccia.\n" +
                "\n" +
                "Notevole la dotazione degli affreschi sacri della chiesa rupestre: a partire da sinistra, sul setto iconostatico troviamo effigiati i resti dei ritratti degli apostoli Sant’Andrea e San Pietro, quindi un santo diacono, probabilmente Santo Stefano; sul primo pilastro, un San Nicola, affiancato dall’Arcangelo Gabriele sul muretto semi-crollato di accesso all’abside, e la Vergine Annunziata sul setto opposto, affiancata al pilastro, ove restano labili tracce di una aureola.\n" +
                "\n" +
                "Nel sottarco tra i due pilastri sono dipinti dei Santi Medici San Cosma (a destra) e San Damiano (a sinistra), di influsso comneno, risalenti al XIII secolo. Anche il San Paolo Eremita e il Sant’Antonio Abate, affrescati nel sottarco tra l’aula e il diaconicon, appartengono all’arte bizantina conservatrice di questo periodo.\n" +
                "\n" +
                "Nell’abside è effigiata una bella rappresentazione di scuola bizantina del Pantocratore in déesis risalente al XIV secolo. Tracce di altri affreschi sono rinvenibili sul pilastro dell’aula e sulla parete ovest della cripta, quest’ultima raffigurante, molto probabilmente, una Annunciazione.', 0, 4, 30)";

        db.execSQL(insert_oggetti);
    }
}
