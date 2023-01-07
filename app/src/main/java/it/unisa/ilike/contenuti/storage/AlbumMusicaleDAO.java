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

    // da implementare
    public List<ContenutoBean> doRetrieveByLista(String nomeLista, String emailIscritto) {
        return null;
    }


    public boolean doSave(AlbumMusicaleBean a){
        QueryManager queryManager = new QueryManager();
        String query = "INSERT INTO AlbumMusicali (id, titolo, descrizione, categoria, artista, data_rilascio, acustica, strumentalita,tempo,valenza,durata) VALUES("
                +a.getId()+"'," +a.getTitolo() +"',"+a.getDescrizione()+"'," +a.getCategoria()+"', "+a.getArtista()+"',"
                +a.getDataRilascio()+"'," +a.getAcustica()+"',"+a.getStrumentalita()+"'," +a.getTempo()+"', "+a.getValenza()+"', "+a.getDurata()+");";

        return queryManager.update(query);
    }


    public List<AlbumMusicaleBean> doRetrieveAll(){
        QueryManager queryManager = new QueryManager();
        String query = "SELECT * FROM AlbumMusicali";

        String res = queryManager.select(query);

        Gson gson = new Gson();

        List<AlbumMusicaleBean> listaAlbum = (List<AlbumMusicaleBean>) gson.fromJson(res, AlbumMusicaleBean.class);

        return listaAlbum;
    }


    public List<AlbumMusicaleBean> doRetrieveByCategoria(String categoria){
        QueryManager queryManager = new QueryManager();

        String query = "SELECT * FROM AlbumMusicali WHERE categoria=" +categoria;

        Gson gson = new Gson();
        String res = queryManager.select(query);

        List<AlbumMusicaleBean> listaAlbum = (List<AlbumMusicaleBean>) gson.fromJson(res,AlbumMusicaleBean.class);

        return listaAlbum;
    }


    public List<AlbumMusicaleBean> search(String s){
        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();

        String query = "SELECT * FROM AlbumMusicali WHERE titolo LIKE '%" + s + "%';";
        String res = queryManager.select(query);

        List<AlbumMusicaleBean> listaAlbum = (List<AlbumMusicaleBean>) gson.fromJson(res, AlbumMusicaleBean.class);

        return listaAlbum;
    }


    public boolean doDeleteById(int id){
        String query = "DELETE FROM AlbumMusicali WHERE id=" +id;

        QueryManager queryManager = new QueryManager();

        return queryManager.update(query);
    }

    public int doRetrieveMaxId(){
        String query = "select max(id) from (select id from AlbumMusicali)";
        QueryManager queryManager= new QueryManager();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        int id = gson.fromJson(res, int.class);

        return id;
    }


}
