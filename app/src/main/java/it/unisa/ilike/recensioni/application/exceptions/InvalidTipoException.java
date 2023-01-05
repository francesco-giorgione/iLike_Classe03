package it.unisa.ilike.recensioni.application.exceptions;

/**
 * Classe che estende Exception
 *  @version 0.1
 *  @author LuiginaCostante
 *  @see java.lang.Exception
 */

public class InvalidTipoException extends Exception{

    /**
     * Questo metodo serve per lanciare una nuova eccezione controllata di tipo InvalidTipoException.
     * Specifica che il tipo della segnalazione relativo alla recensione non è valido in quanto
     * il suo valore non corrisponde nè a zero (Spoiler Alert), nè ad uno (Altre Segnalazioni).
     */

    public InvalidTipoException (){
        super("Il valore inserito non è valido!\n Inserire 0 per Spoiler Alert o 1 per Altre Segnalazioni.");
    }

}
