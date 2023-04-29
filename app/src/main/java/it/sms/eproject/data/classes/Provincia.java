package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Struttura della provincia
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Provincia {
    private final int codice;
    private final int codice_regione;
    private String nome;

    private String siglaProvincia;

    /**
     * Crea un nuovo stato
     *
     * @param codice Codice dello stato
     * @param nome   Nome dello stato
     */
    public Provincia(int codice, String nome, String siglaProvincia, int codice_regione){
        this.codice         = codice;
        this.nome           = nome;
        this.siglaProvincia = siglaProvincia;
        this.codice_regione = codice_regione;
    }

    /**
     * Costruttore utilizzato per creare uno stato non presente
     * nel database.
     * Viene impostato il valore ID a -1
     *
     * @param nome Nome
     */
    public Provincia(String nome, String siglaProvincia, int codice_regione){
        this(-1, nome, siglaProvincia, codice_regione);
    }

    /**
     * Imposta il nome dello stato
     *
     * @param nome Nome da assegnare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il nome dello stato
     *
     * @return Nome
     */
    public int getCodice() {
        return codice;
    }

    /**
     * Restituisce il codice dello stato
     *
     * @return Codice
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il codice della regione
     *
     * @return Codice
     */
    public int getCodice_regione() {
        return codice_regione;
    }
}
