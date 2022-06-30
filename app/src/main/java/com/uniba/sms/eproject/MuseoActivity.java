package com.uniba.sms.eproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.data.classes.Museo;
import com.uniba.sms.eproject.data.classes.Oggetto;

public class MuseoActivity extends AppCompatActivity {

    Museo museo = new Museo("test", "+39 34044030202", "indirizzo test","città test","regione test",
            "provincia test", "cap test", "email@test.com", "www.test.it", "Aperto dalle 9 alle 20 dal Lunedì alla Domenica.");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         *
         * ImageView img_museo = findViewById(R.id.imageMuseo);
         * img_museo.setImageDrawable(findViewById(R.drawable.img));
         *
         */
        setContentView(R.layout.activity_museo);

        TextView labelMuseum = findViewById(R.id.labelMuseum);
        labelMuseum.setText ( museo.getNome() );

    }

    public void visualizzaMuseo(View view){

        Intent intentVisualizzaMuseo = new Intent(MuseoActivity.this, VisualizzaMuseoActivity.class);

        intentVisualizzaMuseo.putExtra("nome_museo", museo.getNome());
        intentVisualizzaMuseo.putExtra("telefono_museo", museo.getTelefono());
        intentVisualizzaMuseo.putExtra("citta_museo", museo.getCitta());
        intentVisualizzaMuseo.putExtra("regione_museo", museo.getRegione());
        intentVisualizzaMuseo.putExtra("provincia_museo", museo.getProvincia());
        intentVisualizzaMuseo.putExtra("cap_museo", museo.getCap());
        intentVisualizzaMuseo.putExtra("email_museo", museo.getEmail());
        intentVisualizzaMuseo.putExtra("sito_web_museo", museo.getSito_web());
        intentVisualizzaMuseo.putExtra("orario_museo", museo.getOrario());

        startActivity(intentVisualizzaMuseo);

    }

    public void cambiaMuseo(View view){

        Toast.makeText(this, "Da Implementare", Toast.LENGTH_SHORT).show();

    }

    public void eliminaMuseo(View view){

        Toast.makeText(this, "Da Implementare", Toast.LENGTH_SHORT).show();

    }
}