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
     * Questo metodo consente di salvare nella tabella Lista del database un nuovo oggetto della classe
     * <code>ListaBean</code> passato come argomento.
     * @param lista nuovo oggetto della classe <code>ListaBean</code> da salvare nel database
     * @return false se la lista passata come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
     */
    public boolean doSave(ListaBean lista){

        if (lista== null){
            return false;
        }

        String nome= lista.getNome();
        String emailIscritto=lista.getEmailIscritto();
        boolean visibilita= lista.isVisibile();


        QueryManager queryManager= new QueryManager();
        String query= "insert into Lista (nome, email_iscritto, visibilita values ('" +
                nome + "', '" + emailIscritto+ "', " + visibilita + ");";
        return queryManager.update(query);
    }

    /**
     * Questo metodo consente di cancellare un oggetto <code>ListaBean</code> dalla tabella Lista del database,
     * individuandolo tramite l'id passato come argomento.
     * @param id id della lista da cancellare dal database
     * @return false se l'id passato come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
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
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>ListaBean</code>
     * presente nella tabella Lista del database, dopo averlo individuato tramite l'id passato come argomento
     * @param id id della lista da cercare nel database
     * @return null se il parametro id non è valido, l'oggetto lista con chiave primaria uguale ad id
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
     * Questo metodo restituisce tutti gli oggetti della classe <code>ListaBean</code> memorizzati nel database
     * @return lista di oggetti della classe <code>ListaBean</code> memorizzata nel database
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
