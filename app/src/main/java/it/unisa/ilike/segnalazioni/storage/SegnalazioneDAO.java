package it.unisa.ilike.segnalazioni.storage;

import com.google.gson.Gson;

import java.util.List;

import it.unisa.ilike.QueryManager;

public class SegnalazioneDAO {

    public boolean doSaveSegnalazione(SegnalazioneBean segnalazione){

        if (segnalazione== null){
            return false;
        }

        int id= segnalazione.getId();
        int tipo= segnalazione.getTipo();
        String motivazione= segnalazione.getMotivazione();
        boolean gestita= segnalazione.isGestita();
        String email_iscritto=segnalazione.getEmail_iscritto();
        int id_recensione= segnalazione.getId_recensione();

        QueryManager queryManager= new QueryManager();
        String query= "insert into Segnalazione (id, tipo, motivazione, gestita, email_iscritto, id_recensione) " +
                "values (" +id+ ", "+ tipo + ", '" + motivazione+ "', " + gestita+ ",'" + email_iscritto+ "'," + id_recensione+ ");";
        return queryManager.update(query);
    }

    public boolean doDeleteByIdSegnalazione(int id){

        if (id<1){
            return false;
        }

        QueryManager queryManager= new QueryManager();
        String query = "delete from Segnalazione where id = " + id;
        return queryManager.update(query);
    }

    public SegnalazioneBean doRetrieveByIdSegnalazione(int id){

        if (id<0){
            return null;
        }
        String query= "select * from Segnalazione where id = " + id;
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);

        Gson gson= new Gson();
        SegnalazioneBean segnalazione= gson.fromJson(res, SegnalazioneBean.class);

        return segnalazione;
    }

    public boolean doUpdateSegnalazione (SegnalazioneBean segnalazione){
        if (segnalazione== null){
            return false;
        }

        int id= segnalazione.getId();
        int tipo= segnalazione.getTipo();
        String motivazione= segnalazione.getMotivazione();
        boolean gestita= segnalazione.isGestita();
        String email_iscritto=segnalazione.getEmail_iscritto();
        int id_recensione= segnalazione.getId_recensione();

        QueryManager queryManager= new QueryManager();
        String query= "update Segnalazione set tipo="+tipo+"', motivazione= '"+motivazione+"', gestita= "+gestita+
                " email_iscritto=' "+email_iscritto+"', id_recensione= "+id_recensione+" where id = " + id;

        return queryManager.update(query);
    }

    public List<SegnalazioneBean> doRetrieveAllSegnalazione(){

        String query="select * from Segnalazione";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        List<SegnalazioneBean> listToReturn = (List<SegnalazioneBean>) gson.fromJson(res, SegnalazioneBean.class);

        return listToReturn;
    }

    public int doRetrieveMaxIdSegnalazione(){

        String query = "select max(id) from (select id from Segnalazione)";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        int id= gson.fromJson(res, int.class);

        return id;
    }

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
