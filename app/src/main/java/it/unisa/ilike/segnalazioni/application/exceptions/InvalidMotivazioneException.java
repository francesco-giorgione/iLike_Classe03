package it.unisa.ilike.segnalazioni.application.exceptions;

/**
 * Questa classe estende Exception
 * @author Simona Lo Conte
 * @version 0.1
 */
public class InvalidMotivazioneException extends Exception{

    /**
     * Questo metodo serve per lanciare una nuova eccezione controllata di tipo InvalidMotivazioneException.
     * Indica che la motivazione relativa alla cancellazione della recensione da inserire contiene un numero
     * di caratteri maggiore di 300 per cui non è valida
     */
    public InvalidMotivazioneException(){

        super("La motivazione contiene un numero di caratteri inferiore ad 1");
    }
}
