package it.sms.eproject.data.classes;

public class Utente {
    String nome;
    String cognome;
    String username;
    String email;
    int tipoUtente;
    // 0 = Visitatore, 1 = Curatore

    public Utente(String nome, String cognome, String username, String email, int tipoUtente) {

        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.tipoUtente = tipoUtente;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public int getTipoUtente() { return tipoUtente; }

    public void setTipoUtente(int tipoUtente) { this.tipoUtente = tipoUtente; }
}