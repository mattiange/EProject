package com.uniba.sms.eproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegistrationActivity extends AppCompatActivity {


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

        if (ab != null) { ab.setDisplayHomeAsUpEnabled(true); }

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

        Toast.makeText(this, "Da implementare", Toast.LENGTH_SHORT).show();

        TextView infoUsername = findViewById(R.id.infoUserName);
        TextView infoPassword = findViewById(R.id.infoPassword);
        EditText insertUserName = findViewById(R.id.insertUserName);
        EditText insertPassword = findViewById(R.id.insertPassword);

        int flag_password = 0;
        int flag_username = 0;

        if ( insertUserName.getEditableText().toString().equals("") ) {

            infoUsername.setVisibility(View.VISIBLE);
            infoUsername.setText(R.string.null_username);
            flag_username = 1;

        } else if ( insertUserName.getEditableText().toString().contains(" ") ){

            infoUsername.setVisibility(View.VISIBLE);
            infoUsername.setText(R.string.info_username);
            flag_username = 2;

        } else { infoUsername.setVisibility(View.GONE); }



        if ( insertPassword.getEditableText().toString().equals("") ) {

            infoPassword.setVisibility(View.VISIBLE);
            infoPassword.setText(R.string.null_password);
            flag_password = 1;

        } else if ( insertPassword.getEditableText().toString().length() < 6 ) {

            infoPassword.setVisibility(View.VISIBLE);
            infoPassword.setText(R.string.info_password);
            flag_password = 2;

        } else { infoPassword.setVisibility(View.GONE); }

    }

}
