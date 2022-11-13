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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.database.DBAutore;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.fragment.home.crud.autori.CrudVisualizzaAutore;
import it.sms.eproject.fragment.home.crud.museo.CrudMuseo_Create;
import it.sms.eproject.fragment.home.crud.museo.CrudVisualizzaMuseo;

public class ListaAutori extends Fragment {
    ListView listView;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_fragment, container, false);


        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.visualizza_tutti_gli_autori);

        Autore[] autori = new Autore[0];
        ArrayAdapter<Autore> adapter = new AutoreAdapter(getContext(), new DBAutore(getContext()).elencoAutori().toArray(autori));
        listView = v.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView codice = ((TextView)view.findViewById(R.id.listViewCodice));

            this.bundle.putString("codice_autore", codice.getText().toString());

            getAutore();

        });

        this.bundle = new Bundle();

        return v;
    }

    /**
     * Porta alla visualizzazione dell'autore selezionato
     */
    public void getAutore(){
        changeFragment(()->{
            Fragment fragment = new CrudVisualizzaAutore();
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
            fragmentTransaction.commit();
        });
    }

    /**
     * Adapter per visualizzare gli stati.
     *
     * Tiene traccia anche dei rispettivi ID
     */
    static class AutoreAdapter extends ArrayAdapter<Autore>{
        private final Context context;
        private final Autore[] values;

        public AutoreAdapter(@NonNull Context context, Autore[]  values) {
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
