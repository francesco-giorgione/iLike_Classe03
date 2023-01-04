package it.unisa.ilike.account.application.exceptions;

/**
 * @author Marta
 * @version 0.1
 * @see java.lang.Exception
 */
public class CredenzialiVuoteException extends Exception {
    /**
     * Questo costruttore consente di lanciare l'eccezione CredenzialiVuoteException
     * quando l’email e/o la password hanno un numero di caratteri minore di 1.
     */
    public CredenzialiVuoteException(){
        super("L’email e/o la password hanno un numero di caratteri minore di 1");
    }
}
