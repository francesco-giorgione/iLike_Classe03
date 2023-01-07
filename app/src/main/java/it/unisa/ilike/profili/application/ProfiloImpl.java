package it.unisa.ilike.profili.application;

import static it.unisa.ilike.utils.Utils.isIscritto;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.profili.storage.IscrittoProxyBean;
import it.unisa.ilike.profili.storage.IscrittoRealBean;
import it.unisa.ilike.utils.exceptions.NotIscrittoException;

/**
 * Questa classe rappresenta l'implentazione che gestisce le informazioni relative al profilo di un iscritto.
 * @author Marta
 * @version 0.2
 */
public class ProfiloImpl implements ProfiloService{

    /**
     * Questo metodo consente di restituire un oggetto Iscritto della tipologia IscrittoReal,
     * cioè l'oggetto contenente anche le recensioni e la fato profilo dell'iscritto.
     * @param i rappresenta l'oggetto IscrittoBean da cui recuperare tutte le sue informazioni
     * @return l'oggetto IscrittoReal
     */
    public IscrittoBean getIscrittoReal(IscrittoBean i) throws NotIscrittoException {
        if (isIscritto(i)){
            if (i instanceof IscrittoProxyBean){
                IscrittoRealBean iscrittoReal =  new IscrittoRealBean(i.getEmail(), i.getPassword(), i.getNickname(), i.getNome(), i.getCognome(), i.getBio());
                iscrittoReal.setRecensioni(i.getRecensioni());
                iscrittoReal.setListe(i.getListe());
                iscrittoReal.setFoto(i.getFoto());
                return iscrittoReal;
            }
            else return i;
        }else throw new NotIscrittoException();
    }
}
