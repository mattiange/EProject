package it.sms.eproject.fragment.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.activity.crud.CrudMuseo;
import it.sms.eproject.activity.crud.CrudOggetto;
import it.sms.eproject.activity.crud.CrudZona;
import it.sms.eproject.annotazioni.Autore;

/**
 * Fragment principale del curatore
 */
@Autore(autore = "Mattia Leonardo Angelillo")
public class CuratoreHomeFragment extends Fragment {

    CallbackFragment callbackFragment;

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
        ImageButton btnMusei = view.findViewById(R.id.btnGestisciMusei);
        btnMusei.setOnClickListener(e-> {
            Fragment fragment = new CrudMuseo();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        //Gestione delle zone
        ImageButton btnZone = view.findViewById(R.id.btnGestisciZone);
        btnZone.setOnClickListener(e-> {
            Fragment fragment = new CrudZona();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        //Gestione degli oggetti
        ImageButton btnOggetti = view.findViewById(R.id.btnGestisciGliOggetti);
        btnOggetti.setOnClickListener(e-> {
            Fragment fragment = new CrudOggetto();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    public void setCallbackFragment(CallbackFragment callbackFragment){
        this.callbackFragment = callbackFragment;
    }
}