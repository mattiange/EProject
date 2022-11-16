package it.sms.eproject.fragment.home.crud.liste;

import static it.sms.eproject.util.EseguiFragment.changeFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Citta;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.fragment.home.crud.museo.CRUDMuseoEliminatoSuccesso;
import it.sms.eproject.fragment.home.crud.museo.CRUDMuseoSalvatoSuccesso;
import it.sms.eproject.fragment.home.crud.museo.CrudMuseo_Create;
import it.sms.eproject.fragment.home.crud.museo.CrudVisualizzaMuseo;
import it.sms.eproject.util.Util;

@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class ListaMusei extends Fragment {
    ListView listView;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_fragment, container, false);


        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.visualizza_tutti_i_musei);

        Museo[] musei = new Museo[0];
        musei = new DBMuseo(getContext()).elencoMusei().toArray(musei);
        if(musei.length > 0) {
            ArrayAdapter<Museo> adapter = new MuseoAdapter(getContext(), musei);
            listView = v.findViewById(R.id.listView);
            listView.setAdapter(adapter);


        /*listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView codice = ((TextView)view.findViewById(R.id.listViewCodice));

            this.bundle.putString("codice_museo", codice.getText().toString());

            getMuseo();
        });*/

            listView.setOnItemLongClickListener((parent, view, position, id) -> {

                //Toast.makeText(getContext(), "OK LONG CLICK", Toast.LENGTH_SHORT).show();

                TextView codiceTv = view.findViewById(R.id.listViewCodice);
                int codice = Integer.parseInt(codiceTv.getText().toString());

                //Visualizzo l'alert per la conferma dell'eliminazione del museo
                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.delete_museum))
                        .setMessage(getResources().getString(R.string.delete_museum_msq_question))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.cancella, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBMuseo db = new DBMuseo(getContext());
                                if (db.eliminaMuseo(codice)) {
                                    Util.visualizzaFragment(() -> {
                                        Fragment fragment = new CRUDMuseoEliminatoSuccesso();

                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                        fragmentTransaction.commit();

                                    });
                                } else {
                                    Toast.makeText(getContext(), getResources().getString(R.string.msg_error_delete, getResources().getString(R.string.del_museo)), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.btn_modifica, (d, w) -> {

                            TextView codiceMuseo = ((TextView) view.findViewById(R.id.listViewCodice));

                            bundle.putString("codice_museo", codiceMuseo.getText().toString());

                            getMuseo();
                        })
                        .show();

                return false;
            });
        }


        this.bundle = new Bundle();

        return v;
    }

    /**
     * Porta alla visualizzazione del museo selezionato
     */
    public void getMuseo(){
        changeFragment(()->{
            Fragment fragment = new CrudVisualizzaMuseo();
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
    static class MuseoAdapter extends ArrayAdapter<Museo>{
        private final Context context;
        private final Museo[] values;

        public MuseoAdapter(@NonNull Context context, Museo[]  values) {
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
