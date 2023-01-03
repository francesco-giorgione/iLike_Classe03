package it.unisa.ilike.liste.storage;

public class ListaBean {

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isVisibile() {
        return visibilita;
    }

    public void setVisibilita(Boolean visibilita) {
        this.visibilita = visibilita;
    }

    private String nome;
    private Boolean visibilita;
}
