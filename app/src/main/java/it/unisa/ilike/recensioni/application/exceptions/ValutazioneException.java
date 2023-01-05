package it.unisa.ilike.recensioni.application.exceptions;

/**
 * Classe che estende RuntimeException
 *  @version 0.1
 *  @author LuiginaCostante
 *  @see java.lang.RuntimeException
 */

public class ValutazioneException extends Exception{

    /**
     * Questo metodo serve per lanciare una nuova eccezione controllata di tipo ValutazioneException.
     * Specifica che la valutazione relativa alla recensione da creare non è compreso tra 1 e 5, per cui
     * non può essere considerato valido
     */

    public ValutazioneException(){
        super("La valutazione inserita non è valida");
    }
}
