package it.unisa.ilike.recensioni.storage;

import static it.unisa.ilike.utils.Utils.addEscape;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unisa.ilike.QueryManager;
import it.unisa.ilike.account.storage.IscrittoDAO;
import it.unisa.ilike.contenuti.storage.ContenutoDAO;
import it.unisa.ilike.utils.Utils;

/**
 * Un oggetto <code>RecensioneDAO</code> serve per interagire con la tabella Recensioni presente nel database
 * @version 0.5
 * @author LuiginaCostante
 */

public class RecensioneDAO {
    /**
     * Modella il risultato della query eseguita in doRetrieveByIdRecensione. Consente cioè
     * di convertire il risultato dal formato json in un oggetto Java.
     */
    private class RisultatoQuery1 {
        public RisultatoQuery1(int id, int valutazione, int cancellata, int idContenuto, String testo, Object data, String motivazioneCancellazione, String emailIscritto) {
            this.id = id;
            this.valutazione = valutazione;
            this.cancellata = cancellata;
            this.idContenuto = idContenuto;
            this.testo = testo;
            this.data = data;
            this.motivazioneCancellazione = motivazioneCancellazione;
            this.emailIscritto = emailIscritto;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getValutazione() {
            return valutazione;
        }

        public void setValutazione(int valutazione) {
            this.valutazione = valutazione;
        }

        public int getCancellata() {
            return cancellata;
        }

        public void setCancellata(int cancellata) {
            this.cancellata = cancellata;
        }

        public int getIdContenuto() {
            return idContenuto;
        }

        public void setIdContenuto(int idContenuto) {
            this.idContenuto = idContenuto;
        }

        public String getTesto() {
            return testo;
        }

        public void setTesto(String testo) {
            this.testo = testo;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getMotivazioneCancellazione() {
            return motivazioneCancellazione;
        }

        public void setMotivazioneCancellazione(String motivazioneCancellazione) {
            this.motivazioneCancellazione = motivazioneCancellazione;
        }

        public String getEmailIscritto() {
            return emailIscritto;
        }

        public void setEmailIscritto(String emailIscritto) {
            this.emailIscritto = emailIscritto;
        }

        Integer id, valutazione, cancellata, idContenuto;
        String testo, motivazioneCancellazione, emailIscritto;
        Object data;
    }

    /**
     * Modella il risultato della query eseguita in doRetrieveAllRecensioniNonCancellate. Consente cioè
     * di convertire il risultato dal formato json in un oggetto Java.
     */
    private class RisultatoQuery2 {
        int id;
    }

    /**
     * Questo metodo consente di salvare nella tabella Recensioni del database un nuovo oggetto della classe
     * <code>RecensioneBean</code> passato come argomento
     * @param recensione oggetto della classe <code>RecensioneBean</code> da salvare nel database
     * @return false se la recensione passata come argomento è null o se l'operazione NON è andata a buon fine,
     * true altrimenti
     */
    public boolean doSaveRecensione(RecensioneBean recensione){
        if (recensione== null){
            return false;
        }

        String testo = Utils.addEscape(recensione.getTesto());
        int valutazione = recensione.getValutazione();
        String data = Utils.getStringaDataForSql(recensione.getData());
        String emailIscritto = Utils.addEscape(recensione.getIscritto().getEmail());
        int idContenuto = recensione.getContenuto().getId();
        int cancellata = recensione.isCancellata() ? 1 : 0;
        String motivazione_cancellazione = Utils.addEscape(recensione.getMotivazioneCancellazione());

        String query = "insert into Recensioni (testo, valutazione, data, cancellata, motivazione_cancellazione, email_iscritto, id_contenuto) " +
                "values ('" + testo + "', '" + valutazione + "', '" + data + "', " + cancellata + ", '" + motivazione_cancellazione+ "', '"
                + emailIscritto + "', " + idContenuto + ")";

        QueryManager queryManager = new QueryManager();
        ContenutoDAO contenutoDAO = new ContenutoDAO();

        if(queryManager.update(query)) {
            return contenutoDAO.doUpdateValutazioneMedia(recensione.getContenuto().getId(), contenutoDAO.
                    calcolaValutazioneMediaAggiornata(recensione.getContenuto().getId(), recensione.getValutazione()));
        }
        else return false;
    }


    /**
     * Questo metodo permette di cercare e successivamente restituire un oggetto della classe <code>RecensioneBean</code>
     * presente nella tabella Recensioni del database, dopo averlo individuato tramite l'id passato come argomento
     * @param id id della recensione da cercare nel database
     * @return null se il parametro id non è valido, l'oggetto recensione con chiave primaria uguale ad id
     * se l'operazione è andata a buon fine
     */
    public RecensioneBean doRetrieveByIdRecensione(int id){
        if (id < 1){
            return null;
        }

        String query = "select id, testo, valutazione, data, cancellata, motivazione_cancellazione as motivazioneCancellazione, email_iscritto as emailIscritto, id_contenuto as idContenuto " +
                "from Recensioni " +
                "where id = " + id;

        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        String jsonRes = queryManager.select(query);

        RisultatoQuery1[] res = gson.fromJson(jsonRes, RisultatoQuery1[].class);

        if(res.length == 0) {
            return null;
        }

        RisultatoQuery1 tmpRes = res[0];
        RecensioneBean recensione = new RecensioneBean(tmpRes.id, tmpRes.testo, tmpRes.valutazione,
                Utils.getBeanData(tmpRes.data.toString().substring(6, 16)), tmpRes.cancellata == 1,
                tmpRes.motivazioneCancellazione, null, null);

        recensione.setIscritto(new IscrittoDAO().doRetrieveByEmail(tmpRes.emailIscritto));
        recensione.setContenuto(new ContenutoDAO().doRetrieveById(tmpRes.idContenuto));

        return recensione;
    }

    /**
     * Questo metodo restituisce tutti gli oggetti della classe <code>RecensioneBean</code>
     * memorizzati nel database che non risultano marcati come "cancellati"
     * @return lista di oggetti della classe <code>RecensioneBean</code> memorizzata nel database
     */
    public List<RecensioneBean> doRetrieveAllRecensioniNonCancellate(){
        String query = "select id from Recensioni where " +
                "cancellata = " + 0;

        List<RecensioneBean> recensioni = new ArrayList<>();
        Gson gson = new Gson();
        QueryManager queryManager = new QueryManager();
        String jsonRes = queryManager.select(query);

        RisultatoQuery2[] res = gson.fromJson(jsonRes, RisultatoQuery2[].class);

        for(RisultatoQuery2 curr: res) {
            recensioni.add(this.doRetrieveByIdRecensione(curr.id));
        }

        return recensioni;
    }


    /**
     * Questo metodo consente di marcare come "cancellata" una recensione della tabella Recensioni del database, a seguito
     * di una decisione presa dal gestore. Permette inoltre di inserire la motivazione della cancellazione relativa alla
     * recensione passata come argomento al metodo.
     * @param recensione recensione da marcare come "cancellata"
     * @return false se la recensione o la motivazione della cancellazione sono nulle, se risulta essere già stata
     * marcata come "cancellata" o se l'operazione NON è andata a buon fine. True altrimenti
     */
    public boolean cancellaRecensione(RecensioneBean recensione){

        if (recensione==null)
            return false;
        if (recensione.isCancellata()==false || recensione.getMotivazioneCancellazione()==null)
            return false;

        int id= recensione.getId();
        String motivazioneCancellazione= addEscape(recensione.getMotivazioneCancellazione());
        String query= "update Recensioni set cancellata= true, motivazione_cancellazione= '"+
                motivazioneCancellazione+"' where id= "+ id;
        QueryManager queryManager= new QueryManager();
        queryManager.update(query);

        return true;
    }

    /**
     * Questo metodo consente di receperare dal DataBase tutte le recensioni di cui l'iscritto ne è l'autore
     * @param email rappresenta l'email dell'iscritto autore delle recensioni
     * @return la lista di recensioni scritte dall'iscritto
     */
    public List<RecensioneBean> doRetriveRecensioniByEmailIscritto(String email){

        email = addEscape(email);
        String query="select * from Recensioni where email_iscritto = " + email + "'";
        QueryManager queryManager= new QueryManager();
        String res= queryManager.select(query);
        Gson gson= new Gson();
        List<RecensioneBean> listToReturn = (List<RecensioneBean>) gson.fromJson(res, RecensioneBean.class);

        return listToReturn;
    }
}
