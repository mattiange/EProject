package it.sms.eproject.fragment.home.crud.oggetto;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.data.classes.Citta;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBAutore;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.fragment.home.crud.autori.CRUDAutoreSalvatoSuccesso;
import it.sms.eproject.fragment.home.crud.autori.CRUDCreateAutori;
import it.sms.eproject.fragment.home.crud.liste.ListaAutori;
import it.sms.eproject.fragment.home.crud.liste.ListaCitta;

public class CrudOggetto_Create extends Fragment {

    Calendar myCalendar;
    EditText anno;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudoggetto_create_fragment, container, false);

        Button salva = v.findViewById(R.id.btn_salva_oggetto);
        myCalendar = Calendar.getInstance();
        anno = v.findViewById(R.id.annoOggetto);

        //Visualizzo il DatePicker
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        anno.setOnClickListener(v1->{
            new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        //----------------------------------------


        init(v, inflater);

        salva.setOnClickListener(e-> salva(v));

        return v;
    }

    /**
     * Inizializza con i valori di default
     *
     * @param v Vista
     */
    public void init(View v, LayoutInflater inflater){
        //Setto il nome della città e il suo ID
        ((TextView)v.findViewById(R.id.cittaCodice)).setText(String.valueOf(getArguments().getString("codice_citta")));
        ((TextView)v.findViewById(R.id.cittaValue)).setText(String.valueOf(getArguments().getString("nome_citta")));

        ((TextView)v.findViewById(R.id.autoreValue)).setOnClickListener(e->getDialogAutori(v, inflater));
    }

    /**
     * Visualizza l'elenco degli autori da selezionare
     *
     * @param v View principale
     * @param inflater File XML per la gestione della lista
     */
    public void getDialogAutori(View v, LayoutInflater inflater){
        View convertView = (View) inflater.inflate(R.layout.lista_fragment, null);
        TextView lvTitolo = convertView.findViewById(R.id.titolo);
        lvTitolo.setText("Autori");

        //Recupero tutti gli autori presenti nel sistema
        Autore[] autori = new Autore[0];
        autori = new DBAutore(getContext()).elencoAutori().toArray(autori);
        //-----------------------------------------------------------------------

        //Instanzio il Dialog per visualizzare
        //la lista degli autori
        Dialog dialog = new Dialog(getContext());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.setContentView(convertView);
        dialog.setTitle("ListView");
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        //-----------------------------------------------------------------------

        //Se sono presenti autori (length > 0) allora
        //li aggiungo alla lista
        if(autori.length>0) {
            ArrayAdapter<Autore> adapter = new ListaAutori.AutoreAdapter(getContext(), autori);
            ListView listview = convertView.findViewById(R.id.listView);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener((parent, view, position, id) -> {
                TextView codiceTv = view.findViewById(R.id.listViewCodice);
                TextView codiceNome = view.findViewById(R.id.listViewNome);
                ((TextView)v.findViewById(R.id.autoreCodice)).setText(codiceTv.getText());
                ((TextView)v.findViewById(R.id.autoreValue)).setText(codiceNome.getText());
                dialog.hide();
            });
        }
    }


    /**
     * Formatta la data
     */
    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ITALY);
        anno.setText(dateFormat.format(myCalendar.getTime()));
    }

    /**
     * Salva l'autore
     */
    public void salva(View v){
        EditText nome           = v.findViewById(R.id.nomeOggetto);
        EditText anno           = v.findViewById(R.id.annoOggetto);
        EditText descrizione    = v.findViewById(R.id.descOggetto);
        TextView autore         = v.findViewById(R.id.autoreCodice);
        TextView citta          = v.findViewById(R.id.cittaCodice);
        TextView error          = v.findViewById(R.id.lblError);

        error.setVisibility(View.INVISIBLE);

        //controllo se è stato inserito
        //il nome dell'opera
        if(nome.getText().toString().trim().isEmpty()){
            error.setVisibility(View.VISIBLE);
            error.setText(R.string.crud_oggetto_nome_obbligatorio);

            return;
        }
        //controllo se è stato inserito
        //l'autore di un'opera
        if(autore.getText().toString().trim().isEmpty()){
            error.setVisibility(View.VISIBLE);
            error.setText(R.string.crud_autori_nome_obbligatorio);

            return;
        }

        DBOggetto dbOggetto = new DBOggetto(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(dbOggetto.inserisciOggetto(new Oggetto(
                    nome.getText().toString(),
                    (anno.getText().toString()==null || anno.getText().toString().trim().isEmpty()) ? null : LocalDate.parse(anno.getText().toString()),
                    Integer.parseInt(autore.getText().toString()),
                    descrizione.getText().toString(),
                    Integer.parseInt(citta.getText().toString())
            ))){
                Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
