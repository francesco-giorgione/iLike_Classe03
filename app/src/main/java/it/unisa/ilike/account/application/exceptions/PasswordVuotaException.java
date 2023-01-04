package it.unisa.ilike.account.application.exceptions;

/**
 * @author Marta
 * @version 0.1
 * @see java.lang.Exception
 */
public class PasswordVuotaException extends Exception {
    /**
     * Questo costruttore consente di lanciare l'eccezione PasswordVuotaException quando
     * il campo password non rispetta il limite minimo o massimo
     */
    public PasswordVuotaException(){
        super("Il campo password non rispetta il limite minimo o massimo");
    }
}
