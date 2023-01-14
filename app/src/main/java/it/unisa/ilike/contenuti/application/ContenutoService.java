package it.unisa.ilike.contenuti.application;

import java.util.List;

import it.unisa.ilike.contenuti.storage.ContenutoBean;

/**
 * L'interfaccia espone i servizi relativi alla gestione dei contenuti.
 *  @version 0.2
 *  @author FrancescoGiorgione
 */
public interface ContenutoService {

    /**
     * Restituisce il contenuto avente un dato id.
     * @param id è l'id del contenuto di cui si vogliono ottenere le informazioni.
     * @post Sia c l’oggetto restituito. Allora <code>c.id = id</code>
     * @return un oggetto ContenutoBean contenente le informazioni relative al contenuto selezionato.
     */
    ContenutoBean getById(int id);


    /**
     * Restituisce una collezione dei contenuti il cui titolo matcha con un dato titolo.
     * @param titolo è il titolo in base a cui si vuole eseguire la ricerca.
     * @post Sia conts la lista restituita dal metodo. Per ogni c in conts, <code>c.titolo like ‘%titolo%’</code>
     * @return un ArrayList di oggetti ContenutoBean.
     */
    List<ContenutoBean> cerca(String titolo);


    /**
     * Restituisce una collezione dei contenuti di un dato tipo il cui titolo matcha con un dato titolo.
     * @param titolo è il titolo in base a cui si vuole eseguire la ricerca.
     * @param tipo è il tipo del contenuto che si vuole selezionare: 0 per film, 1 per serie tv,
     * 2 per libri, 3 per album musicali.
     * @pre <code>tipo = 0 OR tipo = 1 OR tipo = 2 OR tipo = 3</code>
     * @post Sia conts la lista restituita dal metodo. Tutti i contenuti di conts sono del tipo specificato.
     * Inoltre, per ogni c in conts, <code>c.titolo like ‘%titolo%’</code>
     * @return una lista di oggetti ContenutoBean.
     */
    List<ContenutoBean> cerca(String titolo, int tipo);

}
