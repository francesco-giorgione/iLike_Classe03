package it.unisa.ilike.account.application.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.liste.application.activities.CreazioneListaActivity;
import it.unisa.ilike.liste.application.activities.VisualizzazioneContenutiListaPersonaleActivity;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.account.application.VisualizzazioneProfiloPersonaleListeAdapter;
import it.unisa.ilike.account.application.VisualizzazioneProfiloPersonaleRecensioniAdapter;
import it.unisa.ilike.recensioni.application.activities.AggiuntaSegnalazioneRecensioneActivity;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class VisualizzazioneProfiloPersonaleActivity extends Activity {

    private IscrittoBean iscritto;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_profilo_personale);
        ListView listViewListe= findViewById(R.id.elencoListeIscritto);
        ListView listViewRecensioni= findViewById(R.id.recensioniList);

        Intent i = getIntent();

        account = (Account) i.getExtras().getSerializable("account");
        iscritto = account.getIscrittoBean();
        List<ListaBean> listeIscritto= iscritto.getListe();
        List<RecensioneBean> recensioniIscritto= iscritto.getRecensioni();

        TextView nicknameTextView= findViewById(R.id.nicknameTextView);
        TextView infoTextView = findViewById(R.id.infoTextView);
        nicknameTextView.setText(iscritto.getNickname());
        infoTextView.setText(iscritto.getBio());



        //inizializzazione adapter liste profilo iscritto
        VisualizzazioneProfiloPersonaleListeAdapter adapterListe= new VisualizzazioneProfiloPersonaleListeAdapter(
                this, R.layout.activity_list_element_visualizzazione_profilo_personale_liste,
                new ArrayList<ListaBean>());
        listViewListe.setAdapter(adapterListe);
        for (ListaBean l:listeIscritto)
            adapterListe.add(l);

        //inizializzazione adapter recensioni profilo iscritto
        VisualizzazioneProfiloPersonaleRecensioniAdapter adapterRecensioni= new VisualizzazioneProfiloPersonaleRecensioniAdapter (
                this, R.layout.activity_list_element_visualizzazione_profilo_personale_recensioni,
                new ArrayList<RecensioneBean>());
        listViewRecensioni.setAdapter(adapterRecensioni);
        for (RecensioneBean r: recensioniIscritto)
            adapterRecensioni.add(r);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void onClickLogout(View v){
        Intent i = new Intent();
        AccountService accountService = new AccountImpl();
        account = accountService.logout(account.getIscrittoBean());
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
        i.putExtra("nomeLista", lista.getText());
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