package it.sms.eproject.data.classes;

import androidx.annotation.NonNull;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Gestisce i dati del museo
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Museo {

    int ID;
    int citta;
    int durata_visita;

    String nome;
    String telefono;
    String indirizzo;
    String email;
    String sito_web;
    String orario;

    byte[] immagine;

    public Museo(String nome, String telefono, String indirizzo, int citta, String email, String sito_web, String orario, byte[] immagine, int durata_visita) {
        this(0, nome, telefono, indirizzo, citta, email, sito_web, orario, immagine, durata_visita);
    }
    public Museo(int id, String nome, String telefono, String indirizzo, int citta, String email, String sito_web, String orario, byte[] immagine, int durata_visita) {
        this.ID = id;
        this.nome = nome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.email = email;
        this.sito_web = sito_web;
        this.orario = orario;
        this.immagine = immagine;
        this.durata_visita = durata_visita;
    }

    public int getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }


    public String getTelefono() {
        return telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getEmail() {
        return email;
    }

    public int getCitta() {
        return citta;
    }

    public int getDurata_visita() {
        return durata_visita;
    }

    public String getSito_web() {
        return sito_web;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public String getOrario() {
        return orario;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCitta(int citta) {
        this.citta = citta;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public void setSito_web(String sito_web) {
        this.sito_web = sito_web;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    @NonNull
    @Override
    public String toString() {
        return "{nome: "+this.nome+", codice_citta: "+this.citta+"}";
    }
}
