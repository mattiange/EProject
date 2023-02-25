package it.sms.eproject.fragment.home.crud.percorso;

import static it.sms.eproject.util.EseguiFragment.changeFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import it.sms.eproject.util.EseguiFragment;
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

    ScrollView sv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.crudpercorso_create_fragment, container, false);

        init();
        getMusei();
        getOggetti();

        return v;
    }

    /**
     * Inizializzazione dei campi
     */
    private void init(){
        this.sv                 = v.findViewById(R.id.sv);
        durataPercorso          = v.findViewById(R.id.etDurataPercorso);
        nomePercorso            = v.findViewById(R.id.etNomePercorso);
        descrizionePercorso     = v.findViewById(R.id.etDescrizionePercorso);
        errorNomePercorso       = v.findViewById(R.id.errorNomePercorso);

        this.bundle = new Bundle();
        this.codice_citta = Long.parseLong(getArguments().getString("codice_citta"));

        durataPercorso.setText("0");

        FloatingActionButton btnSalva = v.findViewById(R.id.btn_salva_percorso);

        //Salvataggio del percorso
        btnSalva.setOnClickListener((view)->{
            salva();
        });


        TextView titolo = v.findViewById(R.id.titolo);
        titolo.setText(titolo.getText().toString().concat(" " +new DBCitta(getContext()).getNomeCitta(this.codice_citta)));
    }

    /**
     * Restituisce tutti gli oggetti della città
     */
    public void getOggetti() {
        //Popolo la lista con i musei.
        ArrayList<Oggetto> oggetti = new DBOggetto(getContext()).elencoOggettiByCitta(this.codice_citta);

        if (oggetti == null) return;//blocco se non ci sono musei per questa città

        LinearLayout cl = v.findViewById(R.id.containerMusei);

        int posizione = -1;
        for (Oggetto o : oggetti) {
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
            nome.setText(o.getNome());
            nome.setTextSize(30);

            TextView indirizzo = new TextView(getContext());
            indirizzo.setId(View.generateViewId());
            indirizzo.setPadding(10, 10, 10, 10);
            indirizzo.setText(o.getIndirizzo());

            TextView durata_visita = new TextView(getContext());
            durata_visita.setId(View.generateViewId());
            durata_visita.setPadding(10, 10, 10, 10);
            durata_visita.setText(getResources().getString(R.string.durata_visita) + ": " + String.valueOf(o.getDurataVisita()));

            ll.addView(nome);
            ll.addView(indirizzo);
            ll.addView(durata_visita);

            cl.addView(ll);


            int finalPosizione = posizione;
            ll.setOnLongClickListener(v1 -> {

                boolean trovato = false;

                for(Oggetto os : this.oggettiScelti){
                    if(os.getId() == o.getId()){
                        trovato = true;
                    }
                }

                if(!trovato) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Aggiungi voce")
                            .setMessage("Vuoi aggiunger questa voce al percorso?")
                            .setPositiveButton("Si", (dialog, which) -> {
                                oggettiScelti.add(o);

                                EditText durataVisistaEt = v.findViewById(R.id.etDurataPercorso);

                                int totale_durata_visita = Integer.parseInt(durataVisistaEt.getText().toString());
                                totale_durata_visita += oggetti.get(finalPosizione).getDurataVisita();
                                durataVisistaEt.setText(String.valueOf(totale_durata_visita));

                                ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners_green));
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                            })
                            .show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Elimina voce")
                            .setMessage("Vuoi eliminare questa voce dal percorso?")
                            .setPositiveButton("Si", (dialog, which) -> {
                                museiScelti.remove(o);

                                EditText durataVisistaEt = v.findViewById(R.id.etDurataPercorso);

                                int totale_durata_visita = Integer.parseInt(durataVisistaEt.getText().toString());
                                totale_durata_visita -= oggetti.get(finalPosizione).getDurataVisita();
                                durataVisistaEt.setText(String.valueOf(totale_durata_visita));

                                ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                            })
                            .show();
                }

                return true;
            });

        }
    }

    /**
     * Restituisce tutti i musei della città
     */
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


            int finalPosizione = posizione;
            ll.setOnLongClickListener(v1 -> {

                boolean trovato = false;

                for(Museo ms : this.museiScelti){
                    if(ms.getID() == m.getID()){
                        trovato = true;
                    }
                }

                if(!trovato) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Aggiungi voce")
                            .setMessage("Vuoi aggiunger questa voce al percorso?")
                            .setPositiveButton("Si", (dialog, which) -> {
                                museiScelti.add(m);

                                EditText durataVisistaEt = v.findViewById(R.id.etDurataPercorso);

                                int totale_durata_visita = Integer.parseInt(durataVisistaEt.getText().toString());
                                totale_durata_visita += musei.get(finalPosizione).getDurata_visita();
                                durataVisistaEt.setText(String.valueOf(totale_durata_visita));

                                ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners_green));
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                            })
                            .show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Elimina voce")
                            .setMessage("Vuoi eliminare questa voce dal percorso?")
                            .setPositiveButton("Si", (dialog, which) -> {
                                museiScelti.remove(m);

                                EditText durataVisistaEt = v.findViewById(R.id.etDurataPercorso);

                                int totale_durata_visita = Integer.parseInt(durataVisistaEt.getText().toString());
                                totale_durata_visita -= musei.get(finalPosizione).getDurata_visita();
                                durataVisistaEt.setText(String.valueOf(totale_durata_visita));

                                ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                            })
                            .show();
                }

                return true;
            });

        }
    }

    /**
     * Controlla se tutti i campi obbligatori sono correttamente compilati
     */
    private boolean controllaCampiObbligatori(){
        boolean controlloNome, controlloMusei;

        if(nomePercorso.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), getResources().getString(R.string.msg_error_percorso_nome_obbligatorio), Toast.LENGTH_SHORT).show();

            controlloNome = false;
        }else{
            controlloNome = true;
        }
;
        if(museiScelti.size() > 0 || oggettiScelti.size() > 0){
            controlloMusei = true;
        }else{
            Toast.makeText(getContext(), getResources().getString(R.string.msg_error_percorso_musei), Toast.LENGTH_SHORT).show();

            controlloMusei = false;
        }

        return controlloNome && controlloMusei;
    }

    /**
     * Salva il percorso nel database
     */
    private void salva(){
        if(controllaCampiObbligatori()){

            //Lettura dei campi
            String np = nomePercorso.getText().toString();
            String ddp = durataPercorso.getText().toString();
            String dp = descrizionePercorso.getText().toString();
            SharedPreferences pref = getContext().getSharedPreferences("credenziali", 0);


            long codice = new DBPercorso(getContext())
                    .inserisciPercorso(
                            new Percorso(
                                    np,
                                    dp,
                                    Integer.parseInt(ddp),
                                    Integer.parseInt(pref.getString("user_id", "-1")),
                                    Long.parseLong(getArguments().getString("codice_citta"))
                            ),
                            museiScelti,
                            oggettiScelti
                    );
            this.bundle.putLong("codice_percorso", codice);
            changeFragment(()->{
                Fragment fragment = new CRUDVisualizzaPercorso();
                fragment.setArguments(this.bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        }
    }

}

































