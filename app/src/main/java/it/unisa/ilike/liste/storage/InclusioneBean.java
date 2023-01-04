package it.unisa.ilike.liste.storage;

public class InclusioneBean {

    public InclusioneBean() {
    }

    public InclusioneBean(String nome_lista, String email_iscritto, int id_contenuto) {
        this.nome_lista = nome_lista;
        this.email_iscritto = email_iscritto;
        this.id_contenuto = id_contenuto;
    }

    public String getNome_lista() {
        return nome_lista;
    }

    public void setNome_lista(String nome_lista) {
        this.nome_lista = nome_lista;
    }

    public String getEmail_iscritto() {
        return email_iscritto;
    }

    public void setEmail_iscritto(String email_iscritto) {
        this.email_iscritto = email_iscritto;
    }

    public int getId_contenuto() {
        return id_contenuto;
    }

    public void setId_contenuto(int id_contenuto) {
        this.id_contenuto = id_contenuto;
    }

    private String nome_lista;
    private String email_iscritto;
    private int id_contenuto;

}
