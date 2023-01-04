package it.unisa.ilike.account.application.exceptions;

/**
 * @author Marta
 * @version 0.1
 * @see java.lang.Exception
 */
public class DatiIscrittoVuotiException extends Exception {
    /**
     * Questo costruttore consente di lanciare l'eccezione DatiIscrittoVuotiException quando
     * i campi nome, cognome, nickname non rispettano il limite minimo o massimo.
     */
    public DatiIscrittoVuotiException(){
        super("I campi nome, cognome, nickname non rispettano il limite minimo o massimo");
    }
}
