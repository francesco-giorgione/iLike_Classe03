package it.unisa.ilike.recensioni.storage;

import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import it.unisa.ilike.QueryManager;

/**
 * Un oggetto <code>RecensioneDAO</code> serve per interagire con la tabella Recensione presente nel database
 * @version 0.2
 * @author LuiginaCostante
 */

public class RecensioneDAO {


    public boolean doSaveRecensione(RecensioneBean recensione){

        if (recensione== null){
            return false;
        }

        String testo= recensione.getTesto();
        int valutazione= recensione.getValutazione();
        Date data= recensione.getData();
        String emailIscritto= recensione.getEmailIscritto();
        int id_contenuto= recensione.getIdContenuto();
        boolean cancellata= recensione.isCancellata();

        String motivazione_cancellazione=recensione.getMotivazioneCancellazione();

        QueryManager queryManager= new QueryManager();
        String query= "insert into Recensione (testo, valutazione, data, cancellata, motivazione_cancellazione, " +
                "email_iscritto, id_contenuto) values ('" +testo + "', '" + valutazione+ "', '" + data + "', " +cancellata+
                ", '"+motivazione_cancellazione+ "', '" + emailIscritto + "', " + id_contenuto+ ");";
        return queryManager.update(query);
    }

    public boolean doDeleteByIdRecensione(int id){

        if (id<1){
            return false;
        }

        QueryManager queryManager= new QueryManager();
        String query = "delete from Recensione where id = " + id;
        return queryManager.update(query);
    }


    public RecensioneBean doRetrieveByIdRecensione(int id){

        if (id<1){
            return null;
        }
        String query= "select * from Recensione where id = " + id;
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);

        Gson gson= new Gson();
        RecensioneBean recensione= gson.fromJson(res, RecensioneBean.class);

        return recensione;
    }

    public List<RecensioneBean> doRetrieveAllRecensione(){

        String query="select * from Recensione";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        List<RecensioneBean> listToReturn = (List<RecensioneBean>) gson.fromJson(res, RecensioneBean.class);

        return listToReturn;
    }

    public int doRetrieveMaxIdRecensione(){

        String query = "select max(id) from (select id from Recensione)";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        int id= gson.fromJson(res, int.class);

        return id;
    }

    public boolean cancellaRecensione(RecensioneBean recensione){

        if (recensione==null)
            return false;
        if (recensione.isCancellata()==false || recensione.getMotivazioneCancellazione()==null)
            return false;

        int id= recensione.getId();
        String motivazioneCancellazione= recensione.getMotivazioneCancellazione();
        String query= "update Recensione set cancellata= "+true+", motivazione_cancellazione= '"+
                motivazioneCancellazione+"' where id= "+ id;
        QueryManager queryManager= new QueryManager();
        queryManager.update(query);

        return true;
    }
}
