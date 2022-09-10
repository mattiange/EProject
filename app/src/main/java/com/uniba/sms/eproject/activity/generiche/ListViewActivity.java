package com.uniba.sms.eproject.activity.generiche;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.uniba.sms.eproject.Azioni;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.crud.oggetto.CRUDOggettoActivity;
import com.uniba.sms.eproject.activity.crud.oggetto.CRUDOggettoCreateActivity;
import com.uniba.sms.eproject.data.classes.Oggetto;
import com.uniba.sms.eproject.data.classes.Zona;
import com.uniba.sms.eproject.database.DbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.uniba.sms.eproject.Azioni.NUOVA_ZONA;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_OGGETTI;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_PROVINCE;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_REGIONI;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_ZONE;

/**
 * Unica ListView utilizzata per molteplici activity.
 *
 * Sceglie cosa visualizzare in base ai dati passati alla intent.
 */
public class ListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview);

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

        //Esegue le funzioni
        try{
            String s = getIntent().getExtras().getString("funzione");
            switch (Azioni.valueOf(s)) {
                case VISUALIZZA_REGIONI:
                    visualizzaRegioni();
                    break;
                case VISUALIZZA_PROVINCE:
                    visualizzaProvince(getIntent().getExtras().getString("regione"));
                    break;
                case VISUALIZZA_ZONE:
                    visualizzaZone(getIntent().getExtras().getString("provincia"));
                    break;
                case NUOVA_ZONA:
                    //Riporta alla creazione di un nuovo oggetto
                    startActivity(new Intent(ListViewActivity.this, CRUDOggettoCreateActivity.class)
                                    .putExtra("zona_id", getIntent().getExtras().getString("id"))
                                    .putExtra("provincia", getIntent().getExtras().getString("provincia"))
                                    .putExtra("regione", getIntent().getExtras().getString("regione")));
                    break;
                case VISUALIZZA_OGGETTI:
                    visualizzaOggetti(Integer.parseInt(getIntent().getExtras().getString("id")));
                    break;
            }
        }catch(NullPointerException e){//Eccezione lanciata se si arriva a questo punto senza un'azione passata
            System.err.println(e.getMessage());
        }
    }

    /**
     * Reindirizza all'activity per visualizzare gli oggetti di una zona
     * @param zona ID della zona degli oggetti da visualizzare
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public void visualizzaOggetti(int zona){
        ArrayList<Oggetto> oggetti = new DbManager(this).visualizzaOggettiByZona(zona);

        if(oggetti == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText(R.string.oggetti_non_trovati);
            return;
        }

        ListView listView = findViewById(R.id.listViewGenerica);

        OggettoAdapter adapter = new OggettoAdapter(this, R.layout.listview_row_option_style, oggetti);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener( (adapter1, view, position, id)->{
            //Visualizzo il pulsante di modifica
            ImageButton update = findViewById(R.id.updateBtn);
            update.getBackground().setAlpha(255);
            update.setImageDrawable(getResources().getDrawable(R.drawable.ic_pen, null));
            //Visualizzo il pulsante di cancellazione
            ImageButton delete = findViewById(R.id.cancellaBtn);
            delete.getBackground().setAlpha(255);
            delete.setImageDrawable(getResources().getDrawable(R.drawable.ic_trash_can, null));

            //Attivo l'evento per l'aggiornamento
            update.setOnClickListener( v -> {
                Intent intent = new Intent(ListViewActivity.this, CRUDOggettoCreateActivity.class);
                intent.putExtra("id_oggetto", ((Oggetto)(listView.getItemAtPosition(position))).getId());
                intent.putExtra("id", getIntent().getExtras().getString("id"));
                intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                intent.putExtra("azione", String.valueOf(Azioni.UPDATE));
                intent.putExtra("funzione", String.valueOf(NUOVA_ZONA));
                startActivity(intent);
            });

            return true;
        });
    }

    /**
     * Visualizza tutte le zone di una determinata provincia
     * @param provincia Provincia delle zone da visualizzare
     */
    public void visualizzaZone(String provincia){
        ArrayList<Zona> zone = new DbManager(this).visualizzaTutteLeZoneByProvincia(provincia);

        if(zone == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText(String.format(getResources().getString(R.string.zona_non_trovata), provincia));
            return;
        }

        ListView listView = findViewById(R.id.listViewGenerica);

        ZonaAdapter adapter = new ZonaAdapter(this, R.layout.listview_row_option_style, zone);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( (adapter1, view, position, id)->{
            Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
            intent.putExtra("azione", getIntent().getExtras().getString("azione"));
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_OGGETTI));
            intent.putExtra("id", ((Zona)(listView.getItemAtPosition(position))).getId());
            intent.putExtra("provincia", provincia);
            intent.putExtra("regione", getIntent().getExtras().getString("regione"));
            startActivity(intent);
        });
    }


    /**
     * Visualizza tutte le zone delle regioni
     */
    public void visualizzaRegioni(){
        ArrayList<HashMap<String, String>> regioni = new DbManager(this).visualizzaTutteLeRegioniDelleZone();

        if(regioni == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText(R.string.zona_non_trovata_per_la_regione);
            return;
        }

        String[] regioniStr = new String[regioni.size()];

        int pos = 0;
        for(HashMap<String, String> hm : regioni){
            regioniStr[pos] = hm.get("Regione");

            pos ++;
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, R.layout.listview_row,regioniStr);
        ListView listView = findViewById(R.id.listViewGenerica);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( (adapter1, view, position, id)->{
            Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
            intent.putExtra("azione", getIntent().getExtras().getString("azione"));
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_PROVINCE));
            intent.putExtra("regione", (String)listView.getItemAtPosition(position));
            startActivity(intent);
        });
    }

    /**
     * Visualizza tutte le zone delle province
     */
    public void visualizzaProvince(String regione){
        ArrayList<HashMap<String, String>> province = new DbManager(this).visualizzaTutteLeProvinceDiUnaRegione(regione);

        if(province == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText(String.format(getResources().getString(R.string.provincia_non_trovata_per_la_regione), regione));
            return;
        }

        String[] provinceStr = new String[province.size()];

        int pos = 0;
        for(HashMap<String, String> hm : province){
            provinceStr[pos] = hm.get("Provincia");

            pos ++;
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, R.layout.listview_row,provinceStr);
        ListView listView = findViewById(R.id.listViewGenerica);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( (adapter1, view, position, id)->{
            Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
            intent.putExtra("azione", getIntent().getExtras().getString("azione"));
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_ZONE));
            intent.putExtra("provincia", (String)listView.getItemAtPosition(position));
            intent.putExtra("regione", regione);
            startActivity(intent);
        });
    }


    /**
     * Riporta alla visualizzazione delle zone per la provincia
     * precedentemente inviata.
     *
     * @param keyCode Codice del tasto premuto
     * @param event Evento associato al tasto premuto
     * @return Restituisce true se il metodo va a buon fine
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String s = getIntent().getExtras().getString("funzione");

            switch (Azioni.valueOf(s)){
                case VISUALIZZA_OGGETTI:{
                    Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
                    intent.putExtra("funzione", String.valueOf(VISUALIZZA_ZONE));
                    intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                    intent.putExtra("regione", getIntent().getExtras().getString("regione"));
                    intent.putExtra("id", Integer.parseInt(getIntent().getExtras().getString("id")));
                    startActivity(intent);
                }
                case VISUALIZZA_ZONE: {
                        Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
                        intent.putExtra("funzione", String.valueOf(VISUALIZZA_PROVINCE));
                        intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                        intent.putExtra("regione", getIntent().getExtras().getString("regione"));
                        startActivity(intent);
                    }
                    break;
                case VISUALIZZA_PROVINCE: {
                        Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
                        intent.putExtra("funzione", String.valueOf(VISUALIZZA_REGIONI));
                        intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                        startActivity(intent);
                    }
                    break;
                case VISUALIZZA_REGIONI:{
                    Intent intent = new Intent(ListViewActivity.this, CRUDOggettoActivity.class);
                    startActivity(intent);
                }
                break;

            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Adapter per poter gestire l'id e il nome
     *      * delle zone selezionate.
     *      *
     *      * L'ID ci è utile per poter modificare/eliminare l'oggetto
     */
    public static class OggettoAdapter extends ArrayAdapter<Oggetto>{

        public OggettoAdapter(Context context, int textViewResourceId,
                           List <Oggetto> objects) {
            super(context, textViewResourceId, objects);
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_row_option_style, null);
            TextView id = convertView.findViewById(R.id.txt_listview_row_id);
            TextView valore = convertView.findViewById(R.id.txt_listview_row);
            Oggetto c = getItem(position);
            id.setText(c.getId());
            valore.setText(c.getNome());

            return convertView;
        }

    }

    /***
     * Adapter per poter gestire l'id e il nome
     * delle zone selezionate.
     *
     * L'ID ci è utile per poter modificare/eliminare l'oggetto
     */
    public static class ZonaAdapter extends ArrayAdapter<Zona>{

        public ZonaAdapter(Context context, int textViewResourceId,
                             List <Zona> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_row_option_style, null);
            TextView id = convertView.findViewById(R.id.txt_listview_row_id);
            TextView valore = convertView.findViewById(R.id.txt_listview_row);
            Zona c = getItem(position);
            id.setText(c.getId());
            valore.setText(c.getNome());

            return convertView;
        }

    }
}