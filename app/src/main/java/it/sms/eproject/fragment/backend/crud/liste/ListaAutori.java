package it.sms.eproject.fragment.backend.crud.liste;

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
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.database.DBAutore;
import it.sms.eproject.fragment.backend.crud.autori.CRUDAutoreEliminatoSuccesso;
import it.sms.eproject.fragment.backend.crud.autori.CrudVisualizzaAutore;
import it.sms.eproject.util.Util;

public class ListaAutori extends Fragment {
    ListView listView;
    Bundle bundle;
    TextView lblError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_fragment, container, false);


        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.visualizza_tutti_gli_autori);
        this.lblError = ((TextView)v.findViewById(R.id.lblError));
        this.lblError.setText(R.string.msg_error_autori_no_trovati);

        Autore[] autori = new Autore[0];
        autori = new DBAutore(getContext()).elencoAutori().toArray(autori);
        if(autori.length > 0) {
            ArrayAdapter<Autore> adapter = new AutoreAdapter(getContext(), autori);
            listView = v.findViewById(R.id.listView);
            listView.setAdapter(adapter);

            /*listView.setOnItemClickListener((parent, view, position, id) -> {
                TextView codice = ((TextView) view.findViewById(R.id.listViewCodice));

                this.bundle.putString("codice_autore", codice.getText().toString());

                getAutore();

            });*/
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    //Toast.makeText(getContext(), "OK LONG CLICK", Toast.LENGTH_SHORT).show();

                    TextView codiceTv = view.findViewById(R.id.listViewCodice);
                    int codice = Integer.parseInt(codiceTv.getText().toString());

                    //Visualizzo l'alert per la conferma dell'eliminazione del museo
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.delete_autore))
                            .setMessage(getResources().getString(R.string.delete_autore_msq_question))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(R.string.cancella, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBAutore db = new DBAutore(getContext());
                                    if (db.eliminaAutore(codice)) {
                                        Util.visualizzaFragment(() -> {
                                            Fragment fragment = new CRUDAutoreEliminatoSuccesso();

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                            fragmentTransaction.commit();

                                        });
                                    } else {
                                        Toast.makeText(getContext(), getResources().getString(R.string.msg_error_delete, getResources().getString(R.string.del_autore)), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton(R.string.btn_modifica, (d, w) -> {

                                TextView codiceAutore = ((TextView) view.findViewById(R.id.listViewCodice));

                                bundle.putString("codice_autore", codiceAutore.getText().toString());

                                getAutore();

                            })
                            .show();

                    return false;
                }
            });
        }else{
            lblError.setVisibility(View.VISIBLE);
        }

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
     * Adapter per visualizzare gli stati.
     *
     * Tiene traccia anche dei rispettivi ID
     */
    public static class AutoreAdapter extends ArrayAdapter<Autore>{
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
