package it.unisa.ilike.liste.storage;

import com.google.gson.Gson;

import java.util.List;

import it.unisa.ilike.QueryManager;

/**
 * Un oggetto <code>ListaDAO</code> serve per interagire con la tabella Lista presente nel database
 * @version 0.1
 * @author LuiginaCostante
 */

public class ListaDAO {

    /**
     * Questo metodo permette di salvare nella tabella Lista del database un nuovo oggetto <code>ListaBean</code>
     * passato come argomento.
     * @param lista nuovo oggetto <code>ListaBean</code> da salvare nel database
     * @return true se l'operazione è andata a buon fine, false se l'oggetto lista è nullo o se l'operazione non
     * è andata a buon fine
     */
    public boolean doSave(ListaBean lista){

        if (lista== null){
            return false;
        }

        String nome= lista.getNome();
        String emailIscritto=lista.getEmail_iscritto();
        boolean visibilita= lista.isVisibile();


        QueryManager queryManager= new QueryManager();
        String query= "insert into Lista (nome, email_iscritto, visibilita values ('" +
                nome + "', '" + emailIscritto+ "', " + visibilita + ");";
        return queryManager.update(query);
    }

    /**
     * Questo metodo consente di cancellare un oggetto <code>ListaBean</code> dalla tabella Lista del database
     * individuandola tramite l'id passato come argomento.
     * @param id id della lista da cancellare dal database
     * @return true se l'operazione è andata a buon fine, false se l'id non è valido o se l'operazione non è
     * andata a buon fine
     */
    public boolean doDeleteById(int id){

        if (id<1){
            return false;
        }

        QueryManager queryManager= new QueryManager();
        String query = "delete from Lista where id = " + id;
        return queryManager.update(query);
    }


    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto <code>ListaBean</code>
     * presente nella tabella Lista del database, dopo averla individuata tramite l'id passato come argomento
     * @param id id della lista da cercare nel database
     * @return null se il parametro id non è valido, l'oggetto lista contenente il parametro id
     * se l'operazione è andata a buon fine
     */
    public ListaBean doRetrieveById(int id){

        if (id<1){
            return null;
        }
        String query= "select * from Lista where id = " + id;
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);

        Gson gson= new Gson();
        ListaBean lista= gson.fromJson(res, ListaBean.class);

        return lista;
    }

    /**
     * Questo metodo restituisce tutti gli oggetti <code>ListaBean</code> memorizzati nel database
     * @return lista di oggetti <code>ListaBean</code> memorizzata nel database
     */

    public List<ListaBean> doRetrieveAll(){

        String query="select * from Lista";

        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        List<ListaBean> listToReturn = (List<ListaBean>) gson.fromJson(res, ListaBean.class);

        return listToReturn;
    }



}
