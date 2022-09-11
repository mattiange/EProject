package com.uniba.sms.eproject.data.classes;

public class Oggetto {

    int id;
    String nome;
    String anno;
    String autore;
    String descrizione;
    int id_zona;

    public Oggetto(String nome, String anno, String autore, int id_zona, String descrizione) {
        this(-1, nome, anno, autore, descrizione, id_zona);
    }

    public Oggetto(int id, String nome, String anno, String autore, String descrizione, int id_zona) {
        this.id = id;
        this.nome = nome;
        this.anno = anno;
        this.autore = autore;
        this.descrizione = descrizione;
        this.id_zona = id_zona;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public void setId_zona(int id_zona) {
        this.id_zona = id_zona;
    }

    public int getId_zona() {
        return id_zona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
