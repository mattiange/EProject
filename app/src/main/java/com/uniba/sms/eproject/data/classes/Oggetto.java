package com.uniba.sms.eproject.data.classes;

public class Oggetto {

    String nome;
    String anno;
    String autore;
    String descrizione;
    String id_zona;

    public Oggetto(String nome, String anno, String autore, String descrizione, String id_zona) {

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

    public void setId_zona(String id_zona) {
        this.id_zona = id_zona;
    }

    public String getId_zona() {
        return id_zona;
    }
}
