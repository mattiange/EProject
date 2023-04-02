package it.sms.eproject.data.classes;

public class AttivitaOggetto {
    private Attivita attivita;
    private Oggetto oggetto;

    public AttivitaOggetto(Attivita attivita, Oggetto oggetto) {
        this.attivita = attivita;
        this.oggetto = oggetto;
    }

    public Attivita getAttivita() {
        return attivita;
    }

    public void setAttivita(Attivita attivita) {
        this.attivita = attivita;
    }

    public Oggetto getMuseo() {
        return oggetto;
    }

    public void setMuseo(Oggetto museo) {
        this.oggetto = museo;
    }
}
