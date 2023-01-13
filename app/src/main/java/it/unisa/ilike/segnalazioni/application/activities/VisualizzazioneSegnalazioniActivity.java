package it.unisa.ilike.segnalazioni.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.segnalazioni.application.SegnalazioneImpl;
import it.unisa.ilike.segnalazioni.application.SegnalazioneService;
import it.unisa.ilike.segnalazioni.application.VisualizzazioneSegnalazioniAdapter;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class VisualizzazioneSegnalazioniActivity extends AppCompatActivity {
    VisualizzazioneSegnalazioniAdapter adapter;


    private class GsonResultSegnalazioni extends AsyncTask<Void, Void, ArrayList<SegnalazioneBean>> {

        @Override
        protected ArrayList<SegnalazioneBean> doInBackground(Void... v) {
            SegnalazioneService s= new SegnalazioneImpl();
            ArrayList<SegnalazioneBean> segnalazioni= (ArrayList<SegnalazioneBean>) s.getSegnalazione();
            return segnalazioni;
        }


        @Override
        protected void onPostExecute(ArrayList<SegnalazioneBean> segnalazioni) {
            Log.d("MyDebug", "onPostExecute visualizzazione segnalazioni act");

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_segnalazioni);

        Intent i = getIntent();
        setReturnIntent();
        account = (Account) getIntent().getExtras().getSerializable("account");

//        TextView numerSegnalazioniTextView = (TextView) findViewById(R.id.numerSegnalazioniGestite);
//        numerSegnalazioniTextView.setText(account.getGestoreBean().getNumSegnalazioniGestite());
        ListView segnalazioniList= findViewById(R.id.segnalazioniList);

        adapter = new VisualizzazioneSegnalazioniAdapter(this,
                R.layout.activity_list_element_visualizzazione_segnalazioni, new ArrayList<SegnalazioneBean>());
        segnalazioniList.setAdapter(adapter);
        GsonResultSegnalazioni g= (GsonResultSegnalazioni) new GsonResultSegnalazioni().execute(new Void[0]);
    }

    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }

    public void onClickInfo(View view){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), GestioneSegnalazioniActivity.class);
        i.putExtra("account", account);
        ImageButton info= (ImageButton) view;
        String idSegnalazione = (String) info.getTag();
        i.putExtra("idSegnalazione", idSegnalazione);
        startActivity(i);
    }


    public void onClickHomepage(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    public void onClickLogout(View view) {
        AccountService accountService = new AccountImpl();
        account = accountService.logout(account.getGestoreBean());
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    private Account account;
}