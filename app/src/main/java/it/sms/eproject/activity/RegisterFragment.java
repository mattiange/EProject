package it.sms.eproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.time.LocalDate;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Permesso;
import it.sms.eproject.data.classes.Utente;
import it.sms.eproject.database.DbManager;
import it.sms.eproject.util.Util;

public class RegisterFragment extends Fragment {

    Button btnRegistrazione;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        btnRegistrazione = view.findViewById(R.id.btnRegister);

        btnRegistrazione.setOnClickListener(v->{
            String nome             = ((TextView)view.findViewById(R.id.etNome)).getText().toString();
            String cognome          = ((TextView)view.findViewById(R.id.etCognome)).getText().toString();
            String codiceFiscale    = ((TextView)view.findViewById(R.id.etCodiceFiscale)).getText().toString();
            String dataDiNascita    = ((TextView)view.findViewById(R.id.etDataDiNascita)).getText().toString();
            String email            = ((TextView)view.findViewById(R.id.etEmail)).getText().toString();
            String password         = ((TextView)view.findViewById(R.id.etPassword)).getText().toString();
            String ripetiPassword   = ((TextView)view.findViewById(R.id.etConfermaPassword)).getText().toString();

            TextView lblError = view.findViewById(R.id.lblError);

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
                if(new DbManager(getContext()).registrazione(new Utente(
                        nome,
                        cognome,
                        codiceFiscale,
                        LocalDate.parse(dataDiNascita),
                        email,
                        password,
                        new Permesso(Permesso.VISITATORE)
                ))){
                    startActivity(new Intent(getContext(), MainActivity.class));
                }

            }
        });


        return view;
    }
}
