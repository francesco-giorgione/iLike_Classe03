package it.unisa.ilike.profili.storage;

import java.sql.Blob;
import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

public class IscrittoProxyBean extends IscrittoBean {

    /**
     * Questo metodo crea un oggetto IscrittoBean
     *
     * @param id       rappresenta l'id a cui è associato dell'iscritto
     * @param email    rappresenta il testo contenente l'email dell'iscritto
     * @param password rappresenta il testo contenente la password dell'iscritto
     * @param nickname rappresenta il testo contenente il nickname dell'iscritto
     * @param nome     rappresenta il testo contenente il nome dell'iscritto
     * @param cognome  rappresenta il testo contenente il cognome dell'iscritto
     * @param bio      rappresenta il testo contenente la bio dell'iscritto
     */
    public IscrittoProxyBean(int id, String email, String password, String nickname, String nome, String cognome, String bio) {
        super(id, email, password, nickname, nome, cognome, bio);
    }

    /**
     * Questo metodo permette di accedere alla foto profilo dell'iscritto
     *
     * @return la foto profilo dell'iscritto
     */
    @Override
    public Blob getFoto() {
        return null;
    }

    /**
     * Questo metodo permette di modificare la foto profilo
     *
     * @param foto la nuova foto profilo dell'iscritto
     */
    @Override
    public void setFoto(Blob foto) {

    }

    /**
     * Questo metodo permette di accedere alle liste dell'iscritto
     *
     * @return le liste dell'iscritto
     */
    @Override
    public List<ListaBean> getListe() {
        return null;
    }

    /**
     * Questo metodo permette di accedere alle recensioni dove l'iscritto è l'autore
     *
     * @return le recensioni dell'iscritto
     */
    @Override
    public List<RecensioneBean> getRecensioni() {
        return null;
    }

    /**
     * Questo metodo permette di modificare le liste dell'iscritto
     *
     * @param liste le nuove liste dell'iscritto
     */
    @Override
    public void setListe(List<ListaBean> liste) {

    }

    /**
     * Questo metodo permette di modificare le liste di recensioni  associate all'iscritto
     *
     * @param recensioni le nuove recensioni dell'iscritto
     */
    @Override
    public void setRecensioni(List<RecensioneBean> recensioni) {

    }

    private IscrittoRealBean iscrittoReal;
}
