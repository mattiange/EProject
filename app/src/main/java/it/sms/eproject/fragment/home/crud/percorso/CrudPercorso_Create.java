package it.sms.eproject.fragment.home.crud.percorso;

import static it.sms.eproject.util.EseguiFragment.changeFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.data.classes.Percorso;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.database.DBPercorso;
import it.sms.eproject.fragment.home.crud.liste.ListaPercorso;
import it.sms.eproject.util.OnSwipeTouchListener;

public class CrudPercorso_Create extends Fragment {

    View v;
    /**
     * Campo per gestire la durata totale del percorso
     */
    EditText durataPercorso;
    /**
     * Nome del percorso
     */
    EditText nomePercorso;
    /**
     * Descrizione del percorso
     */
    EditText descrizionePercorso;
    /**
     * Label di visualizzazione di errore del
     * nome del percorso
     */
    TextView errorNomePercorso;
    /**
     * Label di visualizzazione di errore nel
     * caso in cui non si è selezionato neanche
     * un museo.
     */
    TextView errorMuseiPercorso;

    /**
     * Calcolo della durata totale.
     * Viene calcolata automaticamente quando
     * viene selezionato un museo dalla lista.
     * Se un museo viene deselezionato allora
     * viene rimossa dal totale anche la sua
     * durata.
     */
    private int totaleDurata = 0;

    private static long codice_citta;

    /**
     * Elenco dei musei associati al percorso
     */
    List<Museo> museiScelti = new ArrayList<>();

    /**
     * Elenco degli oggetti associati al percorso
     */
    List<Oggetto> oggettiScelti = new ArrayList<>();

    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.crudpercorso_create_fragment, container, false);

        init();
        getMusei();

        return v;
    }

    /**
     * Inizializzazione dei campi
     */
    private void init(){
        durataPercorso          = v.findViewById(R.id.etDurataPercorso);
        nomePercorso            = v.findViewById(R.id.etNomePercorso);
        descrizionePercorso     = v.findViewById(R.id.etDescrizionePercorso);
        errorNomePercorso       = v.findViewById(R.id.errorNomePercorso);
        //errorMuseiPercorso      = v.findViewById(R.id.errorMuseiPercorso);

        this.bundle = new Bundle();
        this.codice_citta = Long.parseLong(getArguments().getString("codice_citta"));

        durataPercorso.setText("0");


        TextView titolo = v.findViewById(R.id.titolo);
        titolo.setText(titolo.getText().toString().concat(" " +new DBCitta(getContext()).getNomeCitta(this.codice_citta)));
    }

    public void getMusei() {
        //Popolo la lista con i musei.
        ArrayList<Museo> musei = new DBMuseo(getContext()).elencoMuseiByCitta(this.codice_citta);

        if (musei == null) return;//blocco se non ci sono musei per questa città

        LinearLayout cl = v.findViewById(R.id.containerMusei);

        int posizione = -1;
        for (Museo m : musei) {
            posizione ++;

            LinearLayout ll = new LinearLayout(getContext());
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.topMargin       = 20;
            llp.bottomMargin    = 20;
            ll.setLayoutParams(llp);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setPadding(50, 50, 50, 50);
            ll.setId(View.generateViewId());
            ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners));

            TextView nome = new TextView(getContext());
            nome.setId(View.generateViewId());
            nome.setPadding(10, 10, 10, 10);
            nome.setText(m.getNome());
            nome.setTextSize(30);

            TextView indirizzo = new TextView(getContext());
            indirizzo.setId(View.generateViewId());
            indirizzo.setPadding(10, 10, 10, 10);
            indirizzo.setText(m.getIndirizzo());

            TextView durata_visita = new TextView(getContext());
            durata_visita.setId(View.generateViewId());
            durata_visita.setPadding(10, 10, 10, 10);
            durata_visita.setText(getResources().getString(R.string.durata_visita) + ": " + String.valueOf(m.getDurata_visita()));

            ll.addView(nome);
            ll.addView(indirizzo);
            ll.addView(durata_visita);

            cl.addView(ll);

            ll.setOnTouchListener(new OnSwipeTouchListener(getContext(), posizione){
                @Override
                public void onSwipeLeft() {
                    List<Museo> musei = new DBMuseo(getContext()).elencoMuseiByCitta(codice_citta);

                    Toast.makeText(getContext(), "Left", Toast.LENGTH_SHORT).show();
                    museiScelti.remove(musei.get(this.posizione));

                    EditText durataVisistaEt = v.findViewById(R.id.etDurataPercorso);

                    int totale_durata_visita = Integer.parseInt(durataVisistaEt.getText().toString());
                    totale_durata_visita -= musei.get(this.posizione).getDurata_visita();
                    durataVisistaEt.setText(String.valueOf(totale_durata_visita));

                    ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                }
                @Override
                public void onSwipeRight() {
                    List<Museo> musei = new DBMuseo(getContext()).elencoMuseiByCitta(codice_citta);

                    Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();
                    museiScelti.add(musei.get(this.posizione));

                    EditText durataVisistaEt = v.findViewById(R.id.etDurataPercorso);

                    int totale_durata_visita = Integer.parseInt(durataVisistaEt.getText().toString());
                    totale_durata_visita += musei.get(this.posizione).getDurata_visita();
                    durataVisistaEt.setText(String.valueOf(totale_durata_visita));

                    ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners_green));
                }

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });

        }
    }


}

































