package it.unisa.ilike.contenuti.application;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.contenuti.storage.AlbumMusicaleDAO;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
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
        List<ContenutoBean> tmp = new ArrayList<>();
        tmp.addAll(this.getTop3(0));
        tmp.addAll(this.getTop3(1));
        tmp.addAll(this.getTop3(2));
        tmp.addAll(this.getTop3(3));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tmp.sort((o1, o2) -> o1.getValutazioneMedia() >= o2.getValutazioneMedia() ? 1 : -1);
        }

        return tmp.subList(0, 3);
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

    /** @inheritDoc */
    @Override
    public List<ContenutoBean> cerca(String titolo) {
        List<ContenutoBean> res = new ArrayList<>();
        res.addAll(this.cerca(titolo, 0));
        res.addAll(this.cerca(titolo, 1));
        res.addAll(this.cerca(titolo, 2));
        res.addAll(this.cerca(titolo, 3));
        return res;
    }

    /** @inheritDoc */
    @Override
    public List<ContenutoBean> cerca(String titolo, int tipo) {
        switch (tipo) {
            case 0:     return new FilmDAO().search(titolo);
            case 1:     return new SerieTVDAO().search(titolo);
            case 2:     return new LibroDAO().search(titolo);
            case 3:     return new AlbumMusicaleDAO().search(titolo);
            default:    return null;
        }
    }



}
