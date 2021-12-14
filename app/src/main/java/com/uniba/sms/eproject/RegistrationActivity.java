package com.uniba.sms.eproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText insertName;
    private EditText insertSurname;
    private EditText insertUsername;
    private EditText insertEmail;
    private EditText insertPassword;
    private EditText insertRepeatPassword;
    private RadioGroup groupUserTypes;
    private RadioButton radioVisitatore;
    private RadioButton radioCuratore;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar registrationToolBar = findViewById(R.id.registrationToolBar);
        registrationToolBar.setTitle(R.string.registration);
        setSupportActionBar(registrationToolBar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        insertName = findViewById(R.id.insertName);

        insertSurname = findViewById(R.id.insertSurname);

        insertUsername = findViewById(R.id.insertUserName);

        insertEmail = findViewById(R.id.insertEmail);

        insertPassword = findViewById(R.id.insertPassword);

        insertRepeatPassword = findViewById(R.id.insertRepeatPassword);

        groupUserTypes = findViewById(R.id.groupUserTypes);
        radioCuratore = findViewById(R.id.radioCuratore);
        radioVisitatore = findViewById(R.id.radioVisitatore);


    }

    public void onUserTypeClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {

            case R.id.radioVisitatore:

                if (checked)

                    break;

            case R.id.radioCuratore:

                if (checked)

                    break;
        }
    }


    public void confirmRegistration(View view){



    }

}
