package it.unisa.ilike.segnalazioni.storage;

public class SegnalazioneBean {

    public SegnalazioneBean() {
    }

    public SegnalazioneBean(int id, int tipo, String motivazione, Boolean gestita,
                            String email_iscritto, int id_recensione, int id_contenuto) {
        this.id = id;
        this.tipo = tipo;
        this.motivazione = motivazione;
        this.gestita = gestita;
        this.email_iscritto = email_iscritto;
        this.id_recensione = id_recensione;
        this.id_contenuto = id_contenuto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public Boolean isGestita() {
        return gestita;
    }

    public void setGestita(Boolean gestita) {
        this.gestita = gestita;
    }

    public String getEmail_iscritto() {
        return email_iscritto;
    }

    public void setEmail_iscritto(String email_iscritto) {
        this.email_iscritto = email_iscritto;
    }

    public int getId_recensione() {
        return id_recensione;
    }

    public void setId_recensione(int id_recensione) {
        this.id_recensione = id_recensione;
    }

    public int getId_contenuto() {
        return id_contenuto;
    }

    public void setId_contenuto(int id_contenuto) {
        this.id_contenuto = id_contenuto;
    }

    private int id;
    private int tipo;
    private String motivazione;
    private Boolean gestita;
    private String email_iscritto;
    private int id_recensione;
    private int id_contenuto;

}
