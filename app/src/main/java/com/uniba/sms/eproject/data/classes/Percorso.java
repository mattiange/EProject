package com.uniba.sms.eproject.data.classes;

public class Percorso {

    int ID;
    String nome;
    String descrizione;
    int durata;

    /** Da Implementare**/
    Museo museo = new Museo();
    Zona zone = new Zona();
    Oggetto oggetti = new Oggetto();


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
