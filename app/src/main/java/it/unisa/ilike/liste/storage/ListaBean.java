package it.unisa.ilike.liste.storage;

public class ListaBean {

    public ListaBean() {
    }

    public ListaBean(String nome, String email_iscritto, Boolean visibilita) {
        this.nome = nome;
        this.email_iscritto = email_iscritto;
        this.visibilita = visibilita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail_iscritto() {
        return email_iscritto;
    }

    public void setEmail_iscritto(String email_iscritto) {
        this.email_iscritto = email_iscritto;
    }

    public Boolean isVisibile() {
        return visibilita;
    }

    public void setVisibilita(Boolean visibilita) {
        this.visibilita = visibilita;
    }

    private String nome;
    private String email_iscritto;
    private Boolean visibilita;
}
