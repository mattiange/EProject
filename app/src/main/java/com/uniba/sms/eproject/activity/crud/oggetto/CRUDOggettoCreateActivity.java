package com.uniba.sms.eproject.activity.crud.oggetto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Oggetto;
import com.uniba.sms.eproject.database.DbManager;

/**
 * Questa classe serve a gestire l'activity activity_crud_create_oggetto.
 *
 * Questa activity gestisce l'inserimento di un nuovo oggetto nel DB
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDOggettoCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_oggetto);

        //salvaBtn();


    }

    /**
     * Inserisce un oggetto nel database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void registraOggetto(Oggetto oggetto){
        DbManager db = new DbManager(this);
        if(db.inserisciOggetto(oggetto)){
            Toast.makeText(this, "Oggetto inserito con successo", Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this, "Problema nell'inserimento dell'oggetto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Pulisce tutti i campi dopo l'inserimento
     */
    public void clear(){
        ((TextView)findViewById(R.id.et_nome_oggetto)).setText("");
        ((TextView)findViewById(R.id.et_anno_oggetto)).setText("");
        ((TextView)findViewById(R.id.et_autore_oggetto)).setText("");
        ((TextView)findViewById(R.id.et_descrizione_oggetto)).setText("");
    }

    /**
     * Click sul bottone di salvataggio
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void salvaOggetto(View view){

            registraOggetto(new Oggetto(
                    ((TextView)findViewById(R.id.et_nome_oggetto)).getText().toString(),
                    ((TextView)findViewById(R.id.et_anno_oggetto)).getText().toString(),
                    ((TextView)findViewById(R.id.et_autore_oggetto)).getText().toString(),
                    ((TextView)findViewById(R.id.et_descrizione_oggetto)).getText().toString()
            ));
    }
}