package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Struttura dello stato
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Stato {
    private final int codice;
    private String nome;

    /**
     * Crea un nuovo stato
     *
     * @param codice Codice dello stato
     * @param nome   Nome dello stato
     */
    public Stato(int codice, String nome){
        this.codice = codice;
        this.nome   = nome;
    }

    /**
     * Costruttore utilizzato per creare uno stato non presente
     * nel database.
     * Viene impostato il valore ID a -1
     *
     * @param nome Nome
     */
    public Stato(String nome){
        this(-1, nome);
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
}
