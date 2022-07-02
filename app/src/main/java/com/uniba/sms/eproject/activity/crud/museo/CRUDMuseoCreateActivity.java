package com.uniba.sms.eproject.activity.crud.museo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Museo;
import com.uniba.sms.eproject.database.DbManager;

import static com.uniba.sms.eproject.util.Util.checkEmail;

/**
 * Questa classe serve a gestire l'activity activity_crud_create_museo.
 *
 * Questa activity gestisce l'inserimento di un nuovo museo nel DB
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
        DbManager db = new DbManager(this);
        if(db.inserisciMuseo(museo)){
            Toast.makeText(this, "Museo inserito con successo", Toast.LENGTH_SHORT).show();
            clear();
        }else{
            Toast.makeText(this, "Problema nell'inserimento del museo", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Pulisce tutti i campi dopo l'inserimento
     */
    public void clear(){
        ((TextView)findViewById(R.id.et_nome_museo)).setText("");
        ((TextView)findViewById(R.id.et_telefono_museo)).setText("");
        ((TextView)findViewById(R.id.et_indirizzo_museo)).setText("");
        ((TextView)findViewById(R.id.et_citta_museo)).setText("");
        ((TextView)findViewById(R.id.et_regione_museo)).setText("");
        ((TextView)findViewById(R.id.et_provincia_museo)).setText("");
        ((TextView)findViewById(R.id.et_cap_museo)).setText("");
        ((TextView)findViewById(R.id.et_email_museo)).setText("");
        ((TextView)findViewById(R.id.et_sito_museo)).setText("");
        ((TextView)findViewById(R.id.et_orario_apertura_museo)).setText("");
        ((TextView)findViewById(R.id.et_immagini_museo)).setText("");
    }

    /**
     * Click sul bottone di salvataggio
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void salvaBtn(){

        ((Button)findViewById(R.id.salva_museo)).setOnClickListener( p-> {
            String email = ((TextView)findViewById(R.id.et_email_museo)).getText().toString();
            if(!checkEmail(email)){
                Toast.makeText(this, "L'email non è scritta correttamente", Toast.LENGTH_SHORT).show();
                return;
            }

            registraMuseo(new Museo(
                    ((TextView)findViewById(R.id.et_nome_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_telefono_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_indirizzo_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_citta_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_regione_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_provincia_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_cap_museo)).getText().toString(),
                    email,
                    ((TextView)findViewById(R.id.et_sito_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_orario_apertura_museo)).getText().toString(),
                    ((TextView)findViewById(R.id.et_immagini_museo)).getText().toString()
            ));
        });
    }
}