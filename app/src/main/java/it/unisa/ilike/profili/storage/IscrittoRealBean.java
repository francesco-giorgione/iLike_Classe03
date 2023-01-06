package it.unisa.ilike.profili.storage;

import java.sql.Blob;
import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

public class IscrittoRealBean extends IscrittoBean {

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
        this.foto = null;
    }

    /**
     * Questo metodo crea un oggetto <code>IscrittoRealBean</code> con la fotoProfilo
     * @param email rappresenta il testo contenente l'email dell'iscritto
     * @param password rappresenta il testo contenente la password dell'iscritto
     * @param nickname rappresenta il testo contenente il nickname dell'iscritto
     * @param nome rappresenta il testo contenente il nome dell'iscritto
     * @param cognome rappresenta il testo contenente il cognome dell'iscritto
     * @param bio rappresenta il testo contenente la bio dell'iscritto
     * @param foto rappresenta la foto profilo dell'iscritto
     */
    public IscrittoRealBean(String email, String password, String nickname, String nome, String cognome,
                            String bio, Blob foto) {
        super(email, password, nickname, nome, cognome, bio);
        this.foto = foto;
    }

    /**
     * Questo metodo permette di accedere alla foto profilo dell'iscritto
     *
     * @return la foto profilo dell'iscritto
     */
    @Override
    public Blob getFoto() {
        if(this.foto == null){
            IscrittoDAO iscrittoDAO = new IscrittoDAO();
            this.foto = iscrittoDAO.doRetriveFoto(this.getEmail());
        }
        return this.foto;
    }

    /**
     * Questo metodo permette di accedere alle liste dell'iscritto
     *
     * @return le liste dell'iscritto
     */
    @Override
    public List<ListaBean> getListe() {
        if(this.liste == null){
            IscrittoDAO iscrittoDAO = new IscrittoDAO();
            this.liste = iscrittoDAO.doRetriveListe(this.getEmail());
        }
        return this.liste;
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

    private List<ListaBean> liste;
    private List<RecensioneBean> recensioni;
    private Blob foto;
}
