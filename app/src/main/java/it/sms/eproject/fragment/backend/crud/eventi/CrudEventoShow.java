package it.sms.eproject.fragment.backend.crud.eventi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.data.classes.Attivita;
import it.sms.eproject.database.DBAttivita;

public class CrudEventoShow extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudattivita_show_fragment, container,false);

        Attivita a = new DBAttivita(getContext()).getAttivita(getArguments().getLong("codice_attivita"));

        ((TextView)v.findViewById(R.id.titolo)).setText(a.getNome());
        ((TextView)v.findViewById(R.id.descrizione)).setText(a.getDescrizione());

        ((FloatingActionButton)v.findViewById(R.id.delete)).setOnClickListener(v1 -> {
            new AlertDialog.Builder(getContext())
                    .setTitle(getResources().getString(R.string.cancella))
                    .setMessage(getResources().getString(R.string.delete_attivita_msq_question))
                    .setPositiveButton(android.R.string.yes,
                            (dialog, which) -> {
                                new DBAttivita(getContext()).cancellaAttivita(getArguments().getLong("codice_attivita"));

                                Fragment fragment = new CrudElencoEventi();

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                fragmentTransaction.addToBackStack(null).commit();
                            }
                    )
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });


        return v;

    }

}
