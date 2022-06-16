package com.uniba.sms.eproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VisualizzaMuseoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentMuseo = this.getIntent();

        TextView labelMuseum = findViewById(R.id.labelViewMuseum);
        labelMuseum.setText ( intentMuseo.getStringExtra("nome_museo") );

        TextView museumPhoneNumber = findViewById(R.id.phoneNumber);
        museumPhoneNumber.setText ( intentMuseo.getStringExtra("telefono_museo") );

        TextView museumAddress = findViewById(R.id.address);
        museumAddress.setText ( intentMuseo.getStringExtra("indirizzo_museo") );

        TextView museumCity = findViewById(R.id.city);
        museumCity.setText ( intentMuseo.getStringExtra("citta_museo") );

        TextView museumRegion = findViewById(R.id.regionMus);
        museumRegion.setText ( intentMuseo.getStringExtra("regione_museo") );

        TextView museumProvincia = findViewById(R.id.provinciaMus);
        museumProvincia.setText ( intentMuseo.getStringExtra("provincia_museo") );

        TextView museumZipCode = findViewById(R.id.zipCodeMus);
        museumZipCode.setText ( intentMuseo.getStringExtra("cap_museo") );

        TextView museumEmail = findViewById(R.id.email);
        museumEmail.setText ( intentMuseo.getStringExtra("email_museo") );

        TextView museumWebSite = findViewById(R.id.website);
        museumWebSite.setText ( intentMuseo.getStringExtra("sito_web_museo") );

        TextView museumHours = findViewById(R.id.hours);
        museumHours.setText ( intentMuseo.getStringExtra("orario_museo") );

        /*
         *
         * ImageView img_museo = findViewById(R.id.imageViewMuseo);
         * img_museo.setImageDrawable(findViewById(R.drawable.img));
         *
         */


        setContentView(R.layout.activity_museo_view);




    }
}
