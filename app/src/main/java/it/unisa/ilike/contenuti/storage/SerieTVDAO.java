package it.unisa.ilike.contenuti.storage;

import com.google.gson.Gson;

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
                "FROM SerieTVRid " +
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
     * Restituisce una collezione delle 3 serie tv aventi la massima valutazione media.
     * @return un ArrayList contenente 3 oggetti SerieTVBean.
     */
    public List<ContenutoBean> doRetrieveTop3() {
        List<ContenutoBean> contenuti = super.doRetrieveTop3ByTipo("serie_tv");
        List<ContenutoBean> topSerieTV = new ArrayList<>();

        for(ContenutoBean c : contenuti) {
            topSerieTV.add(this.doRetrieveById(c.getId()));
        }

        return topSerieTV;
    }

    /**
     * Restituisce una collezione di serie tv che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i SerieTVBean selezionati.
     */
    public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> serieTV = (ArrayList<ContenutoBean>) super.search("serie_tv", titolo);
        List<ContenutoBean> serieTVCercate = new ArrayList<>();

        for(ContenutoBean c: serieTV) {
            serieTVCercate.add(this.doRetrieveById(c.getId()));
        }

        return serieTVCercate;
    }

}
