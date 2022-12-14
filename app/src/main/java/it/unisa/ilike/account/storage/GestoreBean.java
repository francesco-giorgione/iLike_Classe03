package it.unisa.ilike.account.storage;

import java.io.Serializable;

/**
 * Questa classe rappresenta il gestore di iLike.
 * @author Marta
 * @version 0.1
 */
public class GestoreBean extends UtenteBean implements Serializable {

    /**
     * Questo metodo crea un oggetto GestoreBean
     * @param email rappresenta il testo contenente l'email del gestore
     * @param password rappresenta il testo contenente la password del gestore
     * @param numSegnalazioniGestite rappresenta il numero di segnalazioni gestite
     */
    public GestoreBean(String email, String password, int numSegnalazioniGestite) {
        super(email, password);
        this.numSegnalazioniGestite = numSegnalazioniGestite;
    }

    /**
     * Questo metodo permette di accedere al numero di segnalazioni gestite
     * @return il numero di segnalazioni gestite
     */
    public int getNumSegnalazioniGestite() {
        return numSegnalazioniGestite;
    }

    /**
     * Questo metodo permette di incrementare il numero di segnalazioni gestite
     */
    public void incrementaNumSegnalazioniGestite(){
        this.numSegnalazioniGestite++;
    }

    private int numSegnalazioniGestite;
}
