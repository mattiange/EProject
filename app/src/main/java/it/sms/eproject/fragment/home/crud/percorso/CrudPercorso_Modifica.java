package it.sms.eproject.fragment.home.crud.percorso;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.List;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.OggettiMuseoHasPercorsi;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.data.classes.Percorso;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.database.DBPercorso;
import it.sms.eproject.util.EseguiFragment;

public class CrudPercorso_Modifica extends Fragment {
    View v;
    Bundle bundle;
    int codice_percorso;
    Percorso percorso;
    OggettiMuseoHasPercorsi oggettiMuseoHasPercorsi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.empty_fragment, container, false);

        bundle = getArguments();
        int codice_percorso = bundle.getInt("codice_percorso");

        percorso = new DBPercorso(getContext()).get(codice_percorso);
        oggettiMuseoHasPercorsi = new DBPercorso(getContext()).getElementiPercorso(codice_percorso);

        getComponentiPercorso();


        return v;
    }

    /**
     * Visualizza i componenti del percorso
     */
    public void getComponentiPercorso(){
        ArrayList<Museo> musei = oggettiMuseoHasPercorsi.getMusei();
        ArrayList<Oggetto> oggetti = oggettiMuseoHasPercorsi.getOggetti();

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
        //tableRow.addView(pencileImage);
        tableRow.addView(titolo);
        tableLayout.addView(tableRow);

        //Se non ci sono elementi visualizzo il messaggio
        //di avviso
        if(musei.size() == 0 && oggetti.size() == 0){
            TextView noElementMsg = new TextView(getContext());
            noElementMsg.setId(View.generateViewId());
            noElementMsg.setText("Questo percorso non contiene elementi");
            noElementMsg.setTextSize(15);
            noElementMsg.setTextColor(Color.RED);

            TableRow tableRow1 = new TableRow(getContext());
            tableRow1.setId(View.generateViewId());

            tableRow1.addView(noElementMsg);
            tableLayout.addView(tableRow1);
        }

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

        /*TableRow tableRow = new TableRow(getContext());
        tableRow.setId(View.generateViewId());

        TextView nome = new TextView(getContext());
        nome.setId(View.generateViewId());
        nome.setText(getResources().getString(R.string.crud_manage_oggetto));

        tableRow.addView(nome);
        tableLayout.addView(tableRow);*/

        for(Oggetto m : oggetti) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setId(View.generateViewId());
            tableRow.setMinimumHeight(300);
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(10,10,10,20);
            tableRow.setLayoutParams(lp);
            tableRow.setBackgroundResource(R.drawable.rounded_corners);

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

            //setto l'evento
            tableRow.setLongClickable(true);
            tableRow.setOnLongClickListener(v -> {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Elimina voce")
                       .setMessage("Vuoi eliminare questa voce dal percorso?")
                        .setPositiveButton("Si", (dialog, which) -> {
                            if(new DBPercorso(getContext()).deleteOggetto(m)) {
                                //Ricarico il fragment dopo che è stato elininato
                                //l'oggetto dal percorso
                                EseguiFragment.changeFragment(()-> {
                                    Fragment fragment = new CrudPercorso_Modifica();
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
        }
    }

    /**
     * Visualizza i musei
     *
     * @param musei Elenco dei musei
     * @param tableLayout TableLayout
     */
    public void getMusei(ArrayList<Museo> musei, TableLayout tableLayout){

        /*TableRow tableRow = new TableRow(getContext());
        tableRow.setId(View.generateViewId());

        TextView nome = new TextView(getContext());
        nome.setId(View.generateViewId());
        nome.setText(getResources().getString(R.string.crud_manage_museo));

        tableRow.addView(nome);
        tableLayout.addView(tableRow);*/

        //Visualizzo tutti i musei
        for(Museo m : musei) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setId(View.generateViewId());
            tableRow.setMinimumHeight(300);
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(10,10,10,20);
            tableRow.setLayoutParams(lp);
            tableRow.setBackgroundResource(R.drawable.rounded_corners);

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
            //setto l'evento
            tableRow.setLongClickable(true);
            tableRow.setOnLongClickListener(v -> {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Elimina voce")
                        .setMessage("Vuoi eliminare questa voce dal percorso?")
                        .setPositiveButton("Si", (dialog, which) -> {
                            if(new DBPercorso(getContext()).deleteMuseo(m)) {
                                //Ricarico il fragment dopo che è stato elininato
                                //l'oggetto dal percorso
                                EseguiFragment.changeFragment(()-> {
                                    Fragment fragment = new CrudPercorso_Modifica();
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
        }
    }
}

























