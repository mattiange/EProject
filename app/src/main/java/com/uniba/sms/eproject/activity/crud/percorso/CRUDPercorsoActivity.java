package com.uniba.sms.eproject.activity.crud.percorso;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;

@Autore(autore = "Giandomenico Bucci")
public class CRUDPercorsoActivity extends AppCompatActivity {

    Button btnCreate;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_percorso);

        btnCreate = findViewById(R.id.btnCRUD_create_percorso);

        showNewOggettoActivity();
    }

    public void showNewOggettoActivity(){
        btnCreate.setOnClickListener( p->{
            Intent intent = new Intent(CRUDPercorsoActivity.this, CRUDPercorsoCreateActivity.class);
            startActivity(intent);
        });
    }

}
