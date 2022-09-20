package com.uniba.sms.eproject.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.crud.museo.CRUDMuseoActivity;
import com.uniba.sms.eproject.activity.crud.oggetto.CRUDOggettoCreateActivity;
import com.uniba.sms.eproject.activity.crud.zona.CRUDZonaCreateActivity;
import com.uniba.sms.eproject.activity.drawermenu.CuratoreDrawerMenuAction;
import com.uniba.sms.eproject.activity.generiche.OggettoListViewActivity;
import com.uniba.sms.eproject.activity.generiche.ZonaListViewActivity;

public class HomeCuratoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_curatore);

        //Add Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ////////////////////////////////////////////////////

        //Drawer menu
        DrawerLayout dl = findViewById(R.id.drawer_layout);
        NavigationView nv = findViewById(R.id.menulaterale);
        CuratoreDrawerMenuAction cdma = new CuratoreDrawerMenuAction(nv);
        cdma.set(this, getApplicationContext());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        ////////////////////////////////////////////////////////////////////////////////
    }
}
