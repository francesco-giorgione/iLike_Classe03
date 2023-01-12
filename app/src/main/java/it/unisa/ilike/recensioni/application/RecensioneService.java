package it.unisa.ilike.recensioni.application;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.recensioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTestoException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTipoException;
import it.unisa.ilike.recensioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.recensioni.application.exceptions.TestoTroppoBreveException;
import it.unisa.ilike.recensioni.application.exceptions.ValutazioneException;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

/**
 * Interfaccia che esplicita i metodi di servizio relativi alle recensioni
 * @version 0.1
 * @author LuiginaCostante
 */

public interface RecensioneService {

    /**
     * Questo metodo consente di creare un nuovo oggetto <code>RecensioneBean</code> e salvarlo nel database
     * @param testo rappresenta il testo della recensione da essere memorizzato
     * @param valutazione rappresenta la valutazione espressa dall'iscritto relativa alla recensione
     * @param i rappresenta l'iscritto che ha composto la recensione
     * @param c rappresenta il contenuto cui la recensione si riferisce
     * @return l'oggetto RecensioneBean contenente le informazioni relative alla recensione creata
     * se l'operazione è andata a buon fine, null altrimenti.
     */
    public RecensioneBean creaRecensione(String testo, int valutazione, IscrittoBean i, ContenutoBean c)
            throws TestoTroppoBreveException, InvalidTestoException, ValutazioneException;


    /**
     * Questo metodo consente di aggiungere una nuova segnalazione relativa ad una recensione r
     * @param tipo questo parametro sarà uguale a 0 se la segnalazione è uno Spoiler Alert, 1 altrimenti
     * @param motivazione indica la motivazione per cui l'iscritto ha effettuato la segnalazione
     * @param r indica l'oggetto <code>RecensioneBean</code> cui la segnalaizone si riferisce
     * @param i indice l'iscritto che ha mandato la recensione
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean aggiungiSegnalazione (int tipo, String motivazione, RecensioneBean r, IscrittoBean i)
            throws InvalidTipoException, MotivazioneVuotaException, InvalidMotivazioneException;

}