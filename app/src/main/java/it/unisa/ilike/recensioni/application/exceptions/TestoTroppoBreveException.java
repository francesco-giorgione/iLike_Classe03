package it.unisa.ilike.recensioni.application.exceptions;

/**
 * Classe che estende RuntimeException
 *  @version 0.1
 *  @author LuiginaCostante
 *  @see java.lang.RuntimeException
 */

public class TestoTroppoBreveException extends Exception{

    /**
     * Questo metodo serve per lanciare una nuova eccezione controllata di tipo TestoTroppoBreveException.
     * Specifica che il testo della recensione da creare contiene un numero di caratteri
     * minore di 3 per cui non può essere considerato valido
     */
    public TestoTroppoBreveException (){
        super ("Il testo della recensione non rispetta il numero minimo di 3 caratteri!");
    }
}
