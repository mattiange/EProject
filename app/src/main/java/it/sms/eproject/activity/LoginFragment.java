package it.sms.eproject.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import it.sms.eproject.R;
import it.sms.eproject.database.DbManager;
import it.sms.eproject.util.Util;

public class LoginFragment extends Fragment {
    Button buttonLogin,
            buttonRegister;
    EditText etEmail, etPassword;
    CallbackFragment callbackFragment;
    String email, password;

    @Override
    public void onAttach(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("usersFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);

        buttonLogin = view.findViewById(R.id.btnLogin);
        buttonRegister = view.findViewById(R.id.btnRegister);

        //Effettuo il login
        buttonLogin.setOnClickListener(v -> {
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            if(email.trim().isEmpty() || password.trim().isEmpty()){
                Snackbar.make(getView(), R.string.campi_vuoti, Snackbar.LENGTH_LONG).show();

                return;
            }

            if(!Util.verificaEmail(email)) {//Verifico se l'email inserita è corretta
                Snackbar.make(getView(), R.string.email_non_corretta, Snackbar.LENGTH_LONG).show();

                return;
            }

            TextView lblError = view.findViewById(R.id.lblError);
            if(new DbManager(getContext()).login(email, password) != null){
                Toast.makeText(getContext(), "DA IMPLEMENTARE HOME ACTIVITY", Toast.LENGTH_SHORT).show();
                lblError.setVisibility(View.INVISIBLE);
            }else{
                lblError.setVisibility(View.VISIBLE);
                lblError.setText(R.string.login_non_valida);
            }
        });

        //Riporto alla pagina di registrazione di un nuovo utente
        buttonRegister.setOnClickListener(v -> {
            Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
            if(callbackFragment!=null){
                callbackFragment.changeFragment();
            }
        });

        return view;
    }

    public void setCallbackFragment(CallbackFragment callbackFragment){
        this.callbackFragment = callbackFragment;
    }
}
