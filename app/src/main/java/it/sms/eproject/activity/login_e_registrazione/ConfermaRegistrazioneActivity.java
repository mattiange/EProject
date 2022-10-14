package it.sms.eproject.activity.login_e_registrazione;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import it.sms.eproject.R;

public class ConfermaRegistrazioneActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conferma_registrazione);

        Button login = findViewById(R.id.btnLogin);
        login.setOnClickListener(v->startActivity(new Intent(ConfermaRegistrazioneActivity.this, LoginActivity.class)));
    }
}
