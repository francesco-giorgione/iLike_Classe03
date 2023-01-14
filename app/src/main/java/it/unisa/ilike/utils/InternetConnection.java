package it.unisa.ilike.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Questa classe contient un unico metodo e serve per verificare la presenza di connessione ad Internet.
 * Viene richiamata in tutte le activity al fine di soddisfare il requisito non funzionale relativo ai
 * tempi di risposta minimi da rispettare anche in assenza di Internet sul dispositivo mobile.
 * @author LuiginaCostante
 * @version 0.1
 */
public class InternetConnection {

    /**
     * Questo metodo serve a verificare la presenza di connessione ad Internet sul dispositivo mobile.
     * @param contesto contesto dell'applicazione
     * @return boolean che descrive lo stato della connessione ad internet
     */

    public static boolean haveInternetConnection(Context contesto) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) contesto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
