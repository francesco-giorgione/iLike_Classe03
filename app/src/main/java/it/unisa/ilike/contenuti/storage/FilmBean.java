package it.unisa.ilike.contenuti.storage;

import java.io.Serializable;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi ai film
 * @author Simona Lo Conte
 * @version 0.1
*/
public class FilmBean extends ContenutoBean  implements Serializable {

    /**
     * Costruttore senza parametri
     */
    public FilmBean() {
    }

    /**
     * Costruttore con parametri
     * @param id è l'id del film.
     * @param titolo è il titolo del film.
     * @param descrizione è la descrizione del film.
     * @param categoria è la categoria del film.
     * @param annoRilascio è l'anno di rilascio del film.
     * @param durata è la durata del film.
     * @param paese è il paese di produzione del film.
     * @param regista è il regista del film.
     * @param attori è l'elenco degli attori del film.
     */
    public FilmBean(int id, String titolo, String descrizione, String categoria, Float annoRilascio, int durata, String paese, String regista, String attori, float valutazioneMedia) {
        super(id, titolo, descrizione, categoria, valutazioneMedia);
        this.annoRilascio = annoRilascio;
        this.durata = durata;
        this.paese = paese;
        this.regista = regista;
        this.attori = attori;
    }

    public FilmBean (int id, String titolo, Float annoRilascio, String categoria,String paese, String regista, String descrizione){
        this.setId(id);
        this.setTitolo(titolo);
        this.setDescrizione(descrizione);
        this.setCategoria(categoria);
        this.annoRilascio=annoRilascio;
        this.paese=paese;
        this.regista=regista;
    }

    /**
     * Questo metodo restituisce l'anno di rilascio del film
     * @return anno di rilascio
     */
    public Float getAnnoRilascio() {
        return annoRilascio;
    }

    /**
     * Questo metodo imposta l'anno di rilascio del film
     * @param annoRilascio
     */
    public void setAnnoRilascio(Float annoRilascio) {
        this.annoRilascio = annoRilascio;
    }

    /**
     * Questo metodo restituisce la durata del film
     * @return durata del film
     */
    public int getDurata() {
        return durata;
    }

    /**
     * Questo metodo imposta la durata del film
     * @param durata
     */
    public void setDurata(int durata) {
        this.durata = durata;
    }

    /**
     * Questo metodo restituisce il paese in cui è stato girato il film
     * @return paese
     */
    public String getPaese() {
        return paese;
    }

    /**
     * Questo metodo imposta il paese in cui è stato girato il film
     * @param paese
     */
    public void setPaese(String paese) {
        this.paese = paese;
    }

    /**
     * Questo metodo restituisce il regista del film
     * @return regista del film
     */
    public String getRegista() {
        return regista;
    }

    /**
     * Questo metodo imposta il regista del film
     * @param regista
     */
    public void setRegista(String regista) {
        this.regista = regista;
    }

    /**
     * Questo metodo restituisce una stringa contenente gli attori del film
     * @return attori del film
     */
    public String getAttori() {
        return attori;
    }

    /**
     * Questo metodo imposta una stringa contenente gli attori del film
     * @param attori
     */
    public void setAttori(String attori) {
        this.attori = attori;
    }

    private Float annoRilascio;
    private int durata;
    private String paese;
    private String regista;
    private String attori;

}
