package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.Autore;

/**
 * Gestisce i dati dei percorsi
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class Percorso {

    int ID;
    String nome;
    String descrizione;
    int durata;

    /** Da Implementare**/
    Museo museo;
    Zona zone;
    Oggetto oggetti;


    public Percorso(int ID, String nome, String descrizione, int durata) {
        this.ID = ID;
        this.nome = nome;
        this.descrizione = descrizione;
        this.durata = durata;

    }

    public Percorso() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }
}