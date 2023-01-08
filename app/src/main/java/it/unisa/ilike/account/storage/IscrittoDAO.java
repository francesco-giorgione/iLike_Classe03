package it.unisa.ilike.account.storage;

import static it.unisa.ilike.utils.Utils.addEscape;

import com.google.gson.Gson;

import java.sql.Blob;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.profili.storage.IscrittoProxyBean;
import it.unisa.ilike.utils.Utils;

/**
 * Un oggetto <code>IscrittoDAO</code> serve per interagire con la tabella Iscritti presente nel database
 * @version 0.2
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

        String email = addEscape(iscritto.getEmail());
        String password = addEscape(iscritto.getPassword());
        String nickname = addEscape(iscritto.getNickname());
        String nome = addEscape(iscritto.getNome());
        String cognome = addEscape(iscritto.getCognome());
        String bio = addEscape(iscritto.getBio());
        Blob foto = iscritto.getFoto();

        String query = "";

        if (bio == null){
            if (foto == null) {
                query = "insert into Iscritti (email, password, nickname, nome, cognome " +
                        "values ('" + email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "');";
            }
            else{
                query = "insert into Iscritti (email, password, nickname, nome, cognome, foto " +
                        "values ('" + email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "', "+
                        foto + ");";
            }
        }
        if (foto==null){
            query = "insert into Iscritti (email, password, nickname, nome, cognome, bio values ('" +
                    email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "', '" +
                    bio + "' );";
        }

        QueryManager queryManager= new QueryManager();
        return queryManager.update(query);
    }

    /*
    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>IscrittoBean</code>
     * presente nella tabella Iscritti del database, dopo averlo individuato tramite l'email passata come argomento
     * @param email email dell'scritto da cercare nel database
     * @return null se il parametro email non è valido, l'oggetto iscritto con chiave primaria uguale ad email
     * se l'operazione è andata a buon fine
     */
    /*
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
    */

    /*
    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>IscrittoBean</code>
     * presente nella tabella Iscritti del database, dopo averlo individuato tramite il nickname passato come argomento
     * @param nickname nickname dell'scritto da cercare nel database
     * @return null se il parametro nickname non è valido, l'oggetto iscritto con nickname uguale alla variabile passata
     * come argomento al metodo se l'operazione è andata a buon fine
     */
    /*
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
    */

    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>IscrittoBean</code>
     * presente nella tabella Iscritti del database, dopo averlo individuato tramite il nickname o l'email
     * e la password passati come argomenti
     * @param nickname nickname dell'iscritto da cercare nel database
     * @param email email dell'iscritto da cercare nel database
     * @param password password crittografata dell'iscritto da cercare nel database
     * @return null se uno o più parametri non sono validi, l'oggetto iscritto se l'operazione è andata a buon fine
     */
    public IscrittoProxyBean doRetrieveByUsernamePassword(String nickname, String email, String password){
        String query;
        if (nickname==null){
            if (email==null)
                return null;
            else{
                if (password==null)
                    return null;
                query = "select email, password, nickname, nome, cognome, bio " +
                        "from Iscritti where email = '" + email + "' and password= '" + password + "'";
            }
        }
        else{
            if (password==null)
                return null;
            nickname = addEscape(nickname);
            query = "select email, password, nickname, nome, cognome, bio " +
                    "from Iscritti where nickname = '" + nickname + "' and password= '" + password + "'";

        }
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        IscrittoProxyBean iscritto= gson.fromJson(res, IscrittoProxyBean.class);
        return iscritto;
    }

    /**
     * Questo metodo permette di caricare la foto profilo dell'utente
     * @param email rappresenta l'email (chiave primaria) dell'utente
     * @return la foto profilo dell'utente con formato Blob
     */
    public Blob doRetriveFoto(String email){
        email = addEscape(email);
        String query = "select foto from Iscritti where email = '" + email + "'";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        Blob foto = gson.fromJson(res, Blob.class);
        return foto;
    }

    /**
     * Restituisce l'iscritto avente una data email.
     * @param email è l'email dell'iscritto che si vuole selezionare.
     * @return un oggetto IscrittoBean contenente le informazioni dell'iscritto selezionato,
     * null se non esiste un iscritto avente email 'email'.
     */
    public IscrittoBean doRetrieveByEmail(String email) {
        if(email == null) {
            return null;
        }

        email = Utils.addEscape(email);
        String query = "select email, nickname, nome, cognome, bio " +
                "from Iscritti " +
                "where email = '" + email + "'";

        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        String jsonRes = queryManager.select(query);
        System.out.println(jsonRes);

        IscrittoProxyBean[] res = gson.fromJson(jsonRes, IscrittoProxyBean[].class);

        if(res.length == 0)
            return null;
        return res[0];
    }
}
