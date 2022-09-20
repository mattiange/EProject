package com.uniba.sms.eproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.database.DbManager;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar loginToolBar = findViewById(R.id.loginToolBar);
        loginToolBar.setTitle(R.string.login);
        setSupportActionBar(loginToolBar);


        ((findViewById(R.id.btnRegistrati))).setOnClickListener( v -> {
            registrazione();
        });
    }


    /**
     * Entra nell'app tramite login
     *
     * @param view
     */
    public void loginIntoApp(View view){
        EditText insertLoginEmail = findViewById(R.id.insertLoginEmail);
        TextView infoLoginEmail = findViewById(R.id.infoLoginEmail);

        EditText insertLoginPassword = findViewById(R.id.insertLoginPassword);
        TextView infoLoginPassword = findViewById(R.id.infoLoginPassword);

        //Flag per controllare se i dati inseriti sono validi
        boolean flag = true;

        if ( insertLoginEmail.getText().toString().equals("") ) {

            infoLoginEmail.setVisibility(View.VISIBLE);
            infoLoginEmail.setText(R.string.info_email);

            Toast.makeText(this, "Login fallito, controlla i campi errati", Toast.LENGTH_SHORT).show();
            flag = false;
        } else {
            infoLoginEmail.setVisibility(View.GONE);
            flag = true;
        }




        if ( insertLoginPassword.getText().toString().equals("") ) {

            infoLoginPassword.setVisibility(View.VISIBLE);
            infoLoginPassword.setText(R.string.null_password);

            Toast.makeText(this, "Login fallito, controlla i campi errati", Toast.LENGTH_SHORT).show();

            flag = false;
        } else {
            infoLoginPassword.setVisibility(View.GONE);
            flag = true;
        }


        if(flag){
            DbManager db = new DbManager(this);
            db.login(insertLoginEmail.getText().toString(), insertLoginPassword.getText().toString());

            startActivity(new Intent(this, HomeActivity.class));
        }else{
            Snackbar.make((ConstraintLayout)findViewById(R.id.loginLayout), "Dati di login errati", Snackbar.LENGTH_LONG).show();
        }
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
