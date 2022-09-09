package com.uniba.sms.eproject.activity.crud.oggetto;

import static com.uniba.sms.eproject.Azioni.VISUALIZZA_PROVINCE;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_ZONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.generiche.ListViewActivity;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Oggetto;
import com.uniba.sms.eproject.database.DbManager;

/**
 * Questa classe serve a gestire l'activity activity_crud_create_oggetto.
 *
 * Questa activity gestisce l'inserimento di un nuovo oggetto nel DB
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDOggettoCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_oggetto);

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

        //Recupero la zona
        TextView tv = findViewById(R.id.et_zona_oggetto);
        tv.setText(getIntent().getExtras().getString("zona_id"));

        TextView provincia = findViewById(R.id.txtProvinciaZona);
        provincia.setText( provincia.getText().toString().concat(getIntent().getExtras().getString("provincia")));

        //Pulsante di salvataggio
        Button btn = findViewById(R.id.salva_oggetto);
        btn.setOnClickListener( v->{
            salvaOggetto();
        });
    }

    /**
     * Click sul bottone di salvataggio
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void salvaOggetto(){

        registraOggetto(new Oggetto(
                ((TextView)findViewById(R.id.et_nome_oggetto)).getText().toString(),
                ((TextView)findViewById(R.id.et_anno_oggetto)).getText().toString(),
                ((TextView)findViewById(R.id.et_autore_oggetto)).getText().toString(),
                ((TextView)findViewById(R.id.et_descrizione_oggetto)).getText().toString(),
                ((TextView)findViewById(R.id.et_zona_oggetto)).getText().toString()
        ));
    }

    /**
     * Inserisce un oggetto nel database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void registraOggetto(Oggetto oggetto){
        DbManager db = new DbManager(this);
        if(db.inserisciOggetto(oggetto)){
            Toast.makeText(this, "Oggetto inserito con successo", Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this, "Problema nell'inserimento dell'oggetto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Pulisce tutti i campi dopo l'inserimento
     */
    public void clear(){
        ((TextView)findViewById(R.id.et_nome_oggetto)).setText("");
        ((TextView)findViewById(R.id.et_anno_oggetto)).setText("");
        ((TextView)findViewById(R.id.et_autore_oggetto)).setText("");
        ((TextView)findViewById(R.id.et_descrizione_oggetto)).setText("");
    }

    /**
     * Riporta alla visualizzazione delle zone per la provincia
     * precedentemente inviata.
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(CRUDOggettoCreateActivity.this, ListViewActivity.class);
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_ZONE));
            intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
            intent.putExtra("regione", getIntent().getExtras().getString("regione"));
            startActivity(intent);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}