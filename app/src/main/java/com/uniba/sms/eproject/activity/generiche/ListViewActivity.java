package com.uniba.sms.eproject.activity.generiche;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.Azioni;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.crud.oggetto.CRUDOggettoActivity;
import com.uniba.sms.eproject.activity.crud.oggetto.CRUDOggettoCreateActivity;
import com.uniba.sms.eproject.activity.crud.zona.CRUDZonaCreateActivity;
import com.uniba.sms.eproject.database.DbManager;

import java.util.ArrayList;
import java.util.HashMap;

import static com.uniba.sms.eproject.Azioni.NUOVA_ZONA;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_PROVINCE;
import static com.uniba.sms.eproject.Azioni.VISUALIZZA_ZONE;

import org.w3c.dom.Text;

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
        NavigationView nv = findViewById(R.id.menulaterale);
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
                    System.out.println("===>PROVINCE");
                    visualizzaProvince(getIntent().getExtras().getString("regione"));
                    break;
                case VISUALIZZA_ZONE:
                    visualizzaZone(getIntent().getExtras().getString("provincia"));
                    break;
                case NUOVA_ZONA:
                    System.out.println("===>ZONE");
                    //Riporta alla creazione di un nuovo oggetto
                    startActivity(new Intent(ListViewActivity.this, CRUDOggettoCreateActivity.class)
                                    .putExtra("zona_id", getIntent().getExtras().getString("id"))
                                    .putExtra("provincia", getIntent().getExtras().getString("provincia")));
                    break;
            }
        }catch(NullPointerException e){}//Eccezione lanciata se si arriva a questo punto senza un'azione passata
    }

    /**
     * Visualizza tutte le zone di una determinata provincia
     * @param provincia
     */
    public void visualizzaZone(String provincia){
        ArrayList<HashMap<String, String>> zone = new DbManager(this).visualizzaTutteLeZoneByProvincia(provincia);

        if(zone == null){
            TextView tv = findViewById(R.id.listTVEmpty);
            tv.setText("Non è stata trovata alcuna zona per la provincia: " + provincia);
            return;
        }

        String zoneStr[] = new String[zone.size()];

        int pos = 0;
        for(HashMap<String, String> hm : zone){
            zoneStr[pos] = hm.get("Nome");

            pos ++;
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.listview_row,zoneStr);
        ListView listView = findViewById(R.id.listViewGenerica);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( (adapter1, view, position, id)->{
            Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
            intent.putExtra("azione", getIntent().getExtras().getString("azione"));
            intent.putExtra("funzione", String.valueOf(NUOVA_ZONA));
            intent.putExtra("id", (String)listView.getItemAtPosition(position));
            intent.putExtra("provincia", provincia);
            System.out.println("PROV: " + provincia);
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
            tv.setText("Non è stata trovata alcuna regione per le zone");
            return;
        }

        String regioniStr[] = new String[regioni.size()];

        int pos = 0;
        for(HashMap<String, String> hm : regioni){
            regioniStr[pos] = hm.get("Regione");

            pos ++;
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.listview_row,regioniStr);
        ListView listView = findViewById(R.id.listViewGenerica);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( (adapter1, view, position, id)->{
            Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
            intent.putExtra("azione", getIntent().getExtras().getString("azione"));
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_PROVINCE));
            intent.putExtra("regione", (String)listView.getItemAtPosition(position));
            //finish();
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
            tv.setText("Non è stata trovata alcuna provincia nella regione: " + regione);
            return;
        }

        String provinceStr[] = new String[province.size()];

        int pos = 0;
        for(HashMap<String, String> hm : province){
            provinceStr[pos] = hm.get("Provincia");

            pos ++;
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.listview_row,provinceStr);
        ListView listView = findViewById(R.id.listViewGenerica);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( (adapter1, view, position, id)->{
            Intent intent = new Intent(ListViewActivity.this, ListViewActivity.class);
            intent.putExtra("azione", getIntent().getExtras().getString("azione"));
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_ZONE));
            intent.putExtra("provincia", (String)listView.getItemAtPosition(position));
            startActivity(intent);
        });
    }

}