package it.unisa.ilike.contenuti.application;

public abstract class ContenutoBean {

    public ContenutoBean() {
    }

    public ContenutoBean(int id, String titolo, String descrizione, String categoria, double valutazioneMedia) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.valutazioneMedia = valutazioneMedia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getValutazioneMedia() {
        return valutazioneMedia;
    }

    public void setValutazioneMedia(double valutazioneMedia) {
        this.valutazioneMedia = valutazioneMedia;
    }

    private int id;
    private String titolo;
    private String descrizione;
    private String categoria;
    private double valutazioneMedia;
}
