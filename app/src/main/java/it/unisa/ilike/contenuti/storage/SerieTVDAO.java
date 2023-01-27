package it.unisa.ilike.contenuti.storage;

public class SerieTVDAO extends ContenutoDAO {}

    /*/**
     * Restituisce la serie tv avente un dato id.
     * @param id è l'id del contenuto che si vuole selezionare dal db.
     * @return un oggetto SerieTVBean contenente i dati relativi alla serie tv avente come id 'id'.
     */
    /*public SerieTVBean doRetrieveById(int id){
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
        serieTV.setValutazioneMedia(contenuto.getValutazioneMedia());

        return serieTV;
    }


    /**
     * Restituisce una collezione di serie tv che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i SerieTVBean selezionati.
     */
    /*public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> serieTV = (ArrayList<ContenutoBean>) super.search("serie_tv", titolo);
        List<ContenutoBean> serieTVCercate = new ArrayList<>();

        for(ContenutoBean c: serieTV) {
            serieTVCercate.add(this.doRetrieveById(c.getId()));
        }

        return serieTVCercate;
    }

}*/
