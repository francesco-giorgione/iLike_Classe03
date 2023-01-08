package it.unisa.ilike.contenuti.application;

import it.unisa.ilike.contenuti.application.ContenutoBean;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi alle serie TV
 * @author Simona Lo Conte
 * @version 0.1
 */
public class SerieTVBean extends ContenutoBean {

    /**
     * Costruttore senza parametri
     */
    public SerieTVBean() {
    }

    /**
     * Costruttore con parametri
     * @param id
     * @param titolo
     * @param descrizione
     * @param categoria
     * @param annoRilascio
     * @param numStagioni
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
