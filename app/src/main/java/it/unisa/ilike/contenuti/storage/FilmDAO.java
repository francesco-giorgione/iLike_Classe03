package it.unisa.ilike.contenuti.storage;


import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.utils.Utils;

public class FilmDAO extends ContenutoDAO {


    private class RisultatoQuery {
        public RisultatoQuery(Integer index, String titolo, Float annoRilascio, String categoria, String paese,  String regista, String descrizione) {
            this.index=index;
            this.titolo=titolo;
            this.annoRilascio=annoRilascio;
            this.categoria=categoria;
            this.paese=paese;
            this.regista=regista;
            this.descrizione=descrizione;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getTitolo() {
            return titolo;
        }

        public void setTitolo(String titolo) {
            this.titolo = titolo;
        }

        public Float getAnnoRilascio() {
            return annoRilascio;
        }

        public void setAnnoRilascio(Float annoRilascio) {
            this.annoRilascio = annoRilascio;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public String getPaese() {
            return paese;
        }

        public void setPaese(String paese) {
            this.paese = paese;
        }

        public String getRegista() {
            return regista;
        }

        public void setRegista(String regista) {
            this.regista = regista;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }

        private Integer index;
        private String titolo;
        private Float annoRilascio;
        private String categoria;
        private String paese;
        private String regista;
        private String descrizione;

    }

    public FilmBean doRetrieveById(int id){
        //ContenutoBean contenuto = super.doRetrieveById(id);

        QueryManager queryManager = new QueryManager();
        String query = "SELECT * " +
                "FROM Film " +
                "WHERE \"index\" = " + id;

        /*String query = "SELECT * " +
                "FROM Film WHERE \"index\" < 3";*/

        Gson gson = new Gson();
        String jsonRes = queryManager.select(query);
        Log.d("MyDebug", jsonRes);
        RisultatoQuery[] res = gson.fromJson(jsonRes, RisultatoQuery[].class);

        if(res == null) {
            return null;
        }

        FilmBean film = new FilmBean();
        film.setId(res[0].getIndex());
        film.setTitolo(res[0].getTitolo());
        film.setAnnoRilascio(res[0].getAnnoRilascio());
        film.setCategoria(res[0].getCategoria());
        film.setPaese(res[0].getPaese());
        film.setRegista(res[0].getRegista());
        film.setDescrizione(res[0].getDescrizione());

        return film;
        //return null;
    }

    /**
     * Restituisce una collezione di film che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente i FilmBean selezionati.
     */
    public ArrayList<FilmBean> search(String titolo){
        //ArrayList<ContenutoBean> film = (ArrayList<ContenutoBean>)
                //super.search("film", titolo);
        //List<ContenutoBean> filmCercati = new ArrayList<>();

        /*for(ContenutoBean c: film) {
            filmCercati.add(this.doRetrieveById(c.getId()));
        }*/

        //return filmCercati;
        titolo = Utils.addEscape(titolo);

        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();
        /*String query = "SELECT id, titolo, descrizione, categoria, valutazione_media as valutazioneMedia " +
                "FROM ContenutiRid " +
                "where tipo like '%" + tipo + "%' and titolo like '%" + titolo + "%'";*/
        String query = "SELECT * " +
                "FROM Film " +
                "where titolo like '%" + titolo + "%'";


        String jsonRes = queryManager.select(query);
        RisultatoQuery[] res= gson.fromJson(jsonRes, RisultatoQuery[].class);

        ArrayList <FilmBean> toReturn= new ArrayList<>();


        int i;
        for (i=0; i<res.length;i++) {
            toReturn.add(new FilmBean(res[i].getIndex(), res[i].getTitolo(), res[i].getAnnoRilascio(), res[i].getCategoria(),
                    res[i].getPaese(), res[i].getRegista(), res[i].getDescrizione()));
        }

        return toReturn;
    }

}