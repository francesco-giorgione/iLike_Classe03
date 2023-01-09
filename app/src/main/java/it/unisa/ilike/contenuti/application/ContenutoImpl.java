package it.unisa.ilike.contenuti.application;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.contenuti.storage.AlbumMusicaleDAO;
import it.unisa.ilike.contenuti.storage.FilmDAO;
import it.unisa.ilike.contenuti.storage.LibroDAO;
import it.unisa.ilike.contenuti.storage.SerieTVDAO;

/**
 * La classe implementa i servizi relativi alla gestione dei contenuti.
 * @version 0.1
 * @author FrancescoGiorgione
 * @see ContenutoService
 */
public class ContenutoImpl implements ContenutoService {
    /** @inheritDoc */
    @Override
    public List<ContenutoBean> getTop3() {
        return null;
    }

    /** @inheritDoc */
    @Override
    public List<ContenutoBean> getTop3(int tipo) {
        switch (tipo) {
            case 0:     return new FilmDAO().doRetrieveTop3();
            case 1:     return new SerieTVDAO().doRetrieveTop3();
            case 2:     return new LibroDAO().doRetrieveTop3();
            case 3:     return new AlbumMusicaleDAO().doRetrieveTop3();
            default:    return null;
        }
    }
}
