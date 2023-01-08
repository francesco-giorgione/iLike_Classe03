package it.unisa.ilike.contenuti.storage;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.contenuti.application.ContenutoBean;
import it.unisa.ilike.contenuti.application.LibroBean;

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

        return libro;
    }


    /**
     * Restituisce una collezione dei 3 libri aventi la massima valutazione media.
     * @return un ArrayList contenente 3 oggetti LibroBean.
     */
    public List<ContenutoBean> doRetrieveTop3() {
        List<ContenutoBean> contenuti = super.doRetrieveTop3ByTipo("libro");
        List<ContenutoBean> topLibri = new ArrayList<>();

        for(ContenutoBean c : contenuti) {
            topLibri.add(this.doRetrieveById(c.getId()));
        }

        return topLibri;
    }
    
    /**
     * Restituisce una collezione dei libri che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i LibroBean selezionati.
     */
    public List<ContenutoBean> search(String titolo){
        ArrayList<ContenutoBean> libri = (ArrayList<ContenutoBean>) super.search("libro", titolo);
        List<ContenutoBean> libriCercati = new ArrayList<>();

        for(ContenutoBean c: libri) {
            libriCercati.add(this.doRetrieveById(c.getId()));
        }

        return libriCercati;
    }


}
