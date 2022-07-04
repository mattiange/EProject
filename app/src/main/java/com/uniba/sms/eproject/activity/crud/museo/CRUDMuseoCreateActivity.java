package com.uniba.sms.eproject.activity.crud.museo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.annotazioni.Autore;
import com.uniba.sms.eproject.data.classes.Museo;
import com.uniba.sms.eproject.database.DbManager;

import static com.uniba.sms.eproject.util.Util.checkEmail;
import static com.uniba.sms.eproject.util.Util.getBytesFromBitmap;

import java.io.IOException;

/**
 * Questa classe serve a gestire l'activity activity_crud_create_museo.
 *
 * Questa activity Gestisce l'inserimento di un nuovo museo nel DB
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CRUDMuseoCreateActivity extends AppCompatActivity {


    final int GALLERY_REQ_CODE = 1000;
    private ActivityResultLauncher<Intent> intentLaunch;
    private Uri imageUri;
    private String imageUriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_museo);

        //Add Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ////////////////////////////////////////////////////

        //Drawer menu
        DrawerLayout dl = findViewById(R.id.drawer_layout);
        NavigationView nv = findViewById(R.id.menulaterale);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////

        intentLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getData() == null){
                        return;
                    }

                    imageUri = result.getData().getData();//Salvo l'URI dell'immagine selezionata per il museo
                    System.out.println("RES: " + result.getData().getData());


                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        imageUriString = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.NO_WRAP);

                        System.out.println( imageUriString );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        salvaBtn();//Bottone di salvataggio

        selezionaImmagine();
    }

    /**
     * Permette di selezionare un immagine dalla galleria per
     * salvarla nel database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void selezionaImmagine(){
        ((Button)findViewById(R.id.btn_immagini_museo)).setOnClickListener( v->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intentLaunch.launch(intent);
        } );
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
    @Autore(autore = "Mattia Leonardo Angelillo")
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
    }

    /**
     * Click sul bottone di salvataggio
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void salvaBtn(){

        ((Button)findViewById(R.id.salva_museo)).setOnClickListener( p-> {
            String email = ((TextView)findViewById(R.id.et_email_museo)).getText().toString();

            //controlla se l'email non è scritta correttamente
            //in questo caso blocca il metodo, impedendo il salvataggio nel DB
            //e visualizza un Toast con il messaggio dell'errore
            if(!checkEmail(email)){
                Toast.makeText(this, "L'email non è scritta correttamente", Toast.LENGTH_LONG).show();
                return;
            }

            //Salvo il museo sul database
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
                    imageUriString
            ));
        });
    }

    /**
     * Legge il risultato della richiesta all'activity, ottenendo l'URI dell'immagine
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
            }
        }

    }
}