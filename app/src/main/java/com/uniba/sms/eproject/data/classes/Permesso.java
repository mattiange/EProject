package com.uniba.sms.eproject.data.classes;

public class Permesso {
    int permesso_id;
    int utente_id;

    public static final int CURATORE = 1;
    public static final int VISITATORE = 2;

    public Permesso(int permesso_id, int utente_id){
        this.permesso_id = permesso_id;
        this.utente_id = utente_id;
    }

    public int getPermesso_id() {
        return permesso_id;
    }

    public int getUtente_id() {
        return utente_id;
    }

    public void setPermesso_id(int permesso_id) {
        this.permesso_id = permesso_id;
    }

    public void setUtente_id(int utente_id) {
        this.utente_id = utente_id;
    }
}
