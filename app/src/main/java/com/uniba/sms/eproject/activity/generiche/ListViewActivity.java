package com.uniba.sms.eproject.activity.generiche;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.database.DbManager;

import java.util.ArrayList;
import java.util.HashMap;

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

        visualizzaRegioni();
    }

    /**
     * Visualizza tutte le zone delle regioni
     */
    public void visualizzaRegioni(){
        //String[] citta= {"Torino","Roma","Milano","Napoli","Firenze"};

        ArrayList<HashMap<String, String>> citta = new DbManager(this).visualizzaTutteLeRegioniDelleZone();
        String cittaStr[] = new String[citta.size()];

        int pos = 0;
        for(HashMap<String, String> hm : citta){
            cittaStr[pos] = hm.get("Regione");

            pos ++;
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.listview_row,cittaStr);
        ListView listView = findViewById(R.id.listViewGenerica);

        System.out.println( "ADAPTER ===> " + adapter );
        System.out.println( listView );

        listView.setAdapter(adapter);
    }

}