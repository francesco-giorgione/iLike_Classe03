package it.unisa.ilike.liste.application;

import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.IscrittoRealBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.application.exceptions.ContenutoGiaPresenteException;
import it.unisa.ilike.liste.application.exceptions.InvalidNomeException;
import it.unisa.ilike.liste.application.exceptions.ListaGiaEsistenteException;
import it.unisa.ilike.liste.application.exceptions.NomeVuotoException;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.liste.storage.ListaDAO;
import it.unisa.ilike.utils.Utils;

/**
 * La classe implementa i servizi relativi alla gestione delle liste.
 * @version 0.2
 * @author FrancescoGiorgione
 * @see ListaService
 */
public class ListaImpl implements ListaService {

    public ListaImpl(){
        this.listaDAO = new ListaDAO();
        this.listaBean = new ListaBean();
        this.utils = new Utils();
    }

    public ListaImpl(ListaDAO listaDAO, Utils utils, ListaBean listaBean){
        this.listaDAO = listaDAO;
        this.utils = utils;
        this.listaBean = listaBean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IscrittoBean creaLista(IscrittoBean i, String nome, boolean pubblica) throws
            NomeVuotoException, InvalidNomeException, ListaGiaEsistenteException {

        if(i == null) {
            return null;
        }

        if(nome.length() == 0)          { throw new NomeVuotoException(); }
        if(nome.length() > 50)          { throw new InvalidNomeException(); }
        if(utils.hasLista(i, nome))     { throw new ListaGiaEsistenteException(); }

        listaBean.setNome(nome);
        listaBean.setIscritto(i);
        listaBean.setVisibilita(pubblica);
        if(listaDAO.doSave(listaBean))
            i.addLista(listaBean);
        else
            return null;

        return i;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean aggiungiContenuto(ListaBean l, ContenutoBean c) throws ContenutoGiaPresenteException {
        for(ContenutoBean curr: l.getContenuti()) {
            if(curr.getId() == c.getId()) {
                throw new ContenutoGiaPresenteException();
            }
        }

        l.aggiungiContenuto(c);
        ListaDAO dao = new ListaDAO();

        if(dao.doUpdate(l)) {
            return true;
        }
        else {
            l.getContenuti().remove(l.getContenuti().size() - 1);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListaBean getLista(String nome, String emailIscritto){
        ListaDAO dao= new ListaDAO();
        return dao.doRetrieveByKey(nome, emailIscritto);
    }

    private ListaDAO listaDAO;
    Utils utils;
    ListaBean listaBean;

}
