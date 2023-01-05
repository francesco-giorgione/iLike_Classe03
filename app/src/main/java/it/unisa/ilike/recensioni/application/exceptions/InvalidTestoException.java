package it.unisa.ilike.recensioni.application.exceptions;

/**
 * Classe che estende RuntimeException
 *  @version 0.1
 *  @author LuiginaCostante
 *  @see java.lang.RuntimeException
 */

public class InvalidTestoException extends Exception{

    /**
     * Questo metodo serve per lanciare una nuova eccezione controllata di tipo InvalidTestoException.
     * Specifica che il testo della recensione da creare contiene un numero di caratteri
     * maggiore di 1000 per cui non può essere considerato valido
     */
    public InvalidTestoException(){
        super ("Il testo della recensione non rispetta il numero massimo di 1000 caratteri!");
    }
}
