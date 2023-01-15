package it.sms.eproject.fragment.home.crud.percorso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.data.classes.Percorso;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.database.DBPercorso;

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

    /**
     * Elenco dei musei associati al percorso
     */
    List<Museo> museiScelti = new ArrayList<>();

    /**
     * Elenco degli oggetti associati al percorso
     */
    List<Oggetto> oggettiScelti = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.crudpercorso_create_fragment, container, false);

        TextView titolo = v.findViewById(R.id.titolo);


        init();

        titolo.setText(titolo.getText().toString().concat(" " +new DBCitta(getContext()).getNomeCitta(Integer.parseInt(getArguments().getString("codice_citta")))));

        Button btnSalva = v.findViewById(R.id.btn_salva_percorso);

        //Salvataggio del percorso
        btnSalva.setOnClickListener((view)->{
            salva();
        });

        //Popolo la lista con i musei.
        //Se non ci sono musei per la città selezionata
        //allora viene visualizzato un messaggio (invece
        //di non dare alcun responso)
        ArrayList<Museo> musei = new DBMuseo(getContext()).elencoMuseiByCitta(Integer.parseInt(getArguments().getString("codice_citta")));

        ListAdapter listAdapter = new MuseoAdapter(getContext(), musei);
        if(musei != null){
            ListView mainListView = (ListView) v.findViewById( R.id.listElencoMusei );
            mainListView.setAdapter(listAdapter);
        }else{
            TextView tvNoMusei = v.findViewById(R.id.lblErrorNoMusei);
            tvNoMusei.setVisibility(View.VISIBLE);
        }
        //--------------------------------------------------------------------------

        /*
        AGGIUNGERE LA LISTA PER LA VISUALIZZAZIONE DEGLI OGGETTI.
        CONTROLLARE CHE CI SIA LA TABELLA musei_has_percorsi ALTRIMENTI CREARLA
                (E AGGIORNARE LA DOCUMENTAZIONE)
        */

        //Popolo la lista con gli oggetti
        //Se non ci sono oggetti per la città selezionata
        //allora viene visualizzato un messaggio (invece
        //di non dare alcun responso)
        ArrayList<Oggetto> oggetti = new DBOggetto(getContext()).elencoOggettiByCitta(Integer.parseInt(getArguments().getString("codice_citta")));

        ListAdapter listAdapterOggetti = new OggettoAdapter(getContext(), oggetti);
        if(oggetti != null){
            ListView mainListView = (ListView) v.findViewById( R.id.listElencoOggetti );
            mainListView.setAdapter(listAdapterOggetti);
        }else{
            TextView tvNoMusei = v.findViewById(R.id.lblErrorNoMusei);
            tvNoMusei.setVisibility(View.VISIBLE);
        }

        return v;
    }

    /**
     * Salva il percorso nel database
     */
    private void salva(){
        if(controllaCampiObbligatori()){
            Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();

            //Lettura dei campi
            String np = nomePercorso.getText().toString();
            String ddp = durataPercorso.getText().toString();
            String dp = descrizionePercorso.getText().toString();
            SharedPreferences pref = getContext().getSharedPreferences("credenziali", 0);


            new DBPercorso(getContext())
                    .inserisciPercorso(
                            new Percorso(
                                    np,
                                    dp,
                                    Integer.parseInt(ddp),
                                    Integer.parseInt(pref.getString("user_id", "-1"))
                            ),
                            museiScelti,
                            oggettiScelti
                    );
        }
    }

    /**
     * Controlla se tutti i campi obbligatori sono correttamente compilati
     */
    private boolean controllaCampiObbligatori(){
        boolean controlloNome, controlloMusei;

        if(nomePercorso.getText().toString().trim().isEmpty()){
            errorNomePercorso.setVisibility(View.VISIBLE);

            controlloNome = false;
        }else{
            errorNomePercorso.setVisibility(View.INVISIBLE);

            controlloNome = true;
        }

        if(museiScelti.size()>0){
            errorMuseiPercorso.setVisibility(View.INVISIBLE);

            controlloMusei = true;
        }else{
            errorMuseiPercorso.setVisibility(View.VISIBLE);

            controlloMusei = false;
        }

        return controlloNome && controlloMusei;
    }

    /**
     * Inizializzazione dei campi
     */
    private void init(){
        durataPercorso          = v.findViewById(R.id.etDurataPercorso);
        nomePercorso            = v.findViewById(R.id.etNomePercorso);
        descrizionePercorso     = v.findViewById(R.id.etDescrizionePercorso);
        errorNomePercorso       = v.findViewById(R.id.errorNomePercorso);
        errorMuseiPercorso      = v.findViewById(R.id.errorMuseiPercorso);

        durataPercorso.setText("0");
    }

    /**
     * Adapter per visualizzare gli oggetti della città selezionata.
     *
     *
     */
    class OggettoAdapter extends ArrayAdapter<Oggetto> {
        private final Context context;
        private final List<Oggetto> values;
        private LayoutInflater inflater;

        public OggettoAdapter(@NonNull Context context, List<Oggetto> values) {
            super(context, R.layout.checkbox_row, R.id.nome, values);

            inflater = LayoutInflater.from(context);
            this.context = context;
            this.values = values;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.checkbox_row, parent, false);

            TextView nome = rowView.findViewById(R.id.nome);
            CheckBox check = rowView.findViewById(R.id.CheckBox01);

            nome.setText(values.get(position).getNome());
            check.setOnClickListener(v -> {

                CheckBox checkBox = (CheckBox) v;

                if (!checkBox.isChecked()) {
                    museiScelti.remove(values.get(position));

                    //Aggiorno la durata della visita per il percorso
                    totaleDurata -= values.get(position).getDurataVisita();
                    //-----------------------------------------------------------
                } else {
                    oggettiScelti.add(values.get(position));

                    //Aggiorno la durata della visita per il percorso
                    totaleDurata += values.get(position).getDurataVisita();
                    //-----------------------------------------------------------
                }

                //Aggiorno il campo per visualizzare i minuti totali della visita
                durataPercorso.setText(String.valueOf(totaleDurata));

            });

            return rowView;
        }
    }

    /**
     * Adapter per visualizzare i musei della città selezionata.
     *
     *
     */
    class MuseoAdapter extends ArrayAdapter<Museo>{
        private final Context context;
        private final List<Museo> values;
        private LayoutInflater inflater;

        public MuseoAdapter(@NonNull Context context, List<Museo> values) {
            super(context, R.layout.checkbox_row, R.id.nome, values);

            inflater = LayoutInflater.from(context) ;
            this.context = context;
            this.values = values;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.checkbox_row, parent, false);

            TextView nome = rowView.findViewById(R.id.nome);
            CheckBox check = rowView.findViewById(R.id.CheckBox01);

            nome.setText(values.get(position).getNome());
            check.setOnClickListener(v->{

                CheckBox checkBox = (CheckBox)v;

                if(!checkBox.isChecked()){
                    museiScelti.remove(values.get(position));

                    //Aggiorno la durata della visita per il percorso
                    totaleDurata -= values.get(position).getDurata_visita();
                    //-----------------------------------------------------------
                }else{
                    museiScelti.add(values.get(position));

                    //Aggiorno la durata della visita per il percorso
                    totaleDurata += values.get(position).getDurata_visita();
                    //-----------------------------------------------------------
                }

                //Aggiorno il campo per visualizzare i minuti totali della visita
                durataPercorso.setText(String.valueOf(totaleDurata));

            });

            return rowView;
        }
    }

}
