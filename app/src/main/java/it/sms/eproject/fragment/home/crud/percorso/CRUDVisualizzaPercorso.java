package it.sms.eproject.fragment.home.crud.percorso;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import java.util.Random;

import it.sms.eproject.R;
import it.sms.eproject.data.classes.OggettiMuseoHasPercorsi;
import it.sms.eproject.database.DBPercorso;

public class CRUDVisualizzaPercorso extends Fragment {
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.crudpercorso_visualizza_fragment, container,false);


         bundle = this.getArguments();
         int codice = -1;

         //Leggo il codice del percorso
         if (bundle != null) {
            codice = bundle.getInt("codice_percorso", -1);
         }

         DBPercorso dbPercorso = new DBPercorso(getContext());
        OggettiMuseoHasPercorsi percorsi_utente = dbPercorso.getElementiPercorso(codice);

         TextView titolo = v.findViewById(R.id.showPercorsoTitle);
         titolo.setText(String.format(getResources().getString(R.string.percorso_titolo), dbPercorso.get(codice)==null?"":dbPercorso.get(codice).getNome()));

         String uri = "@drawable/circle";
         int imageResource = getResources().getIdentifier(uri, null, getContext().getPackageName());

         ConstraintLayout constraintLayout = v.findViewById(R.id.ll_main);
         ImageView oldImageView = new ImageView(getContext());
         TextView oldTextViewLeft = new TextView(getContext());
         TextView oldTextViewRight = new TextView(getContext());

         //Posiziono gli oggetti
         for(int i=0; i<percorsi_utente.getOggetti().size(); i ++) {
            System.out.println(percorsi_utente.getOggetti().get(i));
            String[] textArray={"one","two","asdasasdf asdf dsdaa"};

            ImageView imageView = new ImageView(getContext());
            imageView.setId(View.generateViewId());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);

            constraintLayout.addView(imageView);


            TextView textView = new TextView(getContext());
            textView.setId(new Random().nextInt()+i);
            textView.setText(percorsi_utente.getOggetti().get(i).getNome());
            constraintLayout.addView(textView);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);

            constraintSet.setMargin(imageView.getId(), ConstraintSet.TOP, 150);
            constraintSet.setMargin(textView.getId(), ConstraintSet.TOP, 150);

            //Posiziono centralmente i cerchi
            constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(imageView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            //-----------------------

            //Posiziono il textView rispetto ai cerchi
             if(i==0){
                 // testare
                 constraintSet.connect(textView.getId(), ConstraintSet.TOP, R.id.showPercorsoTitle, ConstraintSet.BOTTOM, 100);
                 constraintSet.connect(imageView.getId(), ConstraintSet.TOP, R.id.showPercorsoTitle, ConstraintSet.BOTTOM, 100);
             }
            if(i%2==0) {
                constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                constraintSet.connect(textView.getId(), ConstraintSet.END, imageView.getId(), ConstraintSet.END);

                if(i!=0){
                    constraintSet.connect(textView.getId(), ConstraintSet.TOP, oldTextViewLeft.getId(), ConstraintSet.BOTTOM, 380);
                }

                oldTextViewLeft  = textView;
            }else{
                constraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.connect(textView.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.START);

                if(i!=1){
                    constraintSet.connect(textView.getId(), ConstraintSet.TOP, oldTextViewRight.getId(), ConstraintSet.BOTTOM, 400);
                }else{
                    constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 200);
                }

                oldTextViewRight  = textView;
            }
            //e rispetto agli altri textview

            //------------------------

            if(i > 0){
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, oldImageView.getId(), ConstraintSet.BOTTOM);
            }else {
                constraintSet.connect(imageView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            }
            constraintSet.applyTo(constraintLayout);

            oldImageView = imageView;
        }

        //Posiziono i musei
        /*for(int i=0; i<percorsi_utente.getMusei().size(); i ++) {
            System.out.println(percorsi_utente.getMusei().get(i));

            ImageView imageView = new ImageView(getContext());
            imageView.setId(View.generateViewId());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);

            constraintLayout.addView(imageView);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);

            constraintSet.setMargin(imageView.getId(), ConstraintSet.TOP, 150);

            constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            constraintSet.connect(imageView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            if(i > 0){
                constraintSet.connect(imageView.getId(), ConstraintSet.TOP, oldImageView.getId(), ConstraintSet.BOTTOM);
            }else {
                constraintSet.connect(imageView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            }
            constraintSet.applyTo(constraintLayout);

            oldImageView = imageView;
        }*/

        return v;
    }



}
