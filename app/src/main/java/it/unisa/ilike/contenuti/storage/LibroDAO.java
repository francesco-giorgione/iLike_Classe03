package it.unisa.ilike.contenuti.storage;

public class LibroDAO extends ContenutoDAO {

}

   /* /**
     * Restituisce il libro avente un dato id.
     * @param id è l'id del contenuto che si vuole selezionare dal db
     * @return un oggetto LibroBean contenente le informazioni relative al libro selezionato.
     */
    /*public LibroBean doRetrieveById(int id){
        ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT autore, isbn, num_pagine as numPagine " +
                "FROM LibriRid " +
                "WHERE id = " + contenuto.getId();

        Gson gson = new Gson();
        String jsonRes = queryManager.select(query);
        LibroBean[] res = gson.fromJson(jsonRes, LibroBean[].class);

        if(res.length == 0) {
            return null;
        }

        LibroBean libro = res[0];

        libro.setId(contenuto.getId());
        libro.setTitolo(contenuto.getTitolo());
        libro.setDescrizione(contenuto.getDescrizione());
        libro.setCategoria(contenuto.getCategoria());
        libro.setValutazioneMedia(contenuto.getValutazioneMedia());

        return libro;
    }

    
    /**
     * Restituisce una collezione dei libri che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i LibroBean selezionati.
     */
    /*public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> libri = (ArrayList<ContenutoBean>) super.search("libro", titolo);
        List<ContenutoBean> libriCercati = new ArrayList<>();

        for(ContenutoBean c: libri) {
            libriCercati.add(this.doRetrieveById(c.getId()));
        }

        return libriCercati;
    }


}*/
