package it.unisa.ilike.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.account.storage.UtenteBean;
import it.unisa.ilike.liste.storage.ListaBean;

public class Utils {
    // manca reale implementazione
    public static Boolean isIscritto(UtenteBean utente) {
        return true;
    }

    // manca reale implementazione
    public static Boolean isGestore(UtenteBean utente) {
        return true;
    }


    /**
     * Il metodo restituisce true se l'iscritto dato ha già una lista di nome 'nome', false altrimenti.
     * Il controllo eseguito NON è case-sensitive.
     * @param i è l'iscritto sulle cui liste si vuole eseguire la verifica.
     * @param nome è il nome che si vuole verificare.
     * @return true se l'iscritto ha già una lista di nome 'nome', false altrimenti.
     */
    public static Boolean hasLista(IscrittoBean i, String nome) {
        for(ListaBean curr: new IscrittoDAO().doRetrieveListe(i.getEmail())) {
            if(curr.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
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

    /**
     * Restituisce la data di input in formato dd/mm/aa.
     * @param data è l'oggetto Date da convertire in stringa.
     * @return una stringa contenente la data in formato dd/mm/aa.
     */
    public static String getStringaData(Date data) {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY).format(data);
    }


    /**
     * Restituisce la data di input in formato aaaa/mm/dd.
     * @param data è l'oggetto Date da convertire in stringa.
     * @return una stringa contenente la data in formato aaaa/mm/dd.
     */
    public static String getStringaDataForSql(Date data) {
        String[] tmp = getStringaData(data).split("/");
        return "20" + tmp[2] + "-" + tmp[1] + "-" + tmp[0];
    }


    /**
     * A partire da una data in formato sql, restituisce un oggetto Date.
     * @param data in formato sql.
     * @return oggetto Date corrispondente alla data ricevuta.
     */
    public static Date getBeanData(String data) {
        String[] tmp = data.split("-");
        return new Date(Integer.parseInt(tmp[0]) - 1900, Integer.parseInt(tmp[1]) - 1, Integer.parseInt(tmp[2]));
    }
}
