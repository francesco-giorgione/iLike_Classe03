package it.unisa.ilike.account.application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.application.VisualizzazioneProfiloPersonaleListeAdapter;
import it.unisa.ilike.account.application.VisualizzazioneProfiloPersonaleRecensioniAdapter;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.liste.application.activities.CreazioneListaActivity;
import it.unisa.ilike.liste.application.activities.VisualizzazioneContenutiListaPersonaleActivity;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.recensioni.application.activities.AggiuntaSegnalazioneRecensioneActivity;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class VisualizzazioneProfiloPersonaleActivity extends Activity {

    List<RecensioneBean> recensioniIscritto;
    List<ListaBean> listeIscritto;

    private class GsonResultListe extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("debugProfilo", "doInBackground");
            listeIscritto= iscritto.getListe();
            return null;
        }


        @Override
        protected void onPostExecute(Void unused) {

            Log.d("debugProfilo", "onPostExecute");
            Log.d("debugProfilo", ""+listeIscritto.size());

            for (ListaBean l:listeIscritto) {
                //Log.d("debugProfilo", l.toString());
                adapterListe.add(l);
            }

            ProgressBar barListe= findViewById(R.id.barListe);
            barListe.setVisibility(View.INVISIBLE);
        }
    }

    private class GsonResultRecensioni extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("debugProfilo", "doInBackground");
            recensioniIscritto= iscritto.getRecensioni();
            return null;
        }


        @Override
        protected void onPostExecute(Void unused) {

            Log.d("debugProfilo", "onPostExecute");
            Log.d("debugProfilo", ""+recensioniIscritto.size());

            for (RecensioneBean r: recensioniIscritto)
                adapterRecensioni.add(r);

            ProgressBar barRecensioni= findViewById(R.id.barRecensioni);
            barRecensioni.setVisibility(View.INVISIBLE);
        }
    }


    private IscrittoBean iscritto;
    private Account account;
    private VisualizzazioneProfiloPersonaleRecensioniAdapter adapterRecensioni;
    private VisualizzazioneProfiloPersonaleListeAdapter adapterListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_profilo_personale);
        ListView listViewListe= findViewById(R.id.elencoListeIscritto);
        ListView listViewRecensioni= findViewById(R.id.recensioniList);

        Intent i = getIntent();

        account = (Account) i.getExtras().getSerializable("account");
        iscritto = account.getIscrittoBean();

        TextView nicknameTextView= findViewById(R.id.nicknameTextView);
        TextView infoTextView = findViewById(R.id.infoTextView);
        nicknameTextView.setText(iscritto.getNickname());
        infoTextView.setText(iscritto.getBio());


        adapterListe= new VisualizzazioneProfiloPersonaleListeAdapter(
                this, R.layout.activity_list_element_visualizzazione_profilo_personale_liste,
                new ArrayList<ListaBean>());
        listViewListe.setAdapter(adapterListe);


        adapterRecensioni= new VisualizzazioneProfiloPersonaleRecensioniAdapter (
                this, R.layout.activity_list_element_visualizzazione_profilo_personale_recensioni,
                new ArrayList<RecensioneBean>());
        listViewRecensioni.setAdapter(adapterRecensioni);

        GsonResultListe g= (GsonResultListe) new GsonResultListe().execute(new Void[0]);
        GsonResultRecensioni g1= (GsonResultRecensioni) new GsonResultRecensioni().execute(new Void[0]);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void onClickLogout(View v){
        Intent i = new Intent();
        AccountService accountService = new AccountImpl();
        account = accountService.logout(account.getIscrittoBean());
        i.setClass(VisualizzazioneProfiloPersonaleActivity.this, VisualizzazioneHomepageActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    public void onClickAggiungiLista(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), CreazioneListaActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(VisualizzazioneProfiloPersonaleActivity.this, VisualizzazioneHomepageActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    public void onClickInfo(View v){
        Intent i = new Intent();
        TextView lista= (TextView) v;
        String nomeLista = String.valueOf(lista.getText());
        i.putExtra("lista", nomeLista);
        i.setClass(VisualizzazioneProfiloPersonaleActivity.this, VisualizzazioneContenutiListaPersonaleActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    public void onClickAggiungiSegnalazione(View v){

        Button spoilerAlert = (Button) v.findViewById(R.id.spoilerAlert);
        SegnalazioneBean s = new SegnalazioneBean();

        if(spoilerAlert.isSelected())
            s.setTipo(0);
        else
            s.setTipo(1);

        Intent i = new Intent();
        i.setClass(getApplicationContext(), AggiuntaSegnalazioneRecensioneActivity.class);
        i.putExtra("segnalazione", (Serializable) s);
        i.putExtra("account", account);
        startActivity(i);
    }
}