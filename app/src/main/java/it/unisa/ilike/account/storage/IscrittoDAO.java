package it.unisa.ilike.account.storage;

import com.google.gson.Gson;

import java.sql.Blob;

import it.unisa.ilike.QueryManager;

/**
 * Un oggetto <code>IscrittoDAO</code> serve per interagire con la tabella Iscritti presente nel database
 * @version 0.1
 * @author LuiginaCostante
 */

public class IscrittoDAO {

    /**
     * Questo metodo consente di salvare nella tabella Iscritti del database un nuovo oggetto della classe
     * <code>IscrittoBean</code> passato come argomento
     * @param iscritto oggetto della classe <code>IscrittoBean</code> da salvare nel database
     * @return false se l'iscritto passato come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
     */

    public boolean doSave(IscrittoBean iscritto){

        String email=iscritto.getEmail();
        String password= iscritto.getPassword();
        String nickname=iscritto.getNickname();
        String nome=iscritto.getNome();
        String cognome=iscritto.getCognome();
        String bio= iscritto.getBio();
        Blob foto=iscritto.getFoto();

        String query="";

        if (bio==null){
            if (foto==null) {
                query = "insert into Iscritti (email, password, nickname, nome, cognome values ('" +
                        email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "');";
            }
            else{
                query = "insert into Iscritti (email, password, nickname, nome, cognome, foto values ('" +
                        email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "', "+
                        foto+ ");";
            }
        }
        if (foto==null){
            query = "insert into Iscritti (email, password, nickname, nome, cognome, bio values ('" +
                    email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "', '"+
                    bio+ "' );";
        }

        QueryManager queryManager= new QueryManager();
        return queryManager.update(query);
    }

    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>IscrittoBean</code>
     * presente nella tabella Iscritti del database, dopo averlo individuato tramite l'email passata come argomento
     * @param email email dell'scritto da cercare nel database
     * @return null se il parametro email non è valido, l'oggetto iscritto con chiave primaria uguale ad email
     * se l'operazione è andata a buon fine
     */

    public IscrittoBean doRetrieveByEmail(String email){

        if(email == null){
            return null;
        }

        String query = "select * from Iscritti where email = '" + email + "' ";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        IscrittoBean iscritto= gson.fromJson(res, IscrittoBean.class);
        return iscritto;
    }

    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>IscrittoBean</code>
     * presente nella tabella Iscritti del database, dopo averlo individuato tramite il nickname passato come argomento
     * @param nickname nickname dell'scritto da cercare nel database
     * @return null se il parametro nickname non è valido, l'oggetto iscritto con nickname uguale alla variabile passata
     * come argomento al metodo se l'operazione è andata a buon fine
     */

    public IscrittoBean doRetrieveByNickname(String nickname){

        if(nickname == null){
            return null;
        }

        String query = "select * from Iscritti where nickname = '" + nickname + "' ";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        IscrittoBean iscritto= gson.fromJson(res, IscrittoBean.class);
        return iscritto;
    }

}
