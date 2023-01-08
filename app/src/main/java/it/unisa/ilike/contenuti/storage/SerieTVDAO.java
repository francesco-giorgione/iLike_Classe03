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

    /**
     * Restituisce la serie tv avente un dato id.
     * @param id è l'id del contenuto che si vuole selezionare dal db.
     * @return un oggetto SerieTVBean contenente i dati relativi alla serie tv avente come id 'id'.
     */
    public SerieTVBean doRetrieveById(int id){
        ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT anno_rilascio as annoRilascio, num_stagioni as numStagioni " +
                "FROM SerieTV " +
                "WHERE id = " + contenuto.getId();

        Gson gson = new Gson();
        String jsonRes = queryManager.select(query);
        SerieTVBean[] res = gson.fromJson(jsonRes, SerieTVBean[].class);

        if(res.length == 0) {
            return null;
        }
        
        SerieTVBean serieTV = res[0];

        serieTV.setId(contenuto.getId());
        serieTV.setTitolo(contenuto.getTitolo());
        serieTV.setDescrizione(contenuto.getDescrizione());
        serieTV.setCategoria(contenuto.getCategoria());

        return serieTV;
    }


    /**
     * Restituisce una collezione delle serie tv di una data categoria.
     * @param categoria è la categoria in base alla quale si vogliono selezionare le serie tv.
     * @return un oggetto ArrayList contenente i SerieTVBean selezionati sulla base della categoria.
     */
    public List<ContenutoBean> doRetrieveAllByCategoria(String categoria){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.doRetrieveAllByCategoria("serie_tv", categoria);
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        List<ContenutoBean> contenuti = new ArrayList<>();

        for(ContenutoBean c: film) {
            int id = c.getId();

            String query = "select anno_rilascio as annoRilascio, num_stagioni as numStagioni " +
                    "from SerieTV " +
                    "where id = " + id;

            String res = queryManager.select(query);
            SerieTVBean stv = gson.fromJson(res, SerieTVBean.class);

            stv.setId(id);
            stv.setTitolo(c.getTitolo());
            stv.setDescrizione(c.getDescrizione());
            stv.setCategoria(c.getCategoria());

            contenuti.add(stv);
        }

        return contenuti;
    }


    /**
     * Restituisce tutte le serie tv aventi una valutazione media rientrante in un dato intervallo.
     * @param minValutazione è la è la minima valutazione media che deve avere una serie tv affinché
     * sia selezionata.
     * @param maxValutazione è la massima valutazione media che deve avere una serie tv affinché
     * sia selezionata.
     * @return un ArrayList contenente tutte le serie tv aventi una valutazione media compatibile
     * con quella richiesta.
     */
    public List<ContenutoBean> doRetrieveAllByValutazioneMedia(double minValutazione, double maxValutazione){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.doRetrieveAllByValutazioneMedia(minValutazione, maxValutazione);
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        List<ContenutoBean> contenuti = new ArrayList<>();

        for(ContenutoBean c: film) {
            int id = c.getId();

            String query = "select anno_rilascio as annoRilascio, num_stagioni as numStagioni " +
                    "from SerieTV " +
                    "where id = " + id;

            String res = queryManager.select(query);
            SerieTVBean stv = gson.fromJson(res, SerieTVBean.class);

            stv.setId(id);
            stv.setTitolo(c.getTitolo());
            stv.setDescrizione(c.getDescrizione());
            stv.setCategoria(c.getCategoria());

            contenuti.add(stv);
        }

        return contenuti;
    }


    /**
     * Restituisce tutte le serie tv del catalogo.
     * @return un oggetto List contenente tutti i SerieTVBean del catalogo.
     */
    public List<ContenutoBean> doRetrieveAll(){
        return this.doRetrieveAllByCategoria("%", "%");
    }


    /**
     * Restituisce una collezione di serie tv che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i SerieTVBean selezionati.
     */
    public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>) super.search("serie_tv", titolo);
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        List<ContenutoBean> contenuti = new ArrayList<>();

        for(ContenutoBean c: film) {
            int id = c.getId();

            String query = "select anno_rilascio as annoRilascio, num_stagioni as numStagioni " +
                    "from SerieTV " +
                    "where id = " + id;

            String res = queryManager.select(query);
            SerieTVBean stv = gson.fromJson(res, SerieTVBean.class);

            stv.setId(id);
            stv.setTitolo(c.getTitolo());
            stv.setDescrizione(c.getDescrizione());
            stv.setCategoria(c.getCategoria());

            contenuti.add(stv);
        }

        return contenuti;
    }

}
