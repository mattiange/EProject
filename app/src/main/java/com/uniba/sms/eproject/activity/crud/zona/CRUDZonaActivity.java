package com.uniba.sms.eproject.activity.crud.zona;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.generiche.ZonaListViewActivity;

/**
 * Questa classe serve a gestire l'activity activity_crud_zona.
 *
 * Questa activity visualizza il CRUD per la gestione delle zone.
 * Le funzuioni che offre sono:
 * <ul>
 *     <li>Aggiunta nuova zona</li>
 *     <li>Visualizzazione di tutte le zone aggiunte</li>
 * </ul>
 */
public class CRUDZonaActivity extends AppCompatActivity {
    Button btnCreate;
    Button btnZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_zona);

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

        btnCreate = findViewById(R.id.btnCRUD_create_zona);
        btnZone   = findViewById(R.id.btnCRUD_show_zona);

        showNewZonaActivity();
        showAllZone();
    }

    /**
     * Visualizza l'activity per creare una nuova zona
     */
    public void showNewZonaActivity(){
        btnCreate.setOnClickListener( p->{
            /*Intent intent = new Intent(CRUDZonaActivity.this, CRUDZonaCreateActivity.class);
            startActivity(intent);*/
            Intent intent = new Intent(CRUDZonaActivity.this, ZonaListViewActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Visualizza tutte le zone salvate
     */
    public void showAllZone(){
        btnZone.setOnClickListener(p->{
            Intent intent = new Intent(CRUDZonaActivity.this, CRUDZonaRegioneListaActivity.class);
            startActivity(intent);
        });
    }
}