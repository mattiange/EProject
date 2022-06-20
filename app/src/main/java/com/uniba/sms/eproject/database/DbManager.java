package com.uniba.sms.eproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.uniba.sms.eproject.annotazioni.Autore;

import java.util.HashMap;

public class DbManager {
    MyHelper helper=null;
    private final static String DATABASE="ProgettoSMS.db";
    private final static int VERSIONE_DATABASE=1;

    private Context context;

    public DbManager(Context context)      {
        helper=new MyHelper(context, DATABASE,  null, VERSIONE_DATABASE);
        this.context = context;
    }

    /**
     * Visualizza tutti gli utenti registrati
     *
     * @return
     */
    @Autore(autore = "Mattia, Giandomenico")
    public Cursor elencoUtenti()      {
        String query="SELECT * FROM Utente_Registrato";
        SQLiteDatabase db= helper.getReadableDatabase();

        return db.rawQuery(query, null);
    }

    /**
     * Registra un nuovo utente
     *
     * @param nome
     * @param cognome
     * @param username
     * @param email
     * @param password
     * @param tipo
     * @return
     */
    @Autore(autore = "Mattia, Giandomenico")
    public boolean registrazione(String nome, String cognome, String username, String email, String password, int tipo){
        String insert1="INSERT INTO Utente_Registrato (ID, Nome, Cognome, Username, Email, Password, tipo) "
                + "VALUES (NULL,'"+nome+"','"+cognome+"', '"+username+"', '"+email+"', '"+password+"', "+tipo+")";
        SQLiteDatabase db= helper.getWritableDatabase();

        try{
            db.execSQL(insert1);

            return true;
        }catch(SQLException ex){
            return false;
        }
    }

    /**
     * Effettua il login di un utente
     *
     * @param username  Nome utente
     * @param password  Password di login
     * @return HashMap<String, String>
     *     Restituisce un HashMap dove la chiave è il nome della colonna della tabella Utente_Registrato
     *     mentre il valore è il valore associato a quella colonna
     *
     *     ESEMPIO:
     *          ID => 1
     *          Nome => Mario
     *          Cognome => Rossi
     *          Email => mariorossi@email.com
     *          ....
     */
    @Autore(autore = "Mattia, Giandomenico")
    public HashMap<String, String> login(String username, String password){
        String query="SELECT * FROM Utente_Registrato WHERE email = '"+username+"' AND password = '"+password+"';";
        SQLiteDatabase db= helper.getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        HashMap<String, String> utente = null;

        if (c.moveToFirst()){
            utente = new HashMap<>();

            do {

                utente.put("ID", c.getString(0));
                utente.put("Nome", c.getString(1));
                utente.put("Cognome", c.getString(2));
                utente.put("Username", c.getString(3));
                utente.put("Email", c.getString(4));
                utente.put("tipo", c.getString(6));

            } while(c.moveToNext());
        }

        c.close();

        return utente;
    }
}
