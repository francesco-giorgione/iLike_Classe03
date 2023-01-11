package it.unisa.ilike.liste.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;

/**
 * Questa classe contiene gli attributi e i metodi di utilità relativi alle liste di contenuti degli iscritti
 * @author Simona Lo Conte
 * @version 0.1
 */
public class ListaBean  implements Serializable {

    /**
     * Costruttore senza parametri
     */
    public ListaBean() {
    }

    /**
     * @param nome è il nome della lista
     * @param iscritto è il bean contenente le informazioni sull'iscritto cui appartiene la lista.
     * @param visibilita indica se la lista deve essere visibile agli altri utenti (true) o meno (false)
     */
    public ListaBean(String nome, IscrittoBean iscritto, Boolean visibilita) {
        this.nome = nome;
        this.iscritto = iscritto;
        this.visibilita = visibilita;
    }

    /**
     * Questo metodo restituisce il nome della lista
     * @return nome della lista
     */
    public String getNome() {
        return nome;
    }

    /**
     * Questo metodo imposta il nome della lista
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Questo metodo restituisce l'iscritto a cui appartiene la lista.
     * @return bean contenente le informazioni dell'iscritto cui appartiene la lista.
     */
    public IscrittoBean getIscritto() {
        return iscritto;
    }

    /**
     * Questo metodo imposta l'email dell'iscritto a cui appartiene la lista
     * @param iscritto
     */
    public void setIscritto(IscrittoBean iscritto) {
        this.iscritto = iscritto;
    }

    /**
     * Questo metodo restituisce un valore booleano riguardo la visibilità della lista
     * (true se è public, quindi visibile a tutti; false, se è private, quindi visibile solo
     * all'iscritto a cui appartiene)
     * @return visibilita della lista
     */
    public Boolean isVisibile() {
        return visibilita;
    }

    /**
     * Questo metodo imposta un valore booleano per la visibilità della lista
     * (true se è public, quindi visibile a tutti; false, se è private, quindi visibile solo
     * all'iscritto a cui appartiene)
     * @param visibilita
     */
    public void setVisibilita(Boolean visibilita) {
        this.visibilita = visibilita;
    }

    /**
     * Restituisce una collezione dei contenuti di una lista.
     * @return un oggetto List contenente i contenuti che appartengono alla lista.
     */
    public List<ContenutoBean> getContenuti() {
        return contenuti;
    }


    /**
     * Setta la collezione dei contenuti di una lista
     * @param contenuti è la collezione dei contenuti da associare alla lista
     */
    public void setContenuti(List<ContenutoBean> contenuti) {
        this.contenuti = contenuti;
    }


    /**
     * Aggiunge un contenuto alla collezione dei contenuti della lista.
     * @param c è il contenuto da aggiungere alla collezione dei contenuti.
     */
    public void aggiungiContenuto(ContenutoBean c) {
        if(this.contenuti == null) {
            this.contenuti = new ArrayList<>();
        }

        this.contenuti.add(c);
    }

    private String nome;
    private IscrittoBean iscritto;
    private Boolean visibilita;
    private List<ContenutoBean> contenuti;
}
