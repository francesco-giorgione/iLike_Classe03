package it.unisa.ilike.recensioni.storage;

import java.util.Date;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi alle recensioni
 * @author Simona Lo Conte
 * @version 0.1
 */
public class RecensioneBean {

    /**
     * Costruttore senza parametri
     */
    public RecensioneBean() {
    }

    /**
     * Costruttore con parametri
     * @param id è l'id della recensione.
     * @param testo è il testo della recensione.
     * @param valutazione è la valutazione (da 1 a 5) della recensione.
     * @param data è la data di pubblicazione della recensione.
     * @param cancellata indica se la recensione è stata cancellata da un gestore
     *                  in seguito all'accoglimento di una segnalazione.
     * @param motivazioneCancellazione indica la motivazione della cancellazione di una recensione eventualmente cancellata.
     * @param iscritto è l'iscritto autore della recensione.
     * @param contenuto è il contenuto cui è riferita la recensione.
     */
    public RecensioneBean(int id, String testo, int valutazione, Date data,
                          Boolean cancellata, String motivazioneCancellazione, IscrittoBean iscritto, ContenutoBean contenuto) {
        this.id = id;
        this.testo = testo;
        this.valutazione = valutazione;
        this.data = data;
        this.cancellata = cancellata;
        this.motivazioneCancellazione = motivazioneCancellazione;
        this.iscritto = iscritto;
        this.contenuto=contenuto;
    }

    /**
     * Questo metodo restituisce l'id della recensione
     * @return id della recensione
     */
    public int getId() {
        return id;
    }

    /**
     * Questo metodo imposta l'id della recensione
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Questo metodo restituisce il testo della recensione
     * @return testo della recensione
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Questo metodo imposta il testo della recensione
     * @param testo
     */
    public void setTesto(String testo) {
        this.testo = testo;
    }

    /**
     * Questo metodo restituisce la valutazione della recensione
     * @return valutazione della recensione espressa con un intero da 1 a 5
     */
    public int getValutazione() {
        return valutazione;
    }

    /**
     * Questo metodo imposta la valutazione della recensione (intero compreso tra 1 e 5)
     * @param valutazione
     */
    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    /**
     * Questo metodo restituisce la data in cui è stata scritta la recensione
     * @return data della recensione
     */
    public Date getData() {
        return data;
    }

    /**
     * Questo metodo imposta la data in cui è stata scritta la recensione
     * @param data
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Questo metodo restituisce il valore booleano dell'attributo cancellata
     * (true, se la recensione è stata cancellata a seguito di una segnalazione;
     * false, se la recensione non è stata cancellata)
     * @return cancellata
     */
    public Boolean isCancellata() {
        return cancellata;
    }

    /**
     * Questo metodo imposta il valore booleano dell'attributo cancellata
     * (true, se la recensione è stata cancellata a seguito di una segnalazione;
     * false, se la recensione non è stata cancellata)
     * @param cancellata
     */
    public void setCancellata(Boolean cancellata) {
        this.cancellata = cancellata;
    }

    /**
     * Questo metodo restituisce la motivazione della cancellazione della recensione
     * @return motivazione della cancellazione
     */
    public String getMotivazioneCancellazione() {
        return motivazioneCancellazione;
    }

    /**
     * Questo metodo imposta la motivazione della cancellazione della recensione
     * @param motivazioneCancellazione
     */
    public void setMotivazioneCancellazione(String motivazioneCancellazione) {
        this.motivazioneCancellazione = motivazioneCancellazione;
    }

    /**
     * Questo metodo restituisce l'iscritto autore della recensione
     * @return email dell'iscritto
     */
    public IscrittoBean getIscritto() {
        return iscritto;
    }

    /**
     * Questo metodo imposta l'iscritto autore della recensione
     * @param iscritto è l'oggetto IscrittoBean da associare alla recensione
     */
    public void setIscritto(IscrittoBean iscritto) {
        this.iscritto = iscritto;
    }

    /**
     * Questo metodo restituisce il a cui è riferita la recensione.
     * @return un oggetto ContenutoBean contenente le informazioni relative al contenuto cui è
     * riferita la recensione.
     */
    public ContenutoBean getContenuto() {
        return contenuto;
    }

    /**
     * Questo metodo imposta il contenuto a cui è riferita la recensione
     * @param contenuto è l'oggetto bean associato alla recensione.
     */
    public void setContenuto(ContenutoBean contenuto) {
        this.contenuto = contenuto;
    }

    private int id;
    private String testo;
    private int valutazione;
    private Date data;
    private Boolean cancellata;
    private String motivazioneCancellazione;
    private IscrittoBean iscritto;
    private ContenutoBean contenuto;
}
