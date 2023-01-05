package it.unisa.ilike.segnalazioni.storage;

import com.google.gson.Gson;

import java.util.List;

import it.unisa.ilike.QueryManager;

/**
 * Un oggetto <code>SegnalazioneDAO</code> serve per interagire con la tabella Segnalazione presente nel database
 * @version 0.3
 * @author LuiginaCostante
 */

public class SegnalazioneDAO {

    /**
     * Questo metodo consente di salvare nella tabella Segnalazione del database un nuovo oggetto della classe
     * <code>SegnalazioneBean</code> passato come argomento
     * @param segnalazione oggetto della classe <code>SegnalazioneBean</code> da salvare nel database
     * @return false se la segnalazione passata come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
     */

    public boolean doSaveSegnalazione(SegnalazioneBean segnalazione){

        if (segnalazione== null){
            return false;
        }

        int id= segnalazione.getId();
        int tipo= segnalazione.getTipo();
        String motivazione= segnalazione.getMotivazione();
        boolean gestita= segnalazione.isGestita();
        String email_iscritto=segnalazione.getEmailIscritto();
        int id_recensione= segnalazione.getIdRecensione();

        QueryManager queryManager= new QueryManager();
        String query= "insert into Segnalazione (id, tipo, motivazione, gestita, email_iscritto, id_recensione) " +
                "values (" +id+ ", "+ tipo + ", '" + motivazione+ "', " + gestita+ ",'" + email_iscritto+ "'," + id_recensione+ ");";
        return queryManager.update(query);
    }

    /**
     * Questo metodo consente di cancellare un oggetto <code>SegnalazioneBean</code> dalla tabella Segnalazione
     * del database, individuandolo tramite l'id passato come argomento.
     * @param id id della segnalazione da cancellare dal database
     * @return false se l'id passato come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
     */

    public boolean doDeleteByIdSegnalazione(int id){

        if (id<1){
            return false;
        }

        QueryManager queryManager= new QueryManager();
        String query = "delete from Segnalazione where id = " + id;
        return queryManager.update(query);
    }

    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>SegnalazioneBean</code>
     * presente nella tabella Segnalazione del database, dopo averlo individuato tramite l'id passato come argomento
     * @param id id della segnalazione da cercare nel database
     * @return null se il parametro id non è valido, l'oggetto segnalazione con chiave primaria uguale ad id
     * se l'operazione è andata a buon fine
     */

    public SegnalazioneBean doRetrieveByIdSegnalazione(int id){

        if (id<1){
            return null;
        }
        String query= "select * from Segnalazione where id = " + id;
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);

        Gson gson= new Gson();
        SegnalazioneBean segnalazione= gson.fromJson(res, SegnalazioneBean.class);

        return segnalazione;
    }

    /*public boolean doUpdateSegnalazione (SegnalazioneBean segnalazione){
        if (segnalazione== null){
            return false;
        }

        int id= segnalazione.getId();
        int tipo= segnalazione.getTipo();
        String motivazione= segnalazione.getMotivazione();
        boolean gestita= segnalazione.isGestita();
        String email_iscritto=segnalazione.getEmailIscritto();
        int id_recensione= segnalazione.getIdRecensione();

        QueryManager queryManager= new QueryManager();
        String query= "update Segnalazione set tipo="+tipo+"', motivazione= '"+motivazione+"', gestita= "+gestita+
                " email_iscritto=' "+email_iscritto+" where id = " + id;

        return queryManager.update(query);
    }*/

    /**
     * Questo metodo restituisce tutti gli oggetti della classe <code>SegnalazioneBean</code> memorizzati nel database
     * @return lista di oggetti della classe <code>SegnalazioneBean</code> memorizzata nel database
     */
    public List<SegnalazioneBean> doRetrieveAllSegnalazione(){

        String query="select * from Segnalazione";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        List<SegnalazioneBean> listToReturn = (List<SegnalazioneBean>) gson.fromJson(res, SegnalazioneBean.class);

        return listToReturn;
    }

    /**
     * Questo metodo restituisce un intero che rappresenta l'id con valore maggiore presente nella tabella Segnalazione del database
     * @return id con valore maggiore presente nella tabella Segnalazione del database
     */

    public int doRetrieveMaxIdSegnalazione(){

        String query = "select max(id) from (select id from Segnalazione)";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        int id= gson.fromJson(res, int.class);

        return id;
    }

    /**
     * Questo metodo consente al gestore di marcare come "gestita" una segnalazione presente
     * nella tabella Segnalazione del database
     * @param segnalazione segnalazione da marcare come "gestita"
     * @return false se la recensione è nulla, se risulta essere già "gestita"o se l'operazione NON è andata
     * a buon fine. True altrimenti
     */

    public boolean gestisciSegnalazione(SegnalazioneBean segnalazione){

        if (segnalazione==null)
            return false;
        if (segnalazione.isGestita()==true)
            return false;

        int id= segnalazione.getId();
        String query= "update Segnalazione set gestita= "+true+" where id= "+ id;
        QueryManager queryManager= new QueryManager();
        queryManager.update(query);

        return true;
    }
}
