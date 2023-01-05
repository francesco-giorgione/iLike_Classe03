package it.unisa.ilike.liste.storage;

import com.google.gson.Gson;

import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.contenuti.storage.ContenutoBean;

/**
 * Un oggetto <code>ListaDAO</code> serve per interagire con la tabella Lista presente nel database
 * @version 0.1
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

        String nome= lista.getNome();
        String emailIscritto=lista.getEmailIscritto();
        boolean visibilita= lista.isVisibile();

        boolean esito;
        QueryManager queryManager= new QueryManager();

        String query= "insert into Liste (nome, email_iscritto, visibilita values ('" +
                nome + "', '" + emailIscritto+ "', " + visibilita + ");";


        if(queryManager.update(query)) {
            if(!lista.getContenuti().isEmpty()) {
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

        QueryManager queryManager= new QueryManager();

        String query = "delete from Liste " +
                "where nome = '" + nome + "' and email_iscritto = '" + emailIscritto + "'";

        return queryManager.update(query);
    }


    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>ListaBean</code>
     * presente nella tabella Lista del database, dopo averlo individuato tramite la chiave
     * (nome, email iscritto) passata come argomento.
     * @param nome è il nome della lista da cercare nel database.
     * @param emailIscritto è l'email dell'iscritto cui appartiene la lista da cercare nel database.
     * @return la lista cercata se l'operazione va a buon fine, null altrimenti.
     */
    public ListaBean doRetrieveByKey(String nome, String emailIscritto){
        if (nome == null || emailIscritto == null){
            return null;
        }

        String query = "select * " +
                "from Liste " +
                "where nome = '" + nome + "' and email_iscritto = '" + emailIscritto + "'";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);

        Gson gson= new Gson();
        ListaBean lista = gson.fromJson(res, ListaBean.class);

        query = "select C.id as id, C.titolo as titolo, C.descrizione as descrizione, C.categoria as categoria " +
                "from Liste L join Inclusioni I on I.nome_lista = L.nome and I.email_iscritto = L.email_iscritto " +
                "join Contenuti C on I.id_contenuto = C.id " +
                "where L.nome = '" + nome + "' and L.email_iscitto = '" + emailIscritto + "'";

        res = queryManager.select(query);

        List<ContenutoBean> contenuti = (List<ContenutoBean>) gson.fromJson(res, ContenutoBean.class);
        lista.setContenuti(contenuti);

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
     * Questo metodo restituisce tutti gli oggetti della classe <code>ListaBean</code> memorizzati nel database
     * @return lista di oggetti della classe <code>ListaBean</code> memorizzata nel database
     */
    /*
    public List<ListaBean> doRetrieveAll(){

        String query="select * from Lista";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        List<ListaBean> listToReturn = (List<ListaBean>) gson.fromJson(res, ListaBean.class);

        query = "select C.id as id, C.titolo as titolo, C.descrizione as descrizione, C.categoria as categoria " +
                "from Liste L join Inclusioni I on I.nome_lista = L.nome and I.email_iscritto = L.email_iscritto " +
                "join Contenuti C on I.id_contenuto = C.id";

        res = queryManager.select(query);

        List<ContenutoBean> contenuti = (List<ContenutoBean>) gson.fromJson(res, ContenutoBean.class);
        listToReturn.setContenuti(contenuti);

        return listToReturn;
    }
    */

}
