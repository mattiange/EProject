package com.uniba.sms.eproject.data.classes;

public class Oggetto {

    String nome;
    int anno;
    String autore;
    String descrizione;

    public Oggetto(String nome, int anno, String autore, String descrizione) {

        this.nome = nome;
        this.anno = anno;
        this.autore = autore;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
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

}
