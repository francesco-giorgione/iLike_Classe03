package it.unisa.ilike.liste.storage;

import static it.unisa.ilike.utils.Utils.addEscape;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.contenuti.storage.AlbumMusicaleBean;
import it.unisa.ilike.contenuti.storage.AlbumMusicaleDAO;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.ContenutoDAO;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.FilmDAO;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.LibroDAO;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.contenuti.storage.SerieTVDAO;
import it.unisa.ilike.utils.Utils;

/**
 * Un oggetto <code>ListaDAO</code> serve per interagire con la tabella Lista presente nel database
 * @version 0.2
 * @author LuiginaCostante
 */

public class ListaDAO {
    /**
     * Modella il risultato della prima query eseguita in doRetrieveByKey. Consente cioè
     * di convertire il risultato dal formato json in un oggetto Java.
     */
    public class RisultatoQuery {
        String nome, emailIscritto;
        int visibilita;
    }

    /**
     * Modella il risultato della seconda query eseguita in doRetrieveByKey. Consente cioè
     * di convertire il risultato dal formato json in un oggetto Java.
     */
    public class RisultatoQuery2 {
        int idContenuto;
    }

    /**
     * Questo metodo consente di salvare nella tabella Lista del database un nuovo oggetto della classe
     * <code>ListaBean</code> passato come argomento, considerando i contenuti appartenenti alla lista.
     * @param lista nuovo oggetto della classe <code>ListaBean</code> da salvare nel database
     * @return false se l'inserimento in entrambe le tabelle del db Liste e Inclusioni è avvenuto
     * correttamente, false altrimenti.
     */
    public boolean doSave(ListaBean lista){
        if (lista == null){
            return false;
        }

        String nome = Utils.addEscape(lista.getNome());
        String emailIscritto = Utils.addEscape(lista.getIscritto().getEmail());
        int visibilita = lista.isVisibile() ? 1 : 0;
        QueryManager queryManager= new QueryManager();

        String query = "insert into Liste (nome, email_iscritto, visibilita) " +
                "values ('" + nome + "', '" + emailIscritto+ "', " + visibilita + ");";

        if(queryManager.update(query)) {
            if(lista.getContenuti() == null || lista.getContenuti().isEmpty()) {
                return true;
            }

            query = "INSERT INTO Inclusioni(nome_lista, email_iscritto, id_contenuto) VALUES ";

            for(ContenutoBean c: lista.getContenuti()) {
                query += "('" + nome + "', '" + emailIscritto + "', " + c.getId() + "),";
            }

            return queryManager.update(query.substring(0, query.length() - 1));
        }
        else return false;
    }

    /**
     * Questo metodo consente di cancellare un oggetto <code>ListaBean</code> dalla tabella Lista del database,
     * individuandolo tramite l'id passato come argomento.
     * @param nome è il nome della lista da cancellare dal database
     * @param emailIscritto è l'email dell'iscritto cui appartiene la lista da cancellare dal database
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    private boolean doDeleteByKey(String nome, String emailIscritto){
        if (nome == null || emailIscritto == null){
            return false;
        }

        nome = addEscape(nome);
        emailIscritto = addEscape(emailIscritto);

        QueryManager queryManager= new QueryManager();

        String query = "delete from Liste where nome = '" + nome + "' and email_iscritto = '" + emailIscritto + "'";

        return queryManager.update(query);
    }


    /**
     * Restituisce un oggetto della classe <code>ListaBean</code>
     * presente nella tabella Lista del database, dopo averlo individuato tramite la chiave
     * (nome, email iscritto) passata come argomento. Nella collezione dei contenuti sono inseriti
     * i ContenutiBean contenenti le informazioni relative ai contenuti appartenenti alla lista.
     * @param nome è il nome della lista da cercare nel database.
     * @param emailIscritto è l'email dell'iscritto cui appartiene la lista da cercare nel database.
     * @return la lista cercata se l'operazione va a buon fine, null altrimenti.
     */
    public ListaBean doRetrieveByKey(String nome, String emailIscritto){
        if (nome == null || emailIscritto == null){
            return null;
        }

        nome = Utils.addEscape(nome);
        emailIscritto = Utils.addEscape(emailIscritto);

        String query = "select nome, email_iscritto as emailIscritto, visibilita " +
                "from Liste " +
                "where nome = '" + nome + "' and email_iscritto = '" + emailIscritto + "'";

        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        String jsonRes = queryManager.select(query);
        RisultatoQuery[] res = gson.fromJson(jsonRes, RisultatoQuery[].class);

        if(res.length == 0) {
            return null;
        }

        RisultatoQuery tmpRes = res[0];
        ListaBean lista = new ListaBean();
        lista.setNome(nome);
        lista.setVisibilita(tmpRes.visibilita == 1);
        lista.setContenuti(new ArrayList<>());
        lista.setIscritto(new IscrittoDAO().doRetrieveByEmail(tmpRes.emailIscritto));

        query = "select id_contenuto as idContenuto " +
                "from Inclusioni " +
                "where nome_lista = '" + nome + "' and email_iscritto = '" + emailIscritto + "'";

        jsonRes = queryManager.select(query);
        RisultatoQuery2[] res2 = gson.fromJson(jsonRes, RisultatoQuery2[].class);

        if(res2.length == 0) {
            return lista;
        }

        for(RisultatoQuery2 curr: res2) {
            int id = curr.idContenuto;
            ContenutoBean currBean = new ContenutoDAO().doRetrieveById(curr.idContenuto);
            ContenutoBean toAdd = null;

            toAdd = new LibroDAO().doRetrieveById(id);

            if(toAdd == null) {
                toAdd = new FilmDAO().doRetrieveById(id);

                if(toAdd == null) {
                    toAdd = new SerieTVDAO().doRetrieveById(id);

                    if(toAdd == null) {
                        toAdd = new AlbumMusicaleDAO().doRetrieveById(id);
                    }
                }
            }

            if(toAdd != null) {
                lista.aggiungiContenuto(toAdd);
            }
        }

        return lista;
    }


    /**
     * Il metodo esegue un aggiornamento nel database di una lista già esistente.
     * @param lista è la lista che si vuole aggiornare
     * @return true se l'aggiornamento va a buon fine, false altrimenti
     */
    public boolean doUpdate(ListaBean lista) {
        if(lista == null) {
            return false;
        }

        if(this.doDeleteByKey(lista.getNome(), lista.getIscritto().getEmail())) {
            return this.doSave(lista);
        }
        else return false;
    }


}
