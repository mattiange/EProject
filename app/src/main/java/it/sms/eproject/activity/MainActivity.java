package it.sms.eproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;

import it.sms.eproject.R;
import it.sms.eproject.fragment.backend.AggiornaDatabaseFragment;
import it.sms.eproject.fragment.backend.CuratoreHomeFragment;
import it.sms.eproject.fragment.backend.RicercaMuseiOggettiFragment;
import it.sms.eproject.fragment.backend.crud.autori.CRUDAutore;
import it.sms.eproject.fragment.backend.crud.liste.ListaStati;
import it.sms.eproject.fragment.backend.crud.museo.CrudMuseo;
import it.sms.eproject.fragment.backend.crud.oggetto.CrudOggetto;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Permesso;
import it.sms.eproject.data.classes.Utente;
import it.sms.eproject.fragment.backend.crud.percorso.CRUDPercorso;
import it.sms.eproject.fragment.backend.utente.UtenteHomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView nv;

    //Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;

    //Utente loggato
    Utente u;

    /**
     * Preferenze del sistema
     */
    SharedPreferences pref;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("credenziali", 0);


        registraUtenteLoggato();

        //Abilito l'apertura/chiusura del drawer menu
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        nv = findViewById(R.id.nav_view);
        switch (u.getPermesso().getCodice()){
            case Permesso.CURATORE:
                nv.inflateMenu(R.menu.activity_backend_drawer);
                break;
            case Permesso.GUIDA:
                nv.inflateMenu(R.menu.activity_backend_drawer_guida);
                break;
            default:
                nv.inflateMenu(R.menu.activity_main_drawer);
        }

        //Passo il pulsante per aprire e chiudere al listener del DrawerMenu
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //visualizzo il pulsante di visualizzazione del menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addFragment();


        nv.setNavigationItemSelectedListener(this);
        //Modifico l'ordine dell'asse z del menu nell'albero
        //portandolo sopra tutto, così da permettere il click
        //sulle singole voci
        nv.bringToFront();
    }



    /**
     * Recupera le informazioni sull'utente che si è loggato all'app
     */
    public void registraUtenteLoggato(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            u = Utente.of(
                    Integer.parseInt(pref.getString("user_id", "-1")),
                    pref.getString("user_nome", ""),
                    pref.getString("user_cognome", ""),
                    pref.getString("user_codice_fiscaale", ""),
                    pref.getString("user_email", ""),
                    LocalDate.parse(pref.getString("user_data_di_nascita", "1993-01-25")),
                    Permesso.of(
                            Integer.parseInt(pref.getString("user_permesso_codice", "")),
                            pref.getString("user_permesso_nome", "")
                    )
            );
        }
    }

    /**
     * Aggiunge il fragment da visualizzare
     * Visualizza i fragment in base ai permessi dell'utente
     */
    public void addFragment(){
        switch (u.getPermesso().getCodice()){
            case Permesso.CURATORE:
                fragment = new CuratoreHomeFragment();
                break;
            case Permesso.GUIDA:
                fragment = new CRUDPercorso();
                break;
            default://utente semplice
                fragment = new UtenteHomeFragment();
                break;
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Imposto l'apertura del DrawerMenu
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Aggiungo il supporto alla navigazione del DrawerMenu
     *
     * @param item
     * @return
     */
    @AutoreCodice(autore = "Mattia Leonardo Angelillo")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {// Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_manage_museo:
                fragment = new CrudMuseo();
                break;
            case R.id.nav_manage_oggetto:
                fragment = new CrudOggetto();
                break;
            case R.id.nav_manage_autori:
                fragment = new CRUDAutore();
                break;
            case R.id.nav_create_percorso:

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("azione-lista","nuovo-percorso");
                editor.apply();

                fragment = new ListaStati();
                break;
            case R.id.nav_manage_percorso:
                fragment = new CRUDPercorso();
                break;
            case R.id.nav_aggiorna_database:
                fragment = new AggiornaDatabaseFragment();
                break;
            case R.id.nav_cerca:
                fragment = new RicercaMuseiOggettiFragment();
                break;
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null).commit();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}