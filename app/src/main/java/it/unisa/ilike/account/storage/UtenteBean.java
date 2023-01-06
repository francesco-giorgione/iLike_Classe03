package it.unisa.ilike.account.storage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Questa classe rappresenta l'utente generico di iLike.
 * @author Marta
 * @version 0.1
 */
public abstract class UtenteBean {

    /**
     * Questo metodo crea un oggetto UtenteBean
     * @param email rappresenta il testo contenente l'email dell'utente
     * @param password rappresenta il testo contenente la password dell'utente
     */
    public UtenteBean(String email, String password){
        this.email = email;
        this.password = password;
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


    private String email, password;
}
