package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Gestisce i dati degli oggetti
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Oggetto {

    int id;
    int codice_citta;
    String nome;
    String anno;
    int autore;
    String descrizione;


    public Oggetto(String nome, String anno, int autore, String descrizione, int codice_citta) {
        this(-1, nome, anno, autore, descrizione, codice_citta);
    }

    public Oggetto(int id, String nome, String anno, int autore, String descrizione, int codice_citta) {
        this.id = id;
        this.nome = nome;
        this.anno = anno;
        this.autore = autore;
        this.descrizione = descrizione;
        this.codice_citta = codice_citta;
    }


    /**
     * imposta il codice dell'autore
     *
     * @param autore Codice
     */
    public void setAutore(int autore) {
        this.autore = autore;
    }

    /**
     * imposta il nome dell'oggetto
     *
     * @param nome Nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * imposta l'anno in cui l'oggetto è stato realizzato
     *
     * @param anno Anno
     */
    public void setAnno(String anno) {
        this.anno = anno;
    }

    /**
     * imposta la descrizione dell'oggetto
     *
     * @param descrizione Descrizione
     */
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    /**
     * imposta il codice della città
     *
     * @param codice_citta Codice
     */
    public void setCodice_citta(int codice_citta) {
        this.codice_citta = codice_citta;
    }

    /**
     * restituisce la descrizione
     *
     * @return Descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * imposta il codice dell'oggetto
     *
     * @param id Codice
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * restituisce il nome
     *
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * restituisce l'anno di realizzazione
     *
     * @return Anno
     */
    public String getAnno() {
        return anno;
    }

    /**
     * restituisce il codice dell'autore
     *
     * @return codice
     */
    public int getAutore() {
        return autore;
    }

    /**
     * restituisce il codice dell'oggetto
     *
     * @return codice
     */
    public int getId() {
        return id;
    }

    /**
     * restituisce il codice della città
     *
     * @return codice
     */
    public int getCodice_citta() {
        return codice_citta;
    }
}