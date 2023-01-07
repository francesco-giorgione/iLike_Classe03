package it.unisa.ilike.liste.storage;

import static it.unisa.ilike.utils.Utils.addEscape;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.QueryManager;
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

        String nome = addEscape(lista.getNome());
        String emailIscritto = addEscape(lista.getEmailIscritto());
        boolean visibilita= lista.isVisibile();
        QueryManager queryManager= new QueryManager();

        String query= "insert into Liste (nome, email_iscritto, visibilita " +
                "values ('" + nome + "', '" + emailIscritto+ "', " + visibilita + ");";

        if(queryManager.update(query)) {
            if(lista.getContenuti() != null && !lista.getContenuti().isEmpty()) {
                query = "INSERT INTO Inclusioni(nome_lista, email_iscritto, id_contenuto) VALUES ";

                for(ContenutoBean c: lista.getContenuti()) {
                    query += "('" + nome + "', '" + emailIscritto + "', " + c.getId() + "),";
                }

                query = query.substring(0, query.length() - 1);
                return queryManager.update(query);
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Questo metodo consente di cancellare un oggetto <code>ListaBean</code> dalla tabella Lista del database,
     * individuandolo tramite l'id passato come argomento.
     * @param nome è il nome della lista da cancellare dal database
     * @param emailIscritto è l'email dell'iscritto cui appartiene la lista da cancellare dal database
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean doDeleteByKey(String nome, String emailIscritto){
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

        nome = addEscape(nome);
        emailIscritto = addEscape(emailIscritto);

         String query = "select L.visibilita as visibilita, L.nome as nome, L.email_iscritto as emailIscritto, I.id_contenuto " +
                "from Liste L join Inclusioni I on I.nome_lista = L.nome and I.email_iscritto = L.email_iscritto " +
                "where L.nome = '" + nome + "' and L.email_iscritto = '" + emailIscritto + "'";

        QueryManager queryManager= new QueryManager();
        String res = queryManager.select(query);
        Gson gson= new Gson();
        ListaBean lista = gson.fromJson(res, ListaBean.class);
        lista.setContenuti(new ArrayList<>());

        for(ContenutoBean c: lista.getContenuti()) {
            int id = c.getId();
            ContenutoBean toAdd = null;

            if(c instanceof FilmBean) {
                toAdd = new FilmDAO().doRetrieveById(id);
            }
            else if(c instanceof SerieTVBean) {
                toAdd = new SerieTVDAO().doRetrieveById(id);
            }
            else if(c instanceof LibroBean) {
                toAdd = new LibroDAO().doRetrieveById(id);
            }
            else if(c instanceof AlbumMusicaleBean) {
                toAdd = new AlbumMusicaleDAO().doRetrieveById(id);
            }

            if(toAdd != null) {
                lista.getContenuti().add(toAdd);
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

        if(this.doDeleteByKey(lista.getNome(), lista.getEmailIscritto())) {
            return this.doSave(lista);
        }
        else return false;
    }


    /**
     * Questo metodo restituisce tutti gli oggetti della classe <code>ListaBean</code> riferiti ad un Iscritto memorizzati nel database
     * @param email rappresenta l'email dell'iscritto
     * @return lista di oggetti della classe <code>ListaBean</code> riferiti ad un Iscritto memorizzata nel database
     */
    public List<ListaBean> doRetrieveListeByEmailIscritto(String email){
        email = addEscape(email);

        String query = "select email_iscritto as emailIscritto, nome " +
                "from Liste " +
                "where email_iscritto= '" + email + "'";

        QueryManager queryManager= new QueryManager();
        String res = queryManager.select(query);
        Gson gson = new Gson();
        List<ListaBean> toReturn = new ArrayList<>();

        ListaPrimaryKeyCollection listaPrimaryKeyCollection = gson.fromJson(res, ListaPrimaryKeyCollection.class);

        for(ListaPrimaryKey key: listaPrimaryKeyCollection.chiavi){
            toReturn.add(this.doRetrieveByKey(key.getNome(), key.getEmailIscritto()));
        }

        return toReturn;
    }

    private class ListaPrimaryKey {
        public ListaPrimaryKey() {}

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmailIscritto() {
            return emailIscritto;
        }

        public void setEmailIscritto(String emailIscritto) {
            this.emailIscritto = emailIscritto;
        }

        String nome;
        String emailIscritto;
    }

    private class ListaPrimaryKeyCollection {
        public ListaPrimaryKeyCollection() {}

        public List<ListaPrimaryKey> getChiavi() {
            return chiavi;
        }

        public void setChiavi(List<ListaPrimaryKey> chiavi) {
            this.chiavi = chiavi;
        }

        List<ListaPrimaryKey> chiavi;
    }
}
