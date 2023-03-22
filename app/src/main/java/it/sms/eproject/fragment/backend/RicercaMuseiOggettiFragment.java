package it.sms.eproject.fragment.backend;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Percorso;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBPercorso;
import it.sms.eproject.fragment.backend.crud.percorso.CRUDVisualizzaPercorso;
import it.sms.eproject.util.EseguiFragment;

/**
 * Fragment principale del curatore
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class RicercaMuseiOggettiFragment extends Fragment {

    EditText et;
    View view;

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.ricerca_musei_oggetti, container, false);

        init();

        return view;
    }

    /**
     * Inizializza gli oggetti e le funzionalità
     */
    private void init(){
        this.et = this.view.findViewById(R.id.cerca_musei_oggetti);
        this.bundle = new Bundle();

        init0();
    }

    /**
     * Inizializza gli eventi associati
     * alla ricerca
     */
    private void init0(){
        this.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Ricerca_onTextChanged", et.getText().toString());

                ArrayList<Percorso> percorsi = new DBPercorso(getContext()).getPercorsoFromMuseoOrOggetto(et.getText().toString());
                LinearLayout ll = view.findViewById(R.id.risultatiRicerca);

                if(percorsi!=null){

                    //Rimuovo tutte le view precedentemente create
                    ll.removeAllViews();

                    for(Percorso p : percorsi){
                        Log.d("Percorso", p.getNome());

                        LinearLayout llc = new LinearLayout(getContext());
                        LinearLayout.LayoutParams llcp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        llcp.bottomMargin   = 50;
                        llcp.topMargin      = 50;
                        llcp.leftMargin     = 50;
                        llcp.rightMargin    = 50;
                        llc.setLayoutParams(llcp);
                        llc.setId(View.generateViewId());
                        llc.setOrientation(LinearLayout.VERTICAL);
                        llc.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                        llc.setPadding(50,50,50,50);

                        //Nome del percorso
                        TextView tvNome = new TextView(getContext());
                        tvNome.setId(View.generateViewId());
                        tvNome.setTypeface(tvNome.getTypeface(), Typeface.BOLD);
                        tvNome.setText(p.getNome());

                        //Durata del percorso
                        SimpleDateFormat sdf = new SimpleDateFormat("mm");
                        String durata = "";
                        try {
                            Date dt = sdf.parse(String.valueOf(p.getDurata()));
                            sdf = new SimpleDateFormat("HH:mm");
                            durata = sdf.format(dt);
                        } catch (ParseException e) {
                        }
                        TextView tvDurata = new TextView(getContext());
                        tvDurata.setId(View.generateViewId());
                        tvDurata.setText(getResources().getString(R.string.durata_visita) + ": " +durata + " " + getResources().getString(R.string.ore));

                        //Città
                        TextView tvCitta = new TextView(getContext());
                        tvCitta.setId(View.generateViewId());
                        tvCitta.setText(new DBCitta(getContext()).getNomeCitta(p.getCodice_citta()));


                        llc.addView(tvNome);
                        llc.addView(tvDurata);
                        llc.addView(tvCitta);
                        ll.addView(llc);

                        //Aggiungo l'evento per portare al percorso
                        llc.setOnClickListener(e->{
                            EseguiFragment.changeFragment( ()->{
                                bundle.putLong("codice_percorso", p.getID());
                                Fragment fragment = new CRUDVisualizzaPercorso();
                                fragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                fragmentTransaction.addToBackStack(null).commit();
                            });
                        });
                    }
                }

                if(et.getText().toString().trim().isEmpty()){
                    ll.removeAllViews();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}



















