package it.unisa.ilike.profili.storage;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.contenuti.storage.LibroDAO;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.liste.storage.ListaDAO;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;

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
        this.recensioni = new ArrayList<>();
        this.liste = new ArrayList<>();
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
        this.recensioni = new ArrayList<>();
        this.liste = new ArrayList<>();
    }

    /**
     * Questo metodo permette di accedere alla foto profilo dell'iscritto
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
     * @return le liste dell'iscritto
     */
    @Override
    public List<ListaBean> getListe() {
        if(this.liste == null){
            ListaDAO listaDAO = new ListaDAO();
            this.liste = listaDAO.doRetrieveListeByEmailIscritto(this.getEmail());
        }
        return this.liste;
    }

    /**
     * Questo metodo permette di accedere alle recensioni dove l'iscritto è l'autore
     * @return le recensioni dell'iscritto
     */
    @Override
    public List<RecensioneBean> getRecensioni() {
        if(this.recensioni == null){
            RecensioneDAO recensioneDAO = new RecensioneDAO();
            this.recensioni = recensioneDAO.doRetriveRecensioniByEmailIscritto(this.getEmail());
        }
        return this.recensioni;
    }

    /**
     * Questo metodo permette di modificare le liste dell'iscritto
     * @param liste le nuove liste dell'iscritto
     */
    @Override
    public void setListe(List<ListaBean> liste) {
        this.liste = liste;
    }

    /**
     * Questo metodo permette di modificare le liste di recensioni  associate all'iscritto
     * @param recensioni le nuove recensioni dell'iscritto
     */
    @Override
    public void setRecensioni(List<RecensioneBean> recensioni) {
        this.recensioni = recensioni;
    }

    /**
     * Questo metodo permette di modificare la foto profilo
     * @param foto la nuova foto profilo dell'iscritto
     */
    @Override
    public void setFoto(Blob foto) {
        this.foto = foto;
    }


    private List<ListaBean> liste;
    private List<RecensioneBean> recensioni;
    private Blob foto;
}
