package com.uniba.sms.eproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar loginToolBar = findViewById(R.id.loginToolBar);
        loginToolBar.setTitle(R.string.login);
        setSupportActionBar(loginToolBar);

        ActionBar ab = getSupportActionBar();

        if (ab != null) { ab.setDisplayHomeAsUpEnabled(true); }

    }


    public void loginIntoApp(View view){

        Toast.makeText(this, "Da implementare", Toast.LENGTH_SHORT).show();

        EditText insertLoginEmail = findViewById(R.id.insertLoginEmail);
        TextView infoLoginEmail = findViewById(R.id.infoLoginEmail);

        EditText insertLoginPassword = findViewById(R.id.insertLoginPassword);
        TextView infoLoginPassword = findViewById(R.id.infoLoginPassword);

        if ( insertLoginEmail.getEditableText().toString().equals("") ) {

            infoLoginEmail.setVisibility(View.VISIBLE);
            infoLoginEmail.setText(R.string.info_email);

            Toast.makeText(this, "Login fallito, controlla i campi errati", Toast.LENGTH_SHORT).show();

        } else { infoLoginEmail.setVisibility(View.GONE); }




        if ( insertLoginPassword.getEditableText().toString().equals("") ) {

            infoLoginPassword.setVisibility(View.VISIBLE);
            infoLoginPassword.setText(R.string.null_password);

            Toast.makeText(this, "Login fallito, controlla i campi errati", Toast.LENGTH_SHORT).show();

        } else { infoLoginPassword.setVisibility(View.GONE); }


    }

    public void forgotPassword(View view){

        Toast.makeText(this, "Da implementare", Toast.LENGTH_SHORT).show();

    }



}
