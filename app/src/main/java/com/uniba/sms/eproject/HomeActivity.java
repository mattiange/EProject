package com.uniba.sms.eproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.uniba.sms.eproject.database.DbManager;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private ListView lv=null;
    private SimpleCursorAdapter adapter=null;
    private DbManager db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Add Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ////////////////////////////////////////////////////

        //Floating button
        FloatingActionButton addFBtn = findViewById(R.id.add);
        addFBtn.setOnClickListener(view->{
            Snackbar.make(view, "Da implementare", Snackbar.LENGTH_LONG).show();
        });
        ////////////////////////////////////////////////////////////////////////////////

        //Drawer menu
        DrawerLayout dl = findViewById(R.id.drawer_layout);
        NavigationView nv = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////

        db=new DbManager(this);
        /*if(db.registrazione("matti", "agelillo", "mattia1","mattia2.angelillo@gmail.com", "ciao", 0)){
            Toast.makeText(this, "Registrazione OK", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Registrazione NO", Toast.LENGTH_LONG).show();
        }*/

        /*HashMap<String, String> utente_login;
        if((utente_login = db.login("mattia1", "ciao")) != null){
            Toast.makeText(this, "Login OK", Toast.LENGTH_LONG).show();

            utente_login.forEach( (k, v) -> Toast.makeText(this,  k + " => " + v, Toast.LENGTH_LONG).show() );
        }else{
            Toast.makeText(this, "Login NO", Toast.LENGTH_LONG).show();
        }*/


        Cursor c = db.elencoUtenti();
        if (c.moveToFirst()){
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();

            do {
                // Passing values
                String column1 = c.getString(0);
                String column2 = c.getString(1);
                String column3 = c.getString(2);

                Toast.makeText(this, column1, Toast.LENGTH_LONG).show();
                Toast.makeText(this, column2, Toast.LENGTH_LONG).show();
                Toast.makeText(this, column3, Toast.LENGTH_LONG).show();
                // Do something Here with values
            } while(c.moveToNext());
        }else{

            Toast.makeText(this, "NO", Toast.LENGTH_LONG).show();
        }
        c.close();

        //Toast.makeText(this, cursor.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
}
