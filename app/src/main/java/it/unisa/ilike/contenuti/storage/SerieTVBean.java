package it.unisa.ilike.contenuti.storage;

public class SerieTVBean extends ContenutoBean{

    public SerieTVBean() {
    }

    public SerieTVBean(int id, String titolo, String descrizione, String categoria, String anno_rilascio, int num_stagioni) {
        super(id, titolo, descrizione, categoria);
        this.anno_rilascio = anno_rilascio;
        this.num_stagioni = num_stagioni;
    }

    public String getAnno_rilascio() {
        return anno_rilascio;
    }

    public void setAnno_rilascio(String anno_rilascio) {
        this.anno_rilascio = anno_rilascio;
    }

    public int getNum_stagioni() {
        return num_stagioni;
    }

    public void setNum_stagioni(int num_stagioni) {
        this.num_stagioni = num_stagioni;
    }

    private String anno_rilascio;
    private int num_stagioni;
}
