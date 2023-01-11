package it.unisa.ilike.account.storage;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

/**
 * Questa classe rappresenta la reale implementazione di IscrittoBean
 * @author Marta
 * @version 0.2
 */
public class IscrittoRealBean extends IscrittoBean  implements Serializable {

    /**
     * Questo metodo crea un oggetto <code>IscrittoRealBean</code>
     * @param email rappresenta il testo contenente l'email dell'iscritto
     * @param password rappresenta il testo contenente la password dell'iscritto
     * @param nickname rappresenta il testo contenente il nickname dell'iscritto
     * @param nome rappresenta il testo contenente il nome dell'iscritto
     * @param cognome rappresenta il testo contenente il cognome dell'iscritto
     * @param bio rappresenta il testo contenente la bio dell'iscritto
     */
    public IscrittoRealBean(String email, String password, String nickname, String nome, String cognome, String bio) {
        super(email, password, nickname, nome, cognome, bio);
        this.recensioni = new ArrayList<>();
        this.liste = new ArrayList<>();
    }


    /**
     * Questo metodo permette di accedere alle liste dell'iscritto
     * @return le liste dell'iscritto
     */
    public List<ListaBean> getListe() {
        if(this.liste == null){
            this.liste = new IscrittoDAO().doRetrieveListe(this.getEmail());
        }
        return this.liste;
    }

    /**
     * Questo metodo permette di accedere alle recensioni dove l'iscritto è l'autore
     * @return le recensioni dell'iscritto
     */
    public List<RecensioneBean> getRecensioni() {
        if(this.recensioni == null){
            this.recensioni = new IscrittoDAO().doRetrieveRecensioni(this.getEmail());
        }
        return this.recensioni;
    }

    /**
     * Questo metodo permette di modificare le liste dell'iscritto
     * @param liste le nuove liste dell'iscritto
     */
    public void setListe(List<ListaBean> liste) {
        this.liste = liste;
    }

    /**
     * Questo metodo permette di modificare le liste di recensioni  associate all'iscritto
     * @param recensioni le nuove recensioni dell'iscritto
     */
    public void setRecensioni(List<RecensioneBean> recensioni) {
        this.recensioni = recensioni;
    }

    /**
     * Questo metodo permette di aggiungere una nuova recensione ad un iscritto
     * @param recensioneBean rappresenta l'oggetto RecensioneBean da aggiungere all'iscritto
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean addRecensione(RecensioneBean recensioneBean){
        return this.recensioni.add(recensioneBean);
    }

    /**
     * Questo metodo permette di aggiungere una nuovo contenuto in una lista di un iscritto
     * @param listaBean rappresenta l'oggetto ListaBean a cui va aggiunto il contenuto
     * @param contenutoBean rappresenta l'oggetto ContenutoBean da aggiungere alla lista
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean addContenutoLista(ContenutoBean contenutoBean, ListaBean listaBean){
        for (int i = 0; i < this.liste.size(); i++){
            ListaBean listaCurr = this.liste.get(i);
            if(listaCurr.getNome().equals(listaBean.getNome()) && listaCurr.getIscritto().getEmail().equals(listaBean.getIscritto().getEmail())){
                this.liste.get(i).aggiungiContenuto(contenutoBean);
                return true;
            }
        }
        return false;
    }

    /**
     * Questo metodo permette di aggiungere una nuova lista ad un iscritto
     * @param listaBean rappresenta l'oggetto ListaBean da aggiungerre all'iscritto
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean addLista(ListaBean listaBean){
        return this.liste.add(listaBean);
    }

    private List<ListaBean> liste;
    private List<RecensioneBean> recensioni;
}
