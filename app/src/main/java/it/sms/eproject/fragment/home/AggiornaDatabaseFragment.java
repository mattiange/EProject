package it.sms.eproject.fragment.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import it.sms.eproject.R;
import it.sms.eproject.activity.CallbackFragment;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.fragment.home.crud.CrudZona;
import it.sms.eproject.fragment.home.crud.museo.CrudMuseo;
import it.sms.eproject.fragment.home.crud.oggetto.CrudOggetto;
import it.sms.eproject.util.Connector;
import it.sms.eproject.util.Link;

/**
 * Fragment principale del curatore
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class AggiornaDatabaseFragment extends Fragment {

    CallbackFragment callbackFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.empty_fragment, container, false);

        Update update = new Update(getActivity().getApplicationContext(), Link.UPDATE_LINK);
        update.execute();

        return view;
    }


    public class Update extends AsyncTask<Void,Void,String> {
        Context c;
        String urlAddress;
        String response;


        public Update(Context c, String urlAddress){
            this.c = c;
            this.urlAddress = urlAddress;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            return this.send();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            this.response = response;

            parseData();
        }

        private String send()
        {
            //CONNECT
            HttpURLConnection con= Connector.connect(urlAddress);

            if(con==null)
            {
                return null;
            }

            //HttpURLConnection con= Connector.connect(urlAddress);
            int responseCode= 0;
            try {
                OutputStream os = con.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.close();
                os.close();

                responseCode = con.getResponseCode();

                BufferedReader br = null;
                if (responseCode == con.HTTP_OK) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuffer response = new StringBuffer();
                    String line;

                    //READ LINE BY LINE
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    //RELEASE RES
                    br.close();

                    //this.getResponse.setResponse(response.toString());
                    return response.toString();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            return "";
        }

        private Boolean parseData() {
            try {
                JSONArray ja = new JSONArray(this.response);
                JSONObject jo;

                for (int i = 0; i < ja.length(); i++) {

                    jo = ja.getJSONObject(i);

                    try {
                        //Inserisco i nuovi musei
                        JSONArray jaMuseo = jo.getJSONArray("museo");
                        JSONObject jo1;
                        for (int j = 0; j < jaMuseo.length(); j++) {
                            jo1 = jaMuseo.getJSONObject(j);


                            Museo m = new Museo(
                                    jo1.getString("nome"),
                                    jo1.getString("numero_telefono"),
                                    jo1.getString("indirizzo"),
                                    jo1.getInt("citta"),
                                    jo1.getString("email"),
                                    jo1.getString("sito_web"),
                                    jo1.getString("orario"),
                                    //jo1.getString("immagine"),
                                    new byte[]{},
                                    Integer.parseInt(jo1.getString("durata_visita"))
                            );

                            if(new DBMuseo(getContext()).inserisciMuseo(m)){
                            }
                        }



                    }catch(JSONException jse){
                        System.out.println("ERRORE museo: " + jse.getMessage());
                    }

                    try {

                        JSONArray jaOggetti = jo.getJSONArray("oggetto");
                        JSONObject jo1;
                        for (int j = 0; j < jaOggetti.length(); j++) {
                            jo1 = jaOggetti.getJSONObject(j);

                            Oggetto o = new Oggetto(
                                    jo1.getString("nome"),
                                    jo1.getInt("anno"),
                                    jo1.getInt("autore"),
                                    jo1.getString("descrizione"),
                                    jo1.getInt("citta"),
                                    jo1.getInt("durata_visita")
                            );

                            if (new DBOggetto(getContext()).inserisciOggetto(o)) {
                                Toast.makeText(getContext(), "Oggetto: inserito: " + o.getNome(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "NO", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch(JSONException jse){
                        System.out.println("ERRORE oggetto: " + jse.getMessage());
                    }
                }

                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}