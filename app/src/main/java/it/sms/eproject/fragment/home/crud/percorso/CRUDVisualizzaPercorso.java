package it.sms.eproject.fragment.home.crud.percorso;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.OggettiMuseoHasPercorsi;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBPercorso;
import it.sms.eproject.fragment.home.crud.liste.ListaPercorso;
import it.sms.eproject.util.EseguiFragment;
import it.sms.eproject.util.Util;

public class CRUDVisualizzaPercorso extends Fragment {
    private Bundle bundle;
    long codice;

    boolean backpressedlistener;

    DBPercorso dbPercorso;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudpercorso_visualizza_fragment, container,false);


        bundle = this.getArguments();

        codice = -1;

        //Leggo il codice del percorso
        if (bundle != null) {
            codice = bundle.getLong("codice_percorso", -1L);
        }

        //ottengo le informazioni sul percorso
        //sui musei
        //e sugli oggetti
        dbPercorso = new DBPercorso(getContext());
        System.out.println("CODICE: " + codice);
        OggettiMuseoHasPercorsi percorsi_utente = dbPercorso.getElementiPercorso(codice);
        ArrayList<Museo> musei = percorsi_utente.getMusei();
        ArrayList<Oggetto> oggetti = percorsi_utente.getOggetti();
        //---------------------------------------------------------------------------------

        //Se non ci sono elementi visualizzo il messaggio
        //di avviso

        TextView noElementMsg = v.findViewById(R.id.lblError);
        if(musei.size() == 0 && oggetti.size() == 0){
            noElementMsg.setVisibility(View.VISIBLE);
        }else {
            noElementMsg.setVisibility(View.INVISIBLE);
        }

        this.bundle.putString("codice_citta", String.valueOf(dbPercorso.getCodiceCitta(codice)));
        this.bundle.putString("nome_citta", dbPercorso.getNomeCitta(codice));

        //Creo il json
        String items = Util.getJsonString(oggetti, musei);
        //--------------------------------------------


        TextView titolo = v.findViewById(R.id.showPercorsoTitle);
        titolo.setText(String.format(getResources().getString(R.string.percorso_titolo), dbPercorso.get(codice)==null?"":dbPercorso.get(codice).getNome()));
        System.out.println(dbPercorso.get(codice)==null?"":dbPercorso.get(codice).getNome());

        WebView webView = v.findViewById(R.id.webview);
        webView.loadUrl("https://www.mattiawebdesigner.com/sms/svg_test/json_read_svg.php?circleR=10&spaceBetweenItem=60&startY=20&items="+items);

        FloatingActionButton updateBtn = v.findViewById(R.id.update);
        FloatingActionButton deleteBtn = v.findViewById(R.id.delete);
        FloatingActionButton accettaBtn = v.findViewById(R.id.accetta);
        FloatingActionButton rifiutaBtn = v.findViewById(R.id.rifiuta);
        if(this.bundle.getBoolean("da-accettarre", false)){
            updateBtn.setVisibility(View.INVISIBLE);
            deleteBtn.setVisibility(View.INVISIBLE);
            accettaBtn.setVisibility(View.VISIBLE);
            rifiutaBtn.setVisibility(View.VISIBLE);
        }else{
            updateBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            accettaBtn.setVisibility(View.INVISIBLE);
            rifiutaBtn.setVisibility(View.INVISIBLE);
        }

        //Attivo gli eventi sui pulsanti
        updateBtn.setOnClickListener(this::getModificaPercorso);
        deleteBtn.setOnClickListener(this::getEliminaPercorso);
        accettaBtn.setOnClickListener(this::getAccettaPercorso);
        rifiutaBtn.setOnClickListener(this::getRifiutaPercorso);
        //------------------

        //Torna sull'elenco dei percorsi creati
        //evitando di ritornare alla creazione
        //del percorso
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(backpressedlistener) {
                    EseguiFragment.changeFragment(() -> {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("azione-lista", "show-percorsi");
                        editor.apply();

                        Fragment fragment = new ListaPercorso();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                        fragmentTransaction.addToBackStack(null).commit();
                    });
                }else{
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        return v;
    }
    @Override
    public void onPause() {
        // passing null value
        // to backpressedlistener
        backpressedlistener = false;
        super.onPause();
    }


    // Overriding onResume() method
    @Override
    public void onResume() {
        super.onResume();
        // passing context of fragment
        //  to backpressedlistener
        backpressedlistener = true;
    }

    /**
     * Accetta il percorso
     *
     * @param v
     */
    public void getAccettaPercorso(View v){
        EseguiFragment.changeFragment(()-> {
            this.bundle.putBoolean("da-accettarre", false);

            Fragment fragment = new CRUDVisualizzaPercorso();
            fragment.setArguments(this.bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();

        });
    }

    /**
     * Rifiuta il percorso
     *
     * @param v
     */
    public void getRifiutaPercorso(View v){
        getEliminaPercorso(v);
    }

    /**
     * Elimina il percorso selezionato dal database
     *
     * @param v
     */
    public void getEliminaPercorso(View v){
        if(this.dbPercorso.eliminaPercorso(this.codice)){
            EseguiFragment.changeFragment(()->{
                Fragment fragment = new CRUDPercorsoEliminatoSuccesso();
                fragment.setArguments(this.bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();

            });
        }
    }

    /**
     * Porta alla pagina di modifica del percorso
     *
     * @param v
     */
    public void getModificaPercorso(View v){
        EseguiFragment.changeFragment(()-> {
            Fragment fragment = new CrudPercorso_Aggiungi_Item();
            fragment.setArguments(this.bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null).commit();

        });
    }

}
