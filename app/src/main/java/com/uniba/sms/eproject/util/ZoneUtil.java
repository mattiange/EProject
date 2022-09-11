package com.uniba.sms.eproject.util;

import static com.uniba.sms.eproject.Azioni.VISUALIZZA_PROVINCE;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.generiche.ZonaListViewActivity;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.database.DbManager;

import java.util.ArrayList;
import java.util.HashMap;

@Autore(autore = "Mattia Leonardo Angelillo")
public class ZoneUtil {
    private ZoneUtil(){}

    /**
     * visualizza tutte le regioni
     */
    public static void visualizzaRegioni(Context c, TextView tv, AppCompatActivity aca){
        ArrayList<HashMap<String, String>> regioni = new DbManager(c).visualizzaTutteLeRegioniDelleZone();

        if(regioni == null){
            tv.setText(R.string.zona_non_trovata_per_la_regione);
            return;
        }
        //Visualizzo il titolo
        TextView title = tv.findViewById(R.id.listViewTitle);
        title.setAlpha(1.0f);
        title.setText(aca.getResources().getText(R.string.msg_seleziona_regione));

        String[] regioniStr = new String[regioni.size()];

        int pos = 0;
        for(HashMap<String, String> hm : regioni){
            regioniStr[pos] = hm.get("Regione");

            pos ++;
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(c, R.layout.listview_row,regioniStr);
        ListView listView = tv.findViewById(R.id.listViewGenerica);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener( (adapter1, view, position, id)->{
            Intent intent = new Intent(c, ZonaListViewActivity.class);
            intent.putExtra("azione", aca.getIntent().getExtras().getString("azione"));
            intent.putExtra("funzione", String.valueOf(VISUALIZZA_PROVINCE));
            intent.putExtra("regione", (String)listView.getItemAtPosition(position));
            aca.startActivity(intent);
        });
    }
}
