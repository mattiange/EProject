package com.uniba.sms.eproject.activity.generiche;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import static com.uniba.sms.eproject.util.Util.addToolbarAndMenu;

import com.uniba.sms.eproject.R;

public class ZonaListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview);

        addToolbarAndMenu(this, findViewById(R.id.toolbar), findViewById(R.id.drawer_layout));


    }
}
