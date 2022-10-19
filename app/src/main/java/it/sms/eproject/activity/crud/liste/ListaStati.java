package it.sms.eproject.activity.crud.liste;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
import it.sms.eproject.data.classes.Stato;
import it.sms.eproject.database.DBStato;

import static it.sms.eproject.util.EseguiFragment.changeFragment;

public class ListaStati extends Fragment {
    ListView listView;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_fragment, container, false);

        try {
            Stato[] stati = new Stato[0];
            ArrayAdapter<Stato> adapter = new StatoAdapter(getContext(), new DBStato(getContext()).elencoStati().toArray(stati));
            listView = v.findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }catch (NullPointerException e) {
            ((TextView)v.findViewById(R.id.msgError)).setText(R.string.msg_error_no_stato);
        }

        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.seleziona_stato);

        this.bundle = new Bundle();

        aggiungiEvento();

        return v;
    }

    /**
     * Aggiunge l'evento per intercettare il click
     * sulla singola riga della lista
     */
    public void aggiungiEvento(){
        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView codice = ((TextView)view.findViewById(R.id.listViewCodice));
            TextView nome   = ((TextView)view.findViewById(R.id.listViewNome));

            this.bundle.putString("codice_stato", codice.getText().toString());
            this.bundle.putString("nome_stato", nome.getText().toString());

            getRegioni();
        });
    }

    /**
     * Visualizzo le regioni
     */
    private void getRegioni() {
        changeFragment(()->{
            Fragment fragment = new ListaRegioni();
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
    static class StatoAdapter extends ArrayAdapter<Stato>{
        private final Context context;
        private final Stato[] values;

        public StatoAdapter(@NonNull Context context, Stato[]  values) {
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
