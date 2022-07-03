package com.uniba.sms.eproject.activity.crud.museo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;

/**
 * Questa classe serve a gestire l'activity activity_crud_museo.
 *
 * Questa activity visualizza il CRUD per la gestione dei musei.
 * Le funzuioni che offre sono:
 * <ul>
 *     <li>Aggiunta nuovo museo</li>
 *     <li>Visualizzazione di tutti i musei</li>
 * </ul>
 */
public class CRUDMuseoActivity extends AppCompatActivity {
    Button btnCreate;
    Button btnShowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_museo);

        btnCreate   = findViewById(R.id.btnCRUD_create_museo);
        btnShowAll  = findViewById(R.id.btnCRUD_show_all_musei);

        showNewMuseoActivity();
        showAllMuseiActivity();
    }

    /**
     * Visualizza l'activity per creare un nuovo museo
     */
    public void showNewMuseoActivity(){
        btnCreate.setOnClickListener( p->{
            Intent intent = new Intent(CRUDMuseoActivity.this, CRUDMuseoCreateActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Visualizza tutti i musei
     */
    public void showAllMuseiActivity(){
        btnShowAll.setOnClickListener( p->{
            Intent intent = new Intent(CRUDMuseoActivity.this, CRUDMuseoListaActivity.class);
            startActivity(intent);
        });
    }
}