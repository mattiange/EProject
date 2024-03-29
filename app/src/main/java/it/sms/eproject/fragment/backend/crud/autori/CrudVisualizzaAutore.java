package it.sms.eproject.fragment.backend.crud.autori;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.database.DBAutore;
import it.sms.eproject.util.EseguiFragment;
import it.sms.eproject.util.Util;

/**
 * Fragment utilizzato per visualizzare un autore
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudVisualizzaAutore extends Fragment {
    View v;

    //campi autori
    TextView titolo;
    EditText nome;
    EditText dataNascita;
    EditText dataMorte;
    EditText descrizione;
    TextView error;
    Button   salva;

    //Autore letto
    Autore autore;

    DBAutore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.crud_autori_create_fragment, container,false);

        init();
        compilaCampi();

        salva.setOnClickListener(e->{
            error.setVisibility(View.INVISIBLE);

            autore.setNome(nome.getText().toString());
            autore.setDataDiNascita(Util.parseDate(dataNascita.getText().toString()));
            autore.setDataDiMorte(Util.parseDate(dataMorte.getText().toString()));
            autore.setDescrizione(descrizione.getText().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(db.aggiornaAutore(autore)){
                    EseguiFragment.changeFragment(()->{
                        Fragment fragment = new CRUDAutoreSalvatoSuccesso();

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                        fragmentTransaction.commit();
                    });
                }else {
                    error.setVisibility(View.VISIBLE);
                    error.setText(R.string.msg_error_update);
                }
            }


        });

        return v;

    }

    /**
     * Inizializza i campi
     */
    @AutoreCodice(autore = "Mattia Leonardo Angelillo")
    public void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            db = new DBAutore(getContext());
            autore = db.getAutore(Integer.parseInt(getArguments().getString("codice_autore")));
        }

        titolo      = v.findViewById(R.id.titolo);
        nome        = v.findViewById(R.id.etNome);
        dataNascita = v.findViewById(R.id.etDataDiNascita);
        dataMorte   = v.findViewById(R.id.etDataMorte);
        descrizione = v.findViewById(R.id.etDescrizione);
        error       = v.findViewById(R.id.lblError);

        salva       = v.findViewById(R.id.btn_salva);

        error.setVisibility(View.INVISIBLE);
    }

    /**
     * Compila i campi del museo da visualizzare
     */
    @AutoreCodice(autore = "Mattia Leonardo Angelillo")
    public void compilaCampi(){
        this.titolo.setText(getResources().getText(R.string.crud_autori_update_titolo));
        this.nome.setText(autore.getNome());
        this.dataNascita.setText(autore.getDataDiNascita()==null?"":autore.getDataDiNascita().toString());
        this.dataMorte.setText(autore.getDataDiMorte()==null?"":autore.getDataDiMorte().toString());
        this.descrizione.setText(autore.getDescrizione());
    }
}
