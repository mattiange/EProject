package com.uniba.sms.eproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.drawermenu.CuratoreDrawerMenuAction;

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

    /**
     * Aggiunge la <strong>toolbar</strong> e il <strong>drawer layout</strong>.
     *
     * @param aca Activity sulla quale aggiungere i componenti
     * @param t Toolbar da aggiungere
     * @param dl DrawerLayout da aggiungere
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public static void addToolbarAndMenu(AppCompatActivity aca, Toolbar t, DrawerLayout dl){
        //Add Toolbar
        aca.setSupportActionBar(t);
        ////////////////////////////////////////////////////

        //Drawer menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(aca, dl, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        NavigationView nv = aca.findViewById(R.id.menulaterale);
        CuratoreDrawerMenuAction cdma = new CuratoreDrawerMenuAction(nv);
        cdma.set(aca, aca.getApplicationContext());
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////
    }
}
