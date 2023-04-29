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
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.fragment.backend.crud.liste.ListaAutori;
import it.sms.eproject.fragment.backend.crud.liste.ListaStati;
import it.sms.eproject.util.Util;

/**
 * Fragment di risposta per un autore eliminato
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CRUDAutoreEliminatoSuccesso extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crud_autore_salvato_successo_fragment, container,false);

        ((TextView)v.findViewById(R.id.titolo)).setText(String.format(getResources().getString(R.string.msg_success_eliminato), getResources().getString(R.string.autore_uc)));

        ((FloatingActionButton)v.findViewById(R.id.btnNuovoAutore)).setOnClickListener(v1 -> {
            Util.visualizzaFragment(() -> {
                Fragment fragment = new ListaStati();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });

        ((FloatingActionButton)v.findViewById(R.id.btnElenco)).setOnClickListener(v1 -> {
            Util.visualizzaFragment(() -> {
                Fragment fragment = new ListaAutori();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });


        return v;
    }



}
