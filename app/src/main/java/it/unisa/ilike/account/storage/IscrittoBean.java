package it.unisa.ilike.account.storage;

import java.sql.Blob;
import java.util.List;

import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

/**
 * Questa classe rappresenta l'iscritto di iLike.
 * @author Marta
 * @version 0.1
 */
public abstract class IscrittoBean extends UtenteBean {

    /**
     * Questo metodo crea un oggetto <code>IscrittoBean</code>
     * @param email rappresenta il testo contenente l'email dell'iscritto
     * @param password rappresenta il testo contenente la password dell'iscritto
     * @param nickname rappresenta il testo contenente il nickname dell'iscritto
     * @param nome rappresenta il testo contenente il nome dell'iscritto
     * @param cognome rappresenta il testo contenente il cognome dell'iscritto
     * @param bio rappresenta il testo contenente la bio dell'iscritto
     */
    public IscrittoBean(String email, String password, String nickname, String nome, String cognome, String bio) {
        super(email, password);
        this.nickname = nickname;
        this.bio = bio;
        this.nome = nome;
        this.cognome = cognome;
    }

    /**
     * Questo metodo permette di accedere al nickname dell'iscritto
     * @return il nickname dell'iscritto
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Questo metodo permette di accedere al nome dell'iscritto
     * @return il nome dell'iscritto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Questo metodo permette di accedere al cognome dell'iscritto
     * @return il cognome dell'iscritto
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Questo metodo permette di accedere alla bio dell'iscritto
     * @return la bio dell'iscritto
     */
    public String getBio() {
        return bio;
    }

    /**
     * Questo metodo permette di accedere alla foto profilo dell'iscritto
     * @return la foto profilo dell'iscritto
     */
    public abstract Blob getFoto();

    /**
     * Questo metodo permette di accedere alle liste dell'iscritto
     * @return le liste dell'iscritto
     */
    public abstract List<ListaBean> getListe();

    /**
     * Questo metodo permette di accedere alle recensioni dove l'iscritto è l'autore
     * @return le recensioni dell'iscritto
     */
    public abstract List<RecensioneBean> getRecensioni();

    /**
     * Questo metodo permette di modificare le liste dell'iscritto
     * @param liste le nuove liste dell'iscritto
     */
    public abstract void setListe(List<ListaBean> liste);

    /**
     * Questo metodo permette di modificare le liste di recensioni  associate all'iscritto
     * @param recensioni le nuove recensioni dell'iscritto
     */
    public abstract void setRecensioni(List<RecensioneBean> recensioni);

    /**
     * Questo metodo permette di modificare la foto profilo
     * @param foto la nuova foto profilo dell'iscritto
     */
    public abstract void setFoto(Blob foto);

    private String nickname, nome, cognome, bio;
}
