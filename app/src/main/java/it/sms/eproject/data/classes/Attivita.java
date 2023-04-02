package it.sms.eproject.data.classes;

public class Attivita {
    private long codice;
    private int utenteCodice;
    private long cittaCodice;
    private String nome;
    private String descrizione;

    public Attivita(long codice, int utenteCodice, long cittaCodice, String nome, String descrizione) {
        this.codice = codice;
        this.utenteCodice = utenteCodice;
        this.cittaCodice = cittaCodice;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public long getCodice() {
        return codice;
    }

    public void setCodice(long codice) {
        this.codice = codice;
    }

    public int getUtenteCodice() {
        return utenteCodice;
    }

    public void setUtenteCodice(int utenteCodice) {
        this.utenteCodice = utenteCodice;
    }

    public long getCittaCodice() {
        return cittaCodice;
    }

    public void setCittaCodice(long cittaCodice) {
        this.cittaCodice = cittaCodice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
