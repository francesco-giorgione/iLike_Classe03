package it.unisa.ilike.contenuti.storage;

public class FilmBean extends ContenutoBean{

    public FilmBean() {
    }

    public FilmBean(int id, String titolo, String descrizione, String categoria, String anno_rilascio, int durata, String paese, String regista, String attori) {
        super(id, titolo, descrizione, categoria);
        this.anno_rilascio = anno_rilascio;
        this.durata = durata;
        this.paese = paese;
        this.regista = regista;
        this.attori = attori;
    }

    public String getAnno_rilascio() {
        return anno_rilascio;
    }

    public void setAnno_rilascio(String anno_rilascio) {
        this.anno_rilascio = anno_rilascio;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getRegista() {
        return regista;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public String getAttori() {
        return attori;
    }

    public void setAttori(String attori) {
        this.attori = attori;
    }

    private String anno_rilascio;
    private int durata;
    private String paese;
    private String regista;
    private String attori;

}
