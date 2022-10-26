package it.sms.eproject.util;

import android.graphics.Bitmap;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import java.io.ByteArrayOutputStream;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;


/**
 * Classe con metodi di utility comuni
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Util {
    private Util(){}

    /**
     * Controlla se un'email è scritta nel formato corretto
     *
     * @param email Email da validare
     * @return true | false a seconda che l'email sia valida o meno
     */
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
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    /**
     * Aggiunge la <strong>toolbar</strong> e il <strong>drawer layout</strong>.
     *
     * @param aca Activity sulla quale aggiungere i componenti
     * @param t Toolbar da aggiungere
     * @param dl DrawerLayout da aggiungere
     */
    public static void addToolbarAndMenu(AppCompatActivity aca, Toolbar t, DrawerLayout dl){
        //Add Toolbar
        aca.setSupportActionBar(t);
        ////////////////////////////////////////////////////

        //Drawer menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(aca, dl, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////
    }

    /**
     * Verifica se un email è valida
     *
     * @param email Email da verificare
     * @return true se l'email è valida, false altrimenti
     */
    public static boolean verificaEmail(String email){
        String secondaParteEmail = "";

        try {
            secondaParteEmail = email.substring(email.indexOf('@'));
        }catch (StringIndexOutOfBoundsException e){
            //Non è contenuto il simbolo della @
            return false;
        }

        //Controllo la seconda parte dell'email (dopo la @) contenga
        //il simbolo del punto (.)
        if(email.contains("@") && secondaParteEmail.contains(".")){
            return true;
        }

        return false;
    }

    /**
     * Verifica che le due password siano uguali
     *
     * @param password1 Password
     * @param password2 Password da confermare
     * @return
     */
    public static boolean confermaPassword(String password1, String password2){
        return password1.equals(password2);
    }

    /**
     * Verifica se un campo è stato correttamente compilato
     *
     * @return true se è compilato, false altrimenti
     */
    public static boolean campoCompilato(String campo){
        return !campo.trim().isEmpty();
    }

}