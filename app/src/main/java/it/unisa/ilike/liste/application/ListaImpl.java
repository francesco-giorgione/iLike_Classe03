package it.unisa.ilike.liste.application;

import java.util.List;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.application.exceptions.ContenutoGiaPresenteException;
import it.unisa.ilike.liste.application.exceptions.InvalidNomeException;
import it.unisa.ilike.liste.application.exceptions.ListaGiaEsistenteException;
import it.unisa.ilike.liste.application.exceptions.NomeVuotoException;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.utils.exceptions.NotIscrittoException;

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
            NotIscrittoException, NomeVuotoException, InvalidNomeException, ListaGiaEsistenteException {

        return false;
    }

    /** @inheritDoc */
    @Override
    public boolean aggiungiContenuto(ListaBean l, ContenutoBean c) throws ContenutoGiaPresenteException {
        return false;
    }

    /** @inheritDoc */
    @Override
    public List<ContenutoBean> getContenutiLista(ListaBean l) {
        return null;
    }
}
