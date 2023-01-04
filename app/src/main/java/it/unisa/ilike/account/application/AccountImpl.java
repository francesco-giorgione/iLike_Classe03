package it.unisa.ilike.account.application;

/**
 * Un oggetto <code>AccountImpl</code> viene utilizzato per accedere ai servizi di autenticazione.
 * @author Marta
 * @version 0.1
 */
public class AccountImpl implements AccountService {

    /**
     * Questo metodo consente di recuperare l’utente dal DB tramite le sue credenziali.
     * @param email rappresenta l'email/nickmane dell'utente
     * @param password rappresenta la password dell'utente
     */
    public void login(String email, String password){

    }

    /**
     * uesto metodo restituisce un bean contenente le informazioni dell’iscritto
     * a meno della foto profilo, delle liste e delle recensioni ad esso associato.
     * @return l'oggetto IscrittoBean recuperata dal DB
     */
    public IscrittoBean getIscritto(){

    }

    /**
     * Questo metodo restituisce un bean contenente le informazioni del gestore.
     * @return l'oggetto GestoreBean recuperata dal DB
     */
    public GestoreBean getGestore(){

    }

    /**
     * Questo metodo consente di effettuare il logout dell’utente.
     * @param u rappresenta l'oggetto utente che deve vuole effettuare il logout
     */
    public void logout(Utente u){

    }

    /**
     * Questo metodo consente di effettuare la registrazione di un iscritto.
     * @param email rappresenta l'email dell'iscritto con la quale effettuare il login
     * @param password rappresenta la password dell'iscritto con la quale effettuare il login
     * @param nome rappresenta il testo contenente il nome dell'iscritto
     * @param cognome rappresenta il testo contenente il cognome dell'iscritto
     * @param nickname rappresenta il nickname dell'iscritto con la quale effettuare il login
     * @param bio rappresenta il testo contenente la bio dell'iscritto
     * @param foto rappresenta la foto profilo dell'iscritto
     * @return l'oggetto IscrittoBean se la registrazione è andata a buon fine, Null altrimenti
     */
    public IscrittoBean registrazioneIscritto(String email, String password, String nome,
                                              String cognome, String nickname, String bio, Blob foto){

    }

}
