package it.unisa.ilike.utils;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.UtenteBean;

public class Utils {
    // manca reale implementazione
    public static Boolean isIscritto(UtenteBean utente) {
        return true;
    }

    // manca reale implementazione
    public static Boolean isGestore(UtenteBean utente) {
        return true;
    }

    // manca reale implementazione
    public static Boolean hasLista(IscrittoBean i, String nome) {
        return false;
    }

    /**
     * Inserisce nella stringa di input un carattere di escape per ogni apice singolo e doppio.
     * @param str è la stringa in cui inserire i caratteri di escape.
     * @return la stringa di input con un carattere di escape immediatamente prima di ogni apice
     * singolo o doppio.
     */
    public static String addEscape(String str) {
        String newStr = str.replace("'", "\\'");
        return newStr.replace("\"", "\\\"");
    }
}
