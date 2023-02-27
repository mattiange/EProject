package it.sms.eproject.fragment.home.crud.museo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DbManager;
import it.sms.eproject.fragment.home.crud.liste.ListaMusei;
import it.sms.eproject.fragment.home.crud.liste.ListaStati;
import it.sms.eproject.util.EseguiFragment;
import it.sms.eproject.util.Util;

@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudVisualizzaMuseo extends Fragment {
    private Button btnShowAll;
    private Button btnCreate;

    View v;

    //campi museo
    EditText codice;
    EditText nome;
    EditText telefono;
    EditText indirizzo;
    EditText email;
    EditText sito;
    EditText orarioApertura;
    ImageView immagineImv;
    EditText codiceCitta;
    EditText nomeCitta;

    ActivityResultLauncher<Intent> launchSomeActivity;
    Bitmap selectedImageBitmap;

    private final int STORAGE_PERMISSION_CODE = 23;
    ImageView imageView;

    byte[] immagine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.crudmuseo_visualizza_fragment, container,false);

        Button btnImageUpload = v.findViewById(R.id.btn_upload_immagine);
        Button salvaBtn = v.findViewById(R.id.btn_salva_museo);

        imageView = v.findViewById(R.id.imageView);

        launchSomeActivity
                = registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                result -> {
                    if (result.getResultCode()
                            == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        //recupero i dati dell'immagine selezionata
                        if (data != null
                                && data.getData() != null) {
                            Uri selectedImageUri = data.getData();
                            selectedImageBitmap = null;
                            try {
                                selectedImageBitmap
                                        = MediaStore.Images.Media.getBitmap(
                                        this.getActivity().getContentResolver(),
                                        selectedImageUri);
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] b = stream.toByteArray();
                            immagine = b;
                            //selectedImageBitmap.recycle();
                            DbManager dbManager = new DbManager(getContext());
                            //dbManager.inserisciMuseo();

                            //Visualizzo l'immagine selezionata
                            imageView.setImageBitmap(
                                    selectedImageBitmap);
                        }
                    }
                });

        //Richiedo il permesso per aprire la galleria
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        btnImageUpload.setOnClickListener(this::fetchImage);
        salvaBtn.setOnClickListener(v1 -> salvaMuseo());

        inizializzaCampi();
        compilaCampi();

        return v;

    }

    /**
     * Inizializza i campi
     */
    public void inizializzaCampi(){
        this.codice = (EditText) this.v.findViewById(R.id.etCodiceMuseo);
        this.nome = (EditText) this.v.findViewById(R.id.etNomeMuseo);
        this.telefono = (EditText) this.v.findViewById(R.id.etNumTelefono);
        this.indirizzo = (EditText) this.v.findViewById(R.id.etIndirizzo);
        this.email = (EditText) this.v.findViewById(R.id.etEmail);
        this.sito = (EditText) this.v.findViewById(R.id.etSitoWeb);
        this.orarioApertura = (EditText) this.v.findViewById(R.id.etOrario);


        this.codiceCitta = (EditText) this.v.findViewById(R.id.etIdCitta);
        this.nomeCitta = (EditText) this.v.findViewById(R.id.etCitta);

        this.immagineImv = (ImageView)this.v.findViewById(R.id.imageView);


    }

    /**
     * Compila i campi del museo da visualizzare
     */
    public void compilaCampi(){
        DBMuseo db = new DBMuseo(getContext());
        DBCitta dbCitta = new DBCitta(getContext());

        Museo museo = db.getMuseo(Long.parseLong(getArguments().getString("codice_museo")));

        this.codice.setText(String.valueOf(museo.getID()));
        this.nome.setText(museo.getNome());
        this.telefono.setText(museo.getTelefono());
        this.indirizzo.setText(museo.getIndirizzo());
        this.email.setText(museo.getEmail());
        this.sito.setText(museo.getSito_web());
        this.orarioApertura.setText(museo.getOrario());

        this.codiceCitta.setText(String.valueOf(museo.getCitta()));
        this.nomeCitta.setText(dbCitta.getNomeCitta(museo.getCitta()));

        Bitmap bitmap = Util.getImageDataInBitmap(museo.getImmagine());
        this.immagineImv.setImageBitmap(bitmap);
    }

    /**
     * Aggiorna il museo
     */
    private void salvaMuseo() {
        EditText codiceMuseo        = v.findViewById(R.id.etCodiceMuseo);
        EditText nome               = v.findViewById(R.id.etNomeMuseo);
        EditText telefono           = v.findViewById(R.id.etNumTelefono);
        EditText indirizzo          = v.findViewById(R.id.etIndirizzo);
        EditText email              = v.findViewById(R.id.etEmail);
        EditText sito_web           = v.findViewById(R.id.etSitoWeb);
        EditText orario_apertura    = v.findViewById(R.id.etOrario);
        EditText citta              = v.findViewById(R.id.etIdCitta);
        EditText durata_visita      = v.findViewById(R.id.etDurataVisita);
        TextView lblError           = v.findViewById(R.id.lblError);

        lblError.setVisibility(View.INVISIBLE);

        boolean salva;

        if(nome.getText().toString().trim().isEmpty()){
            lblError.setVisibility(View.VISIBLE);

            lblError.setText(R.string.campi_vuoti);

            salva = false;
        }else{
            salva = true;
        }


        if(!Util.checkEmail(email.getText().toString()) && !email.getText().toString().trim().isEmpty()){
            lblError.setVisibility(View.VISIBLE);

            lblError.setText(R.string.email_non_corretta);

            salva = false;
        }else{
            salva = true;
        }
        if(salva) {
            DBMuseo dbMuseo = new DBMuseo(getContext());
            System.out.println("Nome del museo: " + nome.getText().toString());

            if (dbMuseo.aggiornaMuseo(new Museo(
                    Integer.parseInt(codiceMuseo.getText().toString()),
                    nome.getText().toString(),
                    telefono.getText().toString(),
                    indirizzo.getText().toString(),
                    Integer.parseInt(citta.getText().toString()),
                    email.getText().toString(),
                    sito_web.getText().toString(),
                    orario_apertura.getText().toString(),
                    immagine,
                    Integer.parseInt(durata_visita.getText().toString())
            ))) {
                System.out.println("UPDATE MUSEO OK");

                EseguiFragment.changeFragment(()-> {
                    Fragment fragment = new CRUDMuseoEliminatoSuccesso();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                    fragmentTransaction.commit();
                });
            } else {
                System.out.println("UPDATE MUSEO NO");

                Toast.makeText(getContext(), String.format(getResources().getString(R.string.msg_error_salvataggio), getResources().getString(R.string.il_museo)), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Recupera l'immagine dalla galleria
     *
     * @param view Vista di riferimento
     */
    public  void fetchImage(View view){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);

    }

}
