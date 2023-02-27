package it.sms.eproject.fragment.home.utente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.fragment.home.crud.autori.CRUDAutore;
import it.sms.eproject.fragment.home.crud.museo.CrudMuseo;
import it.sms.eproject.fragment.home.crud.oggetto.CrudOggetto;
import it.sms.eproject.fragment.home.crud.percorso.CRUDPercorso;

/**
 * Fragment principale del curatore
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class UtenteHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_utente_home, container, false);

        aggiuntaEventiClickBottoni(view);

        return view;
    }

    /**
     * Aggiungo gli eventi ai pulsanti per portare ai giusti fragment
     *
     * @param view Vista sulla quale cambiare il fragment
     */
    public void aggiuntaEventiClickBottoni(View view){
        //Gestione degli autori
        ConstraintLayout btnPercorsi = view.findViewById(R.id.btnGestisciIPercorsi);
        btnPercorsi.setOnClickListener(e-> {
            Fragment fragment = new CRUDPercorso();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
}