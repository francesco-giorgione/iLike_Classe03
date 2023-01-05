package it.unisa.ilike.account.application.exceptions;

/**
 * @author Marta
 * @version 0.1
 * @see java.lang.Exception
 */
public class EmailVuotaException extends Exception {
    /**
     * Questo costruttore consente di lanciare l'eccezione EmailVuotaException quando
     * il campo email non rispetta il limite minimo o massimo.
     */
    public EmailVuotaException(){
        super("Il campo email non rispetta il limite minimo o massimo.");
    }
}
