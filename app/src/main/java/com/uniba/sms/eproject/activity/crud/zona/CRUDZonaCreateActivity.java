package com.uniba.sms.eproject.activity.crud.zona;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.uniba.sms.eproject.Azioni;
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////

        //Preparo per l'aggiornamento
        if(Azioni.valueOf(getIntent().getExtras().getString("azione")) == Azioni.UPDATE) {
            compilaCampiUpdate();
        }

        salvaZona();
    }

    /**
     * Compila i campi della zona automaticamente
     * per poter aggiornare la zona
     */
    public void compilaCampiUpdate(){
        Zona zona = (new DbManager(this)).getZona(Integer.parseInt(getIntent().getExtras().getString("zona_id")));

        ((TextView)findViewById(R.id.et_nome_zona)).setText(zona.getNome());
        ((TextView)findViewById(R.id.et_provincia_zona)).setText(zona.getProvincia());
        ((TextView)findViewById(R.id.et_regione_zona)).setText(zona.getRegione());
        ((TextView)findViewById(R.id.et_cap_zona)).setText(zona.getCAP());

        ((TextView)findViewById(R.id.titolo_zona)).setText(getResources().getString(R.string.aggiorna_zona_title));
        ((Button)findViewById(R.id.salva_zona)).setText(getResources().getString(R.string.aggiorna));
    }

    /**
     * Inserisce una zona nel database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void registraZona(Zona zona){
        DbManager db = new DbManager(this);
        if(db.inserisciZona(zona)){
            Snackbar.make(findViewById(R.id.scrollView), getResources().getString(R.string.zona_update_ok), Snackbar.LENGTH_SHORT).show();
            clear();
        }else{
            Snackbar.make(findViewById(R.id.scrollView), getResources().getString(R.string.zona_update_no), Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Aggiorna una zona
     *
     * @param zona Zona contenente i dati modficati
     */
    private void aggiornaZona(Zona zona){
        DbManager db = new DbManager(this);
        if(db.updateZona(Integer.parseInt(getIntent().getExtras().getString("zona_id")), zona)){
            Snackbar.make(findViewById(R.id.scrollView), getResources().getString(R.string.zona_update_ok), Snackbar.LENGTH_SHORT).show();
            clear();
        }else{
            Snackbar.make(findViewById(R.id.scrollView), getResources().getString(R.string.zona_update_no), Snackbar.LENGTH_SHORT).show();
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
        (findViewById(R.id.salva_zona)).setOnClickListener(p->{
            if(Azioni.valueOf(getIntent().getExtras().getString("azione")) == Azioni.UPDATE){
                aggiornaZona(new Zona(
                        Integer.parseInt(getIntent().getExtras().getString("zona_id")),
                        ((TextView)findViewById(R.id.et_nome_zona)).getText().toString(),
                        ((TextView)findViewById(R.id.et_provincia_zona)).getText().toString(),
                        ((TextView)findViewById(R.id.et_regione_zona)).getText().toString(),
                        ((TextView)findViewById(R.id.et_cap_zona)).getText().toString()
                ));
            }else{
                registraZona(new Zona(
                        ((TextView)findViewById(R.id.et_nome_zona)).getText().toString(),
                        ((TextView)findViewById(R.id.et_provincia_zona)).getText().toString(),
                        ((TextView)findViewById(R.id.et_regione_zona)).getText().toString(),
                        ((TextView)findViewById(R.id.et_cap_zona)).getText().toString()
                ));
            }
        });
    }
}