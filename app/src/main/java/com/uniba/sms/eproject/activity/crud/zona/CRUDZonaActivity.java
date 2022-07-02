package com.uniba.sms.eproject.activity.crud.zona;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;

/**
 * Questa classe serve a gestire l'activity activity_crud_zona.
 *
 * Questa activity visualizza il CRUD per la gestione delle zone.
 * Le funzuioni che offre sono:
 * <ul>
 *     <li>Aggiunta nuova zona</li>
 *     <li>Visualizzazione di tutte le zone aggiunte</li>
 * </ul>
 */
public class CRUDZonaActivity extends AppCompatActivity {
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_zona);

        btnCreate = findViewById(R.id.btnCRUD_create_zona);

        showNewZonaActivity();
    }

    public void showNewZonaActivity(){
        btnCreate.setOnClickListener( p->{
            Intent intent = new Intent(CRUDZonaActivity.this, CRUDZonaCreateActivity.class);
            startActivity(intent);
        });
    }
}