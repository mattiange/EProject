package it.sms.eproject.fragment.home.crud.oggetto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import it.sms.eproject.fragment.home.crud.liste.ListaOggetti;
import it.sms.eproject.fragment.home.crud.liste.ListaStati;
import it.sms.eproject.util.Util;

public class CRUDOggettoEliminatoSuccesso extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudmuseo_salvato_successo_fragment, container,false);

        ((TextView)v.findViewById(R.id.titolo)).setText(String.format(getResources().getString(R.string.msg_success_eliminato), getResources().getString(R.string.object)));


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("azione-lista","");
        editor.apply();

        ((FloatingActionButton)v.findViewById(R.id.btnNuovoMuseo)).setOnClickListener(v1 -> {
            Util.visualizzaFragment(() -> {
                editor.putString("azione-lista","nuovo-oggetto");
                editor.apply();

                Fragment fragment = new ListaStati();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });

        ((FloatingActionButton)v.findViewById(R.id.btnElencoMusei)).setOnClickListener(v1 -> {
            Util.visualizzaFragment(() -> {
                Fragment fragment = new ListaOggetti();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });


        return v;
    }



}
