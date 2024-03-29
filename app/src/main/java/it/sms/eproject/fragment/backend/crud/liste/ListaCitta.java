package it.sms.eproject.fragment.backend.crud.liste;

import static it.sms.eproject.util.EseguiFragment.changeFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.fragment.backend.crud.museo.CrudMuseo_Create;
import it.sms.eproject.data.classes.Citta;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.fragment.backend.crud.oggetto.CrudOggetto_Create;
import it.sms.eproject.fragment.backend.crud.percorso.CrudPercorso_Create;

/**
 * Elenco delle città
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class ListaCitta extends Fragment {
    ListView listView;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_fragment, container, false);

        try {
            Citta[] citta = new Citta[0];
            ArrayAdapter<Citta> adapter = new CittaAdapter(getContext(), new DBCitta(getContext()).elencoCitta(Integer.parseInt(getArguments().getString("codice_provincia"))).toArray(citta));
            listView = v.findViewById(R.id.listView);
            listView.setAdapter(adapter);

            aggiungiEvento();
        }catch (NullPointerException e) {
            ((TextView)v.findViewById(R.id.msgError)).setText(String.format(getResources().getString(R.string.msg_error_no_citta), getArguments().getString("nome_provincia")));
        }

        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.seleziona_citta);

        this.bundle = new Bundle();

        return v;
    }

    /**
     * Visualizza la pagina di creazion
     */
    public void aggiungiEvento(){
        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView codice = ((TextView)view.findViewById(R.id.listViewCodice));
            TextView nome   = ((TextView)view.findViewById(R.id.listViewNome));

            this.bundle.putString("codice_citta", codice.getText().toString());
            this.bundle.putString("nome_citta",   nome.getText().toString());

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String azione = preferences.getString("azione-lista", "");

            if(azione.equalsIgnoreCase("nuovo-museo")) {
                getNuovoMuseo();
            }else if(azione.equalsIgnoreCase("nuovo-oggetto")){
                getNuovoOggetto();
            }else if(azione.equalsIgnoreCase("nuovo-percorso")){
                getNuovoPercorso();
            }
        });
    }

    /**
     * Pagina di creazione nuovo oggetto
     */
    private void getNuovoOggetto() {
        changeFragment(()->{
            Fragment fragment = new CrudOggetto_Create();
            fragment.setArguments(this.bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }

    /**
     * Pagina di creazione nuovo museo
     */
    private void getNuovoMuseo() {
        changeFragment(()->{
            Fragment fragment = new CrudMuseo_Create();
            fragment.setArguments(this.bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }

    /**
     * Pagina di creazione di un nuovo percorso
     */
    private void getNuovoPercorso(){
        changeFragment(()->{
            Fragment fragment = new CrudPercorso_Create();
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
    public static class CittaAdapter extends ArrayAdapter<Citta>{
        private final Context context;
        private final Citta[] values;

        public CittaAdapter(@NonNull Context context, Citta[]  values) {
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

            codice.setText(String.valueOf(values[position].getCodice()));
            nome.setText(values[position].getNome());

            return rowView;
        }
    }
}
