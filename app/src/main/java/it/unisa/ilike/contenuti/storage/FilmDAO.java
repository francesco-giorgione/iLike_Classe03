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

public class FilmDAO {

    public FilmBean doRetrieveById(int id){

        QueryManager queryManager= new QueryManager();

        String query = "SELECT * FROM Film WHERE id=" + id;

        String res = queryManager.select(query);

        Gson gson = new Gson();
        FilmBean f = gson.fromJson(res, FilmBean.class);

        return f;
    }


    public boolean doSave(FilmBean f){

        QueryManager queryManager = new QueryManager();
        String query = "INSERT INTO Film (id, titolo, descrizione, categoria, anno_rilascio, durata, paese, regista, attori) VALUES("+f.getId()+"'," +f.getTitolo() +"',"+f.getDescrizione()+"',"
                +f.getCategoria()+"', "+f.getAnnoRilascio()+"',"+f.getDurata()+"',"
                +f.getPaese()+"',"+f.getRegista()+"',"+f.getAttori()+ ");";
        return queryManager.update(query);
    }


    public List<FilmBean> doRetrieveAll(){

        QueryManager queryManager = new QueryManager();
        String query = "SELECT * FROM Film";

        String res = queryManager.select(query);

        Gson gson = new Gson();

        List<FilmBean> listaFilm = (List<FilmBean>) gson.fromJson(res, FilmBean.class);

        /*
        int id = resultSet.getInt(1);
        String titolo = resultSet.getString(2);
        String descrizione = resultSet.getString(3);
        String categoria = resultSet.getString(4);
        String anno_rilascio = resultSet.getString(5);
        int durata = resultSet.getInt(6);
        String paese = resultSet.getString(7);
        String regista = resultSet.getString(8);
        String attori = resultSet.getString(9);

        listaFilm.add(new FilmBean(id, titolo, descrizione, categoria, anno_rilascio, durata, paese, regista, attori));
         */

        return listaFilm;
    }


    public List<FilmBean> doRetrieveByCategoria(String categoria){
        QueryManager queryManager = new QueryManager();

        String query = "SELECT * FROM Film WHERE categoria=" +categoria;

        Gson gson = new Gson();
        String res = queryManager.select(query);

        List<FilmBean> listaFilm = (List<FilmBean>) gson.fromJson(res,FilmBean.class);

        /*
        FilmBean f = new FilmBean();

        f.setId(rs.getInt(1));
        f.setTitolo(rs.getString(2));
        f.setDescrizione(rs.getString(3));
        f.setCategoria(rs.getString(4));
        f.setAnno_rilascio(rs.getString(5));
        f.setDurata(rs.getInt(6));
        f.setPaese(rs.getString(7));
        f.setRegista(rs.getString(8));
        f.setAttori(rs.getString(9));

        listaFilm.add(f);
        */

        return listaFilm;
    }


    public List<FilmBean> search(String s){
        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();

        String query = "SELECT * FROM Film WHERE titolo LIKE '%" + s + "%';";
        String res = queryManager.select(query);
        /*
        int id = resultSet.getInt(1);
        String titolo = resultSet.getString(2);
        String descrizione = resultSet.getString(3);
        String categoria = resultSet.getString(4);
        String anno_rilascio = resultSet.getString(5);
        int durata = resultSet.getInt(6);
        String paese = resultSet.getString(7);
        String regista = resultSet.getString(8);
        String attori = resultSet.getString(9);

        listaFilm.add(new FilmBean(id, titolo, descrizione, categoria, anno_rilascio, durata, paese, regista, attori));
        */
        List<FilmBean> listaFilm = (List<FilmBean>) gson.fromJson(res, FilmBean.class);

        return listaFilm;
    }


    public boolean doDeleteById(int id){
        String query = "DELETE FROM Film WHERE id=" +id;

        QueryManager queryManager = new QueryManager();

        return queryManager.update(query);
    }

    public int doRetrieveMaxId(){
        String query = "select max(id) from (select id from Film)";
        QueryManager queryManager= new QueryManager();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        int id = gson.fromJson(res, int.class);

        return id;
    }
}
