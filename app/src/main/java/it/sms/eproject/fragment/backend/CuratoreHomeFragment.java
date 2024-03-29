package it.sms.eproject.fragment.backend;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.sms.eproject.R;
import it.sms.eproject.fragment.backend.crud.autori.CRUDAutore;
import it.sms.eproject.fragment.backend.crud.eventi.CrudElencoLuoghi;
import it.sms.eproject.fragment.backend.crud.eventi.CrudEventi;
import it.sms.eproject.fragment.backend.crud.museo.CrudMuseo;
import it.sms.eproject.fragment.backend.crud.oggetto.CrudOggetto;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.fragment.backend.crud.percorso.CRUDPercorso;

/**
 * Fragment principale del curatore
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CuratoreHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_curatore_home, container, false);

        aggiuntaEventiClickBottoni(view);

        return view;
    }

    /**
     * Aggiungo gli eventi ai pulsanti per portare ai giusti fragment
     *
     * @param view Vista sulla quale cambiare il fragment
     */
    public void aggiuntaEventiClickBottoni(View view){
        //Gestione dei musei
        ConstraintLayout btnMusei = view.findViewById(R.id.btnGestisciMusei);
        btnMusei.setOnClickListener(e-> {
            Fragment fragment = new CrudMuseo();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        //Gestione degli oggetti
        ConstraintLayout btnOggetti = view.findViewById(R.id.btnGestisciGliOggetti);
        btnOggetti.setOnClickListener(e-> {
            Fragment fragment = new CrudOggetto();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        //Gestione degli autori
        ConstraintLayout btnAutori = view.findViewById(R.id.btnGestisciGliAutori);
        btnAutori.setOnClickListener(e-> {
            Fragment fragment = new CRUDAutore();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        //Gestione degli autori
        ConstraintLayout btnPercorsi = view.findViewById(R.id.btnGestisciIPercorsi);
        btnPercorsi.setOnClickListener(e-> {
            Fragment fragment = new CRUDPercorso();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });


        //Gestione degli eventi
        ConstraintLayout btnEventi = view.findViewById(R.id.btnGestisciGliEventi);
        btnEventi.setOnClickListener(e-> {
            Fragment fragment = new CrudEventi();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
}