package com.uniba.sms.eproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;

public class VisualizzaOggettoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentOggetto = this.getIntent();

        TextView labelObject = findViewById(R.id.labelViewObject);
        labelObject.setText ( intentOggetto.getStringExtra("nome_oggetto") );

        TextView year = findViewById(R.id.year);
        year.setText(intentOggetto.getStringExtra("anno_oggetto"));

        TextView author = findViewById(R.id.author);
        author.setText(intentOggetto.getStringExtra("autore_oggetto"));

        TextView desc = findViewById(R.id.description);
        desc.setText(intentOggetto.getStringExtra("desc_oggetto"));

        /*
         *
         * ImageView img_oggetto = findViewById(R.id.imageObject);
         * img_oggetto.setImageDrawable(findViewById(R.drawable.img));
         *
         */


        setContentView(R.layout.activity_oggetto_view);




    }
}
