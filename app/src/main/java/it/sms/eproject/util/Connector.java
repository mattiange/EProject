package it.sms.eproject.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import it.sms.eproject.annotazioni.AutoreCodice;

/**
 * Classe utilizzata per connettersi ad un server mediante URL
 */
@AutoreCodice(autore = "Mattia Leonardo Angelillo")
public class Connector {

    public static HttpURLConnection connect(String urlAddress) {

        try
        {
            URL url=new URL(urlAddress);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            //SET PROPERTIES
            con.setRequestMethod("POST");
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setDoInput(true);
            con.setDoOutput(true);

            //RETURN
            return con;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}