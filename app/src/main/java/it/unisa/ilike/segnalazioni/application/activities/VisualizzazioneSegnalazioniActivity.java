package it.unisa.ilike.segnalazioni.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.segnalazioni.application.SegnalazioneImpl;
import it.unisa.ilike.segnalazioni.application.SegnalazioneService;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

/**
 * Questa classe gestisce il flusso di interazioni tra il gestore e il sistema. Essa permette di effettuare tutte
 * le operazioni relative alla visualizzazione delle segnalazioni da parte del gestore di iLike.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class VisualizzazioneSegnalazioniActivity extends AppCompatActivity {
    VisualizzazioneSegnalazioniAdapter adapter;


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
            i.setClass(VisualizzazioneSegnalazioniActivity.this, VisualizzazioneHomepageActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    private class GsonResultSegnalazioni extends AsyncTask<Void, Void, ArrayList<SegnalazioneBean>> {

        @Override
        protected ArrayList<SegnalazioneBean> doInBackground(Void... v) {

            Log.d("MyDebug", "doInBackground visualizzazione segnalazioni act");

            SegnalazioneService service= new SegnalazioneImpl();
            ArrayList<SegnalazioneBean> segnalazioni= (ArrayList<SegnalazioneBean>) service.getSegnalazione();
            return segnalazioni;
        }


        @Override
        protected void onPostExecute(ArrayList<SegnalazioneBean> segnalazioni) {

            ProgressBar bar= findViewById(R.id.progress_circular);
            bar.setVisibility(View.INVISIBLE);

            Log.d("MyDebug", "onPostExecute visualizzazione segnalazioni act");

            for (SegnalazioneBean s: segnalazioni)
                Log.d("MyDebug", "Segnalazioni -->"+s.toString());

            if (segnalazioni.size()>0) {
                for (SegnalazioneBean s : segnalazioni)
                    adapter.add(s);
            }
            else {
                Log.d("MyDebug", "nessuna segnalazione trovata");
                Toast.makeText(getApplicationContext(), "Nessuna segnalazione trovata", Toast.LENGTH_LONG).show();
            }

        }
    }


    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_segnalazioni);

        Intent i = getIntent();

        account = (Account) getIntent().getExtras().getSerializable("account");
        GestoreBean gestoreBean = account.getGestoreBean();
        TextView numeroSegnalazioni = findViewById(R.id.numerSegnalazioniGestite);
        numeroSegnalazioni.setText(String.valueOf(gestoreBean.getNumSegnalazioniGestite()));

        ListView segnalazioniList= findViewById(R.id.segnalazioniList);

        adapter= new VisualizzazioneSegnalazioniAdapter(this,
                R.layout.activity_list_element_visualizzazione_segnalazioni,
                new ArrayList<SegnalazioneBean>());
        segnalazioniList.setAdapter(adapter);

        GsonResultSegnalazioni g= (GsonResultSegnalazioni) new GsonResultSegnalazioni().execute(new Void[0]);

        setReturnIntent();
    }

    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }

    /**
     * Questo metodo permette di visualizzare la pagina contenente la segnalazione e i relativi dettagli.
     * @param view oggetto View usato per ottenere il riferimento alla segnalazione selezionata
     */
    public void onClickInfo(View view){
        Intent i = new Intent();
        i.setClass(VisualizzazioneSegnalazioniActivity.this, GestioneSegnalazioniActivity.class);
        i.putExtra("account", account);
        Button info= (Button) view;
        String idSegnalazione = (String) info.getTag();
        i.putExtra("idSegnalazione", idSegnalazione);
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

    private Account account;
}