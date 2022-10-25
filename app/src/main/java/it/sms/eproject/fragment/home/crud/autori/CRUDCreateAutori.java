package it.sms.eproject.fragment.home.crud.autori;

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

import java.time.LocalDate;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.database.DBAutore;
import it.sms.eproject.fragment.home.crud.oggetto.CRUDAutoreSalvatoSuccesso;

public class CRUDCreateAutori extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crud_autori_create_fragment, container,false);

        Button salva = v.findViewById(R.id.btn_salva);

        salva.setOnClickListener(e->{
            salva(v);
        });


        return v;
    }

    /**
     * Salva l'autore
     */
    public void salva(View v){
        EditText nome = v.findViewById(R.id.etNome);
        EditText dataNascita = v.findViewById(R.id.etDataDiNascita);
        EditText dataMorte = v.findViewById(R.id.etDataMorte);
        EditText descrizione = v.findViewById(R.id.etDescrizione);
        TextView error = v.findViewById(R.id.lblError);

        error.setVisibility(View.INVISIBLE);

        //controllo se è stato inserito
        //il nome dell'autore di un'opera
        if(nome.getText().toString().trim().isEmpty()){
            error.setVisibility(View.VISIBLE);
            error.setText(R.string.crud_autori_nome_obbligatorio);

            return;
        }

        //Salvo l'autore nel database
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if((new DBAutore(getContext())).inserisciAutore(
                    new Autore(
                            nome.getText().toString(),
                            dataNascita.getText().toString().trim().isEmpty() ? null: LocalDate.parse(dataNascita.getText().toString()),
                            dataMorte.getText().toString().trim().isEmpty() ? null: LocalDate.parse(dataMorte.getText().toString()),
                            descrizione.getText().toString()
                    )
            )){
                Fragment fragment = new CRUDAutoreSalvatoSuccesso();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.commit();
            }
        }
    }
}
