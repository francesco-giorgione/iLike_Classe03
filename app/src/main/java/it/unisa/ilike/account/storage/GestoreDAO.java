package it.unisa.ilike.account.storage;

import com.google.gson.Gson;

import it.unisa.ilike.QueryManager;

/**
 * Un oggetto <code>GestoreDAO</code> serve per interagire con la tabella Gestori presente nel database
 * @version 0.1
 * @author LuiginaCostante
 */

public class GestoreDAO {

    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>GestoreBean</code>
     * presente nella tabella Gestori del database, dopo averlo individuato tramite l'email passata come argomento
     * @param email email del gestore da cercare nel database
     * @return null se il parametro email non è valido, l'oggetto gestore con chiave primaria uguale ad email
     * se l'operazione è andata a buon fine
     */

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
