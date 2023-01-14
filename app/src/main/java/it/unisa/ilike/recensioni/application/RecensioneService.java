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
 * @version 0.2
 * @author LuiginaCostante
 */

public interface RecensioneService {

    /**
     * Questo metodo consente di creare un nuovo oggetto <code>RecensioneBean</code> e salvarlo nel database
     * @param testo rappresenta il testo della recensione da essere memorizzato
     * @param valutazione rappresenta la valutazione espressa dall'iscritto relativa alla recensione
     * @param i rappresenta l'iscritto che ha composto la recensione
     * @param c rappresenta il contenuto cui la recensione si riferisce
     * @pre <code> 3 ≤ testo.length ≤ 1000 AND 1 ≤ valutazione ≤ 5 </code>
     * @post sia R l’insieme delle recensioni prima della chiamata di questo metodo e sia r la recensione
     * che si vuole aggiungere ad R. Dopo la chiamata a tale metodo avremo il nuovo insieme R’ = R ∪ {r}.
     * @return l'oggetto RecensioneBean contenente le informazioni relative alla recensione creata
     * se l'operazione è andata a buon fine, null altrimenti.
     * @throws TestoTroppoBreveException se l’argomento testo ha un numero di caratteri minore di 3.
     * @throws InvalidTestoException se l’argomento testo ha un numero di caratteri maggiore di 1000.
     * @throws ValutazioneException se la variabile valutazione ricevuta come argomento dal metodo non ha
     * un valore compreso tra 1 e 5.
     */
    RecensioneBean creaRecensione(String testo, int valutazione, IscrittoBean i, ContenutoBean c)
            throws TestoTroppoBreveException, InvalidTestoException, ValutazioneException;


    /**
     * Questo metodo consente di aggiungere una nuova segnalazione relativa ad una recensione r
     * @param tipo questo parametro sarà uguale a 0 se la segnalazione è uno Spoiler Alert, 1 altrimenti
     * @param motivazione indica la motivazione per cui l'iscritto ha effettuato la segnalazione
     * @param r indica l'oggetto <code>RecensioneBean</code> cui la segnalaizone si riferisce
     * @param i indice l'iscritto che ha mandato la recensione
     * @pre <code>1 ≤ motivazione.length() ≤ 500 AND (tipo==0 OR tipo==1)</code>
     * @post sia S l’insieme delle segnalazioni riferite alla recensione r prima della chiamata a questo
     * metodo e sia s la nuova segnalazione riferita ad r che si desidera aggiungere ad S. Dopo la chiamata
     * a tale metodo avremo il nuovo insieme S’ = S ∪ {s}.
     * @return true se l'operazione è andata a buon fine, false altrimenti
     * @throws MotivazioneVuotaException: l’argomento motivazione ha un numero di caratteri minore di 1.
     * @throws InvalidMotivazioneException l’argomento motivazione ha un numero di caratteri maggiore di 500.
     * @throws InvalidTipoException l’argomento tipo ha un valore non contenuto nell’insieme dei valori ammissibili {0,1}.
     */
    boolean aggiungiSegnalazione (int tipo, String motivazione, RecensioneBean r, IscrittoBean i)
            throws InvalidTipoException, MotivazioneVuotaException, InvalidMotivazioneException;


    /**
     * Questo metodo permette di recuperare la recensione attraverso il suo identificativo
     * @param id l'identificato della recensione
     * @return l'oggetto recensione se esiste, null altrimenti
     */
    RecensioneBean getRecensione(int id);
}