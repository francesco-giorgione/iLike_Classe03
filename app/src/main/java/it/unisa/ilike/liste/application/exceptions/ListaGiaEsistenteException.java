package it.unisa.ilike.liste.application.exceptions;

/**
 * Classe che estende Exception
 *  @version 0.1
 *  @author FrancescoGiorgione
 *  @see java.lang.Exception
 */
public class ListaGiaEsistenteException extends Exception {
    /**
     * Il metodo restituisce un'eccezione controllata che comunica che il nome scelto dall'iscritto
     * per la sua nuova lista è già usato dallo stesso iscritto per un'altra lista già esistente.
     */
    public ListaGiaEsistenteException() {
        super("Il nome della lista è già in uso da parte dello stesso iscritto");
    }
}
