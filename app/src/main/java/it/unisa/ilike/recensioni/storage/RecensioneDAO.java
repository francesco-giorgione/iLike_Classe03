package it.unisa.ilike.recensioni.storage;

import java.util.ArrayList;
import java.util.Date;

import it.unisa.ilike.QueryManager;

/**
 * Un oggetto <code>RecensioneDAO</code> serve per interagire con la tabella recensioni presente nel database
 * @version 0.1
 * @author LuiginaCostante
 */

public class RecensioneDAO {

    public boolean doSaveRecensione(RecensioneBean recensione){
        if (recensione== null){
            return false;
        }

        int id= recensione.getId();
        String testo= recensione.getTesto();
        int valutazione= recensione.getValutazione();
        Date data= recensione.getData();
        String emailIscritto= recensione.getEmail_iscritto();
        int id_contenuto= recensione.getId_contenuto();
        boolean cancellata= recensione.isCancellata();

        String motivazione_cancellazione=recensione.getMotivazione_cancellazione();

        QueryManager queryManager= new QueryManager();
        String query= "insert into Recensione (id, testo, valutazione, data, cancellata, motivazione_cancellazione, " +
                "email_iscritto, id_contenuto) values (" +id+ "'" +testo + "'" + valutazione+ "'" + data + "'" +cancellata+
                motivazione_cancellazione+ "'" + emailIscritto + "'" + id_contenuto+ ");";
        return queryManager.update(query);
    }

    public boolean doDeleteRecensione(RecensioneBean recensione){
        if (recensione==null){
            return false;
        }

        int idRecensione= recensione.getId();
        QueryManager queryManager= new QueryManager();
        String query = "delete from Recensione where id = " + idRecensione;
        return queryManager.update(query);
    }


    public ArrayList<RecensioneBean> doRetrieveByIdRecensione(int id){
        if (id<0){
            return null;
        }
        String query= "select * from Recensione where id = " + id;
        QueryManager queryManager= new QueryManager();
        //queryManager.select(query);
        return null;
    }

    public ArrayList<RecensioneBean> doRetrieveAllRecensione(){
        String query="select * from Recensione";
        QueryManager queryManager= new QueryManager();
        //queryManager.select(query);
        return null;
    }

    public int doRetrieveMaxIdRecensione(){
        String query = "select max(id) from (select id from Recensione)";
        QueryManager queryManager= new QueryManager();
        //queryManager.select(query);
        return -1;
    }
}
