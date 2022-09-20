package com.uniba.sms.eproject.activity.drawermenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.uniba.sms.eproject.R;
import com.uniba.sms.eproject.activity.crud.museo.CRUDMuseoActivity;
import com.uniba.sms.eproject.activity.generiche.OggettoListViewActivity;
import com.uniba.sms.eproject.activity.generiche.ZonaListViewActivity;

/**
 * Gestisce il Drawer menu riservato ai curatori
 */
public class CuratoreDrawerMenuAction {
    private final NavigationView nv;

    public CuratoreDrawerMenuAction(NavigationView nv){
        this.nv = nv;
    }

    /**
     * Esegie le azioni del menu
     *
     * @param aca Activity che usa il metodo
     * @param c Contesto
     */
    @SuppressLint("NonConstantResourceId")
    public NavigationView set(AppCompatActivity aca, Context c){
        nv.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_musei:
                    aca.startActivity(new Intent(c, CRUDMuseoActivity.class));
                    return true;
                case R.id.nav_oggetto:
                    aca.startActivity(new Intent(c, OggettoListViewActivity.class));
                    return true;
                case R.id.nav_zone:
                    aca.startActivity(new Intent(c, ZonaListViewActivity.class));
                    return true;
            }
            return false;
        });

        return nv;
    }
}
