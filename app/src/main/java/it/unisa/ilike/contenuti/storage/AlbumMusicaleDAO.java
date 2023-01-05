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

public class AlbumMusicaleDAO {


    public AlbumMusicaleBean doRetrieveById(int id){

        QueryManager queryManager= new QueryManager();

        String query = "SELECT * FROM AlbumMusicale WHERE id=" + id;

        String res = queryManager.select(query);

        Gson gson = new Gson();
        AlbumMusicaleBean a = gson.fromJson(res, AlbumMusicaleBean.class);

        return a;
    }


    public boolean doSave(AlbumMusicaleBean a){
        QueryManager queryManager = new QueryManager();
        String query = "INSERT INTO AlbumMusicale (id, titolo, descrizione, categoria, artista, data_rilascio, acustica, strumentalita,tempo,valenza,durata) VALUES("
                +a.getId()+"'," +a.getTitolo() +"',"+a.getDescrizione()+"'," +a.getCategoria()+"', "+a.getArtista()+"',"
                +a.getDataRilascio()+"'," +a.getAcustica()+"',"+a.getStrumentalita()+"'," +a.getTempo()+"', "+a.getValenza()+"', "+a.getDurata()+");";

        return queryManager.update(query);
    }


    public List<AlbumMusicaleBean> doRetrieveAll(){
        QueryManager queryManager = new QueryManager();
        String query = "SELECT * FROM AlbumMusicale";

        String res = queryManager.select(query);

        Gson gson = new Gson();

        List<AlbumMusicaleBean> listaAlbum = (List<AlbumMusicaleBean>) gson.fromJson(res, AlbumMusicaleBean.class);

        return listaAlbum;
    }


    public List<AlbumMusicaleBean> doRetrieveByCategoria(String categoria){
        QueryManager queryManager = new QueryManager();

        String query = "SELECT * FROM AlbumMusicale WHERE categoria=" +categoria;

        Gson gson = new Gson();
        String res = queryManager.select(query);

        List<AlbumMusicaleBean> listaAlbum = (List<AlbumMusicaleBean>) gson.fromJson(res,AlbumMusicaleBean.class);

        return listaAlbum;
    }


    public List<AlbumMusicaleBean> search(String s){
        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();

        String query = "SELECT * FROM AlbumMusicale WHERE titolo LIKE '%" + s + "%';";
        String res = queryManager.select(query);

        List<AlbumMusicaleBean> listaAlbum = (List<AlbumMusicaleBean>) gson.fromJson(res, AlbumMusicaleBean.class);

        return listaAlbum;
    }


    public boolean doDeleteById(int id){
        String query = "DELETE FROM AlbumMusicale WHERE id=" +id;

        QueryManager queryManager = new QueryManager();

        return queryManager.update(query);
    }

    public int doRetrieveMaxId(){
        String query = "select max(id) from (select id from AlbumMusicale)";
        QueryManager queryManager= new QueryManager();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        int id = gson.fromJson(res, int.class);

        return id;
    }


}
