package it.sms.eproject.fragment.utente;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Percorso;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBPercorso;
import it.sms.eproject.fragment.backend.crud.liste.ListaStati;
import it.sms.eproject.fragment.backend.crud.percorso.CRUDVisualizzaPercorso;
import it.sms.eproject.util.EseguiFragment;

/**
 * Fragment principale del curatore
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class ListaPercorsiFragment extends Fragment {
    TabLayout tabLayout;

    View view;

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_utente_lista_percorsi, container, false);

        init();

        return view;
    }

    /**
     * Inizializza i tab
     */
    public void init(){
        this.bundle = new Bundle();

        FloatingActionButton nuovoPercorso = view.findViewById(R.id.btnNuovoPercorso);
        nuovoPercorso.setOnClickListener(e->{
            EseguiFragment.changeFragment(()->{
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("azione-lista","nuovo-percorso");
                editor.apply();

                Fragment fragment = new ListaStati();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.addToBackStack(null).commit();
            });
        });

        tabLayout = view.findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new Adapter(getContext()));

        tabLayout.setupWithViewPager(viewPager);
    }

    class Adapter extends PagerAdapter{

        private Context mContext;

        public Adapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            ModelObject modelObject = ModelObject.values()[position];
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
            collection.addView(layout);

            if(position == 0) {
                getAltriPercorsi();
            }else if(position == 1){
                getMyPercorsi();
            }

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return ModelObject.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            ModelObject customPagerEnum = ModelObject.values()[position];
            return mContext.getString(customPagerEnum.getTitleResId());
        }

        /**
         * Restituisce i percorsi creati
         * dall'utente loggato
         */
        public void getMyPercorsi(){
            SharedPreferences preferences = getActivity().getSharedPreferences("credenziali", Context.MODE_PRIVATE);
            final long user_id = Long.valueOf(preferences.getString("user_id", "-1"));

            ArrayList< Percorso> percorsi[] = new ArrayList[]{
                    new DBPercorso(this.mContext).getPercorsoByUtente(user_id)
            };

            final LinearLayout llc = view.findViewById(R.id.ll_my_percorsi_route);

            createItems(llc, percorsi[0]);


            //Ricerca del percorso
            EditText etRicercaMieiPermessi = view.findViewById(R.id.cerca_my_percorsi);
            etRicercaMieiPermessi.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    percorsi[0] = new DBPercorso(mContext).getPercorsoByUtente(user_id, new StringBuilder(s).toString());

                    llc.removeAllViews();
                    if(percorsi[0] != null && percorsi[0].size() > 0){
                        createItems(llc, percorsi[0]);
                    }

                    if(s==null || new StringBuilder(s).toString().trim().isEmpty()){
                        percorsi[0] = new DBPercorso(mContext).getPercorsoByUtente(user_id);
                        createItems(llc, percorsi[0]);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        /**
         * Visualizza tutti i percorsi creati
         * da una guida o da un curatore
         */
        public void getAltriPercorsi(){
            final ArrayList<Percorso>[] percorsi = new ArrayList[]{
                    new DBPercorso(this.mContext).getPercorsoByGuidaOrCuratore()
            };

            final LinearLayout llPercorsi = view.findViewById(R.id.llPercorsi);

            createItems(llPercorsi, percorsi[0]);

            EditText etRicercaPercorsi = view.findViewById(R.id.cerca);
            etRicercaPercorsi.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    percorsi[0] = new DBPercorso(mContext).getPercorsoByGuidaOrCuratore(new StringBuilder(s).toString());

                    llPercorsi.removeAllViews();
                    if(percorsi[0] != null && percorsi[0].size() > 0){
                        createItems(llPercorsi, percorsi[0]);
                    }

                    if(s==null || new StringBuilder(s).toString().trim().isEmpty()){
                        percorsi[0] = new DBPercorso(mContext).getPercorsoByGuidaOrCuratore();

                        createItems(llPercorsi, percorsi[0]);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


        /**
         * Crea dinamenticamente i percorsi
         *
         * @param llc
         * @param percorsi
         */
        public void createItems(LinearLayout llc, ArrayList<Percorso> percorsi){
            for(Percorso p : percorsi){
                TextView nomePercorso = new TextView(getContext());
                nomePercorso.setId(View.generateViewId());
                nomePercorso.setText(p.getNome());
                nomePercorso.setTypeface(nomePercorso.getTypeface(), Typeface.BOLD);

                //Durata del percorso
                SimpleDateFormat sdf = new SimpleDateFormat("mm");
                String durata = "";
                try {
                    Date dt = sdf.parse(String.valueOf(p.getDurata()));
                    sdf = new SimpleDateFormat("HH:mm");
                    durata = sdf.format(dt);
                } catch (ParseException e) {}
                TextView tvDurata = new TextView(getContext());
                tvDurata.setId(View.generateViewId());
                tvDurata.setText(getResources().getString(R.string.durata_visita) + ": " +durata + " " + getResources().getString(R.string.ore));
                //-------------

                //Città
                TextView tvCitta = new TextView(getContext());
                tvCitta.setId(View.generateViewId());
                tvCitta.setText(new DBCitta(mContext).getCap(p.getCodice_citta()) + " - " +
                        new DBCitta(mContext).getNomeCitta(p.getCodice_citta())
                        + " (" + new DBCitta(mContext).getSiglaProvincia(p.getCodice_citta()) + ")");

                LinearLayout ll = new LinearLayout(mContext);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.leftMargin = llp.rightMargin = llp.topMargin = llp.bottomMargin = 20;
                ll.setLayoutParams(llp);
                ll.setId(View.generateViewId());
                ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                ll.setPadding(50,50,50,50);
                ll.setOrientation(LinearLayout.VERTICAL);

                ll.addView(nomePercorso);
                ll.addView(tvDurata);
                ll.addView(tvCitta);

                llc.addView(ll);

                ll.setOnClickListener(e->{
                    EseguiFragment.changeFragment( ()->{
                        bundle.putLong("codice_percorso", p.getID());
                        Fragment fragment = new CRUDVisualizzaPercorso();
                        fragment.setArguments(bundle);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                        fragmentTransaction.addToBackStack(null).commit();
                    });
                });
            }
        }

    }

    /**
     * Usato per visualizzare gli elementi della scheda
     */
    enum ModelObject {

        OTHER_ROUTE(R.string.tab_altri_percorsi, R.layout.fragment_tab_percorsi),
        MY_ROUTE(R.string.tab_i_miei_percorsi, R.layout.fragment_tab_miei_percorsi);

        private int mTitleResId;
        private int mLayoutResId;

        ModelObject(int titleResId, int layoutResId) {
            mTitleResId = titleResId;
            mLayoutResId = layoutResId;

        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }

    }


}