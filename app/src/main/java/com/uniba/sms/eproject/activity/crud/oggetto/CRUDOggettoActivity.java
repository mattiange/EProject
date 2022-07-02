package com.uniba.sms.eproject.activity.crud.oggetto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;

/**
 * Questa classe serve a gestire l'activity activity_crud_oggetto.
 *
 * Questa activity visualizza il CRUD per la gestione degli oggetti.
 * Le funzuioni che offre sono:
 * <ul>
 *     <li>Aggiunta nuovo oggetto</li>
 *     <li>Visualizzazione di tutti gli oggetti aggiunti</li>
 * </ul>
 */
public class CRUDOggettoActivity extends AppCompatActivity {
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_oggetto);

        btnCreate = findViewById(R.id.btnCRUD_create_oggetto);

        showNewOggettoActivity();
    }

    public void showNewOggettoActivity(){
        btnCreate.setOnClickListener( p->{
            Intent intent = new Intent(CRUDOggettoActivity.this, CRUDOggettoCreateActivity.class);
            startActivity(intent);
        });
    }
}