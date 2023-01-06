package it.unisa.ilike.contenuti.storage;

import it.unisa.ilike.QueryManager;

public class ContenutoDAO {
    /**
     * Aggiorna nel database la valutazione media di un contenuto.
     * @param idContenuto è l'id del contenuto di cui si vuole aggiornare la valutazione media.
     * @param valutazioneMedia è la nuova valutazione media del contenuto.
     * @return true se l'aggiornamento è eseguito con successo, false altrimenti.
     */
    public boolean doUpdateValutazioneMedia(int idContenuto, float valutazioneMedia) {
        QueryManager queryManager = new QueryManager();
        String query = "update Contenuti " +
                "set valutazione_media = " + valutazioneMedia + " " +
                "where id = " + idContenuto;

        return queryManager.update(query);
    }
}
