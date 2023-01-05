package it.unisa.ilike.recensioni.application.exceptions;

/**
 * Classe che estende Exception
 *  @version 0.1
 *  @author LuiginaCostante
 *  @see java.lang.Exception
 */

public class MotivazioneVuotaException extends Exception{

    /**
     * Questo metodo serve per lanciare una nuova eccezione controllata di tipo MotivazioneVuotaException.
     * Specifica che la motivazione relativa alla segnalazione da inserire contiene un numero di caratteri
     * inferiore ad 1 per cui non può essere considerata valida
     */
    public MotivazioneVuotaException(){
        super("La motivazione contiene un numero di caratteri inferiore ad 1");
    }
}
