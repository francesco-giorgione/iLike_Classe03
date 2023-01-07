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


    /**
     * Restituisce tutti i libri del catalogo.
     * @return un oggetto List contenente tutti i LibroBean del catalogo.
     */
    public List<ContenutoBean> doRetrieveAll(){
        return this.doRetrieveAllByCategoria("%", "%");
    }


    /**
     * Restituisce una collezione dei libri che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i LibroBean selezionati.
     */
    public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.search("libro", titolo);
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


}
