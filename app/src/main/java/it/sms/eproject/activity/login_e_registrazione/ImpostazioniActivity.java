package it.sms.eproject.activity.login_e_registrazione;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;

public class ImpostazioniActivity extends PreferenceActivity {

    private Button btnImpostazioni;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.impostazioni, container, false);


        btnImpostazioni = v.findViewById(R.id.btnImpostazioni);
        btnImpostazioni.setOnClickListener(this::Impostazione1);


        return v;

    }

    private void Impostazione1(View e) {
        /** DA IMPLEMENTARE LE IMPOSTAZIONI
       /* Intent i = new Intent("com.example.testdati.ImpostazioniActivity");
        startActivity(i);*/
    }
}