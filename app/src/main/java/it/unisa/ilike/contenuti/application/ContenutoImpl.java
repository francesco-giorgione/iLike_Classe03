package it.unisa.ilike.contenuti.application;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.contenuti.storage.AlbumMusicaleBean;
import it.unisa.ilike.contenuti.storage.AlbumMusicaleDAO;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.ContenutoDAO;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.FilmDAO;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.LibroDAO;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
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
    public ContenutoBean getById(int id) {
        if(id < 1 || id > 999) {
            return null;
        }

        FilmBean film = null;
        SerieTVBean serieTV = null;
        LibroBean libro = null;
        AlbumMusicaleBean album = null;

        if((film = new FilmDAO().doRetrieveById(id)) != null) {
            return film;
        }

        if((serieTV = new SerieTVDAO().doRetrieveById(id)) != null) {
            return serieTV;
        }

        if((album = new AlbumMusicaleDAO().doRetrieveById(id)) != null) {
            return album;
        }

        if((libro = new LibroDAO().doRetrieveById(id)) != null) {
            return libro;
        }

        return null;
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
