package it.unisa.ilike.account.storage;

import static it.unisa.ilike.utils.Utils.addEscape;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.liste.storage.ListaDAO;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;
import it.unisa.ilike.utils.Utils;

/**
 * Un oggetto <code>IscrittoDAO</code> serve per interagire con la tabella Iscritti presente nel database
 * @version 0.2
 * @author LuiginaCostante
 */

public class IscrittoDAO {
    private class RisultatoQuery {
        int id;
    }
    private class RisultatoQuery2 {
        String emailIscritto, nomeLista;
    }

    /**
     * Questo metodo consente di salvare nella tabella Iscritti del database un nuovo oggetto della classe
     * <code>IscrittoBean</code> passato come argomento
     * @param iscritto oggetto della classe <code>IscrittoBean</code> da salvare nel database
     * @return false se l'iscritto passato come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
     */
    public boolean doSave(IscrittoBean iscritto){
        String email = Utils.addEscape(iscritto.getEmail());
        String password = Utils.addEscape(iscritto.getPassword());
        String nickname = Utils.addEscape(iscritto.getNickname());
        String nome = Utils.addEscape(iscritto.getNome());
        String cognome = Utils.addEscape(iscritto.getCognome());
        String bio = Utils.addEscape(iscritto.getBio());
        InputStream foto = iscritto.getFoto();

        String query = "";

        if (bio.equals("")){
            if (foto == null) {
                query = "insert into Iscritti (email, password, nickname, nome, cognome) " +
                        "values ('" + email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "');";
            }
            else {
                query = "insert into Iscritti (email, password, nickname, nome, cognome, foto) " +
                        "values ('" + email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "', " +
                        foto + ");";
            }
        }
        if (foto == null){
            query = "insert into Iscritti (email, password, nickname, nome, cognome, bio) " +
                    "values ('" +
                    email + "', '" + password + "', '" + nickname + "', '" + nome + "', '" + cognome + "', '" +
                    bio + "' );";
        }

        QueryManager queryManager= new QueryManager();
        return queryManager.update(query);
    }

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
        if(password == null || (nickname == null && email == null)) {
            return null;
        }
        String query;

        if(email != null) {
            email = Utils.addEscape(email);
            query = "select email, password, nickname, nome, cognome, bio " +
                    "from Iscritti " +
                    "where email = '" + email + "' and password = '" + password + "'";
        }
        else{
            nickname = Utils.addEscape(nickname);
            query = "select email, password, nickname, nome, cognome, bio " +
                    "from Iscritti " +
                    "where nickname = '" + nickname + "' and password = '" + password + "'";

        }

        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        String jsonRes = queryManager.select(query);
        IscrittoProxyBean[] res = gson.fromJson(jsonRes, IscrittoProxyBean[].class);

        if(res.length == 0) {
            return null;
        }
        return res[0];
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

        IscrittoProxyBean[] res = gson.fromJson(jsonRes, IscrittoProxyBean[].class);

        if(res.length == 0)
            return null;
        return res[0];
    }


    /**
     * Questo metodo permette di caricare la foto profilo dell'utente
     * @param email rappresenta l'email (chiave primaria) dell'utente
     * @return la foto profilo dell'utente con formato Blob
     */
    public InputStream doRetriveFoto(String email){
        email = addEscape(email);
        String query = "select foto " +
                "from Iscritti " +
                "where email = '" + email + "'";

        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        String res = queryManager.select(query);

        return gson.fromJson(res, InputStream.class);
    }


    /**
     * Il metodo restituisce una collezione delle liste appartenenti all'iscritto avente una
     * data mail.
     * @param email è l'email dell'iscritto di cui si vogliono selezionare le recensioni scritte.
     * @return un oggetto ArrayList<ListaBean>
     */
    public List<ListaBean> doRetrieveListe(String email) {
        email = Utils.addEscape(email);
        String query = "select email_iscritto as emailIscritto, nome as nomeLista " +
                "from Liste " +
                "where email_iscritto = '" + email + "'";

        List<ListaBean> liste = new ArrayList<>();
        Gson gson = new Gson();
        String jsonRes = new QueryManager().select(query);
        RisultatoQuery2[] res = gson.fromJson(jsonRes, RisultatoQuery2[].class);

        for(RisultatoQuery2 curr: res) {
            liste.add(new ListaDAO().doRetrieveByKey(curr.nomeLista, curr.emailIscritto));
        }

        return liste;
    }


    /**
     * Il metodo restituisce una collezione delle recensioni scritte da un iscritto avente una
     * data mail.
     * @param email è l'email dell'iscritto di cui si vogliono selezionare le recensioni scritte.
     * @return un oggetto ArrayList<RecensioneBean>
     */
    public List<RecensioneBean> doRetrieveRecensioni(String email) {
        email = Utils.addEscape(email);
        String query = "select id " +
                "from Recensioni " +
                "where email_iscritto = '" + email + "'";

        List<RecensioneBean> recensioni = new ArrayList<>();
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        Gson gson = new Gson();
        String jsonRes = new QueryManager().select(query);
        RisultatoQuery[] res = gson.fromJson(jsonRes, RisultatoQuery[].class);

        for(RisultatoQuery curr: res) {
            recensioni.add(recensioneDAO.doRetrieveByIdRecensione(curr.id));
        }

        return recensioni;
    }
}
