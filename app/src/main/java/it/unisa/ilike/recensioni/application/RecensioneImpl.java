package it.unisa.ilike.recensioni.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
import it.unisa.ilike.utils.exceptions.NotIscrittoException;

/**
 * Un oggetto <code>RecensioneImpl</code> serve per accedere ai metodi di servizio relativi ad una recensione
 * @version 0.1
 * @author LuiginaCostante
 */

public class RecensioneImpl implements RecensioneService{

    /**
     * Questo metodo consente di creare un nuovo oggetto <code>RecensioneBean</code> e salvarlo nel database
     * @param testo rappresenta il testo della recensione da essere memorizzato
     * @param valutazione rappresenta la valutazione espressa dall'iscritto relativa alla recensione
     * @param i rappresenta l'iscritto che ha composto la recensione
     * @param c rappresenta il contenuto cui la recensione si riferisce
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean creaRecensione(String testo, int valutazione, IscrittoBean i, ContenutoBean c)
                    throws NotIscrittoException, TestoTroppoBreveException, InvalidTestoException,
                                                                            ValutazioneException {

        if (!(isIscritto(i))) throw new NotIscrittoException();
        if (testo.length()<3) throw new TestoTroppoBreveException();
        if (testo.length()>1000) throw new InvalidTestoException();
        if (valutazione<1 || valutazione>5) throw new ValutazioneException();

        RecensioneDAO recensioneDAO= new RecensioneDAO();
        int id= recensioneDAO.doRetrieveMaxIdRecensione();
        Date data= new Date();

        RecensioneBean recensione= new RecensioneBean(id+1, testo, valutazione, data, false, null, i.getEmail(), c.getId());

        if (recensione!=null){
            if (recensioneDAO.doSaveRecensione(recensione))
                return true;
        }

        return false;
    }

    /**
     * Questo metodo consente di aggiungere una nuova segnalazione relativa ad una recensione r
     * @param tipo questo parametro sarà uguale a 0 se la segnalazione è uno Spoiler Alert, 1 altrimenti
     * @param motivazione indica la motivazione per cui l'iscritto ha effettuato la segnalazione
     * @param r indica l'oggetto <code>RecensioneBean</code> cui la segnalaizone si riferisce
     * @param i indice l'iscritto che ha mandato la recensione
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    @Override
    public boolean aggiungiSegnalazione(int tipo, String motivazione, RecensioneBean r, IscrittoBean i)
        throws NotIscrittoException, InvalidTipoException, MotivazioneVuotaException,
            InvalidMotivazioneException {

        if (!(isIscritto(i))) throw new NotIscrittoException();
        if (tipo!=0 && tipo!=1) throw new InvalidTipoException();
        if (motivazione.length()<1) throw new MotivazioneVuotaException();
        if (motivazione.length()>500) throw new InvalidMotivazioneException();

        SegnalazioneDAO segnalazioneDAO= new SegnalazioneDAO();
        int id=segnalazioneDAO.doRetrieveMaxIdSegnalazione();

        SegnalazioneBean segnalazione= new SegnalazioneBean(id+1, tipo, motivazione, false, i.getEmail(), r.getId());
        segnalazioneDAO.doSaveSegnalazione(segnalazione);

        return true;
    }

    /**
     * Questo metodo permette di ricevere come valore di ritorno una lista contenente oggetti
     * della classe <code>RecensioneBean</code> che si riferiscono al contenuto c passato come argomento
     * al metodo
     * @param c contenuto in base al quale vogliamo filtrare la ricerca delle recensioni
     * @return List contenente le recensioni che si riferiscono al contenuto c
     */
    @Override
    public List<RecensioneBean> getRecensioniContenuto(ContenutoBean c) {

        RecensioneDAO recensioneDAO= new RecensioneDAO();
        List<RecensioneBean> ListToReturn= new ArrayList<>();
        List<RecensioneBean> recensioni= recensioneDAO.doRetrieveAllRecensione();

        for (RecensioneBean r: recensioni){
            if (r.getId_contenuto()==c.getId())
                ListToReturn.add(r);
        }

        return ListToReturn;
    }
}
