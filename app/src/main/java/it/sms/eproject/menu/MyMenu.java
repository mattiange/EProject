package it.sms.eproject.menu;

import static com.google.zxing.client.result.ParsedResultType.WIFI;

import it.sms.eproject.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import it.sms.eproject.activity.QRscannerActivity;
import it.sms.eproject.activity.login_e_registrazione.GestioneProfiloActivity;
import it.sms.eproject.activity.login_e_registrazione.LoginActivity;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.json.MyWifiActivity;
import it.sms.eproject.util.Constants;

/**
 * Gestione delle voci del menu
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class MyMenu {
    private Context c;

    private Activity activity;

    private Menu menu;
    private int menuPos;

    //Voci di menu

    public MyMenu(Context c, Menu menu, Activity activity){
        this.c          = c;
        this.activity   = activity;
        this.menu       = menu;
        this.menuPos    = 0;
    }

    /**
     * Al momento sono disabilitate
     * le operazioni di CRUD dei menu
     *
     * @param vociMenu
     */
    public MyMenu aggiungiVociMenu(VociMenu vociMenu) {
        if(menuPos == 0){
            menu.clear();
        }

        switch (vociMenu) {
            case QR_SCAN:
                aggiungiMenuQrScan();
                break;
            case VERSION:
                aggiungiMenuVersione();
                break;
            case GESTIONE_PROFILO:
                aggiungiMenuGestioneProfilo();
                break;
            case LOGOUT:
                aggiungiMenuLogout();
                break;

        }

        return this;
    }

    /**
     * Aggiunge la voce di menu per il logout
     */
    private void aggiungiMenuLogout(){
        this.menu.add(menuPos, R.id.menu_logout, Menu.NONE, R.string.logout);
        this.menu.getItem(menuPos).setOnMenuItemClickListener(i->{
            this.activity.startActivity(new Intent(this.activity, LoginActivity.class));

            return true;
        });

        menuPos ++;
    }
    /**
     * Aggiunge la voce di menu per il logout
     */
    private void aggiungiMenuWifi(){
        this.menu.add(menuPos, R.id.menu_logout, Menu.NONE, R.string.wifi);
        this.menu.getItem(menuPos).setOnMenuItemClickListener(i->{
            this.activity.startActivity(new Intent(this.activity, MyWifiActivity.class));

            return true;
        });

        menuPos ++;
    }
    /**
     * Aggiunge la gestione del profilo al menu
     */
    private void aggiungiMenuGestioneProfilo(){
        this.menu.add(menuPos, R.id.menu_gestione_profilo, Menu.NONE, R.string.gestioneprofilo);
        this.menu.getItem(menuPos).setOnMenuItemClickListener(i->{
            this.activity.startActivity(new Intent(this.activity, GestioneProfiloActivity.class));

            return true;
        });

        menuPos ++;
    }

    /**
     * Aggiunge la versione al menu
     */
    private void aggiungiMenuVersione(){
        this.menu.add(menuPos, R.id.menu_version, Menu.NONE, R.string.version);
        this.menu.getItem(menuPos).setOnMenuItemClickListener(i->{
            Toast.makeText(this.c, this.activity.getResources().getString(R.string.version) + " " +Constants.VERSION, Toast.LENGTH_SHORT).show();

            return true;
        });

        menuPos ++;
    }

    /**
     * Aggiunge al menu il QR Scan
     */
    private void aggiungiMenuQrScan(){
        this.menu.add(menuPos, R.id.menu_qr_scam, Menu.NONE, R.string.qr_scan)
                .setIcon(R.drawable.qrcode)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.menu.getItem(menuPos).setOnMenuItemClickListener(i->{
            this.activity.startActivity(new Intent(this.activity, QRscannerActivity.class));

            return true;
        });

        menuPos ++;
    }

    public Menu getMenu() {
        return menu;
    }

    public enum VociMenu{
        VERSION,
        QR_SCAN,
        GESTIONE_PROFILO,
        LOGOUT
    }
}