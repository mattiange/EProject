package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Struttura della città
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Citta {
    private final int codice;
    private final int codice_provincia;
    private String nome;
    private String cap;

    /**
     * Crea una nuova cittò
     *
     * @param codice Codice dello stato
     * @param nome   Nome dello stato
     * @param cap    CAP della città
     */
    public Citta(int codice, String nome, String cap, int codice_provincia){
        this.codice = codice;
        this.nome   = nome;
        this.cap    = cap;
        this.codice_provincia = codice_provincia;
    }

    /**
     * Costruttore utilizzato per creare una città non presente
     * nel database.
     * Viene impostato il valore ID a -1
     *
     * @param nome Nome
     */
    public Citta(String nome, String cap, int codice_provincia){
        this(-1, nome, cap, codice_provincia);
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
     * Imposta il CAP
     *
     * @param cap CAP della città
     */
    public void setCap(String cap) {
        this.cap = cap;
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
     * Restitusci il CAP
     *
     * @return CAP della città
     */
    public String getCap() {
        return cap;
    }

    /**
     * Restituisce il codice della provincia
     *
     * @return Codice
     */
    public int getCodice_provincia() {
        return codice_provincia;
    }
}
