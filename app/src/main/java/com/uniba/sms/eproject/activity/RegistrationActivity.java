package com.uniba.sms.eproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uniba.sms.eproject.R;

public class RegistrationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar registrationToolBar = findViewById(R.id.registrationToolBar);
        registrationToolBar.setTitle(R.string.registration);
        setSupportActionBar(registrationToolBar);

        ActionBar ab = getSupportActionBar();

        if (ab != null) { ab.setDisplayHomeAsUpEnabled(true); }

    }


    public void confirmRegistration(View view){
        EditText insertName = findViewById(R.id.insertName);
        TextView infoName = findViewById(R.id.infoName);

        EditText insertSurname = findViewById(R.id.insertSurname);
        TextView infoSurname = findViewById(R.id.infoSurname);

        EditText insertEmail = findViewById(R.id.insertEmail);
        TextView infoEmail = findViewById(R.id.infoEmail);

        EditText insertPassword = findViewById(R.id.insertPassword);
        TextView infoPassword = findViewById(R.id.infoPassword);

        EditText insertRepeatPassword = findViewById(R.id.insertRepeatPassword);
        TextView infoRepeatPassword = findViewById(R.id.infoRepeatPassword);

        int flag = 0;

        if ( insertName.getEditableText().toString().equals("") ) {

            infoName.setVisibility(View.VISIBLE);
            infoName.setText(R.string.info_name);
            flag = 1;

        } else { infoName.setVisibility(View.GONE); }



        if ( insertSurname.getEditableText().toString().equals("") ) {

            infoSurname.setVisibility(View.VISIBLE);
            infoSurname.setText(R.string.info_surname);
            flag = 1;

        } else { infoSurname.setVisibility(View.GONE); }




        if ( insertEmail.getEditableText().toString().equals("") ) {

            infoEmail.setVisibility(View.VISIBLE);
            infoEmail.setText(R.string.info_email);
            flag = 1;

        } else { infoEmail.setVisibility(View.GONE); }



        if ( insertPassword.getEditableText().toString().equals("") ) {

            infoPassword.setVisibility(View.VISIBLE);
            infoPassword.setText(R.string.null_password);
            flag = 1;

        } else if ( insertPassword.getEditableText().toString().length() < 6 ) {

            infoPassword.setVisibility(View.VISIBLE);
            infoPassword.setText(R.string.info_password);
            flag = 1;

        } else { infoPassword.setVisibility(View.GONE); }



        if ( insertRepeatPassword.getEditableText().toString().equals("") || !(insertPassword.getEditableText().toString().equals( insertRepeatPassword.getEditableText().toString() ) ) ) {

            infoRepeatPassword.setVisibility(View.VISIBLE);
            infoRepeatPassword.setText(R.string.passwords_not_equal);
            flag = 1;

        } else { infoRepeatPassword.setVisibility(View.GONE); }


    }

}
