package it.unisa.ilike.recensioni.application;

import java.util.ArrayList;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

/**
 * Interfaccia che esplicita i metodi di servizio relativi alle recensioni
 * @version 0.1
 * @author LuiginaCostante
 */

public interface RecensioneService {
    public boolean creaRecensione(String testo, int valutazione, IscrittoBean i, ContenutoBean c);
    public boolean aggiungiSegnalazione (int tipo, String motivazione, RecensioneBean r, IscrittoBean i);
    public ArrayList<RecensioneBean> getRecensioniContenuto (ContenutoBean c);

}
