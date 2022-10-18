package it.sms.eproject.activity.crud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.sms.eproject.R;

public class CrudMuseo_Delete extends Fragment {

    private Button btnDelete;
    private Button btnCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudmuseo_delete_fragment, container,false);

        btnDelete = v.findViewById(R.id.btnDeleteMuseo);
        btnCancel = v.findViewById(R.id.btnCancel);


        return v;
    }
}
