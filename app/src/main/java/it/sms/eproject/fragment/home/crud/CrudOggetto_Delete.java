package it.sms.eproject.fragment.home.crud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;

public class CrudOggetto_Delete extends Fragment {
    private Button btnDelete;
    private Button btnCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudoggetto_delete_fragment, container,false);

        btnDelete = v.findViewById(R.id.btnDeleteOggetto);
        btnCancel = v.findViewById(R.id.btnCancelOggetto);


        return v;
    }
}
