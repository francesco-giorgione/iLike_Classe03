package it.unisa.ilike.contenuti.application;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.contenuti.storage.AlbumMusicaleDAO;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
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
    /**
     * {@inheritDoc}
     */
    @Override
    public ContenutoBean getById(int id) {
        if(id < 1 || id > 999) {
            return null;
        }

        FilmBean film = null;
        //SerieTVBean serieTV = null;
        //LibroBean libro = null;
        //AlbumMusicaleBean album = null;

        if((film = new FilmDAO().doRetrieveById(id)) != null) {
            return film;
        }

        /*if((serieTV = new SerieTVDAO().doRetrieveById(id)) != null) {
            return serieTV;
        }

        if((album = new AlbumMusicaleDAO().doRetrieveById(id)) != null) {
            return album;
        }

        if((libro = new LibroDAO().doRetrieveById(id)) != null) {
            return libro;
        }*/

        return null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<ContenutoBean> cerca(String titolo) {
        List<ContenutoBean> res = new ArrayList<>();
        res.addAll(this.cerca(titolo, 0));
        res.addAll(this.cerca(titolo, 1));
        res.addAll(this.cerca(titolo, 2));
        res.addAll(this.cerca(titolo, 3));
        return res;
    }

    /**
     * {@inheritDoc}
     */
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
