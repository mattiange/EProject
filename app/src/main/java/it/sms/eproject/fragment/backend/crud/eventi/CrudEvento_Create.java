package it.sms.eproject.fragment.backend.crud.eventi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Attivita;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBAttivita;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;

/**
 * Pagina di creazione di un nuovo evento
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudEvento_Create extends Fragment {
    View v;

    TextView luogo;
    EditText nomeAttivita;
    EditText descrizioneAttivita;
    Button btnSalva;
    Bundle bundle;
    int codiceLuogo;
    long codiceCitta;
    int codiceAutore;

    String tipoLuogo;

    /**
     * Indica se deve creare un'attivitò => create
     * o aggiornarla => update
     */
    String tipoAzione = "create";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.crudattivita_create_fragment, container, false);

        init();

        return v;
    }

    /**
     * Inizializza i dati e i campi
     */
    private void init(){
        this.luogo                  = this.v.findViewById(R.id.tvNomeLuogo);
        this.nomeAttivita           = this.v.findViewById(R.id.etNomeAttivita);
        this.descrizioneAttivita    = this.v.findViewById(R.id.etDescrizioneAttivita);
        this.btnSalva               = this.v.findViewById(R.id.btn_salva_museo);
        this.bundle                 = getArguments();

        this.tipoLuogo              = this.bundle.getString("tipo_luogo", "");
        this.codiceLuogo            = this.bundle.getInt("luogo_codice", 0);
        this.codiceAutore           = -1;

        //Abilitato per la modifica
        if(getArguments().getLong("codice_attivita", -1) > -1){
            Attivita a = new DBAttivita(getContext()).getAttivita(getArguments().getLong("codice_attivita", -1));
            this.tipoAzione = "update";//entro nella modalità di modifica

            this.nomeAttivita.setText(a.getNome());
            this.descrizioneAttivita.setText(a.getDescrizione());
        }

        if(tipoLuogo.trim().equals("museo")){
            Museo m = new DBMuseo(getContext()).getMuseo(this.codiceLuogo);
            this.codiceCitta = m.getCitta();
            //this.luogo.setText("PLUTO");
        }else if(tipoLuogo.trim().equals("oggetto")){
            Oggetto o = new DBOggetto(getContext()).getOggetto(this.codiceLuogo);
            this.codiceCitta    = o.getCodice_citta();
            this.codiceAutore   = o.getAutore();
            //this.luogo.setText("PIPPO");
        }

        this.btnSalva.setOnClickListener(this::salva);
    }

    /**
     * Salva l'evento nel db
     * @param v
     */
    private void salva(View v) {
        SharedPreferences pref = getContext().getSharedPreferences("credenziali", 0);
        SharedPreferences.Editor editor = pref.edit();

        String codiceUtente = pref.getString("user_id", "");

        Attivita a = new Attivita(-1, Integer.valueOf(codiceUtente), this.codiceCitta, this.nomeAttivita.getText().toString(), this.descrizioneAttivita.getText().toString());

        a.setCodice(new DBAttivita(getContext()).inserisciAttivita(a));


        if(this.tipoAzione.equals("create")){
            create(a);
        }else{
            update(a);
        }

        visualizzaFragment(() -> {
            Bundle bundle1 = new Bundle();
            bundle1.putInt("luogo_codice", this.codiceLuogo);
            bundle1.putLong("codice_attivita", a.getCodice());
            bundle1.putString("tipo_luogo", this.tipoLuogo);

            Fragment fragment = new CrudEvento_Create();
            fragment.setArguments(bundle1);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();
        });
    }

    /**
     * Aggiorna l'evento nel db
     *
     * @param a Attività da aggiornare
     */
    private void update(Attivita a){
        new DBAttivita(getContext()).aggiornaAttivita(a);
    }

    /**
     * Inserisce l'evento nel db
     *
     * @param a Attivitò da inserire
     */
    private void create(Attivita a){
        if(this.tipoLuogo.trim().equals("museo")){
            new DBAttivita(getContext()).inserisciMuseoAttivita(a.getCodice(), this.codiceLuogo);
        }else if(this.tipoLuogo.trim().equals("oggetto")){
            new DBAttivita(getContext()).inserisciOggettoAttivita(a.getCodice(), this.codiceLuogo);
        }
    }

    public void visualizzaFragment(CallbackFragment callbackFragment){
        callbackFragment.changeFragment();
    }

}
