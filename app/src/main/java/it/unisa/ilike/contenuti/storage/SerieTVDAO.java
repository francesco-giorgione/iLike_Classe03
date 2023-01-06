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

public class SerieTVDAO extends ContenutoDAO {

    public SerieTVBean doRetrieveById(int id){

        QueryManager queryManager= new QueryManager();

        String query = "SELECT * FROM SerieTV WHERE id=" + id;

        String res = queryManager.select(query);

        Gson gson = new Gson();
        SerieTVBean s = gson.fromJson(res, SerieTVBean.class);

        return s;
    }


    public boolean doSave(SerieTVBean s){
        QueryManager queryManager = new QueryManager();
        String query = "INSERT INTO SerieTV (id, titolo, descrizione, categoria, anno_rilascio, num_stagioni) VALUES("+s.getId()+"'," +s.getTitolo() +"',"+s.getDescrizione()+"',"
                +s.getCategoria()+"', "+s.getAnnoRilascio()+"',"+s.getNumStagioni()+");";

        return queryManager.update(query);
    }


    public List<SerieTVBean> doRetrieveAll(){
        QueryManager queryManager = new QueryManager();
        String query = "SELECT * FROM SerieTV";

        String res = queryManager.select(query);

        Gson gson = new Gson();

        List<SerieTVBean> listaSerie = (List<SerieTVBean>) gson.fromJson(res, SerieTVBean.class);

        return listaSerie;
    }


    public List<SerieTVBean> doRetrieveByCategoria(String categoria){
        QueryManager queryManager = new QueryManager();

        String query = "SELECT * FROM SerieTV WHERE categoria=" +categoria;

        Gson gson = new Gson();
        String res = queryManager.select(query);

        List<SerieTVBean> listaSerie = (List<SerieTVBean>) gson.fromJson(res,SerieTVBean.class);

        return listaSerie;
    }


    public List<SerieTVBean> search(String s){
        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();

        String query = "SELECT * FROM SerieTV WHERE titolo LIKE '%" + s + "%';";
        String res = queryManager.select(query);

        List<SerieTVBean> listaSerie = (List<SerieTVBean>) gson.fromJson(res, SerieTVBean.class);

        return listaSerie;
    }


    public boolean doDeleteById(int id){
        String query = "DELETE FROM SerieTV WHERE id=" +id;

        QueryManager queryManager = new QueryManager();

        return queryManager.update(query);
    }

    public int doRetrieveMaxId(){
        String query = "select max(id) from (select id from SerieTV)";
        QueryManager queryManager= new QueryManager();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        int id = gson.fromJson(res, int.class);

        return id;
    }

}
