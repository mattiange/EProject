package com.uniba.sms.eproject.activity.crud.zona;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;

import java.util.HashMap;
import java.util.Map;

public class CRUDZonaListaActivity extends AppCompatActivity {

    private String[] mNames;
    GridView gridview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_lista_zone);

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

        displayZonaList();
    }

    /**
     * Visualizza l'elenco delle zone
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void displayZonaList(){
        Intent intent = getIntent();
        HashMap<String, String> province = (HashMap<String, String>)intent.getSerializableExtra("zone");

        if(province.size() == 0) {
            Toast.makeText(this, "Nessuna zona registrata nel database", Toast.LENGTH_SHORT).show();
            return;
        }

        //Stampo tutte le province
        mNames = new String[province.size()];
        int i = 0;
        for(Map.Entry<String, String> v : province.entrySet()){
            mNames[i] = v.getValue();

            i ++;
        }

        gridview = (GridView) findViewById(R.id.gridviewZone);
        gridview.setAdapter(new MyAdapter(this));
        gridview.setNumColumns(4);

    }

    /**
     * Adapter per poter visualizzare all'interno della GridView
     * sia il nome del museo che la sua immagine
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public class MyAdapter extends BaseAdapter {

        private Context mContext;

        public MyAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return mNames.length;
        }

        @Override
        public Object getItem(int arg0) {
            return mNames[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        /**
         * Restituisce la view come singolo elemento del GridView.
         *
         * Questa GridView è composta da una {@link TextView} e da
         * una {#code {@link ImageView}.
         *
         * @param position      Indice che indica il singolo elemento da visualizzare
         * @param convertView   Indica la vecchia View da riutilizzare.
         *                      N.B.: è possibile che la vista passata sia nulla, per questo
         *                            va controllato prima se questo parametro è <b>null</b>.
         *                            Se è null allora verrà creata una nuova vista, altrimenti
         *                            verrà utilizzata quella già esistente.
         * @param parent        Indica la View alla quale sarà, eventualmente legata, questa View
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View grid;

            //Se la View è nulla allora crea
            //una nuova new per il context dell'activity
            if(convertView==null){
                LayoutInflater inflater=getLayoutInflater();
                grid=inflater.inflate(R.layout.activity_crud_lista_row, parent, false);
            }else{//altrimenti la variabile grid sarà uguale
                // al parametro convertView
                grid = (View)convertView;
            }

            TextView tv = (TextView) grid.findViewById(R.id.crud_lista_museo_name);
            tv.setText(mNames[position]);


            return grid;
        }

    }
}
