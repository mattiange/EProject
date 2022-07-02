package com.uniba.sms.eproject.activity.crud.zona;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Zona;
import com.uniba.sms.eproject.database.DbManager;

/**
 * Questa classe serve a gestire l'activity activity_crud_create_zona.
 *
 * Questa activity gestisce l'inserimento di una nuova zona nel DB.
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDZonaCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_oggetto);

        //salvaBtn();


    }

    /**
     * Inserisce una zona nel database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void registraZona(Zona zona){
        DbManager db = new DbManager(this);
        if(db.inserisciZona(zona)){
            Toast.makeText(this, "Zona inserita con successo", Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this, "Problema nell'inserimento della zona", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Pulisce tutti i campi dopo l'inserimento
     */
    public void clear(){
        ((TextView)findViewById(R.id.et_nome_zona)).setText("");
        ((TextView)findViewById(R.id.et_provincia_zona)).setText("");
        ((TextView)findViewById(R.id.et_regione_zona)).setText("");
        ((TextView)findViewById(R.id.et_cap_zona)).setText("");
    }

    /**
     * Click sul bottone di salvataggio
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void salvaZona(View view){

            registraZona(new Zona(
                    ((TextView)findViewById(R.id.et_nome_zona)).getText().toString(),
                    ((TextView)findViewById(R.id.et_provincia_zona)).getText().toString(),
                    ((TextView)findViewById(R.id.et_regione_zona)).getText().toString(),
                    ((TextView)findViewById(R.id.et_cap_zona)).getText().toString()
            ));
    }
}