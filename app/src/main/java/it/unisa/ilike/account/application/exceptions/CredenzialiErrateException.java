package it.unisa.ilike.account.application.exceptions;

/**
 * @author Marta
 * @version 0.1
 * @see java.lang.Exception
 */
public class CredenzialiErrateException extends Exception {
    /**
     * Questo costruttore consente di lanciare l'eccezione CredenzialiErrateException
     * quando l’email e/o la password sono errate, dunque non è possibile individuare l’attore.
     */
    public CredenzialiErrateException(){
        super("L’email e/o la password sono errate e non è possibile individuare l’attore");
    }
}
