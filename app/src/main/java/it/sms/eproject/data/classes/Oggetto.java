package it.sms.eproject.data.classes;

import androidx.annotation.NonNull;

import java.time.LocalDate;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Gestisce i dati degli oggetti
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Oggetto {

    int id;
    int codice_citta;
    int anno;
    int autore;
    int durataVisita;

    String nome;
    String descrizione;


    public Oggetto(String nome, int anno, int autore, String descrizione, int codice_citta, int durataVisita) {
        this(-1, nome, anno, autore, descrizione, codice_citta, durataVisita);
    }

    public Oggetto(int id, String nome, int anno, int autore, String descrizione, int codice_citta, int durataVisita) {
        this.id             = id;
        this.nome           = nome;
        this.anno           = anno;
        this.autore         = autore;
        this.durataVisita   = durataVisita;
        this.descrizione    = descrizione;
        this.codice_citta   = codice_citta;
    }

    /**
     * Costruttore tampone, utilizzato per applicare di default
     * la durata di una visita a 10 minuti.
     *
     * =================================================================
     * Appena possibile modificare l'app per permettere di inserire
     * e gestire (CRUD) la durata di una visita direttamente in fase
     * di creazione/modifica dell'oggetto
     * =================================================================
     *
     * @param nome
     * @param anno
     * @param autore
     * @param descrizione
     * @param codice_citta
     */
    public Oggetto(String nome, int anno, int autore, String descrizione, int codice_citta) {
        this(-1, nome, anno, autore, descrizione, codice_citta, 10);
    }

    /**
     * Costruttore tampone, utilizzato per applicare di default
     * la durata di una visita a 10 minuti.
     *
     * =================================================================
     * Appena possibile modificare l'app per permettere di inserire
     * e gestire (CRUD) la durata di una visita direttamente in fase
     * di creazione/modifica dell'oggetto
     * =================================================================
     *
     * @param nome
     * @param anno
     * @param autore
     * @param descrizione
     * @param codice_citta
     */
    public Oggetto(int id, String nome, int anno, int autore, String descrizione, int codice_citta) {
        this(id, nome, anno, autore, descrizione, codice_citta, 10);
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
    public void setAnno(int anno) {
        this.anno = anno;
    }

    /**
     * Imposta la durata di una visita per l'oggetto
     *
     * @param durataVisita Durata della visita, espressa in minuti
     */
    public void setDurataVisita(int durataVisita) {
        this.durataVisita = durataVisita;
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
    public int getAnno() {
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

    /**
     * Restituisce la durata della visita, espressa in minuti
     *
     * @return Durata della visita
     */
    public int getDurataVisita() {
        return durataVisita;
    }

    @NonNull
    @Override
    public String toString() {
        return "{id: " + this.getId()+ ", codice_citta: "
                        + this.getCodice_citta() + ", anno: "
                        + this.getAnno() + ", durata_visita: "
                        + this.getDurataVisita() + ", autore: "
                        + this.getAutore()
                + "}";
    }
}