package it.sms.eproject.data.classes;

import java.util.ArrayList;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Gestisce i punti di interesse e i musei
 * presenti in un percorso
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class OggettiMuseoHasPercorsi {
    private ArrayList<Museo> musei;
    private ArrayList<Oggetto> oggetti;

    public OggettiMuseoHasPercorsi(ArrayList<Museo> musei, ArrayList<Oggetto> oggetti){
        this.musei      = musei;
        this.oggetti    = oggetti;
    }

    public ArrayList<Museo> getMusei() {
        return musei;
    }

    public ArrayList<Oggetto> getOggetti() {
        return oggetti;
    }
}
