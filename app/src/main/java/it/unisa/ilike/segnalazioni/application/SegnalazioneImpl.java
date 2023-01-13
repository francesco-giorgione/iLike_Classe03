package it.unisa.ilike.segnalazioni.application;

import java.util.List;

import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;
import it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneDAO;

/**
 * Questa classe permette di usare i metodi relativi alle segnalazioni
 * @version 0.1
 * @author Simona Lo Conte
 */

public class SegnalazioneImpl implements SegnalazioneService{


    /** @inheritDoc */
    @Override
    public List<SegnalazioneBean> getSegnalazione() {

        SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
        List<SegnalazioneBean> listaSegnalazioniNonGestite  = segnalazioneDAO.doRetrieveAllSegnalazioniNonGestite();

        return listaSegnalazioniNonGestite;
    }

    /** @imheritDoc */
    @Override
    public Boolean cancellaRecensione(SegnalazioneBean s, String motivazione, GestoreBean g)
            throws it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException, it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException {

        if(s == null || g == null) {
            return false;
        }

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

        if(recensioneDAO.cancellaRecensione(r)) {
            return new SegnalazioneDAO().gestisciSegnalazione(s);
        }
        else return false;
    }

    /** @inheritDoc */
    @Override
    public Boolean rifiutaSegnalazione(SegnalazioneBean s, GestoreBean g) {
        if(s == null || g == null) {
            return false;
        }

        int idRecensione = s.getRecensione().getId();
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        RecensioneBean r = recensioneDAO.doRetrieveByIdRecensione(idRecensione);

        s.setGestita(true);
        g.incrementaNumSegnalazioniGestite();

        return !r.isCancellata();
    }

}
