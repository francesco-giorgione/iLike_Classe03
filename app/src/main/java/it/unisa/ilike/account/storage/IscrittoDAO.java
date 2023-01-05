package it.unisa.ilike.account.storage;

import com.google.gson.Gson;

import it.unisa.ilike.QueryManager;

public class IscrittoDAO {

    public IscrittoBean doRetrieveByEmail(String email){

        if(email == null){
            return null;
        }

        String query = "select * from Iscritto where email = '" + email + "' ";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        IscrittoBean iscritto= gson.fromJson(res, IscrittoBean.class);
        return iscritto;
    }

    public IscrittoBean doRetrieveByNickname(String nickname){

        if(nickname == null){
            return null;
        }

        String query = "select * from Iscritto where nickname = '" + nickname + "' ";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        IscrittoBean iscritto= gson.fromJson(res, IscrittoBean.class);
        return iscritto;
    }

    public boolean doSave(IscrittoBean iscritto){

        String email=iscritto.getEmail();
        String password= iscritto.getPassword();
        return false;
    }

}
