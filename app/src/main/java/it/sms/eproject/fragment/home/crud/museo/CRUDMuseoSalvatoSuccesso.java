package it.sms.eproject.fragment.home.crud.museo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;

public class CRUDMuseoSalvatoSuccesso extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudmuseo_salvato_successo_fragment, container,false);

        ((TextView)v.findViewById(R.id.titolo)).setText(String.format(getResources().getString(R.string.msg_success_salvato), getResources().getString(R.string.museo_uc)));


        return v;
    }
}
