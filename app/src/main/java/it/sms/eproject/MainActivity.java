package it.sms.eproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import it.sms.eproject.data.classes.Permesso;
import it.sms.eproject.data.classes.Utente;
import it.sms.eproject.database.DbManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Utente> utenti = new DbManager(this).elencoUtenti();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("===================>" + (new DbManager(this).registrazione(
                    new Utente("Michele", "Giorgio", "hjdwh",
                            LocalDate.of(1950, 05, 10), "m.giorgio1@gmail.com",
                            new Permesso(1)
                    )))
            );
        }else{
            System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        }*/

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            utenti.forEach(utente -> Toast.makeText(getApplicationContext(), utente.getCodice() + " | " + utente.getNome() + " - " + utente.getPermesso().getPermesso(), Toast.LENGTH_SHORT).show());
        }*/
        /*Utente u;
        if((u = new DbManager(this).login("m.angelillo@gmail.com", "test")) != null){
            Toast.makeText(this, "OK LOGIN " + u.getNome() , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "NO LOGIN ", Toast.LENGTH_SHORT).show();
        }*/
    }
}