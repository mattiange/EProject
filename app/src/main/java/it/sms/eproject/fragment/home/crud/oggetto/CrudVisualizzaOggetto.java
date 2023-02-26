package it.sms.eproject.fragment.home.crud.oggetto;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Autore;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBAutore;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.fragment.home.crud.autori.CRUDAutoreSalvatoSuccesso;
import it.sms.eproject.fragment.home.crud.liste.ListaAutori;
import it.sms.eproject.util.EseguiFragment;
import it.sms.eproject.util.Util;

@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudVisualizzaOggetto extends Fragment {
    View v;

    //campi autori
    TextView titolo;
    EditText nome;
    EditText anno;
    TextView codiceAutore;
    TextView nomeAutore;
    TextView codiceCitta;
    TextView nomeCitta;
    EditText descrizione;
    TextView error;
    Button   salva;

    //Oggetto letto
    Oggetto oggetto;

    DBOggetto db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.crudoggetto_create_fragment, container,false);

        init(v, inflater);
        compilaCampi();

        salva.setOnClickListener(e->{
            error.setVisibility(View.INVISIBLE);

            oggetto.setNome(nome.getText().toString());
            oggetto.setAnno(Integer.parseInt(anno.getText().toString()));
            oggetto.setDescrizione(descrizione.getText().toString());
            oggetto.setAutore(Integer.parseInt(codiceAutore.getText().toString()));
            oggetto.setCodice_citta(Integer.parseInt(codiceCitta.getText().toString()));

            if(db.aggiornaOggetto(oggetto)){
                EseguiFragment.changeFragment(()->{
                    Fragment fragment = new CRUDOggettoSalvatoSuccesso();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                    fragmentTransaction.commit();
                });
            }else {
                error.setVisibility(View.VISIBLE);
                error.setText(R.string.msg_error_update);
            }

            /*error.setVisibility(View.INVISIBLE);

            oggetto.setNome(nome.getText().toString());
            oggetto.setDataDiNascita(Util.parseDate(dataNascita.getText().toString()));
            oggetto.setDataDiMorte(Util.parseDate(dataMorte.getText().toString()));
            oggetto.setDescrizione(descrizione.getText().toString());
            if(db.aggiornaAutore(oggetto)){
                EseguiFragment.changeFragment(()->{
                    Fragment fragment = new CRUDAutoreSalvatoSuccesso();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                    fragmentTransaction.commit();
                });
            }else {
                error.setVisibility(View.VISIBLE);
                error.setText(R.string.msg_error_update);
            }*/

        });

        return v;

    }

    /**
     * Inizializza i campi
     */
    @AutoreCodice(autore = "Mattia Leonardo Angelillo")
    public void init(View v, LayoutInflater inflater){
        db = new DBOggetto(getContext());
        oggetto = db.getOggetto(Integer.parseInt(getArguments().getString("codice_oggetto")));

        ((TextView)v.findViewById(R.id.autoreValue)).setOnClickListener(e->getDialogAutori(v, inflater));

        titolo          = v.findViewById(R.id.titolo);
        nome            = v.findViewById(R.id.nomeOggetto);
        anno            = v.findViewById(R.id.annoOggetto);
        codiceAutore    = v.findViewById(R.id.autoreCodice);
        nomeAutore      = v.findViewById(R.id.autoreValue);
        codiceCitta     = v.findViewById(R.id.cittaCodice);
        nomeCitta       = v.findViewById(R.id.cittaValue);
        descrizione     = v.findViewById(R.id.descOggetto);
        error           = v.findViewById(R.id.lblError);

        salva       = v.findViewById(R.id.btn_salva_oggetto);

        error.setVisibility(View.INVISIBLE);
    }

    /**
     * Compila i campi del museo da visualizzare
     */
    @AutoreCodice(autore = "Mattia Leonardo Angelillo")
    public void compilaCampi(){
        this.titolo.setText(getResources().getText(R.string.crud_oggetto_update_titolo));
        this.nome.setText(oggetto.getNome());
        this.anno.setText(String.valueOf(oggetto.getAnno()==0?"":oggetto.getAnno()));
        this.descrizione.setText(oggetto.getDescrizione());
        this.codiceAutore.setText(String.valueOf(oggetto.getAutore()));
        try {
            this.nomeAutore.setText(db.getNomeAutore(oggetto.getAutore()));
        }catch (NullPointerException e){
            this.nomeAutore.setText("");
        }
        this.codiceCitta.setText(String.valueOf(oggetto.getCodice_citta()));
        this.nomeCitta.setText(db.getNomeCitta(oggetto.getCodice_citta()));
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
}
