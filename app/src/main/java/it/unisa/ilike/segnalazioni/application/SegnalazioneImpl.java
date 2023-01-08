package it.unisa.ilike.segnalazioni.application;

import java.util.List;

import it.unisa.ilike.account.application.GestoreBean;
import it.unisa.ilike.recensioni.application.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;
import it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneDAO;
import it.unisa.ilike.utils.Utils;
import it.unisa.ilike.utils.exceptions.NotGestoreException;

/**
 * Questa classe permette di usare i metodi relativi alle segnalazioni
 * @version 0.1
 * @author Simona Lo Conte
 */

public class SegnalazioneImpl implements SegnalazioneService{


    /**
     * Questo metodo consente al gestore di visualizzare tutte le segnalazioni che ancora non sono state gestite.
     * @return lista di segnalazioni non gestite
     */
    @Override
    public List<SegnalazioneBean> getSegnalazione() {

        SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
        List<SegnalazioneBean> listaSegnalazioniNonGestite  = segnalazioneDAO.doRetrieveAllSegnalazioniNonGestite();

        return listaSegnalazioniNonGestite;
    }

    /**
     * Questo metodo può essere chiamato all’interno dell’applicazione solo da chi dispone di un account gestore e
     * consente di cancellare una recensione in seguito alla ricezione di una o più segnalazioni. Purché ciò avvenga
     * occorre fornire al metodo la segnalazione relativa alla recensione da cancellare ed una stringa contenente
     * la motivazione della cancellazione. Il metodo incrementa inoltre il numero di segnalazioni gestite dal gestore.
     * @param s segnalazione relativa alla recensione da cancellare
     * @param motivazione testo che motiva la cancellazione della recensione
     * @param g gestore che si occupa della cancellazione della recensione
     * @return valore booleano che descrive l'esito dell'operazione
     * @throws NotGestoreException
     * @throws it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException
     * @throws it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException
     */
    @Override
    public Boolean cancellaRecensione(SegnalazioneBean s, String motivazione, GestoreBean g)
            throws NotGestoreException, it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException, it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException {

        Utils utils = new Utils();

        if(!(utils.isGestore(g)))
            throw new NotGestoreException();
        if(motivazione.length() < 1)
            throw new MotivazioneVuotaException();
        if(motivazione.length() > 300)
            throw new InvalidMotivazioneException();

        int idRecensione = s.getRecensione().getId();
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        RecensioneBean r = recensioneDAO.doRetrieveByIdRecensione(idRecensione);

        r.setCancellata(true);
        r.setMotivazioneCancellazione(motivazione);
        s.setGestita(true);
        g.incrementaNumSegnalazioniGestite();

        return recensioneDAO.cancellaRecensione(r);
    }

    /**
     * Questo metodo permette al gestore di ignorare una segnalazione, rifiutandola in quanto non veritiera.
     * Inoltre, il metodo incrementa il numero di segnalazioni gestite dal gestore.
     * @param s segnalazione da rifiutare
     * @param g gestore che si occupa di rifiutare la segnalazione
     * @return valore booleano che descrive l'esito dell'operazione
     * @throws NotGestoreException
     */
    @Override
    public Boolean rifiutaSegnalazione(SegnalazioneBean s, GestoreBean g) throws NotGestoreException{

        Utils utils = new Utils();

        if(!(utils.isGestore(g)))
            throw new NotGestoreException();

        int idRecensione = s.getRecensione().getId();
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        RecensioneBean r = recensioneDAO.doRetrieveByIdRecensione(idRecensione);

        s.setGestita(true);
        g.incrementaNumSegnalazioniGestite();

        if(!r.isCancellata())
            return true;
        else
            return false;
    }

}
