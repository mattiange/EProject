package com.uniba.sms.eproject.activity.crud.museo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;

import java.util.HashMap;

/**
 * Questa classe serve a visualizzare i dati di un singolo museo
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDSingleMuseoActivity extends AppCompatActivity {
    HashMap<String, String> museo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_single_museo);

        //Recupero i dati dalla intent
        Bundle bundle = getIntent().getExtras();
        museo = (HashMap<String, String>) (bundle.get("museo"));
        //////////////////////////////////////////////////////////////

        compilaDati();

        goToModifica();

        goToDelete();
    }

    /**
     * Porta all'activity per cancellare
     * il museo
     */
    public void goToDelete(){
        ((Button)findViewById(R.id.btn_museo_elimina)).setOnClickListener( p->{
            Intent intent = new Intent(CRUDSingleMuseoActivity.this, CRUDMuseoCancellaActivity.class);
            intent.putExtra("museo", museo);
            startActivity( intent );
        });
    }

    /**
     * Porta all'activity per modificare
     * il museo
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void goToModifica(){
        ((Button)findViewById(R.id.btn_museo_modifica)).setOnClickListener( p->{
            Intent intent = new Intent(CRUDSingleMuseoActivity.this, CRUDMuseoModificaActivity.class);
            intent.putExtra("museo", museo);
            startActivity( intent );
        });
    }

    /**
     * Compila tutti i campi per visualizzare i
     * dati del museo selezionato
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void compilaDati(){
        byte[] decodedString = Base64.decode(museo.get("Immagine_Museo"), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ((TextView)findViewById(R.id.nome_museo)).setText(museo.get("Nome"));
        ((TextView)findViewById(R.id.telefono_museo)).setText(((TextView)findViewById(R.id.telefono_museo)).getText() + museo.get("Numero_Telefono"));
        ((TextView)findViewById(R.id.indirizzo_museo)).setText(((TextView)findViewById(R.id.indirizzo_museo)).getText() + museo.get("Indirizzo"));
        ((TextView)findViewById(R.id.citta_museo)).setText(((TextView)findViewById(R.id.citta_museo)).getText() + museo.get("Citta"));
        ((TextView)findViewById(R.id.provincia_museo)).setText(((TextView)findViewById(R.id.provincia_museo)).getText() + museo.get("Provincia"));
        ((TextView)findViewById(R.id.cap_museo)).setText(((TextView)findViewById(R.id.cap_museo)).getText() + museo.get("CAP"));
        ((TextView)findViewById(R.id.regione_museo)).setText(((TextView)findViewById(R.id.regione_museo)).getText() + museo.get("Regione"));
        ((TextView)findViewById(R.id.email_museo)).setText(((TextView)findViewById(R.id.email_museo)).getText() + museo.get("Email_Contatti"));
        ((TextView)findViewById(R.id.orario_museo)).setText(((TextView)findViewById(R.id.email_museo)).getText() + museo.get("Orario_Apertura"));
        ((TextView)findViewById(R.id.sito_museo)).setText(((TextView)findViewById(R.id.sito_museo)).getText() + museo.get("Sito_Web"));
        ((ImageView)findViewById(R.id.img_immagine_museo)).setImageBitmap( decodedByte );
    }

}