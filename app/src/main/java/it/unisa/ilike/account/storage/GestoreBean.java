package it.unisa.ilike.account.storage;

/**
 * Questa classe rappresenta il gestore di iLike.
 * @author Marta
 * @version 0.1
 */
public class GestoreBean extends UtenteBean {

    /**
     * Questo metodo crea un oggetto GestoreBean
     * @param id rappresenta l'id a cui è associato il gestore
     * @param email rappresenta il testo contenente l'email del gestore
     * @param password rappresenta il testo contenente la password del gestore
     * @param numSegnalazioniGestite rappresenta il numero di segnalazioni gestite
     */
    public GestoreBean(int id, String email, String password, int numSegnalazioniGestite) {
        super(id, email, password);
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
