package it.sms.eproject.fragment.home.crud.museo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.fragment.home.crud.liste.ListaMusei;
import it.sms.eproject.fragment.home.crud.liste.ListaStati;
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
    ImageView immagine;
    EditText codiceCitta;
    EditText nomeCitta;

    CallbackFragment callbackFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.crudmuseo_visualizza_fragment, container,false);

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

        this.immagine = (ImageView)this.v.findViewById(R.id.imageView);
    }

    /**
     * Compila i campi del museo da visualizzare
     */
    public void compilaCampi(){
        DBMuseo db = new DBMuseo(getContext());

        Museo museo = db.getMuseo(Integer.parseInt(getArguments().getString("codice_museo")));

        this.codice.setText(String.valueOf(museo.getID()));
        this.nome.setText(museo.getNome());
        this.telefono.setText(museo.getTelefono());
        this.indirizzo.setText(museo.getIndirizzo());
        this.email.setText(museo.getEmail());
        this.sito.setText(museo.getSito_web());
        this.orarioApertura.setText(museo.getOrario());


        this.codiceCitta.setText(museo.getNome());
        this.nomeCitta.setText(museo.getNome());

        Bitmap bitmap = Util.getImageDataInBitmap(museo.getImmagine());

        System.out.println("=========> BITMAP: " + bitmap);

        this.immagine.setImageBitmap(bitmap);
    }

}
