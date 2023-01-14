package it.unisa.ilike.segnalazioni.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.segnalazioni.application.SegnalazioneImpl;
import it.unisa.ilike.segnalazioni.application.SegnalazioneService;
import it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneDAO;


/**
 * Questa classe gestisce il flusso di interazioni tra il gestore e il sistema. Essa permette di effettuare tutte
 * le operazioni relative alla visualizzazione dettagliata delle segnalazioni e alla cancellazione delle
 * recensioni da parte del gestore di iLike.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class GestioneSegnalazioniActivity extends AppCompatActivity {

    private class GsonResultCancellaRecensione extends AsyncTask<String, Void, Boolean> {

        Boolean isValidate = true;
        String messaggio = null;

        @Override
        protected Boolean doInBackground(String...  strings) {
            SegnalazioneService segnalazioneService = new SegnalazioneImpl();

            try {
                isValidate = segnalazioneService.cancellaRecensione(segnalazione, strings[0], account.getGestoreBean());

                if(!isValidate) {
                    this.messaggio = "Si è verificato un errore";
                }
                else {
                    this.messaggio = "La recensione è stata cancellata correttamente";
                }

            } catch (MotivazioneVuotaException e) {
                messaggio = "Inserire una motivazione";
                isValidate=false;
            } catch (InvalidMotivazioneException e) {
                messaggio = "Non puoi scrivere più di 300 caretteri";
                isValidate=false;
            }

            return isValidate;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (isValidate) {
                Toast.makeText(GestioneSegnalazioniActivity.this, messaggio, Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setClass(getApplicationContext(), VisualizzazioneSegnalazioniActivity.class);
                i.putExtra("account", (Serializable) account);
                startActivity(i);
            }else {
                Toast.makeText(GestioneSegnalazioniActivity.this, messaggio, Toast.LENGTH_LONG).show();
            }
        }


        public Boolean isValidate(){
            while (this.isValidate==null);
            return this.isValidate;
        }

    }

    private class GsonResultLogout extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AccountService accountService = new AccountImpl();
            account = accountService.logout(account.getGestoreBean());

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Log.d("MyDebug", "sono in onPostExecute");

            Intent i = new Intent();
            i.setClass(GestioneSegnalazioniActivity.this, VisualizzazioneHomepageActivity.class);
            startActivity(i);
        }
    }


    private class GsonResultRifiutaSegnalazione extends AsyncTask<Void, Void, Boolean> {

        Boolean isValidate = true;

        @Override
        protected Boolean doInBackground(Void...  v) {

            SegnalazioneService segnalazioneService = new SegnalazioneImpl();
            isValidate = segnalazioneService.rifiutaSegnalazione(segnalazione, account.getGestoreBean());

            return isValidate;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (isValidate){
                Intent i = new Intent();
                i.setClass(getApplicationContext(), VisualizzazioneSegnalazioniActivity.class);
                i.putExtra("account", (Serializable) account);
                startActivity(i);
            }else{
                Toast.makeText(GestioneSegnalazioniActivity.this, "Rifiuto segnalazione non effettuato", Toast.LENGTH_LONG).show();
            }
        }

        public Boolean isValidate(){
            while (this.isValidate==null);
            return this.isValidate;
        }

    }

    private class GsonResultGetSegnalazione extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void...  v) {

            SegnalazioneService segnalazioneService = new SegnalazioneImpl();
            segnalazione = segnalazioneService.getSegnalazione(idSegnalazione);
            recensione = segnalazione.getRecensione();

            return null;
        }

        protected void onPostExecute(Boolean a) {
            super.onPostExecute(a);
            TextView recensioneText = findViewById(R.id.textViewTestoRecensione);
            recensioneText.setText(recensione.getTesto());

            TextView segnalazioneText = findViewById(R.id.textViewSegnalazione);
            segnalazioneText.setText(segnalazione.getMotivazione());
        }
    }


    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_segnalazione);

        motivazioneCancellazione= findViewById(R.id.motivazioneCancellazione);
        cancellaRecensioneButton= findViewById(R.id.cancellaRecensioneButton);

        Intent i = getIntent();
        setReturnIntent();

        account = (Account) getIntent().getExtras().getSerializable("account");
        idSegnalazione = Integer.parseInt(getIntent().getExtras().getString("idSegnalazione"));

        GsonResultGetSegnalazione g= (GsonResultGetSegnalazione) new GsonResultGetSegnalazione().execute(new Void[0]);
    }


    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }

    /**
     * Questo metodo permette al gestore di rifiutare la segnalazione, considerandola gestita.
     * @param view
     */
    public void onClickRifiutaSegnalazione(View view) {
        GsonResultRifiutaSegnalazione g= (GsonResultRifiutaSegnalazione) new GsonResultRifiutaSegnalazione().execute(new Void[0]);
    }

    /**
     * Questo metodo permette al gestore di accettare la segnalazione, abilitando poi alla successiva
     * cancellazione della recensione segnalata.
     * @param view
     */
    public void onClickAccettaSegnalazione(View view) {
        motivazioneCancellazione.setVisibility(View.VISIBLE);
        cancellaRecensioneButton.setVisibility(View.VISIBLE);
    }

    /**
     * Questo metodo permette al gestore di cancellare la recensione a cui la segnalazione esaminata era
     * riferita.
     * @param view
     */
    public void onClickCancellaRecensione(View view){
        String motivazione = motivazioneCancellazione.toString();
        String[] s= {motivazione};
        GsonResultCancellaRecensione g= (GsonResultCancellaRecensione) new GsonResultCancellaRecensione().execute(s);
    }

    /**
     * Questo metodo permette al gestore di visualizzare tutte le segnalazioni ricevute non ancora gestite.
     * @param view
     */
    public void onClickVisualizzaSegnalazioni(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneSegnalazioniActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    /**
     * Questo metodo permette di passare alla homepage di iLike.
     * @param view
     */
    public void onClickHomepage(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    /**
     * Questo metodo permette al gestore di iLike di effettuare il logout.
     * @param view
     */
    public void onClickLogout(View view) {
        GsonResultLogout g= (GsonResultLogout) new GsonResultLogout().execute(new Void[0]);
    }

    private RecensioneBean recensione;
    private SegnalazioneBean segnalazione;
    private Account account;
    private EditText motivazioneCancellazione;
    private Button cancellaRecensioneButton;
    private int idSegnalazione;
}
