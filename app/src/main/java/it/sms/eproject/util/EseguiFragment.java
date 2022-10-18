package it.sms.eproject.util;

import it.sms.eproject.activity.CallbackFragment;

/**
 * Classe per eseguire un nuovo fragment
 */
public final class EseguiFragment {
    /**
     * Avvia il nuovo fragment
     *
     * @param callbackFragment Codice esecutivo
     */
    public static void changeFragment(CallbackFragment callbackFragment) {
        callbackFragment.changeFragment();
    }
}
