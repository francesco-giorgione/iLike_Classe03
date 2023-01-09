package it.unisa.ilike.segnalazioni.application;

import java.util.List;

import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

/**
 * Interfaccia che contiene i metodi relativi alle segnalazioni
 * @version 0.1
 * @author Simona Lo Conte
 */

public interface SegnalazioneService {

    public List<SegnalazioneBean> getSegnalazione();
    public Boolean cancellaRecensione(SegnalazioneBean s, String motivazione, GestoreBean g)
            throws it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException, it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException;
    public Boolean rifiutaSegnalazione(SegnalazioneBean s, GestoreBean g);

}
