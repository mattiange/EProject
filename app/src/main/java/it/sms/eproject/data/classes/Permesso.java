package it.sms.eproject.data.classes;

public class Permesso {

    public static final int CURATORE = 1;
    public static final int GUIDA = 2;
    public static final int VISITATORE = 3;

    int codice;
    String permesso;

    /**
     *
     * @param codice Codice del permesso
     * @param permesso Descrizione del permesso
     */
    public Permesso(int codice, String permesso){
        this.codice     = codice;
        this.permesso   = permesso;
    }

    /**
     * Usato per registrare il permesso dell'utente
     *
     * @param codice Codice del permesso
     */
    public Permesso(int codice){
        this(codice, "");
    }

    /**
     * Modifica il codice del permesso
     * @param codice Codice del permesso
     */
    public void setCodice(int codice) {
        this.codice = codice;
    }

    /**
     * Modifica la descrizione del permesso
     * @param permesso Descrizione del permesso
     */
    public void setPermesso(String permesso) {
        this.permesso = permesso;
    }

    /**
     * Restituisce il codice del permesso
     * @return Codice del permesso
     */
    public int getCodice() {
        return codice;
    }

    /**
     * Restituisce la descrizione del permesso
     * @return Descrizione del permesso
     */
    public String getPermesso() {
        return permesso;
    }

    /**
     * Creazione di un nuovo permesso
     *
     * @param codice
     * @param permesso
     * @return
     */
    public static Permesso of(int codice, String permesso){
        return new Permesso(codice, permesso);
    }
}
