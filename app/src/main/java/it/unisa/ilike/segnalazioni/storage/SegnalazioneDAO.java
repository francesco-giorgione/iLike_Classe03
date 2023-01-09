package it.unisa.ilike.segnalazioni.storage;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;
import it.unisa.ilike.utils.Utils;

/**
 * Un oggetto <code>SegnalazioneDAO</code> serve per interagire con la tabella Segnalazioni presente nel database
 * @version 0.3
 * @author LuiginaCostante
 */

public class SegnalazioneDAO {
    /**
     * Modella il risultato della query eseguita in doRetrieveByIdSegnalazione. Consente cioè
     * di convertire il risultato dal formato json in un oggetto Java.
     */
    private class RisultatoQuery {
        int id, tipo, gestita, idRecensione;
        String motivazione, emailIscritto;
    }

    /**
     * Modella il risultato della query eseguita in doRetrieveAllSegnalazioniNonGestite. Consente cioè
     * di convertire il risultato dal formato json in un oggetto Java.
     */
    private class RisultatoQuery2 {
        int id;
    }

    /**
     * Questo metodo consente di salvare nella tabella Segnalazioni del database un nuovo oggetto della classe
     * <code>SegnalazioneBean</code> passato come argomento
     * @param segnalazione oggetto della classe <code>SegnalazioneBean</code> da salvare nel database
     * @return false se la segnalazione passata come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
     */
    public boolean doSaveSegnalazione(SegnalazioneBean segnalazione){
        if (segnalazione== null){
            return false;
        }

        int tipo = segnalazione.getTipo();
        String motivazione = Utils.addEscape(segnalazione.getMotivazione());
        int gestita = segnalazione.isGestita() ? 1 : 0;
        String email_iscritto = Utils.addEscape(segnalazione.getRecensione().getIscritto().getEmail());
        int id_recensione = segnalazione.getRecensione().getId();

        QueryManager queryManager= new QueryManager();
        String query= "insert into Segnalazioni (tipo, motivazione, gestita, email_iscritto, id_recensione) " +
                "values (" + tipo + ", '" + motivazione+ "', " + gestita+ ",'" + email_iscritto+ "'," + id_recensione+ ");";
        return queryManager.update(query);
    }


    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>SegnalazioneBean</code>
     * presente nella tabella Segnalazioni del database, dopo averlo individuato tramite l'id passato come argomento
     * @param id id della segnalazione da cercare nel database
     * @return null se il parametro id non è valido, l'oggetto segnalazione con chiave primaria uguale ad id
     * se l'operazione è andata a buon fine
     */
    public SegnalazioneBean doRetrieveByIdSegnalazione(int id){
        if (id < 1){
            return null;
        }

        String query= "select id, tipo, motivazione, gestita, email_iscritto as emailIscritto, id_recensione as idRecensione " +
                "from Segnalazioni " +
                "where id = " + id;

        Gson gson= new Gson();
        QueryManager queryManager= new QueryManager();
        String jsonRes = queryManager.select(query);
        RisultatoQuery[] res = gson.fromJson(jsonRes, RisultatoQuery[].class);

        if(res.length == 0) {
            return null;
        }

        RisultatoQuery tmpRes = res[0];
        SegnalazioneBean segnalazione = new SegnalazioneBean(tmpRes.id, tmpRes.tipo, tmpRes.motivazione, tmpRes.gestita == 1, null, null);
        segnalazione.setIscritto(new IscrittoDAO().doRetrieveByEmail(tmpRes.emailIscritto));
        segnalazione.setRecensione(new RecensioneDAO().doRetrieveByIdRecensione(tmpRes.idRecensione));

        return segnalazione;
    }


    /**
     * Questo metodo restituisce tutti gli oggetti della classe <code>SegnalazioneBean</code> memorizzati nel database
     * che non risultano essere già "gestiti"
     * @return lista di oggetti della classe <code>SegnalazioneBean</code> memorizzata nel database
     */
    public List<SegnalazioneBean> doRetrieveAllSegnalazioniNonGestite(){
        String query = "select id " +
                "from Segnalazioni " +
                "where gestita = 0";

        List<SegnalazioneBean> segnalazioni = new ArrayList<>();
        Gson gson= new Gson();
        QueryManager queryManager= new QueryManager();
        String jsonRes = queryManager.select(query);
        RisultatoQuery2[] res = gson.fromJson(jsonRes, RisultatoQuery2[].class);

        for(RisultatoQuery2 curr: res) {
            segnalazioni.add(this.doRetrieveByIdSegnalazione(curr.id));
        }

        return segnalazioni;
    }


    /**
     * Questo metodo consente al gestore di marcare come "gestita" una segnalazione presente
     * nella tabella Segnalazioni del database
     * @param segnalazione segnalazione da marcare come "gestita"
     * @return false se la recensione è nulla, se risulta essere già "gestita"o se l'operazione NON è andata
     * a buon fine. True altrimenti
     */
    public boolean gestisciSegnalazione(SegnalazioneBean segnalazione){
        if (segnalazione==null)
            return false;

        int id = segnalazione.getId();
        String query = "update Segnalazioni " +
                "set gestita = 1 " +
                "where id = " + id;

        return new QueryManager().update(query);
    }
}
