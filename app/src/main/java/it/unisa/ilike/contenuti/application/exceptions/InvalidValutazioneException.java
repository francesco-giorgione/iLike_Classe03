package it.unisa.ilike.contenuti.application.exceptions;

/**
 * Classe che modella un'eccezione controllata lanciata quando si indica un range di valutazione
 * media non ammissibile secondo i valori interi ammissibili [1, ..., 5] oppure quando la valutazione
 * massima indicata non è maggiore o uguale della valutazione minima indicata.
 * @version 0.1
 * @author FrancescoGiorgione
 * @see java.lang.Exception
 */
public class InvalidValutazioneException extends Exception {
    /**
     * Il metodo restituisce un'eccezione controllata che comunica che il range di valutazione
     * indicato non è ammissibile (si legga descrizione della classe per maggiori dettagli).
     */
    public InvalidValutazioneException() {
        super("Range di valutazione media non valido");
    }
}
