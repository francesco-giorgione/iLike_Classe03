package it.unisa.ilike.profili.application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.liste.application.activities.CreazioneListaActivity;
import it.unisa.ilike.liste.application.activities.VisualizzazioneContenutiListaPersonaleActivity;
import it.unisa.ilike.recensioni.application.activities.AggiuntaSegnalazioneRecensioneActivity;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class VisualizzazioneProfiloPersonaleActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_profilo_personale);

        Intent i = getIntent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void onClickLogout(View v){
        Intent i = new Intent();
        /*
        AccountImpl account = new AccountImpl();

        Account a = account.logout();

        */
    }

    public void onClickAggiungiLista(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), CreazioneListaActivity.class);
        startActivity(i);
    }

    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        startActivity(i);
    }

    public void onClickInfo(View v){
        Intent i = new Intent();
        TextView lista= (TextView) v;
        i.putExtra("nomeLista", lista.getText());
        i.setClass(getApplicationContext(), VisualizzazioneContenutiListaPersonaleActivity.class);
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
        startActivity(i);
    }
}