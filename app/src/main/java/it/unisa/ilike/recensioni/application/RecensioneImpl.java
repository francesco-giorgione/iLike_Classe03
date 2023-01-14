package it.unisa.ilike.recensioni.application;

import java.util.Date;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.recensioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTestoException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTipoException;
import it.unisa.ilike.recensioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.recensioni.application.exceptions.TestoTroppoBreveException;
import it.unisa.ilike.recensioni.application.exceptions.ValutazioneException;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneDAO;

/**
 * Un oggetto <code>RecensioneImpl</code> serve per accedere ai metodi di servizio relativi ad una recensione
 * @version 0.3
 * @author LuiginaCostante
 */

public class RecensioneImpl implements RecensioneService{

    /** @inheritDoc */
    @Override
    public RecensioneBean creaRecensione(String testo, int valutazione, IscrittoBean i, ContenutoBean c)
                    throws TestoTroppoBreveException, InvalidTestoException, ValutazioneException {

        if(testo == null || i == null || c == null) {
            return null;
        }

        if (testo.length()<3) throw new TestoTroppoBreveException();
        if (testo.length()>1000) throw new InvalidTestoException();
        if (valutazione<1 || valutazione>5) throw new ValutazioneException();

        RecensioneDAO recensioneDAO= new RecensioneDAO();
        RecensioneBean recensione= new RecensioneBean(-1, testo, valutazione, new Date(), false, null, i, c);

        if(recensioneDAO.doSaveRecensione(recensione)) {
            return recensione;
        }
        return null;
    }


    /** @inheritDoc */
    @Override
    public boolean aggiungiSegnalazione(int tipo, String motivazione, RecensioneBean r, IscrittoBean i)
        throws InvalidTipoException, MotivazioneVuotaException,
            InvalidMotivazioneException {

        if(tipo < 0 || tipo > 2 || motivazione == null || r == null || i == null) {
            return false;
        }

        if (tipo!=0 && tipo!=1) throw new InvalidTipoException();
        if (motivazione.length()<1) throw new MotivazioneVuotaException();
        if (motivazione.length()>500) throw new InvalidMotivazioneException();

        SegnalazioneBean segnalazione= new SegnalazioneBean(-1, tipo, motivazione, false, i, r);
        return new SegnalazioneDAO().doSaveSegnalazione(segnalazione);
    }

    @Override
    public RecensioneBean getRecensione(int id) {
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        return recensioneDAO.doRetrieveByIdRecensione(id);
    }


}
