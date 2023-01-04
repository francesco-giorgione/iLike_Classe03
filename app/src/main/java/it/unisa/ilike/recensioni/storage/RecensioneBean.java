package it.unisa.ilike.recensioni.storage;

import java.util.Date;

public class RecensioneBean {

    public RecensioneBean() {
    }

    public RecensioneBean(int id, String testo, int valutazione, Date data,
                          Boolean cancellata, String motivazione_cancellazione, String email_iscritto) {
        this.id = id;
        this.testo = testo;
        this.valutazione = valutazione;
        this.data = data;
        this.cancellata = cancellata;
        this.motivazione_cancellazione = motivazione_cancellazione;
        this.email_iscritto = email_iscritto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean isCancellata() {
        return cancellata;
    }

    public void setCancellata(Boolean cancellata) {
        this.cancellata = cancellata;
    }

    public String getMotivazione_cancellazione() {
        return motivazione_cancellazione;
    }

    public void setMotivazione_cancellazione(String motivazione_cancellazione) {
        this.motivazione_cancellazione = motivazione_cancellazione;
    }

    public String getEmail_iscritto() {
        return email_iscritto;
    }

    public void setEmail_iscritto(String email_iscritto) {
        this.email_iscritto = email_iscritto;
    }

    private int id;
    private String testo;
    private int valutazione;
    private Date data;
    private Boolean cancellata;
    private String motivazione_cancellazione;
    private String email_iscritto;
}
