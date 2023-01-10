package it.unisa.ilike.profili.application;

import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.profili.storage.IscrittoProxyBean;
import it.unisa.ilike.profili.storage.IscrittoRealBean;

/**
 * Questa classe rappresenta l'implentazione che gestisce le informazioni relative al profilo di un iscritto.
 * @author Marta
 * @version 0.2
 */
public class ProfiloImpl implements ProfiloService{

    /** @inheritDoc */
    public IscrittoBean getIscrittoReal(IscrittoBean i) {
        if(i == null) {
            return null;
        }

        if (i instanceof IscrittoProxyBean){
            IscrittoRealBean iscrittoReal =  new IscrittoRealBean(i.getEmail(), i.getPassword(), i.getNickname(), i.getNome(), i.getCognome(), i.getBio());
            iscrittoReal.setRecensioni(i.getRecensioni());
            iscrittoReal.setListe(i.getListe());
            iscrittoReal.setFoto(i.getFoto());
            return iscrittoReal;
        }
        else return i;
    }
}
