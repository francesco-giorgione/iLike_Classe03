package it.unisa.ilike.account.application;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unisa.ilike.account.application.exceptions.CredenzialiErrateException;
import it.unisa.ilike.account.application.exceptions.CredenzialiVuoteException;
import it.unisa.ilike.account.application.exceptions.DatiIscrittoVuotiException;
import it.unisa.ilike.account.application.exceptions.EmailVuotaException;
import it.unisa.ilike.account.application.exceptions.PasswordVuotaException;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.account.storage.GestoreDAO;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.account.storage.UtenteBean;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.liste.storage.ListaDAO;
import it.unisa.ilike.profili.storage.IscrittoProxyBean;
import it.unisa.ilike.profili.storage.IscrittoRealBean;

/**
 * Un oggetto <code>AccountImpl</code> viene utilizzato per accedere ai servizi di autenticazione.
 * @author Marta
 * @version 0.2
 */
public class AccountImpl implements AccountService {

    /**
     * Questo metodo controlla se il nickname rispetta i vincoli imposti
     * @param nickname rappresenta la string inserita dall'iscrtto per il nickname
     * @return True se il campo rispetta i vincoli, False altrimenti
     */
    private boolean isNickname(String nickname) {
        Pattern pattern = Pattern.compile("^[A-z0-9'_#& ]{3,30}$");
        Matcher matcher = pattern.matcher(nickname);
        return matcher.matches();
    }


    /**
     * Questo metodo controlla se l'email rispetta i vincoli imposti
     * @param email rappresenta la string inserita dall'iscrtto per l'email
     * @return True se il campo rispetta i vincoli, False altrimenti
     */
    private boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * Questo metodo controlla se la password rispetta i vincoli imposti
     * @param password rappresenta la string inserita dall'iscrtto per la password
     * @return True se il campo rispetta i vincoli, False altrimenti
     */
    private boolean isPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    /**
     * Questo metodo controlla se il nome rispetta i vincoli imposti
     * @param nome rappresenta la string inserita dall'iscrtto per il nome
     * @return True se il campo rispetta i vincoli, False altrimenti
     */
    private boolean isNome(String nome) {
        Pattern pattern = Pattern.compile("^[A-z' ]{3,50}$");
        Matcher matcher = pattern.matcher(nome);
        return matcher.matches();
    }


    /**
     * Questo metodo controlla se il cognome rispetta i vincoli imposti
     * @param cognome rappresenta la string inserita dall'iscrtto per il cognome
     * @return True se il campo rispetta i vincoli, False altrimenti
     */
    private boolean isCognome(String cognome) {
        Pattern pattern = Pattern.compile("^[A-z' ]{3,50}$");
        Matcher matcher = pattern.matcher(cognome);
        return matcher.matches();
    }


    /**
     * Questo metodo permette di codificare la password dell'utente
     * @param passwordUtente password inserita dall'utente da codificare
     * @throws NoSuchAlgorithmException è generata quando un particolare algoritmo crittografico
     * richiesto non è disponibile nell'ambiente.
     * @return la pa
     */
    private String getPasswordCrittografata(String passwordUtente) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedPwd = digest.digest(passwordUtente.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for(byte bit: hashedPwd){
            builder.append(String.format("%02x", bit));
        }
        return builder.toString();
    }


    /** @inheritDoc */
    public Account login(String email, String password) throws CredenzialiVuoteException, CredenzialiErrateException {
        IscrittoDAO iscrittoDAO = new IscrittoDAO();
        GestoreDAO gestoreDAO = new GestoreDAO();
        IscrittoBean iscrittoBean = null;
        GestoreBean gestoreBean = null;
        String passwordCrittografata = null;
        if(isPassword(password)){
            try {
                passwordCrittografata = getPasswordCrittografata(password);
            }catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (isEmail(email)){
                iscrittoBean = iscrittoDAO.doRetrieveByUsernamePassword(null, email, passwordCrittografata);
                gestoreBean = gestoreDAO.doRetrieveByEmailPassword(email, passwordCrittografata);
            }else {
                if (isNickname(email)){
                    iscrittoBean = iscrittoDAO.doRetrieveByUsernamePassword(email, null, passwordCrittografata);
                }else throw new CredenzialiVuoteException();
            }
        }else throw new CredenzialiVuoteException();

        if (iscrittoBean == null && gestoreBean == null)
            throw new CredenzialiErrateException();

        return new Account(iscrittoBean, gestoreBean);
    }


    /** @inheritDoc */
    public Account logout(UtenteBean u){
        if (u instanceof IscrittoBean){
            IscrittoBean iscritto = (IscrittoBean) u;
            //se l'iscritto non è di tipo IscrittoRealBean, non ha mai modificato recensioni/liste
            if(iscritto instanceof IscrittoRealBean){
                ListaDAO listaDAO = new ListaDAO();
                for (ListaBean lista : iscritto.getListe()){
                    listaDAO.doUpdate(lista);
                }
            }
        }else {     //l'attore è un gestore
            GestoreBean gestore = (GestoreBean) u;
            GestoreDAO gestoreDAO = new GestoreDAO();
            gestoreDAO.doUpdate(gestore);
        }
        return new Account(null, null);
    }


    /** @inheritDoc */
    public Account registrazioneIscritto(String email, String password, String nome,
                                         String cognome, String nickname, String bio, InputStream foto)
            throws EmailVuotaException, PasswordVuotaException, DatiIscrittoVuotiException {

        IscrittoBean iscritto;
        String passwordCrittografata = null;

        if(isEmail(email)){
            if(isPassword(password)){
                if(isNome(nome) && isCognome(cognome) && isNickname(nickname)){

                    try {
                        passwordCrittografata = getPasswordCrittografata(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    if (foto == null){
                        iscritto = new IscrittoProxyBean(email, passwordCrittografata, nickname, nome, cognome, bio);
                    }
                    else {
                        iscritto = new IscrittoRealBean(email, passwordCrittografata, nickname, nome, cognome, bio, foto);
                    }

                    IscrittoDAO iscrittoDAO = new IscrittoDAO();
                    iscrittoDAO.doSave(iscritto);
                    Account account = new Account(iscritto, null);
                    return account;

                }else throw new DatiIscrittoVuotiException();
            }else throw new PasswordVuotaException();
        }
        else throw new EmailVuotaException();
    }
}
