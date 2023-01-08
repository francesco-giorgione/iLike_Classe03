package it.unisa.ilike.account.storage;

import java.io.Serializable;

import it.unisa.ilike.account.application.GestoreBean;
import it.unisa.ilike.account.application.IscrittoBean;

/**
 * Questa classe rapresenta l'oggetto che identifica se è stato effettuato il login
 * per valutare se è stato effettuato il login.
 * @author Marta
 * @version 0.1
 */
public class Account implements Serializable {

    /**
     * Questo metodo crea un oggetto Account.
     * @param iscrittoBean istanza dell'iscritto che ha effettuato il login
     * @param gestoreBean istanza dell'gestore che ha effettuato il login
     */
    public Account(IscrittoBean iscrittoBean, GestoreBean gestoreBean) {
        this.iscrittoBean = iscrittoBean;
        this.gestoreBean = gestoreBean;
    }

    /**
     * Questo metodo permette di accederere all'iscritto che ha effettuato il login
     * @return l'istanza dell'iscritto che ha efettuato il login, Null altrimenti
     */
    public IscrittoBean getIscrittoBean() {
        return iscrittoBean;
    }

    /**
     * Questo metodo permette di modificare l'istanza dell'iscrttio che ha effettuato il login
     * @param iscrittoBean nuova istanza dell'iscritto, Null in caso di logout
     */
    public void setIscrittoBean(IscrittoBean iscrittoBean) {
        this.iscrittoBean = iscrittoBean;
    }

    /**
     * Questo metodo permette di accederere al gestore che ha effettuato il login
     * @return l'istanza del gestore che ha efettuato il login, Null altrimenti
     */
    public GestoreBean getGestoreBean() {
        return gestoreBean;
    }

    /**
     * Questo metodo permette di modificare l'istanza del gestore che ha effettuato il login
     * @param gestoreBean nuova istanza del gestore, Null in caso di logout
     */
    public void setGestoreBean(GestoreBean gestoreBean) {
        this.gestoreBean = gestoreBean;
    }

    /**
     * Questo metodo permette di valutare chi ha effettuato il login
     * @return True se l'attore è l'iscritto, False se l'attore è il gestore, Null se non è stato effettuato il login
     */
    public Boolean isIscritto() {
        if (iscrittoBean != null)
            return true;
        else
            if (gestoreBean != null)
                return false;
        return null;
    }

    private IscrittoBean iscrittoBean;
    private GestoreBean gestoreBean;
}
