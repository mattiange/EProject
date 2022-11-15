package it.sms.eproject.fragment.home.crud.autori;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.fragment.home.crud.liste.ListaAutori;

public class CRUDAutore extends Fragment {
    private ImageButton btnShowAll;
    private ImageButton btnCreate;

    CallbackFragment callbackFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crud_autore_fragment, container,false);

        btnCreate = v.findViewById(R.id.btnNuovo);
        btnShowAll = v.findViewById(R.id.btnElenco);

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
            Fragment fragment = new ListaAutori();

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
            Fragment fragment = new CRUDCreateAutori();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }
}
