package it.sms.eproject.fragment.home.crud.liste;

import static it.sms.eproject.util.EseguiFragment.changeFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Percorso;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBPercorso;
import it.sms.eproject.fragment.home.crud.museo.CRUDMuseoEliminatoSuccesso;
import it.sms.eproject.fragment.home.crud.museo.CrudMuseo_Create;
import it.sms.eproject.fragment.home.crud.museo.CrudVisualizzaMuseo;
import it.sms.eproject.fragment.home.crud.percorso.CRUDPercorso;
import it.sms.eproject.fragment.home.crud.percorso.CRUDPercorsoEliminatoSuccesso;
import it.sms.eproject.fragment.home.crud.percorso.CRUDVisualizzaPercorso;
import it.sms.eproject.util.EseguiFragment;
import it.sms.eproject.util.Util;

@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class ListaPercorso extends Fragment {
    ListView listView;
    Bundle bundle;

    boolean backpressedlistener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_fragment, container, false);


        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.visualizza_tutti_i_percorsi);
        this.bundle = new Bundle();

        Percorso[] percorsi = new Percorso[0];
        percorsi = new DBPercorso(getContext()).elencoPercorsi().toArray(percorsi);
        if(percorsi.length > 0) {
            ArrayAdapter<Percorso> adapter = new PercorsoAdapter(getContext(), percorsi);
            listView = v.findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {

                //Toast.makeText(getContext(), "OK LONG CLICK", Toast.LENGTH_SHORT).show();

                TextView codiceTv = view.findViewById(R.id.listViewCodice);
                int codice = Integer.parseInt(codiceTv.getText().toString());

                this.bundle.putLong("codice_percorso", codice);

                getPercorso();

            });
        }


        this.bundle = new Bundle();

        //Torno indietro nella gestione dei percorsi
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(backpressedlistener) {
                    EseguiFragment.changeFragment(() -> {
                        Fragment fragment = new CRUDPercorso();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                        fragmentTransaction.addToBackStack(null).commit();
                    });
                }else{
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        // passing null value
        // to backpressedlistener
        backpressedlistener = false;
        super.onPause();
    }


    // Overriding onResume() method
    @Override
    public void onResume() {
        super.onResume();
        // passing context of fragment
        //  to backpressedlistener
        backpressedlistener = true;
    }

    /**
     * Porta alla visualizzazione del museo selezionato
     */
    public void getPercorso(){
        changeFragment(()->{
            Fragment fragment = new CRUDVisualizzaPercorso();
            fragment.setArguments(this.bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }

    /**
     * Adapter per visualizzare gli stati.
     *
     * Tiene traccia anche dei rispettivi ID
     */
    static class PercorsoAdapter extends ArrayAdapter<Percorso>{
        private final Context context;
        private final Percorso[] values;

        public PercorsoAdapter(@NonNull Context context, Percorso[]  values) {
            super(context, -1, values);

            this.context = context;
            this.values = values;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row, parent, false);

            TextView codice = rowView.findViewById(R.id.listViewCodice);
            TextView nome = rowView.findViewById(R.id.listViewNome);

            codice.setText(String.valueOf(values[position].getID()));
            nome.setText(values[position].getNome());

            return rowView;
        }
    }
}
