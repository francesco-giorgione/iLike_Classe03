package it.unisa.ilike.account.application;

import java.sql.Blob;

import it.unisa.ilike.account.application.exceptions.CredenzialiErrateException;
import it.unisa.ilike.account.application.exceptions.CredenzialiVuoteException;
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

    public Account login(String email, String password) throws CredenzialiVuoteException, CredenzialiErrateException;
    public Account logout(UtenteBean u);
    public Account registrazioneIscritto(String email, String password, String nome, String cognome, String nickname, String bio, Blob foto) throws EmailVuotaException, PasswordVuotaException, DatiIscrittoVuotiException;

}
