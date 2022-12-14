package it.unisa.ilike.contenuti.storage;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;

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
                "FROM FilmRid " +
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
        film.setValutazioneMedia(contenuto.getValutazioneMedia());

        return film;
    }


    /**
     * Restituisce una collezione di film che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i FilmBean selezionati.
     */
    public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.search("film", titolo);
        List<ContenutoBean> filmCercati = new ArrayList<>();

        for(ContenutoBean c: film) {
            filmCercati.add(this.doRetrieveById(c.getId()));
        }

        return filmCercati;
    }

}
