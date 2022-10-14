package it.sms.eproject.activity.login_e_registrazione;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.time.LocalDate;

import it.sms.eproject.EmailGiaEsistenteException;
import it.sms.eproject.R;
import it.sms.eproject.activity.MainActivity;
import it.sms.eproject.data.classes.Permesso;
import it.sms.eproject.data.classes.Utente;
import it.sms.eproject.database.DbManager;
import it.sms.eproject.util.Util;

public class RegistrazioneActivity extends AppCompatActivity {

    Button btnRegistrazione;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrazione);

        //Visualizzo il pulsante back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_back_white);
        //----------------------------------------------------------------------------------------\

        registraElementi();

        impostaAzioni();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Registra gli elementi dell'activity
     */
    public void registraElementi(){
        btnRegistrazione = findViewById(R.id.btnRegister);
    }

    /**
     * Imposta le azioni dell'activity
     */
    public void impostaAzioni(){
        //Registrazione dell'utente nel database
        btnRegistrazione.setOnClickListener(v->{
            String nome             = ((TextView)findViewById(R.id.etNome)).getText().toString();
            String cognome          = ((TextView)findViewById(R.id.etCognome)).getText().toString();
            String codiceFiscale    = ((TextView)findViewById(R.id.etCodiceFiscale)).getText().toString();
            String dataDiNascita    = ((TextView)findViewById(R.id.etDataDiNascita)).getText().toString();
            String email            = ((TextView)findViewById(R.id.etEmail)).getText().toString();
            String password         = ((TextView)findViewById(R.id.etPassword)).getText().toString();
            String ripetiPassword   = ((TextView)findViewById(R.id.etConfermaPassword)).getText().toString();

            TextView lblError = findViewById(R.id.lblError);

            lblError.setVisibility(View.INVISIBLE);

            //Verifico se i campi obbligatori sono tutti compilati
            if(!Util.campoCompilato(nome) && !Util.campoCompilato(cognome) && !Util.campoCompilato(codiceFiscale)
                    && !Util.campoCompilato(dataDiNascita) && !Util.campoCompilato(email)
                    && !Util.campoCompilato(password) && !Util.campoCompilato(ripetiPassword)){
                lblError.setVisibility(View.VISIBLE);
                lblError.setText(R.string.campi_vuoti);

                return;
            }

            //Verifico che le password siano uguali
            if(Util.confermaPassword(password, ripetiPassword)){
                //Toast.makeText(getContext(), "UGUALI", Toast.LENGTH_SHORT).show();

            }else {
                lblError.setVisibility(View.VISIBLE);
                lblError.setText(R.string.passwords_not_equal);

                return;
            }

            //Verifico la correttezza dell'email
            if(!Util.verificaEmail(email)){
                lblError.setVisibility(View.VISIBLE);
                lblError.setText(R.string.email_non_corretta);

                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    if(new DbManager(this).registrazione(new Utente(
                            nome,
                            cognome,
                            codiceFiscale,
                            LocalDate.parse(dataDiNascita),
                            email,
                            password,
                            new Permesso(Permesso.VISITATORE)
                    ))){
                        startActivity(new Intent(this, ConfermaRegistrazioneActivity.class));
                    }
                }catch (EmailGiaEsistenteException ex){
                    lblError.setText(R.string.email_gia_esistente);
                    lblError.setVisibility(View.VISIBLE);
                }

            }
        });
    }


}
