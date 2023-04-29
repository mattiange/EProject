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
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.fragment.backend.crud.oggetto.CRUDOggettoEliminatoSuccesso;
import it.sms.eproject.fragment.backend.crud.oggetto.CrudVisualizzaOggetto;
import it.sms.eproject.util.Util;

/**
 * Elenco dei punti di interesse
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class ListaOggetti extends Fragment {
    ListView listView;
    Bundle bundle;
    TextView lblError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_fragment, container, false);


        ((TextView)v.findViewById(R.id.titolo)).setText(R.string.visualizza_tutti_gli_oggetti);
        this.lblError = ((TextView)v.findViewById(R.id.lblError));
        this.lblError.setText(R.string.msg_error_autori_no_trovati);

        Oggetto[] oggetti = new Oggetto[0];
        oggetti = new DBOggetto(getContext()).elencoOggetti().toArray(oggetti);
        if(oggetti.length > 0) {
            ArrayAdapter<Oggetto> adapter = new OggettoAdapter(getContext(), oggetti);
            listView = v.findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    //Toast.makeText(getContext(), "OK LONG CLICK", Toast.LENGTH_SHORT).show();

                    TextView codiceTv = view.findViewById(R.id.listViewCodice);
                    int codice = Integer.parseInt(codiceTv.getText().toString());

                    //Visualizzo l'alert per la conferma dell'eliminazione del museo
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.delete_oggetto))
                            .setMessage(getResources().getString(R.string.delete_oggetto_msq_question))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(R.string.cancella, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DBOggetto db = new DBOggetto(getContext());
                                    if (db.eliminaOggetto(codice)) {
                                        Util.visualizzaFragment(() -> {
                                            Fragment fragment = new CRUDOggettoEliminatoSuccesso();

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

                                bundle.putString("codice_oggetto", codiceAutore.getText().toString());

                                getOggetto();
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
    public void getOggetto(){
        changeFragment(()->{
            Fragment fragment = new CrudVisualizzaOggetto();
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
    static class OggettoAdapter extends ArrayAdapter<Oggetto>{
        private final Context context;
        private final Oggetto[] values;

        public OggettoAdapter(@NonNull Context context, Oggetto[]  values) {
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

            codice.setText(String.valueOf(values[position].getId()));
            nome.setText(values[position].getNome());

            return rowView;
        }
    }
}
