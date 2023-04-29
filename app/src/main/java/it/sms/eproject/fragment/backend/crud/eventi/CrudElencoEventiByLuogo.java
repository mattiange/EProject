package it.sms.eproject.fragment.backend.crud.eventi;

import static it.sms.eproject.R.drawable.rounded_corners;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Attivita;
import it.sms.eproject.data.classes.AttivitaMuseo;
import it.sms.eproject.data.classes.AttivitaOggetto;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.OggettiMuseoHasPercorsi;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBAttivita;
import it.sms.eproject.database.DBPercorso;

/**
 * Elenco degli eventi di un luogo preciso
 * Usato per i visitatori
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudElencoEventiByLuogo extends Fragment {

    EditText cerca;
    LinearLayout llContainer;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.crudattivita_list_utenti_fragment, container,false);

        init();

        return v;

    }

    private void init(){
        this.llContainer    = this.v.findViewById(R.id.llContainer);

        OggettiMuseoHasPercorsi percorsi_utente = new DBPercorso(getContext()).getElementiPercorso(getArguments().getLong("codice_percorso"));
        for(Museo m : percorsi_utente.getMusei()){
            AttivitaMuseo am = new DBAttivita(getContext()).getAttivitaMuseo(m.getID());

            if(am != null){
                LinearLayout ll = new LinearLayout(getContext());
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.rightMargin = 50;
                llp.leftMargin  = 50;
                llp.topMargin   = 50;
                llp.bottomMargin= 50;
                ll.setLayoutParams(llp);
                ll.setOrientation(LinearLayout.VERTICAL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ll.setBackground(getResources().getDrawable(rounded_corners));
                }else{
                    ll.setBackgroundDrawable(getResources().getDrawable(rounded_corners));
                }
                ll.setPadding(50,50,50,50);

                TextView nomeAttivita = new TextView(getContext());
                nomeAttivita.setText(am.getAttivita().getNome());
                TextView nomeMuseo = new TextView(getContext());
                nomeMuseo.setText(am.getMuseo().getNome());;

                ll.addView(nomeAttivita);
                ll.addView(nomeMuseo);

                llContainer.addView(ll);

                ll.setOnClickListener(v->{
                    Bundle bundle = new Bundle();
                    bundle.putLong("codice_attivita", am.getAttivita().getCodice());
                    bundle.putString("hidden_trash", "true");

                    Fragment fragment = new CrudEventoShow();
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                    fragmentTransaction.addToBackStack(null).commit();
                });
            }
        }
        for(Oggetto m : percorsi_utente.getOggetti()){
            AttivitaOggetto am = new DBAttivita(getContext()).getAttivitaOggetto(m.getId());

            if(am != null){
                LinearLayout ll = new LinearLayout(getContext());
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.rightMargin = 50;
                llp.leftMargin  = 50;
                llp.topMargin   = 50;
                llp.bottomMargin= 50;
                ll.setLayoutParams(llp);
                ll.setOrientation(LinearLayout.VERTICAL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ll.setBackground(getResources().getDrawable(rounded_corners));
                }else{
                    ll.setBackgroundDrawable(getResources().getDrawable(rounded_corners));
                }
                ll.setPadding(50,50,50,50);

                TextView nomeAttivita = new TextView(getContext());
                nomeAttivita.setText(am.getAttivita().getNome());
                TextView nomeMuseo = new TextView(getContext());
                nomeMuseo.setText(am.getMuseo().getNome());;

                ll.addView(nomeAttivita);
                ll.addView(nomeMuseo);

                llContainer.addView(ll);

                ll.setOnClickListener(v->{
                    Bundle bundle = new Bundle();
                    bundle.putLong("codice_attivita", am.getAttivita().getCodice());
                    bundle.putString("hidden_trash", "true");

                    Fragment fragment = new CrudEventoShow();
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                    fragmentTransaction.addToBackStack(null).commit();
                });
            }
        }

        ((TextView)this.v.findViewById(R.id.titolo)).setText(getResources().getString(R.string.crud_list_event));

    }

}
