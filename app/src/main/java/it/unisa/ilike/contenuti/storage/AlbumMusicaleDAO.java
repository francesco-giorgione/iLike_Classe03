package it.unisa.ilike.contenuti.storage;

public class AlbumMusicaleDAO extends ContenutoDAO {}

    /*/**
     * Restituisce l'album musicale avente un dato id.
     * @param id è l'id dell'album musicale che si vuole selezionare dal db
     * @return un oggetto AlbumMusicaleBean contenente le informazioni relative all'album
     * musicale selezionato.
     */
    /*public AlbumMusicaleBean doRetrieveById(int id){
        ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT artista, data_rilascio as dataRilascio, acustica, strumentalita, tempo, valenza, durata " +
                "FROM AlbumMusicaliRid " +
                "WHERE id = " + contenuto.getId();

        Gson gson = new Gson();
        String jsonRes = queryManager.select(query);
        AlbumMusicaleBean[] res = gson.fromJson(jsonRes, AlbumMusicaleBean[].class);

        if(res.length == 0) {
            return null;
        }

        AlbumMusicaleBean albumMusicale = res[0];

        albumMusicale.setId(contenuto.getId());
        albumMusicale.setTitolo(contenuto.getTitolo());
        albumMusicale.setDescrizione(contenuto.getDescrizione());
        albumMusicale.setCategoria(contenuto.getCategoria());
        albumMusicale.setValutazioneMedia(contenuto.getValutazioneMedia());

        return albumMusicale;
    }


    /**
     * Restituisce una collezione degli album musicali che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente gli AlbumMusicaleBean selezionati.
     */
    /*public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> album = (ArrayList<ContenutoBean>) super.search("album", titolo);
        List<ContenutoBean> albumCercati = new ArrayList<>();

        for(ContenutoBean c: album) {
            albumCercati.add(this.doRetrieveById(c.getId()));
        }

        return albumCercati;
    }

}*/
