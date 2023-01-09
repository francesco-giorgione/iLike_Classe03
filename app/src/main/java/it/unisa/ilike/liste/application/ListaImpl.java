package it.unisa.ilike.liste.application;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
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
 * @version 0.1
 * @author FrancescoGiorgione
 * @see ListaService
 */
public class ListaImpl implements ListaService {
    /** @inheritDoc */
    @Override
    public boolean creaLista(IscrittoBean i, String nome, boolean pubblica) throws
            NomeVuotoException, InvalidNomeException, ListaGiaEsistenteException {

        if(i == null) {
            return false;
        }

        if(nome.length() == 0)          { throw new NomeVuotoException(); }
        if(nome.length() > 50)          { throw new InvalidNomeException(); }
        if(Utils.hasLista(i, nome))     { throw new ListaGiaEsistenteException(); }

        ListaBean lista = new ListaBean(nome, i, pubblica);
        lista.setContenuti(new ArrayList<>());
        ListaDAO dao = new ListaDAO();

        return dao.doSave(lista);
    }

    /** @inheritDoc */
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

    /** @inheritDoc */
    @Override
    public List<ContenutoBean> getContenutiLista(ListaBean l) {
        return l.getContenuti();
    }
}
