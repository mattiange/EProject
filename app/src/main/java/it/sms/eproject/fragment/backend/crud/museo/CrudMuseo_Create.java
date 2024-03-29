package it.sms.eproject.fragment.backend.crud.museo;

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

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DbManager;
import it.sms.eproject.util.EseguiFragment;
import it.sms.eproject.util.Util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Pagina di creazione di un nuovo museo
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudMuseo_Create extends Fragment {
    ActivityResultLauncher<Intent> launchSomeActivity;
    Bitmap selectedImageBitmap;

    private final int STORAGE_PERMISSION_CODE = 23;
    ImageView imageView;

    byte[] immagine;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.crudmuseo_create_fragment, container, false);

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

        //Setto il nome della città e il suo ID
        ((TextView)v.findViewById(R.id.etIdCitta)).setText(String.valueOf(getArguments().getString("codice_citta")));
        ((TextView)v.findViewById(R.id.etCitta)).setText(String.valueOf(getArguments().getString("nome_citta")));

        return v;
    }

    private void salvaMuseo() {
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
            if (dbMuseo.inserisciMuseo(new Museo(
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
                EseguiFragment.changeFragment(()-> {
                    Fragment fragment = new CRUDMuseoSalvatoSuccesso();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                    fragmentTransaction.addToBackStack(null).commit();
                });
            } else {
                Toast.makeText(getContext(), String.format(getResources().getString(R.string.msg_error_salvataggio), getResources().getString(R.string.il_museo)), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void fetchImage(View view){


        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);

    }

}
