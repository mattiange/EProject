package it.sms.eproject.data.classes;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Struttura delle attivitò di un museo
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class AttivitaMuseo {
    private Attivita attivita;
    private Museo museo;

    public AttivitaMuseo(Attivita attivita, Museo museo) {
        this.attivita = attivita;
        this.museo = museo;
    }

    public Attivita getAttivita() {
        return attivita;
    }

    public void setAttivita(Attivita attivita) {
        this.attivita = attivita;
    }

    public Museo getMuseo() {
        return museo;
    }

    public void setMuseo(Museo museo) {
        this.museo = museo;
    }
}
