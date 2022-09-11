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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.uniba.sms.eproject.Azioni;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.crud.oggetto.CRUDOggettoActivity;
import com.uniba.sms.eproject.activity.crud.oggetto.CRUDOggettoCreateActivity;
import com.uniba.sms.eproject.annotazioni.Autore;
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
import static com.uniba.sms.eproject.util.Util.addToolbarAndMenu;

/**
 * Unica ListView utilizzata per molteplici activity.
 *
 * Sceglie cosa visualizzare in base ai dati passati alla intent.
 */
public class OggettoListViewActivity extends AppCompatActivity {
    private FloatingActionButton createBtn;

    @Override
    @Autore(autore = "Mattia Leonardo Angelillo")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview);

        addToolbarAndMenu(this, findViewById(R.id.toolbar), findViewById(R.id.drawer_layout));

        //Nascondo il bottone di aggiunta
        //deve essere visualizzato solamente
        //per la sezione di creazione
        createBtn = findViewById(R.id.floatingButtonCreate);
        createBtn.setAlpha(0.0f);
        //////////////////////////////////////////////////////////////////////////////////

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
                    startActivity(new Intent(OggettoListViewActivity.this, CRUDOggettoCreateActivity.class)
                                    .putExtra("zona_id", getIntent().getExtras().getString("id"))
                                    .putExtra("provincia", getIntent().getExtras().getString("provincia"))
                                    .putExtra("regione", getIntent().getExtras().getString("regione")));
                    break;
                case VISUALIZZA_OGGETTI://Visualizza gli oggetti di una zona attraverso l'id della zona
                    System.out.println("ID ZONA CREA OGGETTO ===> " + getIntent().getExtras().getString("id"));
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
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void visualizzaOggetti(int zona){
        ArrayList<Oggetto> oggetti = new DbManager(this).visualizzaOggettiByZona(zona);

        //Visualizzo il bottone per la creazione di un nuovo oggetto
        createBtn.setAlpha(1.0f);
        //Riporto alla pagina di creazione dell'oggettp
        createBtn.setOnClickListener( v->{
            //Snackbar.make(findViewById(R.id.listViewGenerica), "OK", Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(OggettoListViewActivity.this, CRUDOggettoCreateActivity.class);
            intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
            intent.putExtra("azione", String.valueOf(Azioni.CREATE));
            intent.putExtra("id_zona", String.valueOf(zona));
            startActivity(intent);
        });

        if(oggetti == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText(R.string.oggetti_non_trovati);
            return;
        }
        //Visualizzo il titolo
        TextView title = findViewById(R.id.listViewTitle);
        title.setAlpha(1.0f);
        title.setText(getResources().getText(R.string.msg_seleziona_oggetto));

        ListView listView = findViewById(R.id.listViewGenerica);

        OggettoAdapter adapter = new OggettoAdapter(this, R.layout.listview_row_option_style, oggetti);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener( (adapter1, view, position, id)->{
            View v = listView.getChildAt(position  - listView.getFirstVisiblePosition());

            //Visualizzo il pulsante di modifica
            ImageButton update = v.findViewById(R.id.updateBtn);
            update.getBackground().setAlpha(255);
            update.setImageDrawable(getResources().getDrawable(R.drawable.ic_pen, null));
            //Visualizzo il pulsante di cancellazione
            ImageButton delete = v.findViewById(R.id.cancellaBtn);
            delete.getBackground().setAlpha(255);
            delete.setImageDrawable(getResources().getDrawable(R.drawable.ic_trash_can, null));

            int idOggetto = ((Oggetto)(listView.getItemAtPosition(position))).getId();
            //Attivo l'evento per l'aggiornamento
            update.setOnClickListener( view1 -> {
                Oggetto o = (new DbManager(this)).getOggetto(((Oggetto)(listView.getItemAtPosition(position))).getId());

                System.out.println("OGGETTO: " + o.getId() + ", " + o.getNome());

                Intent intent = new Intent(OggettoListViewActivity.this, CRUDOggettoCreateActivity.class);
                intent.putExtra("id_oggetto", String.valueOf(o.getId()));
                intent.putExtra("id", String.valueOf(o.getId_zona()));
                intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                intent.putExtra("azione", String.valueOf(Azioni.UPDATE));
                intent.putExtra("funzione", String.valueOf(NUOVA_ZONA));
                startActivity(intent);
            });

            delete.setOnClickListener(view1 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(OggettoListViewActivity.this);
                builder.setMessage("Vuoi cancellare il record?");
                builder.setTitle("Cancella record");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",  (dialog, which) -> {
                    if((new DbManager(this)).deleteOggetto(idOggetto)){
                        Snackbar.make(findViewById(R.id.listScrollView), getResources().getText(R.string.oggetto_cancellato_ok), Snackbar.LENGTH_SHORT).show();

                        Intent intent = new Intent(OggettoListViewActivity.this, OggettoListViewActivity.class);
                        intent.putExtra("azione", getIntent().getExtras().getString("azione"));
                        intent.putExtra("funzione", String.valueOf(VISUALIZZA_OGGETTI));
                        intent.putExtra("id", getIntent().getExtras().getString("id"));
                        intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                        intent.putExtra("regione", getIntent().getExtras().getString("regione"));
                        startActivity(intent);
                    }else{
                        Snackbar.make(findViewById(R.id.listScrollView), getResources().getText(R.string.oggetto_cancellato_no), Snackbar.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            });

            return true;
        });
    }

    /**
     * Visualizza tutte le zone di una determinata provincia
     * @param provincia Provincia delle zone da visualizzare
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void visualizzaZone(String provincia){
        ArrayList<Zona> zone = new DbManager(this).visualizzaTutteLeZoneByProvincia(provincia);

        if(zone == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText(String.format(getResources().getString(R.string.zona_non_trovata), provincia));
            return;
        }
        //Visualizzo il titolo
        TextView title = findViewById(R.id.listViewTitle);
        title.setAlpha(1.0f);
        title.setText(getResources().getText(R.string.msg_seleziona_zona));

        ListView listView = findViewById(R.id.listViewGenerica);

        ZonaAdapter adapter = new ZonaAdapter(this, R.layout.listview_row_option_style, zone);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( (adapter1, view, position, id)->{
            Intent intent = new Intent(OggettoListViewActivity.this, OggettoListViewActivity.class);
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
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void visualizzaRegioni(){
        ArrayList<HashMap<String, String>> regioni = new DbManager(this).visualizzaTutteLeRegioniDelleZone();

        if(regioni == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText(R.string.zona_non_trovata_per_la_regione);
            return;
        }
        //Visualizzo il titolo
        TextView title = findViewById(R.id.listViewTitle);
        title.setAlpha(1.0f);
        title.setText(getResources().getText(R.string.msg_seleziona_regione));

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
            Intent intent = new Intent(OggettoListViewActivity.this, OggettoListViewActivity.class);
            intent.putExtra("azione", getIntent().getExtras().getString("azione"));
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_PROVINCE));
            intent.putExtra("regione", (String)listView.getItemAtPosition(position));
            startActivity(intent);
        });
    }

    /**
     * Visualizza tutte le zone delle province
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void visualizzaProvince(String regione){
        ArrayList<HashMap<String, String>> province = new DbManager(this).visualizzaTutteLeProvinceDiUnaRegione(regione);

        if(province == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText(String.format(getResources().getString(R.string.provincia_non_trovata_per_la_regione), regione));
            return;
        }
        //Visualizzo il titolo
        TextView title = findViewById(R.id.listViewTitle);
        title.setAlpha(1.0f);
        title.setText(getResources().getText(R.string.msg_seleziona_provincia));

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
            Intent intent = new Intent(OggettoListViewActivity.this, OggettoListViewActivity.class);
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
    @Autore(autore = "Mattia Leonardo Angelillo")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String s = getIntent().getExtras().getString("funzione");

            switch (Azioni.valueOf(s)){
                case VISUALIZZA_OGGETTI:{
                    Intent intent = new Intent(OggettoListViewActivity.this, OggettoListViewActivity.class);
                    intent.putExtra("funzione", String.valueOf(VISUALIZZA_ZONE));
                    intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                    intent.putExtra("regione", getIntent().getExtras().getString("regione"));
                    intent.putExtra("id", Integer.parseInt(getIntent().getExtras().getString("id")));
                    startActivity(intent);
                }
                case VISUALIZZA_ZONE: {
                        Intent intent = new Intent(OggettoListViewActivity.this, OggettoListViewActivity.class);
                        intent.putExtra("funzione", String.valueOf(VISUALIZZA_PROVINCE));
                        intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                        intent.putExtra("regione", getIntent().getExtras().getString("regione"));
                        startActivity(intent);
                    }
                    break;
                case VISUALIZZA_PROVINCE: {
                        Intent intent = new Intent(OggettoListViewActivity.this, OggettoListViewActivity.class);
                        intent.putExtra("funzione", String.valueOf(VISUALIZZA_REGIONI));
                        intent.putExtra("provincia", getIntent().getExtras().getString("provincia"));
                        startActivity(intent);
                    }
                    break;
                case VISUALIZZA_REGIONI:{
                    Intent intent = new Intent(OggettoListViewActivity.this, CRUDOggettoActivity.class);
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
    @Autore(autore = "Mattia Leonardo Angelillo")
    public static class OggettoAdapter extends ArrayAdapter<Oggetto>{

        public OggettoAdapter(Context context, int textViewResourceId,
                           List <Oggetto> objects) {
            super(context, textViewResourceId, objects);
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_row_option_style, null);

            TextView id = convertView.findViewById(R.id.txt_listview_row_id);
            TextView valore = convertView.findViewById(R.id.txt_listview_row);

            Oggetto c = getItem(position);
            id.setText(String.valueOf(c.getId()));
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
    @Autore(autore = "Mattia Leonardo Angelillo")
    public static class ZonaAdapter extends ArrayAdapter<Zona>{

        public ZonaAdapter(Context context, int textViewResourceId,
                             List <Zona> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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