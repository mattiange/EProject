package it.sms.eproject.fragment.backend.crud.eventi;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

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
 * Elenco degli eventi presenti per un percorso
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CrudElencoEventi extends Fragment {

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

        ((TextView)this.v.findViewById(R.id.titolo)).setText(getResources().getString(R.string.crud_list_event));

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
                ArrayList<Attivita> attivitaTrovate = new DBAttivita(getContext()).getAttivita(cerca.getText().toString());

                if(attivitaTrovate != null){
                    for(Attivita a : attivitaTrovate){
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
                        nome.setText(a.getNome());
                        nome.setTextSize(20);

                        ll.addView(nome);

                        llContainer.addView(ll);

                        ll.setOnClickListener(v1 -> {
                            Bundle bundle = new Bundle();
                            bundle.putLong("codice_attivita", a.getCodice());

                            Fragment fragment = new CrudEventoShow();
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
