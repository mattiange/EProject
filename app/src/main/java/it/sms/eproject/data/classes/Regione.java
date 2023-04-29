package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Struttura della regione
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Regione {
    private final int codice;
    private final int codice_stato;
    private String nome;

    /**
     * Crea un nuovo stato
     *
     * @param codice Codice dello stato
     * @param nome   Nome dello stato
     */
    public Regione(int codice, String nome, int codice_stato){
        this.codice = codice;
        this.nome   = nome;
        this.codice_stato = codice_stato;
    }

    /**
     * Costruttore utilizzato per creare uno stato non presente
     * nel database.
     * Viene impostato il valore ID a -1
     *
     * @param nome Nome
     */
    public Regione(String nome, int codice_stato){
        this(-1, nome, codice_stato);
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
     * Restituisce il codice dello stato
     *
     * @return Codice
     */
    public int getCodice_stato() {
        return codice_stato;
    }
}
