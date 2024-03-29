package it.sms.eproject.fragment.backend.crud.museo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.fragment.backend.crud.liste.ListaMusei;
import it.sms.eproject.fragment.backend.crud.liste.ListaStati;

/**
 * Home dei musei
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudMuseo extends Fragment {
    private ConstraintLayout btnShowAll;
    private ConstraintLayout btnCreate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudmuseo_fragment, container,false);

        btnCreate = v.findViewById(R.id.btnCreaMuseo);
        btnShowAll = v.findViewById(R.id.btnVisualizzaMuseo);

        btnCreate.setOnClickListener(this::nuovoMuseo);
        btnShowAll.setOnClickListener(this::visualizzaMusei);

        return v;

    }

    public void visualizzaFragment(CallbackFragment callbackFragment){
        callbackFragment.changeFragment();
    }


    /**
     * Visualizzo la pagina di creazione dei musei
     *
     * @param e
     */
    private void visualizzaMusei(View e) {
        visualizzaFragment(() -> {
            Fragment fragment = new ListaMusei();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }


    /**
     * Visualizzo la pagina di creazione dei musei
     *
     * @param e
     */
    private void nuovoMuseo(View e) {
        visualizzaFragment(() -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("azione-lista","nuovo-museo");
            editor.apply();
            Fragment fragment = new ListaStati();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }
}
