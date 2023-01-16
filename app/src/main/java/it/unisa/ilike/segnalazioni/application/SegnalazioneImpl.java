package it.unisa.ilike.segnalazioni.application;

import java.util.List;

import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.account.storage.GestoreDAO;
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

    /**
     * Questo costruttore viene per ottenere un istanza di <code>SegnalazioneImpl</code>
     */
    public SegnalazioneImpl(){
        this.gestoreDAO = new GestoreDAO();
        this.recensioneDAO = new RecensioneDAO();
    }

    /**
     * Questo costruttore viene per ottenere un istanza di <code>SegnalazioneImpl</code>
     * @param gestoreDAO istanza di GestoreDAO
     * @param recensioneDAO istanza di RecensioneDAO
     */
    public SegnalazioneImpl(GestoreDAO gestoreDAO, RecensioneDAO recensioneDAO){
        this.gestoreDAO = gestoreDAO;
        this.recensioneDAO = recensioneDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SegnalazioneBean> getSegnalazione() {
        SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
        List<SegnalazioneBean> listaSegnalazioniNonGestite  = segnalazioneDAO.doRetrieveAllSegnalazioniNonGestite();

        return listaSegnalazioniNonGestite;
    }

    /**
     * {@inheritDoc}
     */
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
        RecensioneBean r = recensioneDAO.doRetrieveByIdRecensione(idRecensione);

        r.setCancellata(true);
        r.setMotivazioneCancellazione(motivazione);
        s.setGestita(true);
        g.incrementaNumSegnalazioniGestite();

        // se la cancellazione della recensione è andata a buon fine
        if(recensioneDAO.cancellaRecensione(r)) {
            // la segnalazione viene marcata come gestita tramite un trigger nel DB (vedi maggiori dettagli in RecensioneDAO)
            // incrementa il numero di segnalazioni gestite dal gestore g
            gestoreDAO.doUpdate(g);
            return true;
        }
        else return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean rifiutaSegnalazione(SegnalazioneBean s, GestoreBean g) {
        if(s == null || g == null) {
            return false;
        }

        s.setGestita(true);
        g.incrementaNumSegnalazioniGestite();

        return new SegnalazioneDAO().gestisciSegnalazione(s) && new GestoreDAO().doUpdate(g);
    }

    /**
     * Questo metodo permette di ottenere un oggetto segnalazione identificato con il suo id
     * @param id l'identificativo della segnalazione
     * @return l'oggetto segnalazione, null altrimenti
     */
    @Override
    public SegnalazioneBean getSegnalazione(int id){
        SegnalazioneDAO segnalazioneDAO = new SegnalazioneDAO();
        return segnalazioneDAO.doRetrieveByIdSegnalazione(id);
    }

    private GestoreDAO gestoreDAO;
    private RecensioneDAO recensioneDAO;
}
