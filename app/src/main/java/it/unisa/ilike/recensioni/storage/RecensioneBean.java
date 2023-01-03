package it.unisa.ilike.recensioni.storage;

import java.util.Date;

public class RecensioneBean {

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

    private String testo;
    private int valutazione;
    private Date data;
    private Boolean cancellata;
    private String motivazione_cancellazione;
}
