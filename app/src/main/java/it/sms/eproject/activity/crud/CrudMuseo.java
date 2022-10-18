package it.sms.eproject.activity.crud;

import android.os.Bundle;
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
import it.sms.eproject.activity.crud.liste.ListaStati;

public class CrudMuseo extends Fragment {
    private Button btnShowAll;
    private Button btnCreate;

    CallbackFragment callbackFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudmuseo_fragment, container,false);

        btnCreate = v.findViewById(R.id.btnCreaMuseo);
        btnShowAll = v.findViewById(R.id.btnVisualizzaMuseo);

        btnCreate.setOnClickListener(this::nuovoMuseo);

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
    private void nuovoMuseo(View e) {
        visualizzaFragment(() -> {
            Fragment fragment = new ListaStati();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.commit();
        });
    }
}
