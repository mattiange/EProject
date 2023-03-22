package it.sms.eproject.data.classes;

import androidx.annotation.NonNull;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Gestisce i dati dei percorsi
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Percorso {

    int ID;
    int durata;
    int codiceUtente;
    String nome;
    String descrizione;

    long codice_citta;

    /** Da Implementare**/
    Museo museo;
    Zona zone;
    Oggetto oggetti;


    public Percorso(int ID, String nome, String descrizione, int durata, int codiceUtente, long codice_citta) {
        this.ID                 = ID;
        this.nome               = nome;
        this.descrizione        = descrizione;
        this.durata             = durata;
        this.codiceUtente       = codiceUtente;
        this.codice_citta       = codice_citta;
    }
    public Percorso(String nome, String descrizione, int durata, int codiceUtente, long codice_citta) {
        this(-1, nome, descrizione, durata, codiceUtente, codice_citta);
    }

    public void setCodice_citta(long codice_citta) {
        this.codice_citta = codice_citta;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public long getCodice_citta() {
        return codice_citta;
    }

    public int getDurata() {
        return durata;
    }

    public void setCodiceUtente(int codiceUtente) {
        this.codiceUtente = codiceUtente;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

    public int getCodiceUtente() {
        return codiceUtente;
    }

    @NonNull
    @Override
    public String toString() {
        return "{id="+getID()+
                ", nome="+getNome()+
                ", descrizione="+getDescrizione()+
                ", durata="+getDurata()+
                ", codice_citta="+getCodice_citta()+
                ", codice_utente="+getCodiceUtente()+"}";
    }
}