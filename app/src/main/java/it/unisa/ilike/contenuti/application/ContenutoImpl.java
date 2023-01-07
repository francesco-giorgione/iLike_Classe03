package it.unisa.ilike.contenuti.application;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.contenuti.application.exceptions.InvalidValutazioneException;
import it.unisa.ilike.contenuti.storage.AlbumMusicaleBean;
import it.unisa.ilike.contenuti.storage.AlbumMusicaleDAO;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
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
    public List<ContenutoBean> getContenuti(String categoria) {
        if(categoria == null) {
            return null;
        }

        FilmDAO filmDAO = new FilmDAO();
        SerieTVDAO serieTVDAO = new SerieTVDAO();
        LibroDAO libroDAO = new LibroDAO();
        AlbumMusicaleDAO albumMusicaleDAO = new AlbumMusicaleDAO();

        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) filmDAO.doRetrieveAllByCategoria(categoria);
        ArrayList<ContenutoBean> serieTV = (ArrayList<ContenutoBean>) serieTVDAO.doRetrieveAllByCategoria(categoria);
        ArrayList<LibroBean> libri = (ArrayList<LibroBean>) libroDAO.doRetrieveByCategoria(categoria);
        ArrayList<AlbumMusicaleBean> albumMusicali = (ArrayList<AlbumMusicaleBean>) albumMusicaleDAO.doRetrieveByCategoria(categoria);

        ArrayList<ContenutoBean> res = new ArrayList<>();
        res.addAll(film);
        res.addAll(serieTV);
        res.addAll(libri);
        res.addAll(albumMusicali);

        return res;
    }

    /** @inheritDoc */
    @Override
    public List<ContenutoBean> getContenuti(double minValutazione, double maxValutazione) throws InvalidValutazioneException {
        if(minValutazione <= 0 || maxValutazione > 5 || maxValutazione < minValutazione) {
            throw new InvalidValutazioneException();
        }

        // da implementare

        return null;
    }
}
