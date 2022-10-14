package it.sms.eproject.activity.login_e_registrazione;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import it.sms.eproject.R;
import it.sms.eproject.activity.MainActivity;
import it.sms.eproject.database.DbManager;
import it.sms.eproject.util.Util;

public class LoginActivity extends AppCompatActivity {
    Button buttonLogin,
            buttonRegister;
    EditText etEmail, etPassword;
    String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        registraElementi();

        impostaAzioni();
    }

    /**
     * Registra gli elementi dell'activity
     */
    public void registraElementi(){
        etEmail     = findViewById(R.id.etEmail);
        etPassword  = findViewById(R.id.etPassword);

        buttonLogin     = findViewById(R.id.btnLogin);
        buttonRegister  = findViewById(R.id.btnRegister);
    }


    /**
     * Imposta le azioni dell'activity
     */
    public void impostaAzioni(){

        //Riporto alla pagina di registrazione di un nuovo utente
        buttonRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegistrazioneActivity.class)));


        //Effettuo il login
        buttonLogin.setOnClickListener(v -> {
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            TextView lblError = findViewById(R.id.lblError);
            lblError.setVisibility(View.INVISIBLE);
            if(email.trim().isEmpty() || password.trim().isEmpty()){
                lblError.setText(R.string.campi_vuoti);
                lblError.setVisibility(View.VISIBLE);

                return;
            }

            if(!Util.verificaEmail(email)) {//Verifico se l'email inserita è corretta
                lblError.setText(R.string.email_non_corretta);
                lblError.setVisibility(View.VISIBLE);

                return;
            }


            if(new DbManager(this).login(email, password) != null){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else{
                lblError.setVisibility(View.VISIBLE);
                lblError.setText(R.string.login_non_valida);
            }
        });


    }

}
