package it.sms.eproject.fragment.home.crud.percorso;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.fragment.home.crud.liste.ListaPercorso;
import it.sms.eproject.fragment.home.crud.liste.ListaStati;

public class CRUDPercorso extends Fragment {

    private Button btnCreate;
    private Button btnShow;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudpercorso_fragment, container, false);

        btnCreate = v.findViewById(R.id.btnCreaPercorso);
        btnShow = v.findViewById(R.id.btnVisualizzaPercorsi);

        btnCreate.setOnClickListener(this::nuovoPercorso);
        btnShow.setOnClickListener(this::visualizzaPercorsi);

        return v;
    }

    public void visualizzaFragment(CallbackFragment callbackFragment){
        callbackFragment.changeFragment();
    }

    /**
     * Visualizzo la pagina di creazione dei percorsi
     *
     * @param e
     */
    private void nuovoPercorso(View e) {
        visualizzaFragment(() -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("azione-lista","nuovo-percorso");
            editor.apply();

            Fragment fragment = new ListaStati();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }

    /**
     * Visualizzo la l'elenco di tutti i percorsi creati
     *
     * @param e
     */
    private void visualizzaPercorsi(View e) {
        visualizzaFragment(() -> {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("azione-lista","show-percorsi");
            editor.apply();

            Fragment fragment = new ListaPercorso();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }
}
