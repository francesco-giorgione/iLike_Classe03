package it.unisa.ilike.recensioni.application.exceptions;

/**
 * Classe che estende RuntimeException
 *  @version 0.1
 *  @author LuiginaCostante
 *  @see java.lang.RuntimeException
 */

public class InvalidMotivazioneException extends RuntimeException{

    /**
     * Questo metodo serve per lanciare una nuova eccezione di tipo InvalidMotivazioneException.
     * Specifica che la motivazione relativa alla segnalazione da inserire contiene un numero di caratteri
     * maggiore di 500 per cui non può essere considerata valida
     */
    public InvalidMotivazioneException(){
        super ("La motivazione contiene un numero di caratteri maggiore di 500");
    }
}
