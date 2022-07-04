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
public class CRUDMuseoModificaActivity extends AppCompatActivity {


    final int GALLERY_REQ_CODE = 1000;
    private ActivityResultLauncher<Intent> intentLaunch;
    private Uri imageUri;
    private String imageUriString;
    private HashMap<String, String> museo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crud_create_museo);

        museo = (HashMap<String, String>) getIntent().getExtras().get("museo");

        compilaDati();

        intentLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getData() == null){
                        return;
                    }

                    imageUri = result.getData().getData();//Salvo l'URI dell'immagine selezionata per il museo
                    Toast.makeText(this, "immagine caricata correttamente", Toast.LENGTH_SHORT).show();
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
     * Compila tutti i campi per visualizzare i
     * dati del museo selezionato
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    public void compilaDati(){
        imageUriString = museo.get("Immagine_Museo");

        ((EditText)findViewById(R.id.et_nome_museo)).setText(museo.get("Nome"));
        ((EditText)findViewById(R.id.et_telefono_museo)).setText(museo.get("Numero_Telefono"));
        ((EditText)findViewById(R.id.et_indirizzo_museo)).setText(museo.get("Indirizzo"));
        ((EditText)findViewById(R.id.et_citta_museo)).setText(museo.get("Citta"));
        ((EditText)findViewById(R.id.et_provincia_museo)).setText(museo.get("Provincia"));
        ((EditText)findViewById(R.id.et_cap_museo)).setText(museo.get("CAP"));
        ((EditText)findViewById(R.id.et_regione_museo)).setText(museo.get("Regione"));
        ((EditText)findViewById(R.id.et_email_museo)).setText(museo.get("Email_Contatti"));
        ((EditText)findViewById(R.id.et_orario_apertura_museo)).setText(museo.get("Orario_Apertura"));
        ((EditText)findViewById(R.id.et_sito_museo)).setText(museo.get("Sito_Web"));
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
     * Aggiorna un museo nel database
     */
    @Autore(autore = "Mattia Leonardo Angelillo")
    private void updateMuseo(Museo museo){
        DbManager db = new DbManager(this);
        if(db.aggiornaMuseo(museo)){
            Toast.makeText(this, "Museo aggiornato con successo", Toast.LENGTH_SHORT).show();
            clear();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(CRUDMuseoModificaActivity.this, CRUDSingleMuseoActivity.class);
            intent.putExtra("museo", this.museo);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Problema nell'aggiornamento del museo", Toast.LENGTH_SHORT).show();
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

            //Aggiorno il museo sul database
            Museo m = new Museo(
                    Integer.parseInt(museo.get("ID")),
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
            );
            museo = new HashMap<>();
            museo.put("ID", String.valueOf(m.getID()));
            museo.put("Nome", m.getNome());
            museo.put("Numero_Telefono", m.getTelefono());
            museo.put("Indirizzo", m.getIndirizzo());
            museo.put("Citta", m.getCitta());
            museo.put("Provincia", m.getProvincia());
            museo.put("CAP", m.getCap());
            museo.put("Regione", m.getRegione());
            museo.put("Email_Contatti", m.getEmail());
            museo.put("Sito_Web", m.getSito_web());
            museo.put("Orario_Apertura", m.getOrario());
            museo.put("Immagine_Museo", m.getImmagine());

            updateMuseo(m);

            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
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