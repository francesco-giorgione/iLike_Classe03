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
 * @version 0.2
 */
public interface AccountService {

    /**
     * Questo metodo consente di recuperare l’utente dal DB tramite le sue credenziali.
     * @param email rappresenta l'email/nickmane dell'utente
     * @param password rappresenta la password dell'utente
     * @return l'oggetto account salvato nell'application context
     */
    Account login(String email, String password) throws CredenzialiErrateException;


    /**
     * Questo metodo consente di effettuare il logout dell’utente.
     * @param u rappresenta l'oggetto utente che deve vuole effettuare il logout
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
     * @return l'oggetto Account se la registrazione è andata a buon fine, Null altrimenti
     */
    Account registrazioneIscritto(String email, String password, String nome, String cognome,
                                  String nickname, String bio)
            throws EmailVuotaException, PasswordVuotaException, DatiIscrittoVuotiException;

}
