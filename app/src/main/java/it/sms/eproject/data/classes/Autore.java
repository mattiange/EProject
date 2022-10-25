package it.sms.eproject.data.classes;

import java.time.LocalDate;

public class Autore {
    int codice;
    String nome;
    String descrizione;
    LocalDate dataDiNascita;
    LocalDate dataDiMorte;

    /**
     * Creo un autore
     *
     * @param codice Codice dell'autore
     * @param nome Nome dell'autore
     * @param dataDiNascita Data di nascita dell'autore
     * @param dataDiMorte Data di morte dell'autore
     */
    public Autore(int codice, String nome, LocalDate dataDiNascita, LocalDate dataDiMorte, String descrizione){
        this.codice = codice;
        this.nome   = nome;
        this.dataDiNascita = dataDiNascita;
        this.dataDiMorte = dataDiMorte;
        this.descrizione = descrizione;
    }

    /**
     * Creo un autore
     *
     * @param nome Nome dell'autore
     * @param dataDiNascita Data di nascita dell'autore
     * @param dataDiMorte Data di morte dell'autorev
     */
    public Autore(String nome, LocalDate dataDiNascita, LocalDate dataDiMorte, String descrizione){
        this(-1, nome, dataDiNascita, dataDiMorte, descrizione);
    }

    /**
     * Imposta il nome dell'autore
     *
     * @param nome Nome dell'autore
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Imposto il codice dell'autore
     *
     * @param codice Codice dell'autore
     */
    public void setCodice(int codice) {
        this.codice = codice;
    }

    /**
     * Imposto la data di morte dell'autore
     *
     * @param dataDiMorte Data
     */
    public void setDataDiMorte(LocalDate dataDiMorte) {
        this.dataDiMorte = dataDiMorte;
    }

    /**
     * Imposto la data di nascita dell'autore
     *
     * @param dataDiNascita Data
     */
    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    /**
     * Imposto la descrizione dell'autore
     *
     * @param descrizione Descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Restituisco il nome dell'autore
     *
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisco il codice dell'autore
     *
     * @return Codice
     */
    public int getCodice() {
        return codice;
    }

    /**
     * Restituisco la data di morte dell'autore
     *
     * @return Data
     */
    public LocalDate getDataDiMorte() {
        return dataDiMorte;
    }

    /**
     * Restituiscco la data di nascita dell'autore
     *
     * @return Data
     */
    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    /**
     * Restituisco la descrizione dell'autore
     *
     * @return Descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }
}
