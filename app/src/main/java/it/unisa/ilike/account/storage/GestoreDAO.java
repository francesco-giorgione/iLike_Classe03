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

    /*
    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>GestoreBean</code>
     * presente nella tabella Gestori del database, dopo averlo individuato tramite l'email passata come argomento
     * @param email email del gestore da cercare nel database
     * @return null se il parametro email non è valido, l'oggetto gestore con chiave primaria uguale ad email
     * se l'operazione è andata a buon fine
     */
    /*
    public GestoreBean doRetrieveByEmail(String email){

        if(email == null){
            return null;
        }

        String query = "select * from Gestori where email = '" + email + "' ";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        GestoreBean gestore= gson.fromJson(res, GestoreBean.class);
        return gestore;
    }
    */


    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>GestoreBean</code>
     * presente nella tabella Gestori del database, dopo averlo individuato tramite l'email
     * e la password passati come argomenti
     * @param email email del gestore da cercare nel database
     * @param password password crittografata del gestore da cercare nel database
     * @return null se uno o più parametri non sono validi, l'oggetto gestore se l'operazione è andata a buon fine
     */
    public GestoreBean doRetrieveByEmailPassword(String email, String password){

        if (email == null || password == null)
            return null;
        else{
            email = addEscape(email);
            String query = "select * from Gestori where email= '" + email + "' and password= '" + password + "'";
            QueryManager queryManager= new QueryManager();
            String res= queryManager.select(query);
            Gson gson= new Gson();
            GestoreBean gestore= gson.fromJson(res, GestoreBean.class);
            return gestore;
        }
    }

    /**
     * Questo metodo permette di modificare il Gestore memorizzato nel Database
     * @param gestore oggetto GestoreBean da modificare
     * @return True se l'operazione è andata a buon fine, False altrimenti
     */
    public boolean doUpdate(GestoreBean gestore) {
        String query = "update Gestori set num_segnalazioni_gestite where email= '" + addEscape(gestore.getEmail())+ "'";
        QueryManager queryManager= new QueryManager();
        return queryManager.update(query);
    }


    /*public boolean doSave(GestoreBean gestore){

        String email=gestore.getEmail();
        String password= gestore.getPassword();
        int numSegnalazioniGestite=gestore.getNumSegnalazioniGestite();

        String query = "insert into Gestori (email, password, num_segnalazioni_gestite) values ('" +
                email + "', '" + password + "', " + numSegnalazioniGestite+");";
        QueryManager queryManager= new QueryManager();
        return queryManager.update(query);
    }*/
}
