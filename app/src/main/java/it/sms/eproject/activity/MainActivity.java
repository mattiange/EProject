package it.sms.eproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.activity.LoginFragment;
import it.sms.eproject.activity.RegisterFragment;
import it.sms.eproject.data.classes.Permesso;
import it.sms.eproject.data.classes.Utente;
import it.sms.eproject.database.DbManager;
import it.sms.eproject.util.Util;

public class MainActivity extends AppCompatActivity implements CallbackFragment {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment();
    }

    /**
     * Aggiunge il fragment della login
     */
    public void addFragment(){
        LoginFragment fragment = new LoginFragment();
        fragment.setCallbackFragment(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Rimpiazza il fragment della login con quello della registrazione
     */
    public void replaceFragment (){
        fragment = new RegisterFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void changeFragment() {
        replaceFragment();
    }
}