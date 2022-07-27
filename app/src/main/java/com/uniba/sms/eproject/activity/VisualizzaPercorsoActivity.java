package com.uniba.sms.eproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;

@Autore(autore = "Giandomenico Bucci")
public class VisualizzaPercorsoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_percorso_view);

        Intent intentPercorso = this.getIntent();

        TextView labelObject = findViewById(R.id.labelViewObject);
        labelObject.setText ( intentPercorso.getStringExtra("nome_percorso") );

        TextView desc = findViewById(R.id.description);
        desc.setText(intentPercorso.getStringExtra("desc_oggetto"));

        TextView duration = findViewById(R.id.duration);
        duration.setText ( intentPercorso.getStringExtra("durata_percorso") );

        /** Da terminare**/
    }
}
