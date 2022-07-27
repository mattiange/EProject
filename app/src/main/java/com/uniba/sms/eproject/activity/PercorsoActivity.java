package com.uniba.sms.eproject.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Percorso;


import androidx.appcompat.app.AppCompatActivity;


@Autore(autore = "Giandomenico Bucci")
public class PercorsoActivity extends AppCompatActivity {


    private int nome;
    Percorso percorso = new Percorso(1,"test","test",1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView labelObject = findViewById(R.id.labelObject);
        labelObject.setText ( percorso.getNome() );

        setContentView(R.layout.activity_percorso);

    }

    public void visualizzaPercorso(View view){
        Intent intentVisualizzaPercorso = new Intent(PercorsoActivity.this, VisualizzaPercorsoActivity.class);
        intentVisualizzaPercorso.putExtra("nome_percorso", percorso.getNome());
        intentVisualizzaPercorso.putExtra("descrizione_percorso", percorso.getDescrizione());
        intentVisualizzaPercorso.putExtra("durata_percorso", percorso.getDurata());




    }

    public void cambiaPercorso(View view){
        Intent intentcambiaPercorso = new Intent(PercorsoActivity.this, VisualizzaPercorsoActivity.class);

        /** Da Implementare**/

    }

    public void eliminaPercorso(View view){
        Intent intenteliminaPercorso = new Intent(PercorsoActivity.this, VisualizzaPercorsoActivity.class);

        /** Da Implementare**/
    }


}
