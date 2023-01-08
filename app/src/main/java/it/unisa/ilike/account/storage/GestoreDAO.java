package it.unisa.ilike.account.storage;

import static it.unisa.ilike.utils.Utils.addEscape;

import com.google.gson.Gson;
import it.unisa.ilike.QueryManager;

/**
 * Un oggetto <code>GestoreDAO</code> serve per interagire con la tabella Gestori presente nel database
 * @version 0.2
 * @author LuiginaCostante
 */

public class GestoreDAO {

    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>GestoreBean</code>
     * presente nella tabella Gestori del database, dopo averlo individuato tramite l'email
     * e la password passati come argomenti
     * @param email email del gestore da cercare nel database
     * @param password password crittografata del gestore da cercare nel database
     * @return null se uno o più parametri non sono validi o se le credenziali sono errate,
     * l'oggetto gestore se l'operazione è andata a buon fine
     */
    public GestoreBean doRetrieveByEmailPassword(String email, String password){
        if (email == null || password == null)
            return null;

        email = addEscape(email);
        String query = "select email, num_segnalazioni_gestite as numSegnalazioniGestite" +
                " from Gestori " +
                "where email = '" + email + "' and password= '" + password + "'";

        Gson gson= new Gson();
        QueryManager queryManager= new QueryManager();
        String jsonRes = queryManager.select(query);

        GestoreBean[] res = gson.fromJson(jsonRes, GestoreBean[].class);

        if(res.length == 0)
            return null;
        return res[0];
    }

    /**
     * Questo metodo permette di modificare il Gestore memorizzato nel Database
     * @param gestore oggetto GestoreBean da modificare
     * @return True se l'operazione è andata a buon fine, False altrimenti
     */
    public boolean doUpdate(GestoreBean gestore) {
        String query = "update Gestori " +
                "set num_segnalazioni_gestite = " + gestore.getNumSegnalazioniGestite() + " " +
                "where email = '" + addEscape(gestore.getEmail())+ "'";

        QueryManager queryManager= new QueryManager();
        return queryManager.update(query);
    }
}
