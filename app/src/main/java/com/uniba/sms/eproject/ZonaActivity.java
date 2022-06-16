package com.uniba.sms.eproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.uniba.sms.eproject.data.classes.Zona;

public class ZonaActivity extends AppCompatActivity {

    Zona zona = new Zona("test", "test", "test", "test");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView labelZona = findViewById(R.id.labelObject);
        labelZona.setText ( zona.getNome() );

        /*
         *
         * ImageView img_zona = findViewById(R.id.imageZona);
         * img_zona.setImageDrawable(findViewById(R.drawable.img));
         *
         */

        setContentView(R.layout.activity_zona);

    }

    public void visualizzaZona(View view){

        Intent intentVisualizzaZona = new Intent(ZonaActivity.this, VisualizzaZonaActivity.class);

        intentVisualizzaZona.putExtra("nome_zona", zona.getNome());
        intentVisualizzaZona.putExtra("provincia_zona", zona.getProvincia());
        intentVisualizzaZona.putExtra("regione_zona", zona.getRegione());
        intentVisualizzaZona.putExtra("cap_zona", zona.getCAP());

        startActivity(intentVisualizzaZona);

    }

    public void cambiaZona(View view){

        Toast.makeText(this, "Da Implementare", Toast.LENGTH_SHORT).show();

    }

    public void eliminaZona(View view){

        Toast.makeText(this, "Da Implementare", Toast.LENGTH_SHORT).show();

    }
}