package it.unisa.ilike.contenuti.storage;

import java.util.ArrayList;
import java.util.List;

public abstract class ContenutoBean {

    public ContenutoBean() {
    }

    public ContenutoBean(int id, String titolo, String descrizione, String categoria,
                         ArrayList<ContenutoBean> contenuti) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.contenuti = contenuti;
    }

    public ContenutoBean(int id, String titolo, String descrizione, String categoria) {
        this(id, titolo, descrizione, categoria, new ArrayList<>());
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

    public List<ContenutoBean> getContenuti() { return contenuti; }

    public void setContenuti(List<ContenutoBean> contenuti) { this.contenuti = contenuti; }

    private int id;
    private String titolo;
    private String descrizione;
    private String categoria;
    private List<ContenutoBean> contenuti;
}
