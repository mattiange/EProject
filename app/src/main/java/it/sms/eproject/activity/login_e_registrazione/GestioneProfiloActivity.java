package it.sms.eproject.activity.login_e_registrazione;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Utente;
import it.sms.eproject.database.DbManager;
import it.sms.eproject.util.Util;


public class GestioneProfiloActivity  extends AppCompatActivity{


    Button btnModify;
    String nome;
    String cognome;
    String password, ripetiPassword;
    EditText etNome, etCognome, etPassword, etRipetiPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestioneprofilo);

        registraElementi();
        impostAzioni();

    }

    public void registraElementi(){
        etNome    = findViewById(R.id.etNomeUtente);
        etCognome = findViewById(R.id.etCognomeUtente);
        etPassword  = findViewById(R.id.etPasswordUtente);
        etRipetiPassword = findViewById(R.id.etRipetiPasswordUtente);

        btnModify  = findViewById(R.id.btnModify);
    }

    public void impostAzioni(){


        btnModify.setOnClickListener(v -> {
                    nome = etNome.getText().toString();
                    cognome = etCognome.getText().toString();
                    password = etPassword.getText().toString();
                    ripetiPassword = etRipetiPassword.getText().toString();

                    TextView lblError = findViewById(R.id.lblError);
                    lblError.setVisibility(View.INVISIBLE);

                    if (!Util.campoCompilato(nome) && !Util.campoCompilato(cognome) && !Util.campoCompilato(password)
                            && !Util.campoCompilato(ripetiPassword)) {
                        lblError.setVisibility(View.VISIBLE);
                        lblError.setText(R.string.campi_vuoti);

                        return;
                    }

                    if (Util.confermaPassword(password, ripetiPassword)) {
                        //Toast.makeText(getContext(), "UGUALI", Toast.LENGTH_SHORT).show();

                    } else {
                        lblError.setVisibility(View.VISIBLE);
                        lblError.setText(R.string.passwords_not_equal);

                        return;
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        if (new DbManager(this).aggiornaProfilo(new Utente(nome, cognome, password))) {
                        }
                    }
                }
        );
    }

}