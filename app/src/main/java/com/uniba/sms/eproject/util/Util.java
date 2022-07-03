package com.uniba.sms.eproject.util;

import android.graphics.Bitmap;

import com.uniba.sms.eproject.annotazioni.Autore;

import java.io.ByteArrayOutputStream;

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

    /**
     * Converte un'immagine Bitmap in una sequenza di byte
     *
     * @param bitmap
     * @return
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
}
