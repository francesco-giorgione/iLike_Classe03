package it.unisa.ilike.contenuti.storage;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;

public class AlbumMusicaleDAO extends ContenutoDAO {

    /**
     * Restituisce l'album musicale avente un dato id.
     * @param id è l'id dell'album musicale che si vuole selezionare dal db
     * @return un oggetto AlbumMusicaleBean contenente le informazioni relative all'album
     * musicale selezionato.
     */
    public AlbumMusicaleBean doRetrieveById(int id){
        ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT artista, data_rilascio as dataRilascio, acustica, strumentalita, tempo, valenza, durata " +
                "FROM AlbumMusicali " +
                "WHERE id = " + contenuto.getId();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        AlbumMusicaleBean am = gson.fromJson(res, AlbumMusicaleBean.class);

        if(am == null) {
            return null;
        }

        am.setId(contenuto.getId());
        am.setTitolo(contenuto.getTitolo());
        am.setDescrizione(contenuto.getDescrizione());
        am.setCategoria(contenuto.getCategoria());

        return am;
    }

    /**
     * Restituisce una collezione degli album musicali di una data categoria.
     * @param categoria è la categoria sulla base della quale si vogliono selezionare gli album musicali.
     * @return un ArrayList di oggetti AlbumMusicaleBean corrispondenti agli album musicali selezionati
     * in base a 'categoria'.
     */
    public List<ContenutoBean> doRetrieveAllByCategoria(String categoria){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.doRetrieveAllByCategoria("album", categoria);
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        List<ContenutoBean> contenuti = new ArrayList<>();

        for(ContenutoBean c: film) {
            int id = c.getId();

            String query = "select artista, data_rilascio as dataRilascio, acustica, strumentalita, tempo, valenza, durata " +
                    "from AlbumMusicali " +
                    "where id = " + id;

            String res = queryManager.select(query);
            AlbumMusicaleBean am = gson.fromJson(res, AlbumMusicaleBean.class);

            am.setId(id);
            am.setTitolo(c.getTitolo());
            am.setDescrizione(c.getDescrizione());
            am.setCategoria(c.getCategoria());

            contenuti.add(am);
        }

        return contenuti;
    }

    /**
     * Restituisce tutti gli album musicali del catalogo.
     * @return un oggetto List contenente tutti gli AlbumMusicaleBean del catalogo.
     */
    public List<ContenutoBean> doRetrieveAll(){
        return this.doRetrieveAllByCategoria("%", "%");
    }


    /**
     * Restituisce una collezione degli album musicali che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente gli AlbumMusicaleBean selezionati.
     */
    public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.search("album", titolo);
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        List<ContenutoBean> contenuti = new ArrayList<>();

        for(ContenutoBean c: film) {
            int id = c.getId();

            String query = "select artista, data_rilascio as dataRilascio, acustica, strumentalita, tempo, valenza, durata " +
                    "from AlbumMusicali " +
                    "where id = " + id;

            String res = queryManager.select(query);
            AlbumMusicaleBean am = gson.fromJson(res, AlbumMusicaleBean.class);

            am.setId(id);
            am.setTitolo(c.getTitolo());
            am.setDescrizione(c.getDescrizione());
            am.setCategoria(c.getCategoria());

            contenuti.add(am);
        }

        return contenuti;
    }

}
