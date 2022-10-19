package it.sms.eproject.activity.crud;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.database.DbManager;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CrudMuseo_Create extends Fragment {
    ActivityResultLauncher<Intent> launchSomeActivity;

    private int STORAGE_PERMISSION_CODE = 23;
    ImageView imageView;
    SQLiteDatabase db;

    byte[] immagine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.crudmuseo_create_fragment, container, false);

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
                            Bitmap selectedImageBitmap = null;
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
                            //selectedImageBitmap.recycle();
                            DbManager dbManager = new DbManager(getContext());
                            //dbManager.inserisciMuseo();

                            imageView.setImageBitmap(
                                    selectedImageBitmap);
                        }
                    }
                });
        /*btnImageUpload.setOnClickListener(
                view -> {
                    try {
                        fetchImage(view);
                    }catch (IOException e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println("ERRORE: " + e.getMessage());
                        Log.e("IOEXCEPTION", e.getMessage());
                    }
                }
        );
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
         */
        btnImageUpload.setOnClickListener(this::fetchImage);
        salvaBtn.setOnClickListener(this::salvaMuseo);

        //Setto il nome della città e il suo ID
        ((TextView)v.findViewById(R.id.etIdCitta)).setText(String.valueOf(getArguments().getString("codice_citta")));
        ((TextView)v.findViewById(R.id.etCitta)).setText(String.valueOf(getArguments().getString("nome_citta")));

        return v;
    }

    private void salvaMuseo(View view) {
        EditText nome = view.findViewById(R.id.etNomeMuseo);
        EditText telefono = view.findViewById(R.id.etNomeMuseo);
        EditText indirizzo = view.findViewById(R.id.etIndirizzo);
        EditText citta = view.findViewById(R.id.etCitta);
        EditText email = view.findViewById(R.id.etEmail);
        EditText sito_web = view.findViewById(R.id.etSitoWeb);
        EditText orario_apertura = view.findViewById(R.id.etOrario);

        /*Museo m = new Museo(nome.getText().toString(), telefono.getText().toString(), indirizzo.getText().toString(),
                            citta.getText().toString(), provincia.getText().toString(), cap.getText().toString(),
                            email.getText().toString(), sito_web.getText().toString(), orario_apertura.getText().toString(),
                        immagine);*/
    }

    /*public void viewImage(View view)
    {
        Cursor c = db.rawQuery("select * from imageTb", null);
        if(c.moveToNext())
        {
            byte[] image = c.getBlob(0);
            Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
            imageView.setImageBitmap(bmp);
            Toast.makeText(getContext(),"Done", Toast.LENGTH_SHORT).show();
        }
    }*/

    public  void fetchImage(View view)/* throws IOException */{

        /*File folder= new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
        FileInputStream fis = new FileInputStream(folder);
        byte[] image= new byte[fis.available()];
        fis.read(image);
        ContentValues values = new ContentValues();
        values.put("image",image);
        db.insert("imageTb", null,values);
        fis.close();
        Toast.makeText(getContext(),"Image Fetched", Toast.LENGTH_SHORT).show();*/


        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(i, "Select Picture"), CODE_OK);
        launchSomeActivity.launch(i);

    }

     /* private void uploadImmagine(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(i, "Select Picture"), CODE_OK);
        launchSomeActivity.launch(i);
    }*/
}
