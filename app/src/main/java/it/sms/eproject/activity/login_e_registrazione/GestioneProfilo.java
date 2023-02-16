package it.sms.eproject.activity.login_e_registrazione;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;

public class GestioneProfilo extends Fragment {
    private Button btnModify;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gestioneprofilo_fragment, container,false);

        btnModify = v.findViewById(R.id.btnModificaProfilo);

        btnModify.setOnClickListener(this::modificaProfilo);


        return v;

    }

    /**
     * Visualizzo la pagina di modifica del profilo
     *
     * @param e
     */

    private void modificaProfilo(View e) {

        /**Intent i = new Intent("com.example.testdati.GestioneProfiloModifica");
        startActivity(i)**/

    }
}
