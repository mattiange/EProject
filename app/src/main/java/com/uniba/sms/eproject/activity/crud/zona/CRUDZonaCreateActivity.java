package com.uniba.sms.eproject.activity.crud.zona;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Zona;
import com.uniba.sms.eproject.database.DbManager;

/**
 * Questa classe serve a gestire l'activity activity_crud_create_zona.
 *
 * Questa activity gestisce l'inserimento di una nuova zona nel DB.
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDZonaCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_zona);

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

        salvaZona();


    }

    /**
     * Inserisce una zona nel database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void registraZona(Zona zona){
        DbManager db = new DbManager(this);
        if(db.inserisciZona(zona)){
            Toast.makeText(this, "Zona inserita con successo", Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this, "Problema nell'inserimento della zona", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Pulisce tutti i campi dopo l'inserimento
     */
    public void clear(){
        ((TextView)findViewById(R.id.et_nome_zona)).setText("");
        ((TextView)findViewById(R.id.et_provincia_zona)).setText("");
        ((TextView)findViewById(R.id.et_regione_zona)).setText("");
        ((TextView)findViewById(R.id.et_cap_zona)).setText("");
    }

    /**
     * Click sul bottone di salvataggio
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void salvaZona(){
        (findViewById(R.id.salva_zona)).setOnClickListener(p->
            registraZona(new Zona(
                    ((TextView)findViewById(R.id.et_nome_zona)).getText().toString(),
                    ((TextView)findViewById(R.id.et_provincia_zona)).getText().toString(),
                    ((TextView)findViewById(R.id.et_regione_zona)).getText().toString(),
                    ((TextView)findViewById(R.id.et_cap_zona)).getText().toString()
            )));
    }
}