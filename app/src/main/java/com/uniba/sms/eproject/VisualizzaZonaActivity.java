package com.uniba.sms.eproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VisualizzaZonaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentZona = this.getIntent();

        TextView labelZona = findViewById(R.id.labelObject);
        labelZona.setText ( intentZona.getStringExtra("nome_zona") );

        TextView region = findViewById(R.id.region);
        region.setText(intentZona.getStringExtra("regione_zona"));

        TextView provincia = findViewById(R.id.provincia);
        provincia.setText(intentZona.getStringExtra("provincia_zona"));

        TextView zipCode = findViewById(R.id.zipcode);
        zipCode.setText(intentZona.getStringExtra("cap_zona"));

        /*
         *
         * ImageView img_zona = findViewById(R.id.imageZonaView);
         * img_zona.setImageDrawable(findViewById(R.drawable.img));
         *
         */


        setContentView(R.layout.activity_zona_view);




    }
}
