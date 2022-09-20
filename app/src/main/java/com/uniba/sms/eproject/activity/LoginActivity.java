package com.uniba.sms.eproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.data.classes.Permesso;
import com.uniba.sms.eproject.data.classes.Utente;
import com.uniba.sms.eproject.database.DbManager;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar loginToolBar = findViewById(R.id.loginToolBar);
        loginToolBar.setTitle(R.string.login);
        setSupportActionBar(loginToolBar);

        loginIntoApp();

        //registrazione nuovo utente
        ((findViewById(R.id.btnRegistrati))).setOnClickListener( v -> registrazione());
    }

    /**
     * Entra nell'app tramite login
     */
    public void loginIntoApp(){
        EditText insertLoginEmail = findViewById(R.id.insertLoginEmail);
        TextView infoLoginEmail = findViewById(R.id.infoLoginEmail);

        EditText insertLoginPassword = findViewById(R.id.insertLoginPassword);
        TextView infoLoginPassword = findViewById(R.id.infoLoginPassword);

        //login utente registrato
        (findViewById(R.id.loginButton)).setOnClickListener( v-> {
            if(insertLoginEmail.getText().toString().trim().isEmpty()){
                infoLoginEmail.setVisibility(View.VISIBLE);
            }
            if(insertLoginPassword.getText().toString().trim().isEmpty()){
                infoLoginPassword.setVisibility(View.VISIBLE);
            }

            DbManager dbManager = new DbManager(this);
            Utente u = dbManager.login(insertLoginEmail.getText().toString(), insertLoginPassword.getText().toString());
            if( u != null){
                if(u.getPermesso_id() == Permesso.VISITATORE){
                    startActivity(new Intent(this, HomeActivity.class));
                }else if(u.getPermesso_id() == Permesso.CURATORE){
                }
            }else{
                Snackbar.make(findViewById(R.id.loginLayout), getResources().getText(R.string.login_error), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void forgotPassword(View view){
        Toast.makeText(this, "Da implementare", Toast.LENGTH_SHORT).show();

    }

    /**
     * Roconduce all'activity per registrarsi
     */
    public void registrazione(){
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

}
