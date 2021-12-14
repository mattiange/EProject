package com.uniba.sms.eproject;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class LoginActivity extends AppCompatActivity {

    private EditText insertEmail;
    private EditText insertPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar loginToolBar = findViewById(R.id.loginToolBar);
        loginToolBar.setTitle(R.string.login);
        setSupportActionBar(loginToolBar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        insertEmail = findViewById(R.id.insertEmail);
        insertPassword = findViewById(R.id.insertPassword);

    }



}
