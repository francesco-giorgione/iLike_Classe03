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
    /**
     * Esegue il fetch di un film dal database.
     * @param id è l'id del contenuto che si vuole selezionare dal db
     * @return un oggetto FilmBean contenente le informazioni del film selezionato, null se il film non viene trovato.
     */
    public FilmBean doRetrieveById(int id){
        ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT anno_rilascio as annoRilascio, durata, paese, regista, attori " +
                "FROM Film " +
                "WHERE id = " + contenuto.getId();

        Gson gson = new Gson();
        String jsonRes = queryManager.select(query);
        FilmBean[] res = gson.fromJson(jsonRes, FilmBean[].class);

        if(res.length == 0) {
            return null;
        }

        FilmBean film = res[0];

        film.setId(contenuto.getId());
        film.setTitolo(contenuto.getTitolo());
        film.setDescrizione(contenuto.getDescrizione());
        film.setCategoria(contenuto.getCategoria());

        return film;
    }

    /**
     * Restituisce una collezione dei 3 film aventi la massima valutazione media.
     * @return un ArrayList contenente 3 oggetti FilmBean.
     */
    public List<ContenutoBean> doRetrieveTop3() {
        List<ContenutoBean> contenuti = super.doRetrieveTop3ByTipo("film");
        List<ContenutoBean> topFilm = new ArrayList<>();

        for(ContenutoBean c : contenuti) {
            topFilm.add(this.doRetrieveById(c.getId()));
        }

        return topFilm;
    }


    /**
     * Restituisce una collezione di film che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i FilmBean selezionati.
     */
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


}
