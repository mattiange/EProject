package it.sms.eproject.eccezioni;

import android.content.Context;

import it.sms.eproject.R;

public class EmailGiaEsistenteException extends RuntimeException{
    public EmailGiaEsistenteException(Context c){
        super(c.getString(R.string.email_gia_esistente));
    }
}
