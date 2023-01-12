package it.unisa.ilike.recensioni.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneDettagliataContenutoActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTipoException;
import it.unisa.ilike.recensioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class AggiuntaSegnalazioneRecensioneActivity extends AppCompatActivity {


    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio aggiungiSegnalazione
     * contenuto in RecensioneService. Questo è necessario in quanto il metodo in questione richiama metodi
     * della classe SegnalazioneDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultValidate extends AsyncTask<String, Void, Boolean> {

        Boolean isValidate = true;
        String messaggio = null;

        /**
         * Consente di utilizzare il metodo aggiungiSegnalazione di RecensioneService e di memorizzarne
         * l'esito nella variabile di istanza isValidate;
         * @param string array di stringhe contenente il tipo di segnalazione e la motivazione della
         *               segnalazione
         * @return true se l'operazione è andata a buon fine, false altrimenti
         */
        @Override
        protected Boolean doInBackground(String... string) {
            RecensioneService recensioneService = new RecensioneImpl();

            int tipo= Integer.parseInt(string[0]);
            try {
                recensioneService.aggiungiSegnalazione(tipo, string[1], segnalazione.getRecensione(), account.getIscrittoBean());
            } catch (InvalidTipoException e) {
                messaggio = "Tipo segnalazione non corretto";
                isValidate = false;
                e.printStackTrace();
            } catch (MotivazioneVuotaException e) {
                messaggio = "Inserire almeno 1 carattere per la motivazione";
                isValidate = false;
                e.printStackTrace();
            } catch (InvalidMotivazioneException e) {
                messaggio = "Puoi inserire al massimo 500 caratteri";
                isValidate = false;
                e.printStackTrace();
            }
            return isValidate;
        }

        protected void onPostExecute(Boolean b) {
            if(this.isValidate){
                Intent i = new Intent();
                i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
                i.putExtra("account", account);
                i.putExtra("contenuto", contenuto);
                startActivity(i);
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), this.messaggio, Toast.LENGTH_LONG);
                toast.show();
            }
        }

        /**
         * Restituisce il valore della variabile di istanza isValidate dopo che il metodo doInBackground
         * ha terminato la sua esecuzione
         * @return il valore della variabile d'istanza isValidate
         */
        public Boolean isValidate(){
            while (this.isValidate==null);
            return this.isValidate;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_segnalazione_recensione);
        TextView tipoSegnalazioneTextView = findViewById(R.id.tipoSegnalazione);
        segnalazione = (SegnalazioneBean) getIntent().getExtras().getSerializable("segnalazione");
        account = (Account) getIntent().getExtras().getSerializable("account");
        contenuto = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");
        String spoiler = "spoiler allert";
        String altre = "altre segnalazioni";
        if(segnalazione.getTipo() == 1)
            tipoSegnalazioneTextView.setText(spoiler);
        else
            tipoSegnalazioneTextView.setText(altre);

    }

    public void onClickInviaSegnalazione(View view) {


        TextView motivazioneSegnalazioneTextView = findViewById(R.id.motivazioneSegnalazione);
        String motivazioneSegnalazione = String.valueOf(motivazioneSegnalazioneTextView.getText());
        String[] s= new String[2];

        s[0]=String.valueOf(segnalazione.getTipo());
        s[1]=motivazioneSegnalazione;

        GsonResultValidate g= (GsonResultValidate) new GsonResultValidate().execute(s);
    }

    private Account account;
    private SegnalazioneBean segnalazione;
    private ContenutoBean contenuto;
}