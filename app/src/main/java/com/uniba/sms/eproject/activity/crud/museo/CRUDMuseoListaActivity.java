package com.uniba.sms.eproject.activity.crud.museo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.database.DbManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Questa classe serve a gestire l'activity activity_crud_lista_museo.
 *
 * Questa activity visualizza l'elenco di tutti i musei presenti all'interno
 * del database.
 */
public class CRUDMuseoListaActivity extends AppCompatActivity {
    private Bitmap[] mThumbIds;
    private String[] mNames;
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_lista_museo);


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

        gridview = (GridView)findViewById(R.id.gridview);

        displayMuseiList();

        gridViewClick();

    }

    /**
     * Porta all'activity per visualizzare i dati
     * del singolo museo selezionato
     */
    public void gridViewClick(){
        Context c = this;
        gridview.setOnItemClickListener( (p1, p2, p3, p4)->{
            HashMap<String, String> museo = ((new DbManager(c).visualizzaTuttiIMusei())).get(p3);

            Intent intent = new Intent(CRUDMuseoListaActivity.this, CRUDSingleMuseoActivity.class);
            intent.putExtra("museo", museo);
            startActivity( intent );
        });
    }

    /**
     * Visualizza l'elenco dei musei
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void displayMuseiList(){
        DbManager db = new DbManager(this);
        ArrayList<HashMap<String, String>> musei = db.visualizzaTuttiIMusei();
        if(musei == null) {
            Toast.makeText(this, "Nessun museo registrato nel database", Toast.LENGTH_SHORT).show();
            return;
        }
        mThumbIds = new Bitmap[musei.size()];
        mNames = new String[musei.size()];

        int posizione = 0;
        for(HashMap<String, String> hm : musei){
            byte[] decodedString = Base64.decode(hm.get("Immagine_Museo"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            mThumbIds[posizione] = decodedByte;
            mNames[posizione ++] = hm.get("Nome");
        }

        gridview = (GridView) findViewById(R.id.gridview);
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
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int arg0) {
            return mThumbIds[arg0];
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
                grid = new View(mContext);
                LayoutInflater inflater=getLayoutInflater();
                grid=inflater.inflate(R.layout.activity_crud_lista_row, parent, false);
            }else{//altrimenti la variabile grid sarà uguale
                  // al parametro convertView
                grid = (View)convertView;
            }

            ImageView imageView = (ImageView)grid.findViewById(R.id.crud_lista_museo_image);
            imageView.setImageBitmap(mThumbIds[position]);

            TextView tv = (TextView) grid.findViewById(R.id.crud_lista_museo_name);
            tv.setText(mNames[position]);


            return grid;
        }

    }

}