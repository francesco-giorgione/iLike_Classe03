package it.unisa.ilike.contenuti.storage;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi ai film
 * @author Simona Lo Conte
 * @version 0.1
*/
public class FilmBean extends ContenutoBean{

    /**
     * Costruttore senza parametri
     */
    public FilmBean() {
    }

    /**
     * Costruttore con parametri
     * @param id
     * @param titolo
     * @param descrizione
     * @param categoria
     * @param annoRilascio
     * @param durata
     * @param paese
     * @param regista
     * @param attori
     */
    public FilmBean(int id, String titolo, String descrizione, String categoria, String annoRilascio, int durata, String paese, String regista, String attori) {
        super(id, titolo, descrizione, categoria);
        this.annoRilascio = annoRilascio;
        this.durata = durata;
        this.paese = paese;
        this.regista = regista;
        this.attori = attori;
    }

    /**
     * Questo metodo restituisce l'anno di rilascio del film
     * @return anno di rilascio
     */
    public String getAnnoRilascio() {
        return annoRilascio;
    }

    /**
     * Questo metodo imposta l'anno di rilascio del film
     * @param annoRilascio
     */
    public void setAnnoRilascio(String annoRilascio) {
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

    private String annoRilascio;
    private int durata;
    private String paese;
    private String regista;
    private String attori;

}
