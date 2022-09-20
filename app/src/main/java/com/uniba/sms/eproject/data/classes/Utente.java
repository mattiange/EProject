package com.uniba.sms.eproject.data.classes;

public class Utente {
    int id;
    String nome;
    String cognome;
    String email;
    int permesso_id;

    /*int tipoUtente;
    // 0 = Visitatore, 1 = Curatore*/

    public Utente(String nome, String cognome, String email) {
        this(-1, nome, cognome, email);
    }
    public Utente(int id, String nome, String cognome, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        //this.tipoUtente = tipoUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPermesso_id(int permesso_id) {
        this.permesso_id = permesso_id;
    }

    public int getPermesso_id() {
        return permesso_id;
    }

    /*public int getTipoUtente() { return tipoUtente; }

    public void setTipoUtente(int tipoUtente) { this.tipoUtente = tipoUtente; }*/
}
