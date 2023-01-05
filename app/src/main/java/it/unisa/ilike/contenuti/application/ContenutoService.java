package it.unisa.ilike.contenuti.application;

import java.util.List;

import it.unisa.ilike.contenuti.application.exceptions.InvalidValutazioneException;
import it.unisa.ilike.contenuti.storage.ContenutoBean;

/**
 * L'interfaccia espone i servizi relativi alla gestione dei contenuti.
 *  @version 0.1
 *  @author FrancescoGiorgione
 */
public interface ContenutoService {
    /**
     * @param categoria è la categoria dei contenuti che si vogliono selezionare
     * @return una collezione List dei contenuti della categoria indicata
     */
    List<ContenutoBean> getContenuti(String categoria);

    /**
     *
     * @param minValutazione è la minima valutazione media che devono avere i contenuti che si vogliono selezionare
     * @param maxValutazione è la massima valutazione media che devono avere i contenuti che si vogliono selezionare
     * @return una collezione List dei contenuti aventi una valutazione media che rientra nel range indicato
     * @throws InvalidValutazioneException
     */
    List<ContenutoBean> getContenuti(Double minValutazione, Double maxValutazione)
            throws InvalidValutazioneException;
}
