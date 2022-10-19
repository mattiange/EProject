package it.sms.eproject.fragment.home.crud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;

public class CrudOggetto extends Fragment {

    private Button btnShowAll;
    private Button btnCreate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudoggetto_fragment, container, false);

        btnCreate = v.findViewById(R.id.btnCreaOggetto);
        btnShowAll = v.findViewById(R.id.btnVisualizzaOggetto);

        return v;
    }

    //Da implementare click sui bottoni
}