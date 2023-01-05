package it.unisa.ilike.contenuti.application;

import java.util.List;

import it.unisa.ilike.contenuti.application.exceptions.InvalidValutazioneException;
import it.unisa.ilike.contenuti.storage.ContenutoBean;

/**
 * La classe implementa i servizi relativi alla gestione dei contenuti.
 * @version 0.1
 * @author FrancescoGiorgione
 * @see ContenutoService
 */
public class ContenutoImpl implements ContenutoService {
    /** @inheritDoc */
    @Override
    public List<ContenutoBean> getContenuti(String categoria) {
        return null;
    }

    /** @inheritDoc */
    @Override
    public List<ContenutoBean> getContenuti(Double minValutazione, Double maxValutazione) throws InvalidValutazioneException {
        return null;
    }
}
