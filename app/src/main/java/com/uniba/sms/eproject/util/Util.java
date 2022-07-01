package com.uniba.sms.eproject.util;

import com.uniba.sms.eproject.annotazioni.Autore;

/**
 * Classe con metodi di utility comuni
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class Util {
    private Util(){}

    /**
     * Controlla se un'email è scritta nel formato corretto
     *
     * @param email Email da validare
     * @return true | false a seconda che l'email sia valida o meno
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public static boolean checkEmail(String email){
        String[] parts = email.split("@");

        if(parts.length == 2){
            parts = parts[1].split("\\.");
            
            if(parts.length>=2)
                return true;
        }

        return false;
    }
}
