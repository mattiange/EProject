package com.uniba.sms.eproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        Toast.makeText(this, "Da implementare", Toast.LENGTH_SHORT).show();

        EditText insertName = findViewById(R.id.insertName);
        TextView infoName = findViewById(R.id.infoName);

        EditText insertSurname = findViewById(R.id.insertSurname);
        TextView infoSurname = findViewById(R.id.infoSurname);

        EditText insertUserName = findViewById(R.id.insertUserName);
        TextView infoUsername = findViewById(R.id.infoUserName);

        EditText insertEmail = findViewById(R.id.insertEmail);
        TextView infoEmail = findViewById(R.id.infoEmail);

        EditText insertPassword = findViewById(R.id.insertPassword);
        TextView infoPassword = findViewById(R.id.infoPassword);

        EditText insertRepeatPassword = findViewById(R.id.insertRepeatPassword);
        TextView infoRepeatPassword = findViewById(R.id.infoRepeatPassword);

        RadioButton radioVisitatore = findViewById(R.id.radioVisitatore);
        boolean checkedVisitatore = ((RadioButton) radioVisitatore).isChecked();

        RadioButton radioCuratore = findViewById(R.id.radioCuratore);
        boolean checkedCuratore = ((RadioButton) radioCuratore).isChecked();

        TextView infoUserType = findViewById(R.id.infoUserType);

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



        if ( insertUserName.getEditableText().toString().equals("") ) {

            infoUsername.setVisibility(View.VISIBLE);
            infoUsername.setText(R.string.null_username);
            flag = 1;

        } else if ( insertUserName.getEditableText().toString().contains(" ") ){

            infoUsername.setVisibility(View.VISIBLE);
            infoUsername.setText(R.string.info_username);
            flag = 1;

        } else { infoUsername.setVisibility(View.GONE); }



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


        if ( !(checkedVisitatore) && !(checkedCuratore) ) {

            infoUserType.setVisibility(View.VISIBLE);
            infoUserType.setText(R.string.infouser_type);
            flag = 1;

        } else { infoUserType.setVisibility(View.GONE); }

    }

}
