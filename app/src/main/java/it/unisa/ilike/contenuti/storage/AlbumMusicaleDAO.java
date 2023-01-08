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
import it.unisa.ilike.utils.Utils;

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

        Gson gson = new Gson();
        String jsonRes = queryManager.select(query);
        AlbumMusicaleBean[] res = gson.fromJson(jsonRes, AlbumMusicaleBean[].class);

        if(res.length == 0) {
            return null;
        }

        AlbumMusicaleBean albumMusicale = res[0];

        albumMusicale.setId(contenuto.getId());
        albumMusicale.setTitolo(contenuto.getTitolo());
        albumMusicale.setDescrizione(contenuto.getDescrizione());
        albumMusicale.setCategoria(contenuto.getCategoria());

        return albumMusicale;
    }


    /**
     * Restituisce una collezione dei 3 album musicali aventi la massima valutazione media.
     * @return un ArrayList contenente 3 oggetti AlbumMusicaleBean.
     */
    public List<ContenutoBean> doRetrieveTop3() {
        List<ContenutoBean> contenuti = super.doRetrieveTop3ByTipo("album");
        List<ContenutoBean> topAlbum = new ArrayList<>();

        for(ContenutoBean c : contenuti) {
            topAlbum.add(this.doRetrieveById(c.getId()));
        }

        return topAlbum;
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
