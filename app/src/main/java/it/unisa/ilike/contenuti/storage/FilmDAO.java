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

        if(film == null) {
            return null;
        }

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

    /**
     * Restituisce tutti i film del catalogo.
     * @return un oggetto List contenente tutti i FilmBean del catalogo.
     */
    public List<ContenutoBean> doRetrieveAll(){
        return this.doRetrieveAllByCategoria("%", "%");
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
