package it.sms.eproject.json;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import it.sms.eproject.R;

public class CondivisioneJson extends AppCompatActivity {

    Button btnInvia;

    GestioneJsonPercorso gestione = new GestioneJsonPercorso();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invio_json);

        btnInvia = findViewById(R.id.btnInvioMail);

        btnInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, gestione.getPercorsoAsJson(1));
                intent.setType("text/json");

                startActivity(Intent.createChooser(intent, null));
            }
        });
    }
}