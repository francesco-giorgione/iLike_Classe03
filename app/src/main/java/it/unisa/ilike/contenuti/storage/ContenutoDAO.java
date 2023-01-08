package it.unisa.ilike.contenuti.storage;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.utils.Utils;

public class ContenutoDAO {
    /**
     * Estende la classe ContenutoBean senza modificarne il comportamento. Consente al DAO di
     * istanziare oggetti ContenutoBean in modo da costruire i bean da restituire in modo incrementale.
     */
    private class NotAbstractContenutoBean extends ContenutoBean {}

    /**
     * Aggiorna nel database la valutazione media di un contenuto.
     * @param idContenuto è l'id del contenuto di cui si vuole aggiornare la valutazione media.
     * @param valutazioneMedia è la nuova valutazione media del contenuto.
     * @return true se l'aggiornamento è eseguito con successo, false altrimenti.
     */
    public boolean doUpdateValutazioneMedia(int idContenuto, double valutazioneMedia) {
        QueryManager queryManager = new QueryManager();
        String query = "update Contenuti " +
                "set valutazione_media = " + valutazioneMedia + " " +
                "where id = " + idContenuto;

        return queryManager.update(query);
    }

    /**
     * Restituisce la valutazione media corrente di un dato contenuto.
     * @param idContenuto è l'id del contenuto di cui si vuole ottenere la valutazione media.
     * @return la valutazione media del contenuto avente come id 'id'.
     */
    public double calcolaValutazioneMediaAggiornata(int idContenuto, int newValutazione) {
        class RisultatoQuery {
            public int getNumRecensioni() {
                return numRecensioni;
            }

            public void setNumRecensioni(int numRecensioni) {
                this.numRecensioni = numRecensioni;
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

        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();

        String query = "select count(valutazione) as numRecensioni, sum(valutazione) as sommaValutazioni " +
                "from Recensioni " +
                "where id_contenuto = " + idContenuto;

        String res = queryManager.select(query);
        int numRecensioni = gson.fromJson(res, RisultatoQuery.class).getNumRecensioni() + 1;
        int sommaValutazioni = gson.fromJson(res, RisultatoQuery.class).getSommaValutazioni() + newValutazione;

        return (double) (sommaValutazioni / numRecensioni);
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

        String jsonRes = queryManager.select(query);
        NotAbstractContenutoBean[] res = gson.fromJson(jsonRes, NotAbstractContenutoBean[].class);

        if(res.length > 0) {
            return res[0];
        }
        else return null;
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
     * Restituisce una collezione di tutti i contenuti aventi una valutazione rientrante in un dato
     * intervallo.
     * @param minValutazione è la minima valutazione media che deve avere un contenuto affinché
     *                       sia selezionato.
     * @param maxValutazione è la massima valutazione media che deve avere un contenuto affinché
     *      *                sia selezionato.
     * @return un ArrayList contenente tutti i contenuti aventi una valutazione media compatibile
     * con quella richiesta.
     */
    public List<ContenutoBean> doRetrieveAllByValutazioneMedia(double minValutazione, double maxValutazione) {
        QueryManager queryManager = new QueryManager();
        Gson gson = new Gson();
        String query = "SELECT id, titolo, descrizione, categoria, valutazione_media as valutazioneMedia " +
                "FROM Contenuti " +
                "where valutazione_media >= " + minValutazione + " and valutazione_media <= " + maxValutazione;

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
