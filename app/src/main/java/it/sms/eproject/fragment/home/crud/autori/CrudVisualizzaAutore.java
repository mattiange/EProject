package it.sms.eproject.fragment.home.crud.autori;

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

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.database.DBAutore;
import it.sms.eproject.database.DBMuseo;

@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudVisualizzaAutore extends Fragment {
    private Button btnShowAll;
    private Button btnCreate;

    View v;

    //campi autori
    EditText nome;
    EditText dataNascita;
    EditText dataMorte;
    EditText descrizione;
    TextView error;

    CallbackFragment callbackFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.crud_autori_create_fragment, container,false);

        inizializzaCampi();
        compilaCampi();

        return v;

    }

    /**
     * Inizializza i campi
     */
    public void inizializzaCampi(){
        nome        = v.findViewById(R.id.etNome);
        dataNascita = v.findViewById(R.id.etDataDiNascita);
        dataMorte   = v.findViewById(R.id.etDataMorte);
        descrizione = v.findViewById(R.id.etDescrizione);
        error       = v.findViewById(R.id.lblError);

        error.setVisibility(View.INVISIBLE);
    }

    /**
     * Compila i campi del museo da visualizzare
     */
    public void compilaCampi(){
        DBAutore db = new DBAutore(getContext());

        Autore autore = db.getAutore(Integer.parseInt(getArguments().getString("codice_museo")));
    }

}
