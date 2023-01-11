package it.unisa.ilike.account.storage;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

/**
 * Questa classe rappresenta il Proxy di IscrittoBean.
 * @author Marta
 * @version 0.2
 */
public class IscrittoProxyBean extends IscrittoBean  implements Serializable {

    /**
     * Questo metodo crea un oggetto IscrittoBean
     * @param email rappresenta il testo contenente l'email dell'iscritto
     * @param password rappresenta il testo contenente la password dell'iscritto
     * @param nickname rappresenta il testo contenente il nickname dell'iscritto
     * @param nome rappresenta il testo contenente il nome dell'iscritto
     * @param cognome rappresenta il testo contenente il cognome dell'iscritto
     * @param bio rappresenta il testo contenente la bio dell'iscritto
     */
    public IscrittoProxyBean(String email, String password, String nickname, String nome, String cognome, String bio) {
        super(email, password, nickname, nome, cognome, bio);
        this.iscrittoReal = null;
    }

    /**
     * Questo metodo permette di accedere alla foto profilo dell'iscritto
     * @return la foto profilo dell'iscritto
     */
    public InputStream getFoto() {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        return this.iscrittoReal.getFoto();
    }

    /**
     * Questo metodo permette di accedere alle liste dell'iscritto
     * @return le liste dell'iscritto
     */
    public List<ListaBean> getListe() {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        return this.iscrittoReal.getListe();
    }

    /**
     * Questo metodo permette di accedere alle recensioni dove l'iscritto è l'autore
     * @return le recensioni dell'iscritto
     */
    public List<RecensioneBean> getRecensioni() {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        return this.iscrittoReal.getRecensioni();
    }

    /**
     * Questo metodo permette di modificare le liste dell'iscritto
     * @param liste le nuove liste dell'iscritto
     */
    public void setListe(List<ListaBean> liste) {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        this.iscrittoReal.setListe(liste);
    }

    /**
     * Questo metodo permette di modificare le liste di recensioni  associate all'iscritto
     * @param recensioni le nuove recensioni dell'iscritto
     */
    public void setRecensioni(List<RecensioneBean> recensioni) {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        this.iscrittoReal.setRecensioni(recensioni);
    }

    /**
     * Questo metodo permette di modificare la foto profilo
     * @param foto la nuova foto profilo dell'iscritto
     */
    public void setFoto(InputStream foto) {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        this.iscrittoReal.setFoto(foto);
    }

    /**
     * Questo metodo permette di aggiungere una nuova lista ad un iscritto
     * @param listaBean rappresenta l'oggetto ListaBean da aggiungerre all'iscritto
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean addLista(ListaBean listaBean) {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        return this.iscrittoReal.addLista(listaBean);
    }

    /**
     * Questo metodo permette di aggiungere una nuovo contenuto in una lista di un iscritto
     * @param contenutoBean rappresenta l'oggetto ContenutoBean da aggiungere alla lista
     * @param listaBean     rappresenta l'oggetto ListaBean a cui va aggiunto il contenuto
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean addContenutoLista(ContenutoBean contenutoBean, ListaBean listaBean) {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        return this.iscrittoReal.addContenutoLista(contenutoBean, listaBean);
    }

    /**
     * Questo metodo permette di aggiungere una nuova recensione ad un iscritto
     * @param recensioneBean rappresenta l'oggetto RecensioneBean da aggiungere all'iscritto
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean addRecensione(RecensioneBean recensioneBean) {
        if (this.iscrittoReal == null)
            this.iscrittoReal = new IscrittoRealBean(this.getEmail(), this.getPassword(),
                    this.getNickname(), this.getNome(), this.getCognome(), this.getBio());
        return this.iscrittoReal.addRecensione(recensioneBean);
    }


    private IscrittoRealBean iscrittoReal;
}
