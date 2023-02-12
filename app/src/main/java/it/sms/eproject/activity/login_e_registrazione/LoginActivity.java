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

import java.util.ArrayList;

import it.sms.eproject.R;
import it.sms.eproject.activity.MainActivity;
import it.sms.eproject.data.classes.Utente;
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

        /*SOLO PER TEST (LOGIN DIRETTA)*/
        email = "m.rossi@gmail.com";
        password = "test";
        Utente u;
        if((u = new DbManager(this).login(email, password)) != null){
            //Dati dell'utente da passare tra le varie sezioni del sito
            SharedPreferences pref = getApplicationContext().getSharedPreferences("credenziali", 0);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("user_id", String.valueOf(u.getCodice()));
            editor.putString("user_nome", u.getNome());
            editor.putString("user_cognome", u.getCognome());
            editor.putString("user_codice_fiscale", u.getCodice_fiscale());
            editor.putString("user_email", u.getEmail());
            editor.putString("user_data_di_nascita", String.valueOf(u.getData_di_nascita()));
            editor.putString("user_permesso_codice", String.valueOf(u.getPermesso().getCodice()));
            editor.putString("user_permesso_nome", String.valueOf(u.getPermesso().getPermesso()));
            editor.apply();
            //-------------------------------

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        //---------------------------------------------

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

            Utente u1 = null;
            if((u1 = new DbManager(this).login(email, password)) != null){
                //Dati dell'utente da passare tra le varie sezioni del sito
                SharedPreferences pref = getApplicationContext().getSharedPreferences("credenziali", 0);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("user_id", String.valueOf(u1.getCodice()));
                editor.putString("user_nome", u1.getNome());
                editor.putString("user_cognome", u1.getCognome());
                editor.putString("user_codice_fiscale", u1.getCodice_fiscale());
                editor.putString("user_email", u1.getEmail());
                editor.putString("user_data_di_nascita", String.valueOf(u1.getData_di_nascita()));
                editor.putString("user_permesso_codice", String.valueOf(u1.getPermesso().getCodice()));
                editor.putString("user_permesso_nome", String.valueOf(u1.getPermesso().getPermesso()));
                editor.apply();
                //-------------------------------

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else{
                lblError.setVisibility(View.VISIBLE);
                lblError.setText(R.string.login_non_valida);
            }
        });


    }

}
