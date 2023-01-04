package it.unisa.ilike.segnalazioni.storage;

import java.util.ArrayList;

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

    public boolean doDeleteSegnalazione(SegnalazioneBean segnalazione){
        if (segnalazione==null){
            return false;
        }

        int idSegnalazione= segnalazione.getId();
        QueryManager queryManager= new QueryManager();
        String query = "delete from Segnalazione where id = " + idSegnalazione;
        return queryManager.update(query);
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

    public int doRetrieveMaxIdSegnalazione(){
        String query = "select max(id) from (select id from Segnalazione)";
        QueryManager queryManager= new QueryManager();
        //queryManager.select(query);
        return -1;
    }
}
