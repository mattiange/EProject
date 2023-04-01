package it.sms.eproject.fragment.backend.crud.eventi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.data.classes.Attivita;
import it.sms.eproject.database.DBAttivita;

public class CrudEventoShow extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudattivita_show_fragment, container,false);

        Attivita a = new DBAttivita(getContext()).getAttivita(getArguments().getLong("codice_attivita"));

        ((TextView)v.findViewById(R.id.titolo)).setText(a.getNome());
        ((TextView)v.findViewById(R.id.descrizione)).setText(a.getDescrizione());


        return v;

    }

    public void visualizzaFragment(CallbackFragment callbackFragment){
        callbackFragment.changeFragment();
    }

}
