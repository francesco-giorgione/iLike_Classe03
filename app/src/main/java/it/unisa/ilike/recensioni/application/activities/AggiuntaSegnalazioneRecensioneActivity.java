package it.unisa.ilike.recensioni.application.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTipoException;
import it.unisa.ilike.recensioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare
 * l'aggiunta di una segnalazione ad una recensione.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class AggiuntaSegnalazioneRecensioneActivity extends AppCompatActivity {


    private class GsonResultValidate extends AsyncTask<String, Void, Boolean> {

        Boolean isValidate = true;
        String messaggio = null;


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
                onBackPressed();
                /*Intent i = new Intent();
                i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
                i.putExtra("account", account);
                i.putExtra("idContenuto", contenuto.getId());
                startActivity(i);*/
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), this.messaggio, Toast.LENGTH_LONG);
                toast.show();
            }
        }

        public Boolean isValidate(){
            while (this.isValidate==null);
            return this.isValidate;
        }
    }


    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
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

    /**
     * Questo metodo permette di aggiungere una segnalazione alla recensione.
     * @param view
     */
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