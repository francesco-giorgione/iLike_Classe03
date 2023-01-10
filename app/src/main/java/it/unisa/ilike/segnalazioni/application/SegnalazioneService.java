package it.unisa.ilike.segnalazioni.application;

import java.util.List;

import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

/**
 * Interfaccia che contiene i metodi relativi alle segnalazioni
 * @version 0.1
 * @author Simona Lo Conte
 */

public interface SegnalazioneService {
    /**
     * Questo metodo consente al gestore di visualizzare tutte le segnalazioni che ancora non sono state gestite.
     * @return lista di segnalazioni non gestite
     */
    public List<SegnalazioneBean> getSegnalazione();


    /**
     * Questo metodo può essere chiamato all’interno dell’applicazione solo da chi dispone di un account gestore e
     * consente di cancellare una recensione in seguito alla ricezione di una o più segnalazioni. Purché ciò avvenga
     * occorre fornire al metodo la segnalazione relativa alla recensione da cancellare ed una stringa contenente
     * la motivazione della cancellazione. Il metodo incrementa inoltre il numero di segnalazioni gestite dal gestore.
     * @param s segnalazione relativa alla recensione da cancellare
     * @param motivazione testo che motiva la cancellazione della recensione
     * @param g gestore che si occupa della cancellazione della recensione
     * @return valore booleano che descrive l'esito dell'operazione
     * @throws it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException
     * @throws it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException
     */
    public Boolean cancellaRecensione(SegnalazioneBean s, String motivazione, GestoreBean g)
            throws it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException, it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException;


    /**
     * Questo metodo permette al gestore di ignorare una segnalazione, rifiutandola in quanto non veritiera.
     * Inoltre, il metodo incrementa il numero di segnalazioni gestite dal gestore.
     * @param s segnalazione da rifiutare
     * @param g gestore che si occupa di rifiutare la segnalazione
     * @return valore booleano che descrive l'esito dell'operazione
     */
    public Boolean rifiutaSegnalazione(SegnalazioneBean s, GestoreBean g);

}
