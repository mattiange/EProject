package com.uniba.sms.eproject.activity.crud.percorso;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;

@Autore(autore = "Giandomenico Bucci")
public class CRUDPercorsoCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_percorso);

    }

    /**
     * Da implementare Database
     */
}
