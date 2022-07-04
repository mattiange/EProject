package com.uniba.sms.eproject.activity.crud.museo;

import static com.uniba.sms.eproject.util.Util.checkEmail;
import static com.uniba.sms.eproject.util.Util.getBytesFromBitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Museo;
import com.uniba.sms.eproject.database.DbManager;

import java.io.IOException;
import java.util.HashMap;

/**
 * Questa classe serve a gestire l'activity activity_crud_create_museo.
 *
 * Questa activity Gestisce l'inserimento di un nuovo museo nel DB
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDMuseoCancellaActivity extends AppCompatActivity {
    private HashMap<String, String> museo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_delete_museo);

        museo = (HashMap<String, String>) getIntent().getExtras().get("museo");

        annulla();
        cancella();

    }

    /**
     * Cancella un museo
     */
    public void cancella(){
        //Cancella BTN
        ((Button)findViewById(R.id.btn_cancella)).setOnClickListener( p->{
            DbManager db = new DbManager(this);
            if(db.eliminaMuseo( Integer.parseInt(museo.get("ID")) ) ) {
                Toast.makeText(this, "Museo eliminato con successo", Toast.LENGTH_LONG).show();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(CRUDMuseoCancellaActivity.this, CRUDMuseoListaActivity.class);
                startActivity(intent);
            }else Toast.makeText(this, "Problema con l'eliminazione del museo", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Annulla l'operazione e ritorna alla visualizzazione del museo
     */
    public void annulla(){
        //Annulla BTN
        ((Button)findViewById(R.id.btn_annulla)).setOnClickListener( p->{
            Intent intent = new Intent(CRUDMuseoCancellaActivity.this, CRUDSingleMuseoActivity.class);
            intent.putExtra("museo", museo);
            startActivity(intent);
        });
    }
}