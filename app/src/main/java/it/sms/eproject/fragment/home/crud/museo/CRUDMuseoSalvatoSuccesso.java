package it.sms.eproject.fragment.home.crud.museo;

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
import it.sms.eproject.fragment.home.crud.liste.ListaMusei;
import it.sms.eproject.fragment.home.crud.liste.ListaStati;
import it.sms.eproject.util.Util;

public class CRUDMuseoSalvatoSuccesso extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudmuseo_salvato_successo_fragment, container,false);

        ((TextView)v.findViewById(R.id.titolo)).setText(String.format(getResources().getString(R.string.msg_success_salvato), getResources().getString(R.string.museo_uc)));

        ((FloatingActionButton)v.findViewById(R.id.btnNuovoMuseo)).setOnClickListener(v1 -> {
            Util.visualizzaFragment(() -> {
                Fragment fragment = new ListaStati();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });

        ((FloatingActionButton)v.findViewById(R.id.btnElencoMusei)).setOnClickListener(v1 -> {
            Util.visualizzaFragment(() -> {
                Fragment fragment = new ListaMusei();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });


        return v;
    }



}
