package com.uniba.sms.eproject.activity.crud.oggetto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.generiche.ListViewActivity;
import com.uniba.sms.eproject.annotazioni.Autore;

import static com.uniba.sms.eproject.Azioni.VISUALIZZA_REGIONI;
import static com.uniba.sms.eproject.Azioni.NUOVA_ZONA;

/**
 * Questa classe serve a gestire l'activity activity_crud_oggetto.
 *
 * Questa activity visualizza il CRUD per la gestione degli oggetti.
 * Le funzuioni che offre sono:
 * <ul>
 *     <li>Aggiunta nuovo oggetto</li>
 *     <li>Visualizzazione di tutti gli oggetti aggiunti</li>
 * </ul>
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDOggettoActivity extends AppCompatActivity {
    Button btnCreate;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_oggetto);

        //Add Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ////////////////////////////////////////////////////

        //Drawer menu
        DrawerLayout dl = findViewById(R.id.drawer_layout);
        NavigationView nv = findViewById(R.id.menulaterale);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////

        btnCreate = findViewById(R.id.btnCRUD_create_oggetto);

        showNewOggettoActivity();
    }

    /**
     * Creazione del nuovo oggetto.
     * Per crearlo bisogna scegliere la zona, passando dalla regione e dalla provincia.
     */
    public void showNewOggettoActivity(){
        btnCreate.setOnClickListener( p->{
            Intent intent = new Intent(CRUDOggettoActivity.this, ListViewActivity.class);
            intent.putExtra("azione", String.valueOf(NUOVA_ZONA));
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_REGIONI));
            startActivity(intent);
        });
    }
}