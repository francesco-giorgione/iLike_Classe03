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

    /**
     * Questo metodo permette di codificare la password dell'utente
     * @param passwordUtente password inserita dall'utente da codificare
     * @throws NoSuchAlgorithmException è generata quando un particolare algoritmo crittografico
     * richiesto non è disponibile nell'ambiente.
     */
    public void setPassword(String passwordUtente) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedPwd = digest.digest(passwordUtente.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for(byte bit: hashedPwd){
            builder.append(String.format("%02x", bit));
        }
        this.password = builder.toString();
    }

    private String email, password;
}
