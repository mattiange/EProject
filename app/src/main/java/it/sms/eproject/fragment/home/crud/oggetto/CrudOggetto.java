package it.sms.eproject.fragment.home.crud.oggetto;

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
import it.sms.eproject.fragment.home.crud.liste.ListaMusei;
import it.sms.eproject.fragment.home.crud.liste.ListaOggetti;
import it.sms.eproject.fragment.home.crud.liste.ListaStati;

public class CrudOggetto extends Fragment {

    private ConstraintLayout btnShowAll;
    private ConstraintLayout btnCreate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudoggetto_fragment, container, false);

        btnCreate = v.findViewById(R.id.btnNuovo);
        btnShowAll = v.findViewById(R.id.btnElenco);


        btnCreate.setOnClickListener(this::nuovoOggetto);
        btnShowAll.setOnClickListener(this::visualizzaOggetti);

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
    private void visualizzaOggetti(View e) {
        visualizzaFragment(() -> {
            Fragment fragment = new ListaOggetti();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.commit();
        });
    }


    /**
     * Visualizzo la pagina di creazione dei musei
     *
     * @param e
     */
    private void nuovoOggetto(View e) {
        visualizzaFragment(() -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("azione-lista","nuovo-oggetto");
            editor.apply();

            Fragment fragment = new ListaStati();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.commit();
        });
    }
}