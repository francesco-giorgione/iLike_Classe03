package it.unisa.ilike.account.application;

import it.unisa.ilike.account.application.exceptions.CredenzialiErrateException;
import it.unisa.ilike.account.application.exceptions.DatiIscrittoVuotiException;
import it.unisa.ilike.account.application.exceptions.EmailVuotaException;
import it.unisa.ilike.account.application.exceptions.PasswordVuotaException;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.UtenteBean;

/**
 * Interfaccia esplicita i metodi di servizio per effettuare operazioni di autenticazione.
 * @author Marta
 * @version 0.3
 */
public interface AccountService {

    /**
     * Questo metodo consente di recuperare l’utente dal DB tramite le sue credenziali.
     * @param email rappresenta l'email/nickmane dell'utente
     * @param password rappresenta la password dell'utente
     * @pre <code>(3 <= email.length() <= 100 AND 8 <= password.length() <= 25) or
     * (8 <= password.length() <= 25 AND 3 <= nickname.length() <= 30)</code>
     * @post se email & password corrispondono all’iscritto: account.iscritto = {obj corrispondente},
     * account.gestore = Null;
     * se email & password corrispondono al gestore: account.iscritto = Null ,
     * account.gestore = {obj corrispondente}.
     * @return l'oggetto account salvato nell'application context
     * @throws CredenzialiErrateException: se l’email e/o la password sono errate e non è possibile individuare l’attore.
     */
    Account login(String email, String password) throws CredenzialiErrateException;


    /**
     * Questo metodo consente di effettuare il logout dell’utente.
     * @param u rappresenta l'oggetto utente che deve vuole effettuare il logout
     * @post account.iscritto = Null, account.gestore = Null
     * @return un oggetto <code>Account</code> con variabili iscritto e gestore impostate a Null
     */
    Account logout(UtenteBean u);


    /**
     * Questo metodo consente di effettuare la registrazione di un iscritto.
     * @param email rappresenta l'email dell'iscritto con la quale effettuare il login
     * @param password rappresenta la password dell'iscritto con la quale effettuare il login
     * @param nome rappresenta il testo contenente il nome dell'iscritto
     * @param cognome rappresenta il testo contenente il cognome dell'iscritto
     * @param nickname rappresenta il nickname dell'iscritto con la quale effettuare il login
     * @param bio rappresenta il testo contenente la bio dell'iscritto
     * @pre <code>email.match(‘^[A-z0-9\.\+_-]+@[A-z0-9\._-]+\.[A-z]{2,6}$’) AND password.match
     * (‘^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$’) AND 3 <= nome.length() <= 50
     * AND 3 <= cognome.length() <= 50 AND 3 <= nickname.length() <= 30</code>
     * @post account.iscritto = {obj corrispondente} AND a.gestore = Null AND
     * sia S l’insieme contenente tutti gli iscritti di iLike e sia i il nuovo iscritto,
     * allora <code>S’ = S + {i} </code> con S’ lo l’insieme aggiornato.
     * @return l'oggetto Account se la registrazione è andata a buon fine, Null altrimenti
     * @throws EmailVuotaException: se il campo email non rispetta il limite minimo o massimo.
     * @throws PasswordVuotaException: se il campo password non rispetta il limite minimo o massimo.
     * @throws DatiIscrittoVuotiException: se i campi nome, cognome, nickname non rispettano il
     * limite minimo o massimo.
     */
    Account registrazioneIscritto(String email, String password, String nome, String cognome,
                                  String nickname, String bio)
            throws EmailVuotaException, PasswordVuotaException, DatiIscrittoVuotiException;

}
