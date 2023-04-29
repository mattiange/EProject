package it.sms.eproject.eccezioni;

import android.content.Context;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Eccezione lanciata se un email risulta giò presente
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class EmailGiaEsistenteException extends RuntimeException{
    public EmailGiaEsistenteException(Context c){
        super(c.getString(R.string.email_gia_esistente));
    }
}
