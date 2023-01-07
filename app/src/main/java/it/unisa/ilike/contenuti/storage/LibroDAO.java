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

public class LibroDAO extends ContenutoDAO {

    /**
     * Restituisce il libro avente un dato id.
     * @param id è l'id del contenuto che si vuole selezionare dal db
     * @return un oggetto LibroBean contenente le informazioni relative al libro selezionato.
     */
    public LibroBean doRetrieveById(int id){
        ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT autore, isbn, num_pagine as numPagine " +
                "FROM Libri " +
                "WHERE id = " + contenuto.getId();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        LibroBean libro = gson.fromJson(res, LibroBean.class);

        if(libro == null) {
            return null;
        }

        libro.setId(contenuto.getId());
        libro.setTitolo(contenuto.getTitolo());
        libro.setDescrizione(contenuto.getDescrizione());
        libro.setCategoria(contenuto.getCategoria());

        return libro;
    }


    // da implementare
    public List<ContenutoBean> doRetrieveByLista(String nomeLista, String emailIscritto) {
        return null;
    }

    public List<LibroBean> doRetrieveAll(){
        QueryManager queryManager = new QueryManager();
        String query = "SELECT * FROM Libri";

        String res = queryManager.select(query);

        Gson gson = new Gson();

        List<LibroBean> listaLibri = (List<LibroBean>) gson.fromJson(res, LibroBean.class);

        return listaLibri;
    }


    /**
     * Restituisce una collezione dei libri di una data categoria.
     * @param categoria è la categoria sulla base della quale si vogliono selezionare i libri.
     * @return un ArrayList di oggetti LibroBean corrispondenti ai libri selezionati in base a 'categoria'.
     */
    public List<ContenutoBean> doRetrieveAllByCategoria(String categoria){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.doRetrieveAllByCategoria("libro", categoria);
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        List<ContenutoBean> contenuti = new ArrayList<>();

        for(ContenutoBean c: film) {
            int id = c.getId();

            String query = "select autore, isbn, num_pagine as numPagine " +
                    "from Libri " +
                    "where id = " + id;

            String res = queryManager.select(query);
            LibroBean l = gson.fromJson(res, LibroBean.class);

            l.setId(id);
            l.setTitolo(c.getTitolo());
            l.setDescrizione(c.getDescrizione());
            l.setCategoria(c.getCategoria());

            contenuti.add(l);
        }

        return contenuti;
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
