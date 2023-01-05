package it.unisa.ilike.account.storage;

/**
 * Questa classe rappresenta l'utente generico di iLike.
 * @author Marta
 * @version 0.1
 */
public abstract class UtenteBean {

    /**
     * Questo metodo crea un oggetto UtenteBean
     * @param id rappresenta l'id a cui è associato l'utente
     * @param email rappresenta il testo contenente l'email dell'utente
     * @param password rappresenta il testo contenente la password dell'utente
     */
    public UtenteBean(int id, String email, String password){
        this.email = email;
        this.password = password;
        this.id = id;
    }

    /**
     * Questo metodo permette di accedere all'email dell'utente
     * @return la stringa contente l'email dell'utente
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Questo metodo permette di accedere alla password dell'utente
     * @return la stringa contente la password dell'utente
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Questo metodo permette di accedere all'identificativo dell'utente
     * @return l'inter contente l'id dell'utente
     */
    public int getId(){
        return this.id;
    }


    private String email, password;
    private int id;
}
