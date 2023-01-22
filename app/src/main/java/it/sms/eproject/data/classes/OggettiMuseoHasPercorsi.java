package it.sms.eproject.data.classes;

import java.util.ArrayList;

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
