package it.sms.eproject.fragment.backend.crud.eventi;

import android.os.Build;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;

public class CrudElencoLuoghi extends Fragment {

    EditText cerca;
    LinearLayout llContainer;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.crudattivita_list_fragment, container,false);

        init();

        return v;

    }

    private void init(){
        this.cerca          = this.v.findViewById(R.id.cercaLuogo);
        this.llContainer    = this.v.findViewById(R.id.llContainer);

        this.cerca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                llContainer.removeAllViews();
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Museo>    museiTrovati    = new DBMuseo(getContext()).elencoMusei(cerca.getText().toString());
                ArrayList<Oggetto>  oggettiTrovati  = new DBOggetto(getContext()).elencoOggetti(cerca.getText().toString());

                if(museiTrovati != null){
                    for(Museo m : museiTrovati){
                        LinearLayout ll = new LinearLayout(getContext());
                        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        llp.topMargin   = 50;
                        llp.leftMargin  = 50;
                        llp.bottomMargin= 50;
                        llp.rightMargin = 50;
                        ll.setLayoutParams(llp);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                        }else{
                            ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners));
                        }
                        ll.setPadding(50,50,50,50);

                        TextView nome = new TextView(getContext());
                        nome.setText(m.getNome());
                        nome.setTextSize(20);
                        TextView indirizzo = new TextView(getContext());
                        indirizzo.setText(m.getIndirizzo());

                        ll.addView(nome);
                        ll.addView(indirizzo);

                        llContainer.addView(ll);

                        Bundle bundle = new Bundle();
                        bundle.putInt("luogo_codice", m.getID());
                        bundle.putString("tipo_luogo", "museo");

                        ll.setOnClickListener(v1 -> {
                            Fragment fragment = new CrudEvento_Create();
                            fragment.setArguments(bundle);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                            fragmentTransaction.addToBackStack(null).commit();
                        });

                    }

                }
                if(oggettiTrovati != null){
                    for(Oggetto o : oggettiTrovati){LinearLayout ll = new LinearLayout(getContext());
                        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        llp.topMargin   = 50;
                        llp.leftMargin  = 50;
                        llp.bottomMargin= 50;
                        llp.rightMargin = 50;
                        ll.setLayoutParams(llp);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ll.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                        }else{
                            ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners));
                        }
                        ll.setPadding(50,50,50,50);

                        TextView nome = new TextView(getContext());
                        nome.setText(o.getNome());
                        nome.setTextSize(20);
                        TextView indirizzo = new TextView(getContext());
                        indirizzo.setText(o.getIndirizzo());

                        ll.addView(nome);
                        ll.addView(indirizzo);

                        llContainer.addView(ll);


                        Bundle bundle = new Bundle();
                        bundle.putInt("luogo_codice", o.getId());
                        bundle.putString("tipo_luogo", "oggetto");

                        /*Log.d("CITTAAAA", String.valueOf(o.getCodice_citta()));
                        Log.d("NOMEEEE", o.getNome());
                        Log.d("OGGETTO", o.toString());*/

                        ll.setOnClickListener(v1 -> {
                            Fragment fragment = new CrudEvento_Create();
                            fragment.setArguments(bundle);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                            fragmentTransaction.addToBackStack(null).commit();
                        });
                    }

                }
            }
        });
    }

    public void visualizzaFragment(CallbackFragment callbackFragment){
        callbackFragment.changeFragment();
    }

}
