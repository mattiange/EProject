package com.uniba.sms.eproject.data.classes;

public class Zona {

    String id;
    String nome;
    String provincia;
    String regione;
    String cap;

    public Zona(String id, String nome, String provincia, String regione, String cap){
        this.id = id;
        this.nome = nome;
        this.provincia = provincia;
        this.regione = regione;
        this.cap = cap;
    }

    public Zona(String nome, String provincia, String regione, String cap) {
        this(null, nome, provincia, regione, cap);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getCAP() {
        return cap;
    }

    public void setCAP(String cap) { this.cap = cap; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
