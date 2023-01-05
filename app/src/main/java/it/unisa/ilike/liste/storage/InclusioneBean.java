package it.unisa.ilike.liste.storage;


/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi all'inclusione dei contenuti all'interno
 * delle liste dell'iscritto
 * @author Simona Lo Conte
 * @version 0.1
 */
public class InclusioneBean {

    /**
     * Costruttore senza parametri
     */
    public InclusioneBean() {
    }

    /**
     * Costruttore con parametri
     * @param nomeLista
     * @param emailIscritto
     * @param idContenuto
     */
    public InclusioneBean(String nomeLista, String emailIscritto, int idContenuto) {
        this.nomeLista = nomeLista;
        this.emailIscritto = emailIscritto;
        this.idContenuto = idContenuto;
    }

    /**
     * Questo metodo restituisce il nome della lista
     * @return nome della lista
     */
    public String getNomeLista() {
        return nomeLista;
    }

    /**
     * Questo metodo imposta il nome della lista
     * @param nomeLista
     */
    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    /**
     * Questo metodo restituisce l'email dell'iscritto a cui appartiene la lista
     * @return email dell'iscritto
     */
    public String getEmailIscritto() {
        return emailIscritto;
    }

    /**
     * Questo metodo imposta l'email dell'iscritto a cui appartiene la lista
     * @param emailIscritto
     */
    public void setEmailIscritto(String emailIscritto) {
        this.emailIscritto = emailIscritto;
    }

    /**
     * Questo metodo restituisce l'id del contenuto appartenente alla lista
     * @return id del ontenuto
     */
    public int getIdContenuto() {
        return idContenuto;
    }

    /**
     * Questo metodo imposta l'id del contenuto appartenente alla lista
     * @param idContenuto
     */
    public void setIdContenuto(int idContenuto) {
        this.idContenuto = idContenuto;
    }

    private String nomeLista;
    private String emailIscritto;
    private int idContenuto;

}
