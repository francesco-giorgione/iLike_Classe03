package it.unisa.ilike.contenuti.storage;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.utils.Utils;

public class ContenutoDAO {
    /**
     * Aggiorna nel database la valutazione media di un contenuto.
     * @param idContenuto è l'id del contenuto di cui si vuole aggiornare la valutazione media.
     * @param valutazioneMedia è la nuova valutazione media del contenuto.
     * @return true se l'aggiornamento è eseguito con successo, false altrimenti.
     */
    public boolean doUpdateValutazioneMedia(int idContenuto, float valutazioneMedia) {
        QueryManager queryManager = new QueryManager();
        String query = "update Contenuti " +
                "set valutazione_media = " + valutazioneMedia + " " +
                "where id = " + idContenuto;

        return queryManager.update(query);
    }

    /**
     * Esegue il fetch di un contenuto dal database.
     * @param id è l'id del contenuto che si vuole selezionare dal db
     * @return un oggetto ContenutoBean contenente l'id, il titolo, la descrizione, la categoria
     * e la valutazione media del contenuto selezionato.
     */
    public ContenutoBean doRetrieveById(int id) {
        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();
        String query = "SELECT id, titolo, descrizione, categoria, valutazione_media as valutazioneMedia " +
                "from Contenuti " +
                "where id = " + id;

        String res = queryManager.select(query);
        return gson.fromJson(res, ContenutoBean.class);
    }


    /**
     * Restituisce una collezione di tutti i contenuti di un certo tipo (es. tutti i film).
     * @param tipo - è il tipo del contenuto di cui si bvuole eseguire il fetch ('film' per film,
     *             'serie_tv' per serie tv, 'libri' per libri, 'album' per album musicali, '%' per
     *             tutti i tipi).
     * @return un ArrayList contenente tutti i contenuti di un certo tipo (es. film, serie tv, ecc.).
     */
    public List<ContenutoBean> doRetrieveAllByCategoria(String tipo, String categoria) {
        if(!tipo.equals("%") && !tipo.equals("film") && !tipo.equals("serie_tv") && !tipo.equals("libri") && !tipo.equals("album")) {
            return null;
        }

        categoria = Utils.addEscape(categoria);

        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();
        String query = "SELECT id, titolo, descrizione, categoria, valutazione_media as valutazioneMedia " +
                "FROM Contenuti " +
                "where tipo like '%" + tipo + "%' and categoria like '%" + categoria + "%'";

        String res = queryManager.select(query);
        // da controllare cast, probabilmente non funziona
        List<ContenutoBean> contenuti = (List<ContenutoBean>) gson.fromJson(res, ContenutoBean.class);
        return contenuti;
    }




    /**
     * Restituisce una collezione di contenuti di un certo tipo (es. film) che matchano con un dato titolo.
     * @param tipo è il tipo del contenuto di cui si bvuole eseguire il fetch ('film' per film,
     *      *             'serie_tv' per serie tv, 'libri' per libri, 'album' per album musicali).
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente tutti i contenuti di un certo tipo (es. film, serie tv, ecc.)
     * e che matchano con 'titolo'.
     */
    public List<ContenutoBean> search(String tipo, String titolo) {
        if(!tipo.equals("film") && !tipo.equals("serie_tv") && !tipo.equals("libri") && !tipo.equals("album")) {
            return null;
        }

        titolo = Utils.addEscape(titolo);

        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();
        String query = "SELECT id, titolo, descrizione, categoria, valutazione_media as valutazioneMedia " +
                "FROM Contenuti " +
                "where tipo = '" + tipo + "' and titolo like = '%" + titolo + "%';";

        String res = queryManager.select(query);
        // da controllare cast, probabilmente non funziona
        List<ContenutoBean> contenuti = (List<ContenutoBean>) gson.fromJson(res, ContenutoBean.class);
        return contenuti;
    }

    /**
     * Restituisce una collezione di contenuti che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente tutti i contenuti che matchano con 'titolo'.
     */
    public List<ContenutoBean> search(String titolo) {
        titolo = Utils.addEscape(titolo);

        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();
        String query = "SELECT id, titolo, descrizione, categoria, valutazione_media as valutazioneMedia " +
                "FROM Contenuti " +
                "where titolo like = '%" + titolo + "%';";

        String res = queryManager.select(query);
        // da controllare cast, probabilmente non funziona
        List<ContenutoBean> contenuti = (List<ContenutoBean>) gson.fromJson(res, ContenutoBean.class);
        return contenuti;
    }
}
