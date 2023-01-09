package it.unisa.ilike.contenuti.application;

import java.util.List;

/**
 * L'interfaccia espone i servizi relativi alla gestione dei contenuti.
 *  @version 0.1
 *  @author FrancescoGiorgione
 */
public interface ContenutoService {
    /**
     * Restituisce una collezione dei 3 contenuti aventi la massima valutazione media.
     * @return un ArrayList di oggetti ContenutoBean.
     */
    List<ContenutoBean> getTop3();

    /**
     * Restituisce una collezione dei 3 contenuti aventi la massima valutazione media.
     * @param tipo descrive il tipo dei contenuti che si vogliono selezionare (0 per film, 1 per
     *             serie tv, 2 per libri, 3 per album musicali).
     * @return un ArrayList di oggetti ContenutoBean.
     */
    List<ContenutoBean> getTop3(int tipo);

}
