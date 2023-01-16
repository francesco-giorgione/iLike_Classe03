package it.unisa.ilike.contenuti.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;

public abstract class ContenutoBean implements Serializable {

    /**
     * Costruttore senza parametri
     */
    public ContenutoBean() {
        this.recensioni = new ArrayList<>();
    }


    /**
     * Costruttore con parametri
     * @param id è l'id del contenuto
     * @param titolo è il titolo del contenuto
     * @param descrizione è la descrizione del contenuto
     * @param categoria è la categoria del contenuto
     * @param valutazioneMedia è la valutazione media del contenuto
     */
    public ContenutoBean(int id, String titolo, String descrizione, String categoria, double valutazioneMedia) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.valutazioneMedia = valutazioneMedia;
        this.recensioni = new ArrayList<>();
    }

    /**
     * Restituisce l'id del contenuto.
     * @return una variabile int
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta nel bean l'id del contenuto.
     * @param id una variabile int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce il titolo del contenuto.
     * @return una variabile String
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta nel bean il titolo del contenuto.
     * @param titolo una variabile String
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce la descrizione del contenuto.
     * @return una variabile String
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta nel bean la descrizione del contenuto.
     * @param descrizione una variabile String
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Resituisce la categoria del contenuto.
     * @return una variabile String
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Imposta nel bean la categoria del contenuto.
     * @param categoria una variabile String
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Restituisce la valutazione media del contenuto.
     * @return una variabile double
     */
    public double getValutazioneMedia() {
        return valutazioneMedia;
    }

    /**
     * Imposta nel bean la valutazione media del contenuto.
     * @param valutazioneMedia una variabile double
     */
    public void setValutazioneMedia(double valutazioneMedia) {
        this.valutazioneMedia = valutazioneMedia;
    }

    /**
     * Restituisce una collezione delle recensioni del contenuto.
     * @return un ArrayList di oggetti RecensioneBean
     */
    public List<RecensioneBean> getRecensioni() {
        return new RecensioneDAO().doRetrieveRecensioniByIdContenuto(this.id);
    }

    /**
     * Aggiunge una nuova recensione alla collezione delle recensioni del contenuto.
     * @param recensione un oggetto RecensioneBean
     * @return
     */
    public boolean aggiungiRecensione(RecensioneBean recensione) {
        return this.recensioni.add(recensione);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ContenutoBean{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", valutazioneMedia=" + valutazioneMedia +
                '}';
    }

    private int id;
    private String titolo;
    private String descrizione;
    private String categoria;
    private double valutazioneMedia;
    private List<RecensioneBean> recensioni;
}
