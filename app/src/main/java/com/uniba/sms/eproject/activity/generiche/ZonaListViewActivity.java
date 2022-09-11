package com.uniba.sms.eproject.activity.generiche;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.uniba.sms.eproject.Azioni.VISUALIZZA_PROVINCE;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_ZONE;
import static com.uniba.sms.eproject.util.Util.addToolbarAndMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniba.sms.eproject.Azioni;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.crud.zona.CRUDZonaCreateActivity;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Zona;
import com.uniba.sms.eproject.database.DbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZonaListViewActivity extends AppCompatActivity {
    private FloatingActionButton createBtn;

    @Override
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

        try{
            String s = getIntent().getExtras().getString("funzione");
            switch (Azioni.valueOf(s)){
                case VISUALIZZA_PROVINCE:
                    visualizzaProvince(getIntent().getExtras().getString("regione"));
                    break;
                case VISUALIZZA_ZONE:
                    visualizzaZone(getIntent().getExtras().getString("provincia"));
                    break;
            }
        }catch(NullPointerException e){//Eccezione lanciata se si arriva a questo punto senza un'azione passata
            visualizzaRegioni();
        }
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

            int idZona = ((Zona)(listView.getItemAtPosition(position))).getId();
            update.setOnClickListener(view1->{
                Toast.makeText(this, String.valueOf(idZona), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ZonaListViewActivity.this, CRUDZonaCreateActivity.class);
                intent.putExtra("azione", String.valueOf(Azioni.UPDATE));
                intent.putExtra("funzione", String.valueOf(VISUALIZZA_ZONE));
                intent.putExtra("zona_id", String.valueOf(((Zona)(listView.getItemAtPosition(position))).getId()));
                intent.putExtra("provincia", provincia);
                intent.putExtra("regione", getIntent().getExtras().getString("regione"));
                startActivity(intent);
            });

            /*Intent intent = new Intent(ZonaListViewActivity.this, ZonaListViewActivity.class);
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_OGGETTI));
            intent.putExtra("id", ((Zona)(listView.getItemAtPosition(position))).getId());
            intent.putExtra("provincia", provincia);
            intent.putExtra("regione", getIntent().getExtras().getString("regione"));
            startActivity(intent);*/

            return true;
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
            Intent intent = new Intent(ZonaListViewActivity.this, ZonaListViewActivity.class);
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_ZONE));
            intent.putExtra("provincia", (String)listView.getItemAtPosition(position));
            intent.putExtra("regione", regione);
            startActivity(intent);
        });
    }

    /**
     * Visualizza tutte le regioni
     */
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
            Intent intent = new Intent(ZonaListViewActivity.this, ZonaListViewActivity.class);
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_PROVINCE));
            intent.putExtra("regione", (String)listView.getItemAtPosition(position));
            startActivity(intent);
        });
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
                           List<Zona> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_row_option_style, null);

            TextView id = convertView.findViewById(R.id.txt_listview_row_id);
            TextView valore = convertView.findViewById(R.id.txt_listview_row);

            Zona c = getItem(position);
            id.setText(String.valueOf(c.getId()));
            valore.setText(c.getNome());

            return convertView;
        }

    }
}
