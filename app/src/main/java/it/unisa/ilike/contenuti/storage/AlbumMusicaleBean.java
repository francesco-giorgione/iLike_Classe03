package it.unisa.ilike.contenuti.storage;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi agli album musicali
 * @author Simona Lo Conte
 * @version 0.1
 */
public class AlbumMusicaleBean extends ContenutoBean {

    /**
     * Costruttore senza parametri
     */
    public AlbumMusicaleBean() {
    }

    /**
     * Costruttore con parametri
     * @param id
     * @param titolo
     * @param descrizione
     * @param categoria
     * @param artista
     * @param dataRilascio
     * @param acustica
     * @param strumentalita
     * @param tempo
     * @param valenza
     * @param durata
     */
    public AlbumMusicaleBean(int id, String titolo, String descrizione, String categoria, String artista, double valutazioneMedia, String dataRilascio,
                             double acustica, double strumentalita, double tempo, double valenza, float durata) {
        super(id, titolo, descrizione, categoria, valutazioneMedia);
        this.artista = artista;
        this.dataRilascio = dataRilascio;
        this.acustica = acustica;
        this.strumentalita = strumentalita;
        this.tempo = tempo;
        this.valenza = valenza;
        this.durata = durata;
    }

    /**
     * Questo metodo restituisce l'artista dell'album musicale
     * @return artista dell'album musicale
     */
    public String getArtista() {
        return artista;
    }

    /**
     * Questo metodo imposta l'artista dell'album musicale
     * @param artista
     */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /**
     * Questo metodo restituisce la data di rilascio dell'album musicale
     * @return data di rilascio dell'album musicale
     */
    public String getDataRilascio() {
        return dataRilascio;
    }

    /**
     * Questo metodo imposta la data di rilascio dell'album musicale
     * @param dataRilascio
     */
    public void setDataRilascio(String dataRilascio) {
        this.dataRilascio = dataRilascio;
    }

    /**
     * Questo metodo restituisce l'acustica dell'album musicale
     * @return acustica dell'album musicale
     */
    public double getAcustica() {
        return acustica;
    }

    /**
     * Questo metodo imposta l'acustica dell'album musicale
     * @param acustica
     */
    public void setAcustica(double acustica) {
        this.acustica = acustica;
    }

    /**
     * Questo metodo restituisce la strumentalità dell'album musicale
     * @return strumentalità dell'album musicale
     */
    public double getStrumentalita() {
        return strumentalita;
    }

    /**
     * Questo metodo imposta la strumentalità dell'album musicale
     * @param strumentalita
     */
    public void setStrumentalita(double strumentalita) {
        this.strumentalita = strumentalita;
    }

    /**
     * Questo metodo restituisce il tempo dell'album musicale
     * @return tempo dell'album musicale
     */
    public double getTempo() {
        return tempo;
    }

    /**
     * Questo metodo imposta il tempo dell'album musicale
     * @param tempo
     */
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    /**
     * Questo metodo restituisce la valenza dell'album musicale
     * @return valenza dell'album musicale
     */
    public double getValenza() {
        return valenza;
    }

    /**
     * Questo metodo imposta la valenza dell'album musicale
     * @param valenza
     */
    public void setValenza(double valenza) {
        this.valenza = valenza;
    }

    /**
     * Questo metodo restituisce la durata dell'album musicale
     * @return durata dell'album musicale
     */
    public double getDurata() {
        return durata;
    }

    /**
     * Questo metodo imposta la durata dell'album musicale
     * @param durata
     */
    public void setDurata(double durata) {
        this.durata = durata;
    }

    private String artista;
    private String dataRilascio;
    private double acustica;
    private double strumentalita;
    private double tempo;
    private double valenza;
    private double durata;


}
