package com.uniba.sms.eproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.data.classes.Oggetto;

public class OggettoActivity extends AppCompatActivity {

    Oggetto oggetto = new Oggetto("test", "2004", "test", "test test test");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView labelObject = findViewById(R.id.labelObject);
        labelObject.setText ( oggetto.getNome() );

        /*
         *
         * ImageView img_oggetto = findViewById(R.id.imageOggetto);
         * img_oggetto.setImageDrawable(findViewById(R.drawable.img));
         *
         */

        setContentView(R.layout.activity_oggetto);

    }

    public void visualizzaOggetto(View view){

        Intent intentVisualizzaOggetto = new Intent(OggettoActivity.this, VisualizzaOggettoActivity.class);

        intentVisualizzaOggetto.putExtra("nome_oggetto", oggetto.getNome());
        intentVisualizzaOggetto.putExtra("anno_oggetto", oggetto.getAnno());
        intentVisualizzaOggetto.putExtra("autore_oggetto", oggetto.getAutore());
        intentVisualizzaOggetto.putExtra("desc_oggetto", oggetto.getDescrizione());

        startActivity(intentVisualizzaOggetto);

    }

    public void cambiaOggetto(View view){

        Toast.makeText(this, "Da Implementare", Toast.LENGTH_SHORT).show();

    }

    public void eliminaOggetto(View view){

        Toast.makeText(this, "Da Implementare", Toast.LENGTH_SHORT).show();

    }
}