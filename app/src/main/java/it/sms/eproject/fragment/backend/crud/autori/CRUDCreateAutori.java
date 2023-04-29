package it.sms.eproject.fragment.backend.crud.autori;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.database.DBAutore;

/**
 * Fragment per creare un nuovo autore
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CRUDCreateAutori extends Fragment {
    EditText dataNascita;
    EditText dataMorte;
    Calendar myCalendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crud_autori_create_fragment, container,false);

        Button salva = v.findViewById(R.id.btn_salva);
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        dataNascita = v.findViewById(R.id.etDataDiNascita);
        dataMorte = v.findViewById(R.id.etDataMorte);

        dataNascita.setOnClickListener(v1->{
            new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        dataMorte.setOnClickListener(v1->{
            new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        salva.setOnClickListener(e->{
            salva(v);
        });


        return v;
    }

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ITALY);
        dataNascita.setText(dateFormat.format(myCalendar.getTime()));
    }

    /**
     * Salva l'autore
     */
    public void salva(View v){
        EditText nome = v.findViewById(R.id.etNome);
        EditText descrizione = v.findViewById(R.id.etDescrizione);
        TextView error = v.findViewById(R.id.lblError);

        error.setVisibility(View.INVISIBLE);

        //controllo se è stato inserito
        //il nome dell'autore di un'opera
        if(nome.getText().toString().trim().isEmpty()){
            error.setVisibility(View.VISIBLE);
            error.setText(R.string.crud_autori_nome_obbligatorio);

            return;
        }

        //Salvo l'autore nel database
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if((new DBAutore(getContext())).inserisciAutore(
                    new Autore(
                            nome.getText().toString(),
                            dataNascita.getText().toString().trim().isEmpty() ? null: LocalDate.parse(dataNascita.getText().toString()),
                            dataMorte.getText().toString().trim().isEmpty() ? null: LocalDate.parse(dataMorte.getText().toString()),
                            descrizione.getText().toString()
                    )
            )){
                Fragment fragment = new CRUDAutoreSalvatoSuccesso();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            }
        }
    }
}
