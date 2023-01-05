package it.unisa.ilike.liste.application.exceptions;

/**
 * Classe che estende Exception
 *  @version 0.1
 *  @author FrancescoGiorgione
 *  @see java.lang.Exception
 */
public class InvalidNomeException extends Exception {
    /**
     * Il metodo restituisce un'eccezione controllata che comunica che il nome scelto dall'iscritto
     * per la sua nuova lista contiene un numero di caratteri maggiore di quello consentito.
     */
    public InvalidNomeException() {
        super("Il nome della lista non può contenere più di 50 caratteri");
    }
}
