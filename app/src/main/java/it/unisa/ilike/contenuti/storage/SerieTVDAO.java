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
        ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT anno_rilascio as annoRilascio, num_stagioni as numStagioni " +
                "FROM SerieTV " +
                "WHERE id = " + contenuto.getId();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        SerieTVBean serieTV = gson.fromJson(res, SerieTVBean.class);

        serieTV.setId(contenuto.getId());
        serieTV.setTitolo(contenuto.getTitolo());
        serieTV.setDescrizione(contenuto.getDescrizione());
        serieTV.setCategoria(contenuto.getCategoria());

        return serieTV;
    }


    // da implementare
    public List<ContenutoBean> doRetrieveByLista(String nomeLista, String emailIscritto) {
        return null;
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

}
