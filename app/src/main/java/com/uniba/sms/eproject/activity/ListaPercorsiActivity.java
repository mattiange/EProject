package com.uniba.sms.eproject.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.data.classes.Zona;


public class ListaPercorsiActivity extends AppCompatActivity {

    private Zona zona = new Zona("Nome", "Provincia", "Regione", "CAP");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_percorsi);

        //Add Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar_ListaPercorsi);
        toolbar.setTitle(zona.getNome());
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        if (ab != null) { ab.setDisplayHomeAsUpEnabled(true); }

        ////////////////////////////////////////////////////

        //Floating button
        FloatingActionButton addFBtn = findViewById(R.id.add_ListaPercorsi);
        addFBtn.setOnClickListener(view -> {
            Snackbar.make(view, "Da implementare", Snackbar.LENGTH_LONG).show();
        });
        ////////////////////////////////////////////////////////////////////////////////



    }

}
