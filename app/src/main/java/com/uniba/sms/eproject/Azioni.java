package com.uniba.sms.eproject;

/**
 * Enumerazioni elencante le tipologie di azioni che si possono
 * eseguire nel programma.
 */
public enum Azioni {
    /**
     * Usato per gli aggiornamenti
     */
    UPDATE,
    /**
     * Usato per le creazioni
     */
    CREATE,

    //Funzioni
    /**
     * Usato per visualizzare una nuova zona
     */
    NUOVA_ZONA,
    /**
     * Usato per visualizzare le regioni
     */
    VISUALIZZA_REGIONI,
    /**
     * Usato per visualizzare le province
     */
    VISUALIZZA_PROVINCE,
    /**
     * Usato per visualizzare le zone
     */
    VISUALIZZA_ZONE,
    /**
     * Usato per visualizzare gli oggetti
     */
    VISUALIZZA_OGGETTI
}
