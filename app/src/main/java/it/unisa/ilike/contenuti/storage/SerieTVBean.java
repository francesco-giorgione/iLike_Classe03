package it.unisa.ilike.contenuti.storage;

import java.io.Serializable;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi alle serie TV
 * @author Simona Lo Conte
 * @version 0.1
 */
public class SerieTVBean extends ContenutoBean  implements Serializable {

    /**
     * Costruttore senza parametri
     */
    public SerieTVBean() {
    }

    /**
     * Costruttore con parametri
     * @param id è l'id della serie tv.
     * @param titolo è il titolo della serie tv.
     * @param descrizione è la descrizione della serie tv.
     * @param categoria è la categoria della serie tv.
     * @param annoRilascio è l'anno di rilascio della serie tv.
     * @param numStagioni è il numero di stagioni della serie tv.
     */
    public SerieTVBean(int id, String titolo, String descrizione, String categoria, double valutazioneMedia, int annoRilascio, String numStagioni) {
        super(id, titolo, descrizione, categoria, valutazioneMedia);
        this.annoRilascio = annoRilascio;
        this.numStagioni = numStagioni;
    }

    /**
     * Questo metodo restituisce l'anno di rilascio della serie TV
     * @return anno di rilascio della serie TV
     */
    public int getAnnoRilascio() {
        return annoRilascio;
    }

    /**
     * Questo metodo imposta l'anno di rilascio della serie TV
     * @param annoRilascio
     */
    public void setAnnoRilascio(int annoRilascio) {
        this.annoRilascio = annoRilascio;
    }

    /**
     * Questo metodo restituisce il numero di stagioni della serie TV
     * @return numero di stagioni della serie TV
     */
    public String getNumStagioni() {
        return numStagioni;
    }

    /**
     * Questo metodo imposta il numero di stagioni della serie TV
     * @param numStagioni
     */
    public void setNumStagioni(String numStagioni) {
        this.numStagioni = numStagioni;
    }

    private int annoRilascio;
    private String numStagioni;
}
