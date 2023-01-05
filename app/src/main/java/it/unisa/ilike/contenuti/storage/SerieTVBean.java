package it.unisa.ilike.contenuti.storage;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi alle serie TV
 * @author Simona Lo Conte
 * @version 0.1
 */
public class SerieTVBean extends ContenutoBean{

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
    public SerieTVBean(int id, String titolo, String descrizione, String categoria, String annoRilascio, int numStagioni) {
        super(id, titolo, descrizione, categoria);
        this.annoRilascio = annoRilascio;
        this.numStagioni = numStagioni;
    }

    /**
     * Questo metodo restituisce l'anno di rilascio della serie TV
     * @return anno di rilascio della serie TV
     */
    public String getAnnoRilascio() {
        return annoRilascio;
    }

    /**
     * Questo metodo imposta l'anno di rilascio della serie TV
     * @param annoRilascio
     */
    public void setAnnoRilascio(String annoRilascio) {
        this.annoRilascio = annoRilascio;
    }

    /**
     * Questo metodo restituisce il numero di stagioni della serie TV
     * @return numero di stagioni della serie TV
     */
    public int getNumStagioni() {
        return numStagioni;
    }

    /**
     * Questo metodo imposta il numero di stagioni della serie TV
     * @param numStagioni
     */
    public void setNumStagioni(int numStagioni) {
        this.numStagioni = numStagioni;
    }

    private String annoRilascio;
    private int numStagioni;
}
