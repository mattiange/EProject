package com.uniba.sms.eproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

        TextView labelName = findViewById(R.id.labelName);
        insertName = findViewById(R.id.insertName);

        TextView labelSurname = findViewById(R.id.labelSurname);
        insertSurname = findViewById(R.id.insertSurname);

        TextView labelUsername = findViewById(R.id.labelUserName);
        insertUsername = findViewById(R.id.insertUserName);

        TextView labelEmail = findViewById(R.id.labelEmail);
        insertEmail = findViewById(R.id.insertEmail);

        TextView labelPassword = findViewById(R.id.labelPassword);
        insertPassword = findViewById(R.id.insertPassword);

        TextView labelRepeatPassword = findViewById(R.id.labelRepeatPassword);
        insertRepeatPassword = findViewById(R.id.insertRepeatPassword);

        TextView labelUserType = findViewById(R.id.labelUserType);
        groupUserTypes = findViewById(R.id.groupUserTypes);
        radioCuratore = findViewById(R.id.radioCuratore);
        radioVisitatore = findViewById(R.id.radioVisitatore);

        Button buttonRegistrationForm = findViewById(R.id.buttonRegistrationForm);


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
