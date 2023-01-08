package it.unisa.ilike.recensioni.storage;

import static it.unisa.ilike.utils.Utils.addEscape;

import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.contenuti.storage.ContenutoDAO;
import it.unisa.ilike.utils.Utils;

/**
 * Un oggetto <code>RecensioneDAO</code> serve per interagire con la tabella Recensioni presente nel database
 * @version 0.5
 * @author LuiginaCostante
 */

public class RecensioneDAO {

    /**
     * Questo metodo consente di salvare nella tabella Recensioni del database un nuovo oggetto della classe
     * <code>RecensioneBean</code> passato come argomento
     * @param recensione oggetto della classe <code>RecensioneBean</code> da salvare nel database
     * @return false se la recensione passata come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
     */
    public boolean doSaveRecensione(RecensioneBean recensione){
        if (recensione== null){
            return false;
        }

        String testo = Utils.addEscape(recensione.getTesto());
        int valutazione = recensione.getValutazione();
        String data = Utils.getStringaDataForSql(recensione.getData());
        String emailIscritto = Utils.addEscape(recensione.getIscritto().getEmail());
        int idContenuto = recensione.getContenuto().getId();
        int cancellata = recensione.isCancellata() ? 1 : 0;
        String motivazione_cancellazione = Utils.addEscape(recensione.getMotivazioneCancellazione());

        String query = "insert into Recensioni (testo, valutazione, data, cancellata, motivazione_cancellazione, email_iscritto, id_contenuto) " +
                "values ('" + testo + "', '" + valutazione + "', '" + data + "', " + cancellata + ", '" + motivazione_cancellazione+ "', '"
                + emailIscritto + "', " + idContenuto + ")";

        QueryManager queryManager = new QueryManager();
        ContenutoDAO contenutoDAO = new ContenutoDAO();

        if(queryManager.update(query)) {
            return contenutoDAO.doUpdateValutazioneMedia(recensione.getContenuto().getId(), contenutoDAO.
                    calcolaValutazioneMediaAggiornata(recensione.getContenuto().getId(), recensione.getValutazione()));
        }
        else return false;
    }


    /*

     * Questo metodo consente di cancellare un oggetto <code>RecensioneBean</code> dalla tabella
     * Recensioni del database, individuandolo tramite l'id passato come argomento.
     * @param id id della recensione da cancellare dal database
     * @return false se l'id passato come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti

    public boolean doDeleteByIdRecensione(int id){

        if (id<1){
            return false;
        }

        QueryManager queryManager= new QueryManager();
        String query = "delete from Recensioni where id = " + id;
        return queryManager.update(query);
    }*/


    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>RecensioneBean</code>
     * presente nella tabella Recensioni del database, dopo averlo individuato tramite l'id passato come argomento
     * @param id id della recensione da cercare nel database
     * @return null se il parametro id non è valido, l'oggetto recensione con chiave primaria uguale ad id
     * se l'operazione è andata a buon fine
     */
    public RecensioneBean doRetrieveByIdRecensione(int id){

        if (id<1){
            return null;
        }
        String query= "select * from Recensioni where id = " + id;
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        RecensioneBean recensione= gson.fromJson(res, RecensioneBean.class);

        return recensione;
    }

    /**
     * Questo metodo restituisce tutti gli oggetti della classe <code>RecensioneBean</code>
     * memorizzati nel database che non risultano marcati come "cancellati"
     * @return lista di oggetti della classe <code>RecensioneBean</code> memorizzata nel database
     */
    public List<RecensioneBean> doRetrieveAllRecensioniNonCancellate(){

        String query="select * from Recensioni where cancellata=false";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        List<RecensioneBean> listToReturn = (List<RecensioneBean>) gson.fromJson(res, RecensioneBean.class);

        return listToReturn;
    }

    /**
     * Questo metodo restituisce un intero che rappresenta l'id con valore maggiore presente nella tabella Recensioni del database
     * @return id con valore maggiore presente nella tabella Recensioni del database
     */
    public int doRetrieveMaxIdRecensione(){

        String query = "select max(id) from (select id from Recensioni)";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        int id= gson.fromJson(res, int.class);

        return id;
    }

    /**
     * Questo metodo consente di marcare come "cancellata" una recensione della tabella Recensioni del database, a seguito
     * di una decisione presa dal gestore. Permette inoltre di inserire la motivazione della cancellazione relativa alla
     * recensione passata come argomento al metodo.
     * @param recensione recensione da marcare come "cancellata"
     * @return false se la recensione o la motivazione della cancellazione sono nulle, se risulta essere già stata
     * marcata come "cancellata" o se l'operazione NON è andata a buon fine. True altrimenti
     */
    public boolean cancellaRecensione(RecensioneBean recensione){

        if (recensione==null)
            return false;
        if (recensione.isCancellata()==false || recensione.getMotivazioneCancellazione()==null)
            return false;

        int id= recensione.getId();
        String motivazioneCancellazione= addEscape(recensione.getMotivazioneCancellazione());
        String query= "update Recensioni set cancellata= true, motivazione_cancellazione= '"+
                motivazioneCancellazione+"' where id= "+ id;
        QueryManager queryManager= new QueryManager();
        queryManager.update(query);

        return true;
    }

    /**
     * Questo metodo consente di receperare dal DataBase tutte le recensioni di cui l'iscritto ne è l'autore
     * @param email rappresenta l'email dell'iscritto autore delle recensioni
     * @return la lista di recensioni scritte dall'iscritto
     */
    public List<RecensioneBean> doRetriveRecensioniByEmailIscritto(String email){

        email = addEscape(email);
        String query="select * from Recensioni where email_iscritto = " + email + "'";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        List<RecensioneBean> listToReturn = (List<RecensioneBean>) gson.fromJson(res, RecensioneBean.class);

        return listToReturn;
    }
}
