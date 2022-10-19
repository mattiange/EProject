package it.sms.eproject.activity.crud.liste;

import static it.sms.eproject.util.EseguiFragment.changeFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import it.sms.eproject.R;
import it.sms.eproject.activity.crud.CrudMuseo_Create;
import it.sms.eproject.data.classes.Citta;
import it.sms.eproject.data.classes.Stato;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBStato;

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
        }catch (NullPointerException e) {
            ((TextView)v.findViewById(R.id.msgError)).setText(String.format(getResources().getString(R.string.msg_error_no_citta), getArguments().getString("nome_provincia")));
        }

        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.seleziona_citta);

        this.bundle = new Bundle();

        //aggiungiEvento();

        return v;
    }

    /**
     * Visualizza la pagina di creazione di un nuovo museo
     */
    public void aggiungiEvento(){
        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView codice = ((TextView)view.findViewById(R.id.listViewCodice));
            TextView nome   = ((TextView)view.findViewById(R.id.listViewNome));

            this.bundle.putString("codice_citta", codice.getText().toString());
            this.bundle.putString("nome_citta",   nome.getText().toString());

            getNuovoMuseo();
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
            fragmentTransaction.commit();
        });
    }

    /**
     * Adapter per visualizzare gli stati.
     *
     * Tiene traccia anche dei rispettivi ID
     */
    static class CittaAdapter extends ArrayAdapter<Citta>{
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
