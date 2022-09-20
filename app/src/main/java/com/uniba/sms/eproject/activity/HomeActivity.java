package com.uniba.sms.eproject.activity;

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
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.data.classes.Permesso;
import com.uniba.sms.eproject.data.classes.Zona;
import com.uniba.sms.eproject.database.DbManager;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private ListView lv=null;
    private SimpleCursorAdapter adapter=null;
    private DbManager db=null;
    private ArrayList<Zona> zone = new ArrayList<Zona>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Add Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ////////////////////////////////////////////////////

        //Drawer menu
        DrawerLayout dl = findViewById(R.id.drawer_layout);
        NavigationView nv = findViewById(R.id.menulaterale);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////

        DbManager dbManager = new DbManager(this);
        System.out.println("PERMESSI");
        for(Permesso p : dbManager.visualizzaTuttiIPermessi()){
            System.out.println("===============> " + p.getPermesso_id());
            System.out.println("===============> " + p.getUtente_id());
            System.out.println("============================================");
        }

        //Floating button
        FloatingActionButton addFBtn = findViewById(R.id.add);
        addFBtn.setOnClickListener(view->{
            Snackbar.make(view, "Da implementare", Snackbar.LENGTH_LONG).show();
        });

        db=new DbManager(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }
}
