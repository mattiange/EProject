package com.uniba.sms.eproject.activity;

import static com.uniba.sms.eproject.util.Util.addToolbarAndMenu;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.uniba.sms.eproject.R;

public class HomeCuratoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_curatore);

        addToolbarAndMenu(this, findViewById(R.id.toolbar), findViewById(R.id.drawer_layout));
    }
}
