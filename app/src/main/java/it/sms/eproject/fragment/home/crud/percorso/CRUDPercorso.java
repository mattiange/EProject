package it.sms.eproject.fragment.home.crud.percorso;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;

public class CRUDPercorso extends Fragment {

    private Button btnCreate;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudpercorso_create_fragment, container, false);

        btnCreate = v.findViewById(R.id.btnCreaPercorso);
        return v;
    }
}
