package it.unisa.ilike.account.application;

import java.sql.Blob;

import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.UtenteBean;

/**
 * Interfaccia esplicita i metodi di servizio per effettuare operazioni di autenticazione.
 * @author Marta
 * @version 0.1
 */
public interface AccountService {

    public void login(String email, String password);
    public IscrittoBean getIscritto();
    public GestoreBean getGestore();
    public void logout(UtenteBean u);
    public IscrittoBean registrazioneIscritto(String email, String password, String nome, String cognome, String nickname, String bio, Blob foto);

}
