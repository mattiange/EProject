package com.uniba.sms.eproject.activity.crud.museo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Museo;

/**
 * Questa classe serve a gestire l'activity activity_crud_create_museo.
 *
 * Questa activity Gestisce l'inserimento di un nuovo museo nel DB
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDMuseoCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_museo);

        salvaBtn();


    }

    /**
     * Inserisce un museo nel database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void registraMuseo(Museo museo){
        Toast.makeText(this, museo.getCap(), Toast.LENGTH_LONG).show();
    }

    /**
     * Click sul bottone di salvataggio
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void salvaBtn(){
        ((Button)findViewById(R.id.salva_museo)).setOnClickListener( p-> {
            registraMuseo(new Museo(
                    ((TextView)findViewById(R.id.et_nome_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_telefono_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_indirizzo_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_citta_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_regione_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_provincia_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_cap_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_email_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_sito_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_orario_apertura_museo)).getText().toString()
            ));
        });
    }
}