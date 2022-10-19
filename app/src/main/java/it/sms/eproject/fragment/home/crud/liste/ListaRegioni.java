package it.sms.eproject.fragment.home.crud.liste;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Regione;
import it.sms.eproject.database.DBRegione;

public class ListaRegioni extends Fragment {
    ListView listView;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_fragment, container, false);

        try{
            Regione[] regioni = new Regione[0];
            ArrayAdapter<Regione> adapter = new RegioneAdapter(getContext(), new DBRegione(getContext()).elencoRegioni(Integer.parseInt(getArguments().getString("codice_stato"))).toArray(regioni));
            listView = v.findViewById(R.id.listView);
            listView.setAdapter(adapter);

            aggiungiEvento();
        }catch (NullPointerException e) {
            ((TextView)v.findViewById(R.id.msgError)).setText(String.format(getResources().getString(R.string.msg_error_no_regione), getArguments().getString("nome_stato")));
        }

        this.bundle = new Bundle();

        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.seleziona_regione);

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

            this.bundle.putString("codice_regione", codice.getText().toString());
            this.bundle.putString("nome_regione", nome.getText().toString());

            getProvince();
        });
    }

    /**
     * Visualizzo le città
     */
    private void getProvince() {
        changeFragment(()->{
            Fragment fragment = new ListaProvince();
            fragment.setArguments(this.bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.commit();
        });
    }

    /**
     * Adapter per visualizzare le regioni.
     *
     * Tiene traccia anche dei rispettivi ID
     */
    static class RegioneAdapter extends ArrayAdapter<Regione>{
        private final Context context;
        private final Regione[] values;

        public RegioneAdapter(@NonNull Context context, Regione[]  values) {
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
