package it.sms.eproject.fragment.backend.crud.autori;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.fragment.backend.crud.liste.ListaAutori;

/**
 * Fragment di risposta per un autore inserito
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CRUDAutoreSalvatoSuccesso extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crud_autore_salvato_successo_fragment, container,false);

        ((TextView)v.findViewById(R.id.titolo)).setText(String.format(getResources().getString(R.string.msg_success_salvato), getResources().getString(R.string.autore_uc)));

        FloatingActionButton elenco = v.findViewById(R.id.btnElenco);
        FloatingActionButton nuovo = v.findViewById(R.id.btnNuovoAutore);

        elenco.setOnClickListener(e->{
            getFragment(()->{
                Fragment fragment = new ListaAutori();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });

        nuovo.setOnClickListener(e->{
            getFragment(()->{
                Fragment fragment = new CRUDCreateAutori();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });


        return v;
    }

    public void getFragment(CallbackFragment callbackFragment){
        callbackFragment.changeFragment();
    }
}
