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

public class LibroDAO {


    public LibroBean doRetrieveById(int id){

        QueryManager queryManager= new QueryManager();

        String query = "SELECT * FROM Libri WHERE id=" + id;

        String res = queryManager.select(query);

        Gson gson = new Gson();
        LibroBean l = gson.fromJson(res, LibroBean.class);

        return l;
    }


    public boolean doSave(LibroBean l){
        QueryManager queryManager = new QueryManager();
        String query = "INSERT INTO Libri (id, titolo, descrizione, categoria, autore, isbn, num_pagine) VALUES("+l.getId()+"'," +l.getTitolo() +"',"+l.getDescrizione()+"',"
                +l.getCategoria()+"', "+l.getAutore()+"',"+l.getIsbn()+"',"
                +l.getNumPagine()+");";

        return queryManager.update(query);
    }


    public List<LibroBean> doRetrieveAll(){
        QueryManager queryManager = new QueryManager();
        String query = "SELECT * FROM Libri";

        String res = queryManager.select(query);

        Gson gson = new Gson();

        List<LibroBean> listaLibri = (List<LibroBean>) gson.fromJson(res, LibroBean.class);

        return listaLibri;
    }


    public List<LibroBean> doRetrieveByCategoria(String categoria){
        QueryManager queryManager = new QueryManager();

        String query = "SELECT * FROM Libri WHERE categoria=" +categoria;

        Gson gson = new Gson();
        String res = queryManager.select(query);

        List<LibroBean> listaLibri = (List<LibroBean>) gson.fromJson(res,LibroBean.class);

        return listaLibri;
    }


    public List<LibroBean> search(String s){
        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();

        String query = "SELECT * FROM Libri WHERE titolo LIKE '%" + s + "%';";
        String res = queryManager.select(query);

        List<LibroBean> listaLibri = (List<LibroBean>) gson.fromJson(res, LibroBean.class);

        return listaLibri;
    }


    public boolean doDeleteById(int id){
        String query = "DELETE FROM Libri WHERE id=" +id;

        QueryManager queryManager = new QueryManager();

        return queryManager.update(query);
    }

    public int doRetrieveMaxId(){
        String query = "select max(id) from (select id from Libri)";
        QueryManager queryManager= new QueryManager();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        int id = gson.fromJson(res, int.class);

        return id;
    }

}
