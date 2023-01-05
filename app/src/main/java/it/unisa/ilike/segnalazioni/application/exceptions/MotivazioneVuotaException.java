package it.unisa.ilike.segnalazioni.application.exceptions;

/**
 * Questa classe estende Exception
 * @author Simona Lo Conte
 * @version 0.1
 */
public class MotivazioneVuotaException extends Exception{

    /**
     * Questo metodo serve per lanciare una nuova eccezione controllata di tipo MotivazioneVuotaException.
     * Indica che la motivazione relativa alla cancellazione della recensione da inserire contiene un numero
     * di caratteri inferiore a 1 per cui è vuota e non può essere considerata come valida
     */
    public MotivazioneVuotaException(){
        super("La motivazione contiene un numero di caratteri inferiore a 1");
    }
}
