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

public class FilmDAO extends ContenutoDAO {

    public FilmBean doRetrieveById(int id){
        ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT anno_rilascio as annoRilascio, durata, paese, regista, attori " +
                "FROM Film " +
                "WHERE id = " + contenuto.getId();

        String res = queryManager.select(query);
        Gson gson = new Gson();
        FilmBean film = gson.fromJson(res, FilmBean.class);

        film.setId(contenuto.getId());
        film.setTitolo(contenuto.getTitolo());
        film.setDescrizione(contenuto.getDescrizione());
        film.setCategoria(contenuto.getCategoria());

        return film;
    }

    /**
     * Restituisce tutti i film di una certa categoria
     * @param categoria è la categoria dei film che si vogliono ottenere.
     * @return una collezione di tutti i film la cui categoria è 'categoria'.
     */
    public List<ContenutoBean> doRetrieveAllByCategoria(String categoria) {
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.doRetrieveAllByCategoria("film", categoria);
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        List<ContenutoBean> contenuti = new ArrayList<>();

        for(ContenutoBean c: film) {
            int id = c.getId();

            String query = "select anno_rilascio as annoRilascio, durata, paese, regista, attori " +
                    "from Film " +
                    "where id = " + id;

            String res = queryManager.select(query);
            FilmBean f = gson.fromJson(res, FilmBean.class);

            f.setId(id);
            f.setTitolo(c.getTitolo());
            f.setDescrizione(c.getDescrizione());
            f.setCategoria(c.getCategoria());

            contenuti.add(f);
        }

        return contenuti;
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


    public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.search("film", titolo);
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        List<ContenutoBean> contenuti = new ArrayList<>();

        for(ContenutoBean c: film) {
            int id = c.getId();

            String query = "select anno_rilascio as annoRilascio, durata, paese, regista, attori " +
                    "from Film " +
                    "where id = " + id;

            String res = queryManager.select(query);
            FilmBean f = gson.fromJson(res, FilmBean.class);

            f.setId(id);
            f.setTitolo(c.getTitolo());
            f.setDescrizione(c.getDescrizione());
            f.setCategoria(c.getCategoria());

            contenuti.add(f);
        }

        return contenuti;
    }

    // da implementare
    public List<ContenutoBean> doRetrieveByLista(String nomeLista, String emailIscritto) {
        return null;
    }

}
