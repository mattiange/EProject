package com.uniba.sms.eproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar mainToolBar = findViewById(R.id.mainToolBar);

        setSupportActionBar(mainToolBar);

        Button login = findViewById(R.id.buttonLogin);
        Button registration = findViewById(R.id.buttonRegistration);
        Button guestAccess = findViewById(R.id.buttonGuestAccess);


    }

    public void startLogin(View view){

        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intentLogin);

    }

    public void startRegistration(View view){

        Intent intentRegistration = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intentRegistration);

    }

    public void accessAsGuest(View view){


    }
}