package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.Autore;

/**
 * Gestisce i dati del museo
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class Museo {

    int ID;
    String nome;
    String telefono;
    String indirizzo;
    String citta;
    String regione;
    String provincia;
    String cap;
    String email;
    String sito_web;
    String orario;
    byte[] immagine;

    @Autore(autore = "Mattia Leonardo Angelillo")
    public Museo(String nome, String telefono, String indirizzo, String citta, String regione, String provincia, String cap, String email, String sito_web, String orario, byte[] immagine) {
        this(0, nome, telefono, indirizzo, citta, regione, provincia, cap, email, sito_web, orario, immagine);
    }
    public Museo(int id, String nome, String telefono, String indirizzo, String citta, String regione, String provincia, String cap, String email, String sito_web, String orario, byte[] immagine) {
        this.ID = id;
        this.nome = nome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.regione = regione;
        this.provincia = provincia;
        this.cap = cap;
        this.email = email;
        this.sito_web = sito_web;
        this.orario = orario;
        this.immagine = immagine;
    }

    public int getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSito_web() {
        return sito_web;
    }

    public void setSito_web(String sito_web) {
        this.sito_web = sito_web;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    public byte[] getImmagine() {
        return immagine;
    }
}
