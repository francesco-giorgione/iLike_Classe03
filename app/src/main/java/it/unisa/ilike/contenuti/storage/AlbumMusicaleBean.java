package it.unisa.ilike.contenuti.storage;

public class AlbumMusicaleBean extends ContenutoBean{

    public AlbumMusicaleBean() {
    }

    public AlbumMusicaleBean(int id, String titolo, String descrizione, String categoria, String artista, String data_rilascio,
                             String acustica, String strumentalita, String tempo, String valenza, float durata) {
        super(id, titolo, descrizione, categoria);
        this.artista = artista;
        this.data_rilascio = data_rilascio;
        this.acustica = acustica;
        this.strumentalita = strumentalita;
        this.tempo = tempo;
        this.valenza = valenza;
        this.durata = durata;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getData_rilascio() {
        return data_rilascio;
    }

    public void setData_rilascio(String data_rilascio) {
        this.data_rilascio = data_rilascio;
    }

    public String getAcustica() {
        return acustica;
    }

    public void setAcustica(String acustica) {
        this.acustica = acustica;
    }

    public String getStrumentalita() {
        return strumentalita;
    }

    public void setStrumentalita(String strumentalita) {
        this.strumentalita = strumentalita;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getValenza() {
        return valenza;
    }

    public void setValenza(String valenza) {
        this.valenza = valenza;
    }

    public float getDurata() {
        return durata;
    }

    public void setDurata(float durata) {
        this.durata = durata;
    }

    private String artista;
    private String data_rilascio;
    private String acustica;
    private String strumentalita;
    private String tempo;
    private String valenza;
    private float durata;


}
