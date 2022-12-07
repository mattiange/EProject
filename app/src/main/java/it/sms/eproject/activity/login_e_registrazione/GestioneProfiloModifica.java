package it.sms.eproject.activity.login_e_registrazione;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;

public class GestioneProfiloModifica extends Fragment {

    private Button btnConfirm;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gestioneprofilo_modifica_fragment, container,false);

        btnConfirm = v.findViewById(R.id.btnConfirmModifica);

        return v;

    }
}
