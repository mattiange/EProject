package it.sms.eproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;

import it.sms.eproject.R;
import it.sms.eproject.activity.crud.CrudMuseo;
import it.sms.eproject.data.classes.Permesso;
import it.sms.eproject.data.classes.Utente;
import it.sms.eproject.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity implements CallbackFragment{
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
        if(u.getPermesso().getCodice() == Permesso.CURATORE) {
            nv.inflateMenu(R.menu.activity_backend_drawer);
        }else{
            nv.inflateMenu(R.menu.activity_main_drawer);
        }

        //Passo il pulsante per aprire e chiudere al listener del DrawerMenu
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //visualizzo il pulsante di visualizzazione del menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addFragment();
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
                    LocalDate.parse(pref.getString("user_data_di_nascita", "")),
                    Permesso.of(
                            Integer.parseInt(pref.getString("user_permesso_codice", "")),
                            pref.getString("user_permesso_nome", "")
                    )
            );
        }
    }

    /**
     * Aggiunge il fragment della login
     */
    public void addFragment(){
        HomeFragment fragment = new HomeFragment();
        fragment.setCallbackFragment(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Rimpiazza il fragment della login con quello della registrazione
     */
    public void replaceFragment (){
        /*fragment = new RegisterFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();*/
        fragment = new CrudMuseo();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void changeFragment() {
        replaceFragment();
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
}