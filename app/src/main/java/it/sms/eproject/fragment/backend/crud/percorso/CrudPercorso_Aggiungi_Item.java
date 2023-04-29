package it.sms.eproject.fragment.backend.crud.percorso;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.OggettiMuseoHasPercorsi;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.data.classes.Percorso;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.database.DBPercorso;
import it.sms.eproject.util.EseguiFragment;

/**
 * Permette di aggiungere un nuovo item al percorso
 * selezionato
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudPercorso_Aggiungi_Item extends Fragment {
    View v;
    Bundle bundle;
    long codice_percorso;
    Percorso percorso;
    OggettiMuseoHasPercorsi oggettiMuseoHasPercorsi_salvati;
    ArrayList<Museo> tutti_i_musei;
    ArrayList<Oggetto> tutti_gli_oggetti;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.empty_fragment, container, false);

        bundle = getArguments();
        this.codice_percorso = bundle.getLong("codice_percorso");
        System.out.println("CODICE PERCORSO: " + codice_percorso);

        percorso = new DBPercorso(getContext()).get(codice_percorso);
        long codice_citta_percorso = new DBPercorso(getContext()).getCodiceCitta(codice_percorso);

        oggettiMuseoHasPercorsi_salvati = new DBPercorso(getContext()).getElementiPercorso(codice_percorso);

        tutti_i_musei       = new DBMuseo(getContext()).elencoMuseiByCitta(codice_citta_percorso);
        tutti_gli_oggetti   = new DBOggetto(getContext()).elencoOggettiByCitta(codice_citta_percorso);

        getComponentiPercorso();


        return v;
    }

    /**
     * Visualizza i componenti del percorso
     */
    public void getComponentiPercorso(){
        ArrayList<Museo> musei      = oggettiMuseoHasPercorsi_salvati.getMusei();
        ArrayList<Oggetto> oggetti  = oggettiMuseoHasPercorsi_salvati.getOggetti();

        //creo il constraint layout
        ConstraintLayout layout = (ConstraintLayout) v.findViewById(R.id.constraint);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);



        //Creo l'immagine
        /*ImageView pencileImage = new ImageView(getContext());
        pencileImage.setId(View.generateViewId());
        TableRow.LayoutParams trp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        trp.width  = 39;
        trp.height = 39;
        pencileImage.setLayoutParams(trp);
        pencileImage.setImageDrawable(getResources().getDrawable(R.drawable.pencil));*/

        //Creo il titolo
        TextView titolo = new TextView(getContext());
        titolo.setId(View.generateViewId());
        titolo.setTextSize(36);
        titolo.setText(percorso.getNome());

        //Creo il table layout
        TableLayout tableLayout = new TableLayout(getContext());
        tableLayout.setId(View.generateViewId());
        tableLayout.setPadding(50,50,50,50);

        TableRow tableRow = new TableRow(getContext());
        tableRow.setId(View.generateViewId());
        tableRow.addView(titolo);
        tableLayout.addView(tableRow);

        //creo lo scroll view
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.setId(View.generateViewId());
        ViewGroup.LayoutParams scp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scp);
        scrollView.addView(tableLayout);

        layout.addView(scrollView);

        //Posiziono gli elementi nel constraint layout
        //set.connect(tableLayout.getId(), ConstraintSet.TOP, titolo.getId(), ConstraintSet.BOTTOM, 300);
        //------------------------
        set.constrainWidth(tableLayout.getId(), ViewGroup.LayoutParams.MATCH_PARENT);
        set.constrainHeight(tableLayout.getId(), ViewGroup.LayoutParams.MATCH_PARENT);
        set.applyTo(layout);


        //visualizzo tutti i musei
        getMusei(musei, tableLayout);

        //Visualizzo tutti gli oggetti
        getOggetti(oggetti, tableLayout);
    }

    /**
     * Visualizza gli oggetti
     * @param oggetti oggetti da visualizzare
     * @param tableLayout TableLayout
     */
    public void getOggetti(ArrayList<Oggetto> oggetti, TableLayout tableLayout){
        System.out.println(oggetti);
        for(Oggetto m : tutti_gli_oggetti) {
            System.out.println(m.getCodice_citta());

            TableRow tableRow = new TableRow(getContext());
            tableRow.setId(View.generateViewId());
            tableRow.setMinimumHeight(300);
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(10,10,10,20);
            tableRow.setLayoutParams(lp);

            boolean trovato = false;

            tableRow.setBackgroundResource(R.drawable.rounded_corners_green);

            final long tmp_codice_percorso = this.codice_percorso;
            for(Oggetto o_find : oggetti){
                if(m.getId() == o_find.getId()) {
                    tableRow.setBackgroundResource(R.drawable.rounded_corners_red);

                    tableRow.setOnLongClickListener(v -> {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setTitle("Elimina voce")
                                .setMessage("Vuoi eliminare questa voce dal percorso?")
                                .setPositiveButton("Si", (dialog, which) -> {
                                    if(new DBPercorso(getContext()).deleteOggetto(m)) {
                                        //Ricarico il fragment dopo che è stato elininato
                                        //l'oggetto dal percorso
                                        EseguiFragment.changeFragment(()-> {
                                            Fragment fragment = new CrudPercorso_Aggiungi_Item();
                                            fragment.setArguments(this.bundle);

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                            fragmentTransaction.addToBackStack(null).commit();
                                        });
                                    }else{
                                        Toast.makeText(getContext(), "Oggetto rimosso dal percorso con successo", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", (dialog, which) -> {})
                                .show();

                        return true;
                    });

                    trovato = true;

                    break;
                }
            }

            if(!trovato){
                tableRow.setOnLongClickListener(v -> {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder.setTitle("Inserisci voce")
                            .setMessage("Vuoi inserire questa voce nel percorso?")
                            .setPositiveButton("Si", (dialog, which) -> {
                                if(new DBPercorso(getContext()).insertOggetto(tmp_codice_percorso, m.getId())){
                                    //Ricarico il fragment dopo che è stato inserito
                                    //l'oggetto dal percorso
                                    EseguiFragment.changeFragment(()-> {
                                        Fragment fragment = new CrudPercorso_Aggiungi_Item();
                                        fragment.setArguments(this.bundle);

                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                        fragmentTransaction.addToBackStack(null).commit();
                                    });
                                }else{
                                    Toast.makeText(getContext(), "NOOO", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", (dialog, which) -> {})
                            .show();

                    return true;
                });
            }

            tableLayout.addView(tableRow);

            TextView nome = new TextView(getContext());
            nome.setTextColor(Color.BLACK);
            nome.setText(getResources().getString(R.string.name)+": "+m.getNome());
            nome.setId(View.generateViewId());

            TextView citta  = new TextView(getContext());
            citta.setTextColor(Color.BLACK);
            citta.setText(getResources().getString(R.string.crud_txt_citta)+": "+new DBCitta(getContext()).getNomeCitta(m.getCodice_citta()));
            citta.setId(View.generateViewId());

            TextView durata_visita  = new TextView(getContext());
            durata_visita.setTextColor(Color.BLACK);
            durata_visita.setText(getResources().getString(R.string.crud_durata_visita_museo)+": "+String.valueOf(m.getDurataVisita()));
            durata_visita.setId(View.generateViewId());

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setId(LinearLayout.generateViewId());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(50, 50, 50, 50);
            linearLayout.addView(nome);
            linearLayout.addView(citta);
            linearLayout.addView(durata_visita);

            tableRow.addView(linearLayout);

            
        }
    }

    /**
     * Visualizza i musei
     *
     * @param musei Elenco dei musei
     * @param tableLayout TableLayout
     */
    public void getMusei(ArrayList<Museo> musei, TableLayout tableLayout){
        for(Museo m : tutti_i_musei){
            TableRow tableRow = new TableRow(getContext());
            tableRow.setId(View.generateViewId());
            tableRow.setMinimumHeight(300);
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(10,10,10,20);
            tableRow.setLayoutParams(lp);

            boolean trovato = false;

            tableRow.setBackgroundResource(R.drawable.rounded_corners_green);

            final long tmp_codice_percorso = this.codice_percorso;

            tableRow.setBackgroundResource(R.drawable.rounded_corners_green);
            for(Museo m_find : musei){
                if(m.getID() == m_find.getID()) {
                    tableRow.setBackgroundResource(R.drawable.rounded_corners_red);
                    tableRow.setOnLongClickListener(v -> {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setTitle("Elimina voce")
                                .setMessage("Vuoi eliminare questa voce dal percorso?")
                                .setPositiveButton("Si", (dialog, which) -> {
                                    if(new DBPercorso(getContext()).deleteMuseo(m)) {
                                        //Ricarico il fragment dopo che è stato elininato
                                        //l'oggetto dal percorso
                                        EseguiFragment.changeFragment(()-> {
                                            Fragment fragment = new CrudPercorso_Aggiungi_Item();
                                            fragment.setArguments(this.bundle);

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                            fragmentTransaction.addToBackStack(null).commit();
                                        });
                                    }else{
                                        Toast.makeText(getContext(), "Oggetto rimosso dal percorso con successo", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", (dialog, which) -> {})
                                .show();

                        return true;
                    });

                    trovato = true;

                    break;
                }
            }

            if(!trovato){
                tableRow.setOnLongClickListener(v -> {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder.setTitle("Inserisci voce")
                            .setMessage("Vuoi inserire questa voce nel percorso?")
                            .setPositiveButton("Si", (dialog, which) -> {
                                if(new DBPercorso(getContext()).insertMuseo(tmp_codice_percorso, m.getID())){
                                    //Ricarico il fragment dopo che è stato inserito
                                    //l'oggetto dal percorso
                                    EseguiFragment.changeFragment(()-> {
                                        Fragment fragment = new CrudPercorso_Aggiungi_Item();
                                        fragment.setArguments(this.bundle);

                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                        fragmentTransaction.addToBackStack(null).commit();
                                    });
                                }else{
                                    Toast.makeText(getContext(), "NOOO", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", (dialog, which) -> {})
                            .show();

                    return true;
                });
            }

            tableLayout.addView(tableRow);

            TextView nome = new TextView(getContext());
            nome.setTextColor(Color.BLACK);
            nome.setText(getResources().getString(R.string.name)+": "+m.getNome());
            nome.setId(View.generateViewId());

            TextView citta  = new TextView(getContext());
            citta.setTextColor(Color.BLACK);
            citta.setText(getResources().getString(R.string.crud_txt_citta)+": "+new DBCitta(getContext()).getNomeCitta(m.getCitta()));
            citta.setId(View.generateViewId());

            TextView durata_visita  = new TextView(getContext());
            durata_visita.setTextColor(Color.BLACK);
            durata_visita.setText(getResources().getString(R.string.crud_durata_visita_museo)+": "+String.valueOf(m.getDurata_visita()));
            durata_visita.setId(View.generateViewId());

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setId(LinearLayout.generateViewId());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(50, 50, 50, 50);
            linearLayout.addView(nome);
            linearLayout.addView(citta);
            linearLayout.addView(durata_visita);

            tableRow.addView(linearLayout);
        }
    }
}

























