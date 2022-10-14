package it.sms.eproject;

import android.content.Context;

public class EmailGiaEsistenteException extends RuntimeException{
    public EmailGiaEsistenteException(Context c){
        super(c.getString(R.string.email_gia_esistente));
    }
}
