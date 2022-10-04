package it.sms.eproject.data.classes;

import java.time.LocalDate;

public class Utente {
    int codice;

    String nome;
    String cognome;
    String codice_fiscale;
    String email;
    String password;

    LocalDate data_di_nascita;

    Permesso permesso;

    /**
     *
     * @param codice  Codice dell'utente
     * @param nome      Nome  dell'utente
     * @param cognome Cognome dell'utente
     * @param codice_fiscale Codice fiscale dell'utente
     * @param data_di_nascita Data di nascita dell'utente
     * @param email Email dell'utente
     * @param permesso Permesso associato all'utente
     */
    public Utente(int codice, String nome, String cognome, String codice_fiscale, LocalDate data_di_nascita, String email, Permesso permesso) {
        this.codice             = codice;
        this.nome               = nome;
        this.cognome            = cognome;
        this.codice_fiscale     = codice_fiscale;
        this.email              = email;
        this.data_di_nascita    = data_di_nascita;
        this.permesso           = permesso;
    }

    /**
     *
     * @param nome      Nome  dell'utente
     * @param cognome Cognome dell'utente
     * @param codice_fiscale Codice fiscale dell'utente
     * @param data_di_nascita Data di nascita dell'utente
     * @param email Email dell'utente
     * @param permesso Permesso associato all'utente
     */
    public Utente(String nome, String cognome, String codice_fiscale, LocalDate data_di_nascita, String email, Permesso permesso){
        this(-1, nome, cognome, codice_fiscale, data_di_nascita, email, permesso);
    }

    /**
     * Costruttore utilizzato per l'inserimento di un nuovo utente nel database
     *
     * @param nome      Nome  dell'utente
     * @param cognome Cognome dell'utente
     * @param codice_fiscale Codice fiscale dell'utente
     * @param data_di_nascita Data di nascita dell'utente
     * @param email Email dell'utente
     * @param password Password dell'utente
     * @param permesso Permesso associato all'utente
     */
    public Utente(String nome, String cognome, String codice_fiscale, LocalDate data_di_nascita, String email, String password, Permesso permesso){
        this(nome, cognome, codice_fiscale, data_di_nascita, email, permesso);
        this.password = password;
    }


    /**
     *
     * @param email Email dell'utente
     */
    public void setEmail(String email) { this.email = email; }

    /**
     *
     * @param nome      Nome  dell'utente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @param cognome Cognome dell'utente
     */
    public void setCognome(String cognome) {this.cognome = cognome;}

    /**
     *
     * @param codice_fiscale Codice fiscale dell'utente
     */
    public void setCodice_fiscale(String codice_fiscale) {this.codice_fiscale = codice_fiscale;}

    /**
     *
     * @param data_di_nascita Data di nascita dell'utente
     */
    public void setData_di_nascita(LocalDate data_di_nascita) {this.data_di_nascita = data_di_nascita;}

    /**
     *
     * @param password Password dell'utente
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param permesso Permesso assocciato all'utente
     */
    public void setPermesso(Permesso permesso) {
        this.permesso = permesso;
    }

    /**
     *
     * @return Restituisce il codice
     */
    public int getCodice() {return codice;}

    /**
     *
     * @return Restituisce il Nome
     */
    public String getNome() {return nome;}

    /**
     *
     * @return Restituisce il cognome
     */
    public String getCognome() {return cognome; }

    /**
     *
     * @return Restituisce l'email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return Restituisce il codice fiscale
     */
    public String getCodice_fiscale() {return codice_fiscale;}

    /**
     *
     * @return Restituisce la data di nascita
     */
    public LocalDate getData_di_nascita() {return data_di_nascita;}

    /**
     *
     * @return Restituisce la password dell'utente
     */
    public String getPassword() {return password;}

    /**
     *
     * @return Permesso dell'utente
     */
    public Permesso getPermesso() {return permesso;}
}