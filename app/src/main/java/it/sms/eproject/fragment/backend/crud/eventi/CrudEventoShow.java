package it.sms.eproject.fragment.backend.crud.eventi;

import android.os.Bundle;
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

public class CrudEventoShow extends Fragment {
    private ConstraintLayout btnShowAll;
    private ConstraintLayout btnCreate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudattivita_fragment, container,false);

        btnCreate = v.findViewById(R.id.btnCreaEvento);
        btnShowAll = v.findViewById(R.id.btnVisualizzaEvento);

        btnCreate.setOnClickListener(this::nuovoEvento);
        btnShowAll.setOnClickListener(this::visualizzaEvento);


        return v;

    }

    public void visualizzaFragment(CallbackFragment callbackFragment){
        callbackFragment.changeFragment();
    }

    /**
     * Visualizza tutti gli eventi inseriti
     *
     * @param e
     */
    private void visualizzaEvento(View e) {



        /*visualizzaFragment(() -> {
            Fragment fragment = new ListaMusei();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });*/
    }

    /**
     * Visualizzo la pagina di creazione di un nuovo evento
     *
     * @param e
     */
    private void nuovoEvento(View e) {
        visualizzaFragment(() -> {
            Fragment fragment = new CrudElencoLuoghi();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }

}
