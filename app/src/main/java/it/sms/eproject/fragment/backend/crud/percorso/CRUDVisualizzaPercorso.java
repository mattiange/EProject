package it.sms.eproject.fragment.backend.crud.percorso;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import it.sms.eproject.R;
import it.sms.eproject.annotazioni.AutoreCodice;
import it.sms.eproject.data.classes.Museo;
import it.sms.eproject.data.classes.OggettiMuseoHasPercorsi;
import it.sms.eproject.data.classes.Oggetto;
import it.sms.eproject.database.DBCitta;
import it.sms.eproject.database.DBMuseo;
import it.sms.eproject.database.DBOggetto;
import it.sms.eproject.database.DBPercorso;
import it.sms.eproject.fragment.backend.crud.eventi.CrudElencoEventi;
import it.sms.eproject.fragment.backend.crud.eventi.CrudElencoEventiByLuogo;
import it.sms.eproject.fragment.backend.crud.liste.ListaPercorso;
import it.sms.eproject.maps.DirectionsJSONParser;
import it.sms.eproject.util.EseguiFragment;

/**
 * Visualizza un percorso
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class CRUDVisualizzaPercorso extends Fragment implements OnMapReadyCallback {
    private Bundle bundle;
    /**
     * Codice del percorso
     */
    long codice;

    boolean backpressedlistener;

    DBPercorso dbPercorso;

    /*================= INFORMAZIONI SULLA MAPPA ==================*/
    /**
     * Mappa di Google
     */
    private GoogleMap mMap;
    /**
     * Punto di origine
     */
    private LatLng mOrigin;
    /**
     * Punto di fine
     */
    private LatLng mDestination;
    /**
     * Linea da tracciare
     */
    private Polyline mPolyline;

    ArrayList<LatLng> mMarkerPoints;
    /*=============================================================*/

    ArrayList<Museo> musei;
    ArrayList<Oggetto> oggetti;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maps, container,false);


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
        OggettiMuseoHasPercorsi percorsi_utente = dbPercorso.getElementiPercorso(codice);
        musei   = percorsi_utente.getMusei();
        oggetti = percorsi_utente.getOggetti();
        //---------------------------------------------------------------------------------

        this.bundle.putString("codice_citta", String.valueOf(dbPercorso.getCodiceCitta(codice)));
        this.bundle.putString("nome_citta", dbPercorso.getNomeCitta(codice));

        //Creo il json
        //String items = Util.getJsonString(oggetti, musei);
        //--------------------------------------------



        //Ottiene un oggetto di SupportMapFragment
        //e ricevi una notifica quando la mappa è pronta per essere utilizzata.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                                                                            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView titolo = v.findViewById(R.id.showPercorsoTitle);
        titolo.setText(String.format(getResources().getString(R.string.percorso_titolo), dbPercorso.get(codice)==null?"":dbPercorso.get(codice).getNome()));


        FloatingActionButton updateBtn = v.findViewById(R.id.update);
        FloatingActionButton deleteBtn = v.findViewById(R.id.delete);
        FloatingActionButton accettaBtn = v.findViewById(R.id.accetta);
        FloatingActionButton rifiutaBtn = v.findViewById(R.id.rifiuta);
        FloatingActionButton attivitaBtn = v.findViewById(R.id.eventi);
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
        attivitaBtn.setOnClickListener(this::getAttivita);
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

    public void getAttivita(View v){
        Bundle bundle1 = new Bundle();
        bundle1.putLong("codice_percorso", this.codice);

        Fragment fragment = new CrudElencoEventiByLuogo();
        fragment.setArguments(bundle1);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null).commit();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.crud_conferma_elimina_title_percorso))
                .setMessage(getResources().getString(R.string.crud_conferma_elimina_percorso))
                .setPositiveButton("Si", (dialog, which) -> {
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
                })
                .setNegativeButton("No", (dialog, which) -> {
                })
                .show();
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if(this.musei.size()>0) routeMusei();

        mOrigin = mDestination;

        if(this.oggetti.size()>0) routeOggetti();

    }

    private void routeOggetti(){
        try{
            //Aggiungo i percorsi per gli oggetti
            int i = 0;
            String nomeCitta        = new DBCitta(getContext()).getNomeCitta(oggetti.get(i).getCodice_citta());
            String capCitta         = new DBCitta(getContext()).getCap(oggetti.get(i).getCodice_citta());
            String siglaProvincia   = new DBCitta(getContext()).getSiglaProvincia(oggetti.get(i).getCodice_citta());
            String indirizzo        = oggetti.get(i).getCodice_citta() + ", " + capCitta + " " + nomeCitta + " " + siglaProvincia;
            mDestination = getLocationFromAddress(getContext(), indirizzo);

            drawRoute();

            Drawable circleDrawable = getResources().getDrawable(R.drawable.object_marjer);
            BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
            MarkerOptions options = new MarkerOptions();
            options.position(mOrigin).icon(markerIcon);


            circleDrawable = getResources().getDrawable(R.drawable.object_marjer);
            markerIcon = getMarkerIconFromDrawable(circleDrawable);
            options = new MarkerOptions();
            options.position(mOrigin).icon(markerIcon);
            //mMap.addMarker(options).setIcon(markerIcon);
            try {
                mMap.addMarker(options).setTag("O" + oggetti.get(i).getId());
            }catch (IndexOutOfBoundsException e){}


            for(i = 1; i<this.oggetti.size(); i ++){

                nomeCitta        = new DBCitta(getContext()).getNomeCitta(oggetti.get(i).getCodice_citta());
                capCitta         = new DBCitta(getContext()).getCap(oggetti.get(i).getCodice_citta());
                siglaProvincia   = new DBCitta(getContext()).getSiglaProvincia(oggetti.get(i).getCodice_citta());
                indirizzo        = oggetti.get(i).getIndirizzo() + ", " + capCitta + " " + nomeCitta + " " + siglaProvincia;

                mDestination = getLocationFromAddress(getContext(), indirizzo);

                options = new MarkerOptions();
                options.position(mOrigin).icon(markerIcon);
                mMap.addMarker(options).setTag("O"+oggetti.get(i).getId());

                options = new MarkerOptions();
                options.position(mDestination).icon(markerIcon);
                mMap.addMarker(options).setTag("O"+oggetti.get(i).getId());

                drawRoute();

                mOrigin = mDestination;
            }

        }catch (NullPointerException e){}
    }

    /**
     * Aggiunge i percorsi per i musei
     */
    private void routeMusei(){
        try {
            //Aggiungo i percorsi per i musei
            int i = 0;
            String nomeCitta = new DBCitta(getContext()).getNomeCitta(musei.get(i).getCitta());
            String capCitta = new DBCitta(getContext()).getCap(musei.get(i).getCitta());
            String siglaProvincia = new DBCitta(getContext()).getSiglaProvincia(musei.get(i).getCitta());
            String indirizzo = musei.get(i).getIndirizzo() + ", " + capCitta + " " + nomeCitta + " " + siglaProvincia;

            mOrigin = getLocationFromAddress(getContext(), indirizzo);

            try {

                i++;
                nomeCitta = new DBCitta(getContext()).getNomeCitta(musei.get(i).getCitta());
                capCitta = new DBCitta(getContext()).getCap(musei.get(i).getCitta());
                siglaProvincia = new DBCitta(getContext()).getSiglaProvincia(musei.get(i).getCitta());
                indirizzo = musei.get(i).getIndirizzo() + ", " + capCitta + " " + nomeCitta + " " + siglaProvincia;
                mDestination = getLocationFromAddress(getContext(), indirizzo);

                drawRoute();
            } catch (IndexOutOfBoundsException e) {}


            Drawable circleDrawable = getResources().getDrawable(R.drawable.museo_marker);
            BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
            MarkerOptions options = new MarkerOptions();
            options.position(mOrigin).icon(markerIcon);
            //mMap.addMarker(options).setIcon(markerIcon);
            try {
                mMap.addMarker(options).setTag("M" + musei.get(i).getID());
            }catch (IndexOutOfBoundsException e){}


            try {
                options = new MarkerOptions();
                options.position(mDestination).icon(markerIcon);
                mMap.addMarker(options).setTag("M"+musei.get(i).getID());
            } catch (IllegalArgumentException e) {
            }

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mOrigin, 20));


            for (i = 1; i < this.musei.size(); i++) {

                nomeCitta = new DBCitta(getContext()).getNomeCitta(musei.get(i).getCitta());
                capCitta = new DBCitta(getContext()).getCap(musei.get(i).getCitta());
                siglaProvincia = new DBCitta(getContext()).getSiglaProvincia(musei.get(i).getCitta());
                indirizzo = musei.get(i).getIndirizzo() + ", " + capCitta + " " + nomeCitta + " " + siglaProvincia;

                mDestination = getLocationFromAddress(getContext(), indirizzo);

                options = new MarkerOptions();
                options.position(mOrigin).icon(markerIcon);
                mMap.addMarker(options).setTag("M"+musei.get(i).getID());

                options = new MarkerOptions();
                options.position(mDestination).icon(markerIcon);
                mMap.addMarker(options).setTag("M"+musei.get(i).getID());

                drawRoute();

                mOrigin = mDestination;
            }

            mMap.setOnMarkerClickListener(e->{

                getMarkerInfo(e.getTag());

                return true;
            });
        }catch (NullPointerException e){}
    }

    /**
     * Visualizza le informazioni in
     * base al marker cliccato
     *
     * @param tag
     */
    private void getMarkerInfo(Object tag){
        String t = (String)tag;
        long id = Long.valueOf(t.substring(1));
        String type = t.substring(0, 1);

        Log.d("TAG Marker", t);

        if(type.equals("M")){
            getMarkerInfoMuseo(id);
        }else{
            getMarkerInfoOggetto(id);
        }


    }

    /**
     * Restituisce le informazioni sul museo selezionato
     * @param id
     */
    private void getMarkerInfoMuseo(long id){
        Museo m = new DBMuseo(getContext()).getMuseo(id);

        DialogInfo info = new DialogInfo(m.getNome(), m.getIndirizzo());
        info.show();
    }

    /**
     * Restituisce le informazioni sull'oggetto selezionato
     * @param id
     */
    private void getMarkerInfoOggetto(long id){
        Oggetto m = new DBOggetto(getContext()).getOggetto(id);

        DialogInfo info = new DialogInfo(m.getNome(), m.getIndirizzo());
        info.show();
    }

    /**
     * Visualizza il Dialog con le informazioni
     * del luogo selezionato
     */
    private class DialogInfo{
        String titolo;
        String indirizzo;

        public DialogInfo(String titolo, String indirizzo){
            this.titolo     = titolo;
            this.indirizzo  = indirizzo;
        }

        public void show(){
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_marker);

            TextView title = dialog.findViewById(R.id.title_dialog);
            TextView indirizzo = dialog.findViewById(R.id.address_dialog);
            title.setText(this.titolo);
            indirizzo.setText(this.indirizzo);

            dialog.show();
        }
    }

    /**
     * Crea un'immagine BitmapDescriptor
     *
     * @param drawable
     * @return
     */
    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private void drawRoute(){

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }


    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception on download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                int[] color = {
                        Color.RED,
                        Color.GREEN,
                        Color.YELLOW,
                        Color.BLUE,
                        Color.MAGENTA,
                        Color.CYAN,
                        Color.DKGRAY
                };
                Random r = new Random();
                lineOptions.addAll(points);
                lineOptions.width(20);
                lineOptions.color(color[r.nextInt(color.length)]);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mPolyline = mMap.addPolyline(lineOptions);

            }else
                Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
        }
    }
}
