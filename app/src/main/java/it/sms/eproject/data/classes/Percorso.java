package it.sms.eproject.data.classes;

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

    /** Da Implementare**/
    Museo museo;
    Zona zone;
    Oggetto oggetti;


    public Percorso(int ID, String nome, String descrizione, int durata, int codiceUtente) {
        this.ID                 = ID;
        this.nome               = nome;
        this.descrizione        = descrizione;
        this.durata             = durata;
        this.codiceUtente       = codiceUtente;
    }
    public Percorso(String nome, String descrizione, int durata, int codiceUtente) {
        this(-1, nome, descrizione, durata, codiceUtente);
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
}