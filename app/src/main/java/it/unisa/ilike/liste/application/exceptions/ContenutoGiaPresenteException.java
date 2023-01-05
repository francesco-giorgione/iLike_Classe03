package it.unisa.ilike.liste.application.exceptions;

/**
 * Classe che estende Exception
 *  @version 0.1
 *  @author FrancescoGiorgione
 *  @see java.lang.Exception
 */
public class ContenutoGiaPresenteException extends Exception {
    /**
     * Il metodo restituisce un'eccezione controllata che comunica che il contenuto C che si
     * vuole inserire nella lista L è in realtà già presente in L.
     */
    public ContenutoGiaPresenteException() {
        super("Il contenuto che si vuole inserire è già presente nella lista selezionata");
    }
}
