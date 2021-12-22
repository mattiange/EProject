package com.uniba.sms.eproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startLogin(View view){

        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intentLogin);

    }

    public void startRegistration(View view){

        Intent intentRegistration = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intentRegistration);

    }
}