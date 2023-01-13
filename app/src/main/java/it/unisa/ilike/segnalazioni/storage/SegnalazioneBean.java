package it.unisa.ilike.segnalazioni.storage;

import java.io.Serializable;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi alle segnalazioni delle recensioni
 * @author Simona Lo Conte
 * @version 0.1
 */
public class SegnalazioneBean  implements Serializable {

    /**
     * Costruttore senza parametri
     */
    public SegnalazioneBean() {
    }

    /**
     * Costruttore con parametri
     * @param id è l'id della segnalazione.
     * @param tipo è il tipo della segnalazione (0 per altre segnalazioni, 1 per spoiler alert).
     * @param motivazione è la motivazione della segnalazione.
     * @param gestita indica se la segnalazione è già stata gestita da un gestore.
     * @param iscritto è l'iscritto che ha inviato la segnalazione.
     * @param recensione è la recensione cui è riferita la segnalazione.
     */
    public SegnalazioneBean(int id, int tipo, String motivazione, Boolean gestita,
                            IscrittoBean iscritto, RecensioneBean recensione) {
        this.id = id;
        this.tipo = tipo;
        this.motivazione = motivazione;
        this.gestita = gestita;
        this.iscritto = iscritto;
        this.recensione = recensione;
    }

    /**
     * Questo metodo restituisce l'id della segnalazione
     * @return id della segnalazione
     */
    public int getId() {
        return id;
    }

    /**
     * Questo metodo imposta l'id della segnalazione
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Questo metodo restituisce il tipo della segnalazione
     * (0 per Altre Segnalazioni; 1 per Spoiler Alert)
     * @return tipo della segnalazione
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Questo metodo imposta il tipo della segnalazione
     * (0 per Altre Segnalazioni; 1 per Spoiler Alert)
     * @param tipo
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Questo metodo restituisce la motivazione della segnalazione
     * @return motivazione della segnalazione
     */
    public String getMotivazione() {
        return motivazione;
    }

    /**
     * Questo metodo imposta la motivazione della segnalazione
     * @param motivazione
     */
    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    /**
     * Questo metodo restituisce un booleano che indica se la segnalazione
     * è stata gestita o meno dal gestore
     * @return gestita
     */
    public Boolean isGestita() {
        return gestita;
    }

    /**
     * Questo metodo imposta un booleano che indica se la segnalazione
     * è stata gestita o meno dal gestore
     * @param gestita
     */
    public void setGestita(Boolean gestita) {
        this.gestita = gestita;
    }

    /**
     * Questo metodo restituisce l'email dell'iscritto che ha scritto la recensione
     * @return email dell'iscritto
     */
    public IscrittoBean getIscritto() {
        return iscritto;
    }

    /**
     * Questo metodo imposta l'iscritto autore della recensione.
     * @param iscritto è il bean contenente le informazioni dell'iscritto autore della recensione.
     */
    public void setIscritto(IscrittoBean iscritto) {
        this.iscritto = iscritto;
    }

    /**
     * Questo metodo restituisce l'id della recensione a cui è riferita la segnalazione
     * @return id della recensione
     */
    public RecensioneBean getRecensione() {
        return recensione;
    }

    /**
     * Questo metodo imposta la recensione a cui è riferita la segnalazione
     * @param recensione è l'oggetto bean contenente le informazioni relative alla recensione.
     */
    public void setRecensione(RecensioneBean recensione) {
        this.recensione = recensione;
    }

    @Override
    public String toString() {
        return "SegnalazioneBean{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", motivazione='" + motivazione + '\'' +
                ", gestita=" + gestita +
                "}\n";
    }

    private int id;
    private int tipo;
    private String motivazione;
    private Boolean gestita;
    private IscrittoBean iscritto;
    private RecensioneBean recensione;
}
