package it.unisa.ilike.segnalazioni.storage;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi alle segnalazioni delle recensioni
 * @author Simona Lo Conte
 * @version 0.1
 */
public class SegnalazioneBean {

    /**
     * Costruttore senza parametri
     */
    public SegnalazioneBean() {
    }

    /**
     * Costruttore con parametri
     * @param id
     * @param tipo
     * @param motivazione
     * @param gestita
     * @param emailIscritto
     * @param idRecensione
     */
    public SegnalazioneBean(int id, int tipo, String motivazione, Boolean gestita,
                            String emailIscritto, int idRecensione) {
        this.id = id;
        this.tipo = tipo;
        this.motivazione = motivazione;
        this.gestita = gestita;
        this.emailIscritto = emailIscritto;
        this.idRecensione = idRecensione;
    }

    /**
     * Questo metodo restituisce l'id della segnalazione
     * @return id della segnalazione
     */
    public int getId() {
        return id;
    }

    /**
     * Questo metodo imposta l'id della segnalazione
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Questo metodo restituisce il tipo della segnalazione
     * (0 per Altre Segnalazioni; 1 per Spoiler Alert)
     * @return tipo della segnalazione
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Questo metodo imposta il tipo della segnalazione
     * (0 per Altre Segnalazioni; 1 per Spoiler Alert)
     * @param tipo
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Questo metodo restituisce la motivazione della segnalazione
     * @return motivazione della segnalazione
     */
    public String getMotivazione() {
        return motivazione;
    }

    /**
     * Questo metodo imposta la motivazione della segnalazione
     * @param motivazione
     */
    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    /**
     * Questo metodo restituisce un booleano che indica se la segnalazione
     * è stata gestita o meno dal gestore
     * @return gestita
     */
    public Boolean isGestita() {
        return gestita;
    }

    /**
     * Questo metodo imposta un booleano che indica se la segnalazione
     * è stata gestita o meno dal gestore
     * @param gestita
     */
    public void setGestita(Boolean gestita) {
        this.gestita = gestita;
    }

    /**
     * Questo metodo restituisce l'email dell'iscritto che ha scritto la recensione
     * @return email dell'iscritto
     */
    public String getEmailIscritto() {
        return emailIscritto;
    }

    /**
     * Questo metodo imposta l'email dell'iscritto che ha scritto la recensione
     * @param emailIscritto
     */
    public void setEmailIscritto(String emailIscritto) {
        this.emailIscritto = emailIscritto;
    }

    /**
     * Questo metodo restituisce l'id della recensione a cui è riferita la segnalazione
     * @return id della recensione
     */
    public int getIdRecensione() {
        return idRecensione;
    }

    /**
     * Questo metodo imposta l'id della recensione a cui è riferita la segnalazione
     * @param idRecensione
     */
    public void setIdRecensione(int idRecensione) {
        this.idRecensione = idRecensione;
    }

    private int id;
    private int tipo;
    private String motivazione;
    private Boolean gestita;
    private String emailIscritto;
    private int idRecensione;
}
