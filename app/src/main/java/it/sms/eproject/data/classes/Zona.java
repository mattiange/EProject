package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Gestisce i dati della zona
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Zona {

    int id;
    String nome;
    String provincia;
    String regione;
    String cap;

    public Zona(int id, String nome, String provincia, String regione, String cap){
        this.id = id;
        this.nome = nome;
        this.provincia = provincia;
        this.regione = regione;
        this.cap = cap;
    }

    public Zona(String nome, String provincia, String regione, String cap) {
        this(-1, nome, provincia, regione, cap);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}