package com.uniba.sms.eproject.activity.crud.oggetto;

import static com.uniba.sms.eproject.Azioni.VISUALIZZA_OGGETTI;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_ZONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.uniba.sms.eproject.Azioni;
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

    private int zona_id;

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////

        //Recupero la zona
        TextView provincia = findViewById(R.id.txtProvinciaZona);
        provincia.setText( provincia.getText().toString().concat(getIntent().getExtras().getString("provincia")));

        if(Azioni.valueOf(getIntent().getExtras().getString("azione")) == Azioni.UPDATE) {
            compilaCampi();
        }

        //Pulsante di salvataggio
        Button btn = findViewById(R.id.salva_oggetto);
        btn.setOnClickListener( v-> salvaOggetto());
    }

    /**
     * Compila i campi per l'aggiornamento
     */
    public void compilaCampi(){
        TextView zona = findViewById(R.id.et_zona_oggetto);
        zona.setText(getIntent().getExtras().getString("id"));

        Oggetto oggetto = (new DbManager(this)).getOggetto(Integer.parseInt(getIntent().getExtras().getString("id_oggetto")));

        ((TextView)findViewById(R.id.et_nome_oggetto)).setText(oggetto.getNome());
        ((TextView)findViewById(R.id.et_anno_oggetto)).setText(oggetto.getAnno());
        ((TextView)findViewById(R.id.et_autore_oggetto)).setText(oggetto.getAutore());
        ((TextView)findViewById(R.id.et_zona_oggetto)).setText(String.valueOf(oggetto.getId_zona()));
        ((TextView)findViewById(R.id.et_descrizione_oggetto)).setText(oggetto.getDescrizione());

        zona_id = oggetto.getId_zona();//ID Della zona

        //Sostituisco i testi di un nuovo oggetto
        //con quelli dell'aggiornamento
        ((TextView)findViewById(R.id.crea_oggetto_title)).setText(getResources().getText(R.string.aggiorna_oggetto_title));
        ((Button)findViewById(R.id.salva_oggetto)).setText(getResources().getText(R.string.aggiorna));
    }

    /**
     * Click sul bottone di salvataggio
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void salvaOggetto(){

        if(Azioni.valueOf(getIntent().getExtras().getString("azione")) == Azioni.UPDATE) {
            aggiornaOggetto(new Oggetto(
                    Integer.parseInt(getIntent().getExtras().getString("id_oggetto")),
                    ((TextView) findViewById(R.id.et_nome_oggetto)).getText().toString(),
                    ((TextView) findViewById(R.id.et_anno_oggetto)).getText().toString(),
                    ((TextView) findViewById(R.id.et_autore_oggetto)).getText().toString(),
                    ((TextView) findViewById(R.id.et_descrizione_oggetto)).getText().toString(),
                    Integer.parseInt(((TextView) findViewById(R.id.et_zona_oggetto)).getText().toString())
            ));
        }else{
            registraOggetto(new Oggetto(
                    ((TextView) findViewById(R.id.et_nome_oggetto)).getText().toString(),
                    ((TextView) findViewById(R.id.et_anno_oggetto)).getText().toString(),
                    ((TextView) findViewById(R.id.et_autore_oggetto)).getText().toString(),
                    Integer.parseInt(((TextView) findViewById(R.id.et_zona_oggetto)).getText().toString()),
                    ((TextView) findViewById(R.id.et_descrizione_oggetto)).getText().toString()
            ));
        }
    }

    /**
     * Aggiorna un oggetto già esistente
     *
     * @param oggetto Nuovi dati dell'oggetto. L'ID non è stato modificato.
     */
    public void aggiornaOggetto(Oggetto oggetto){
        DbManager db = new DbManager(this);
        if(db.updateOggetto(Integer.parseInt(getIntent().getExtras().getString("id_oggetto")), oggetto)){
            Toast.makeText(this, getResources().getText(R.string.oggetto_update), Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this, getResources().getText(R.string.oggetto_problema_update), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Inserisce un oggetto nel database
     *
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void registraOggetto(Oggetto oggetto){
        DbManager db = new DbManager(this);
        if(db.inserisciOggetto(oggetto)){
            Toast.makeText(this, getResources().getText(R.string.oggetto_creato), Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this, getResources().getText(R.string.oggetto_problema_creato), Toast.LENGTH_SHORT).show();
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
     * @param keyCode Codice del tasto premuto
     * @param event Evento associato al tasto premuto
     * @return Restituisce <strong>true</strong> per confermare l'avvenuto click di un tasto
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(CRUDOggettoCreateActivity.this, ListViewActivity.class);
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_OGGETTI));
            intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
            intent.putExtra("regione", getIntent().getExtras().getString("regione"));
            intent.putExtra("id", zona_id);
            startActivity(intent);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}