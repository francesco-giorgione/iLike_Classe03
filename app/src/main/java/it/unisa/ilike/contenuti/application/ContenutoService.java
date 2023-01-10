package it.unisa.ilike.contenuti.application;

import java.util.List;

import it.unisa.ilike.contenuti.storage.ContenutoBean;

/**
 * L'interfaccia espone i servizi relativi alla gestione dei contenuti.
 *  @version 0.1
 *  @author FrancescoGiorgione
 */
public interface ContenutoService {

    /**
     * Restituisce il contenuto avente un dato id.
     * @param id è l'id del contenuto di cui si vogliono ottenere le informazioni.
     * @return un oggetto ContenutoBean contenente le informazioni relative al contenuto selezionato.
     */
    ContenutoBean getById(int id);


    /**
     * Restituisce una collezione dei contenuti il cui titolo matcha con un dato titolo.
     * @param titolo è il titolo in base a cui si vuole eseguire la ricerca.
     * @return un ArrayList di oggetti ContenutoBean.
     */
    List<ContenutoBean> cerca(String titolo);


    /**
     * Restituisce una collezione dei contenuti di un dato tipo il cui titolo matcha con un dato titolo.
     * @param titolo è il titolo in base a cui si vuole eseguire la ricerca.
     * @param tipo è il tipo del contenuto che si vuole selezionare: 0 per film, 1 per serie tv,
     * 2 per libri, 3 per album musicali.
     * @return un ArrayList di oggetti ContenutoBean.
     */
    List<ContenutoBean> cerca(String titolo, int tipo);

}
