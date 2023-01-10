package it.unisa.ilike.profili.application;

import it.unisa.ilike.account.storage.IscrittoBean;

/**
 * Questa interfaccia consente di gestire le informazioni relative al profilo di un iscritto.
 * @author Marta
 * @version 0.1
 */
public interface ProfiloService {
    /**
     * Questo metodo consente di restituire un oggetto Iscritto della tipologia IscrittoReal,
     * cioè l'oggetto contenente anche le recensioni e la fato profilo dell'iscritto.
     * @param i rappresenta l'oggetto IscrittoBean da cui recuperare tutte le sue informazioni
     * @return l'oggetto IscrittoReal
     */
    public IscrittoBean getIscrittoReal(IscrittoBean i);
}
