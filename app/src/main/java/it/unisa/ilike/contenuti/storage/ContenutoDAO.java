package it.unisa.ilike.contenuti.storage;

import com.google.gson.Gson;

import it.unisa.ilike.QueryManager;

public class ContenutoDAO {
    /**
     * Estende la classe ContenutoBean senza modificarne il comportamento. Consente al DAO di
     * istanziare oggetti ContenutoBean in modo da costruire i bean da restituire in modo incrementale.
     */
    private class NotAbstractContenutoBean extends ContenutoBean {}

    /**
     * Modella il risultato della query eseguita in calcolaValutazioneMediaAggiornata. Consente cioè
     * di convertire il risultato dal formato json in un oggetto Java.
     */
    private class RisultatoQuery {
        public RisultatoQuery(Integer numRecensioni, Integer sommaValutazioni) {
            this.numRecensioni = numRecensioni;
            this.sommaValutazioni = sommaValutazioni;
        }

        public Integer getNumRecensioni() {
            return numRecensioni;
        }

        public void setNumRecensioni(Integer numRecensioni) {
            this.numRecensioni = numRecensioni;
        }

        public Integer getSommaValutazioni() {
            return sommaValutazioni;
        }

        public void setSommaValutazioni(Integer sommaValutazioni) {
            this.sommaValutazioni = sommaValutazioni;
        }

        Integer numRecensioni;
        Integer sommaValutazioni;
    }

    /**
     * Aggiorna nel database la valutazione media di un contenuto.
     * @param idContenuto è l'id del contenuto di cui si vuole aggiornare la valutazione media.
     * @param valutazioneMedia è la nuova valutazione media del contenuto.
     * @return true se l'aggiornamento è eseguito con successo, false altrimenti.
     */
    public boolean doUpdateValutazioneMedia(int idContenuto, double valutazioneMedia) {
        QueryManager queryManager = new QueryManager();
        String query = "update ContenutiRid " +
                "set valutazione_media = " + valutazioneMedia + " " +
                "where id = " + idContenuto;

        return queryManager.update(query);
    }

    /**
     * Restituisce la valutazione media corrente di un dato contenuto.
     * @param idContenuto è l'id del contenuto di cui si vuole ottenere la valutazione media.
     * @return la valutazione media del contenuto avente come id 'id'.
     */
    public double calcolaValutazioneMediaAggiornata(int idContenuto) {

        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();

        String query = "select count(valutazione) as numRecensioni, sum(valutazione) as sommaValutazioni " +
                "from Recensioni " +
                "where id_contenuto = " + idContenuto + " and cancellata = 0";

        String jsonRes = queryManager.select(query);
        RisultatoQuery[] res = gson.fromJson(jsonRes, RisultatoQuery[].class);

        Integer numRecensioni = res[0].getNumRecensioni();
        Integer sommaValutazioni = res[0].getSommaValutazioni();

        if(numRecensioni == 0) {
            return 0;
        }

        if(sommaValutazioni == null) {
            sommaValutazioni = 0;
        }

        return (double) (sommaValutazioni / numRecensioni);
    }

    /**
     * Esegue il fetch di un contenuto dal database.
     * @param id è l'id del contenuto che si vuole selezionare dal db
     * @return un oggetto ContenutoBean contenente l'id, il titolo, la descrizione, la categoria
     * e la valutazione media del contenuto selezionato.
     */
    public ContenutoBean doRetrieveById(int id) {
        /*QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();
        String query = "SELECT * " +
                "from Film " +
                "where \"index\" = " + id;

        String jsonRes = queryManager.select(query);
        NotAbstractContenutoBean[] res = gson.fromJson(jsonRes, NotAbstractContenutoBean[].class);

        if(res != null) {
            return res[0];
        }
        else return null;*/
        FilmDAO dao= new FilmDAO();
        return dao.doRetrieveById(id);
    }


    private class RisultatoQueryRicercaFilm extends ContenutoBean {

        public RisultatoQueryRicercaFilm(Integer id, String titolo, Float annoRilascio, String categoria, String paese,  String regista, String descrizione) {
            this.id =id;
            this.titolo=titolo;
            this.annoRilascio=annoRilascio;
            this.categoria=categoria;
            this.paese=paese;
            this.regista=regista;
            this.descrizione=descrizione;
        }

        public int getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        private Integer id;
        private String titolo;
        private Float annoRilascio;
        private String categoria;
        private String paese;
        private String regista;
        private String descrizione;

    }


    /**
     * Restituisce una collezione di contenuti di un certo tipo (es. film) che matchano con un dato titolo.
     * @param tipo è il tipo del contenuto di cui si bvuole eseguire il fetch ('film' per film,
     *       'serie_tv' per serie tv, 'libro' per libri, 'album' per album musicali, '%' per tutti i tipi).
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente tutti i contenuti di un certo tipo (es. film, serie tv, ecc.)
     * e che matchano con 'titolo'.
     */
    /*public List<ContenutoBean> search(String tipo, String titolo) {
        if(!tipo.equals("film")) {
            return null;
        }

        titolo = Utils.addEscape(titolo);

        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();
        /*String query = "SELECT id, titolo, descrizione, categoria, valutazione_media as valutazioneMedia " +
                "FROM ContenutiRid " +
                "where tipo like '%" + tipo + "%' and titolo like '%" + titolo + "%'";*/
        /*String query = "SELECT * " +
               "FROM Film " +
               "where titolo like '%" + titolo + "%'";


        String jsonRes = queryManager.select(query);
        //NotAbstractContenutoBean[] res = gson.fromJson(jsonRes, NotAbstractContenutoBean[].class);
        RisultatoQueryRicercaFilm[] res= gson.fromJson(jsonRes, RisultatoQueryRicercaFilm[].class);

        return new ArrayList<>(Arrays.asList(res));
    }*/

    /**
     * Restituisce una collezione di contenuti che matchano con un dato titolo.
     * @param titolo è il titolo sulla base di cui viene eseguita la ricerca.
     * @return un ArrayList contenente tutti i contenuti che matchano con 'titolo'.
     */
    /*public List<ContenutoBean> search(String titolo) {
        return this.search("%", titolo);
    }*/
}