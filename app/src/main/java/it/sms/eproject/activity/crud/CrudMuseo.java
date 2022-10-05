package it.sms.eproject.activity.crud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;

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

        return v;

    }

    //Da implementare click sui bottoni



}
