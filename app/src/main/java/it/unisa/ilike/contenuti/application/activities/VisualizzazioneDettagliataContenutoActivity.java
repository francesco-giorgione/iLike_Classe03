package it.unisa.ilike.contenuti.application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.application.activities.AggiuntaContenutoListaActivity;
import it.unisa.ilike.profili.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.recensioni.application.activities.PubblicazioneRecensioneActivity;

public class VisualizzazioneDettagliataContenutoActivity extends AppCompatActivity {

    ImageButton profiloButton;
    ImageButton homepageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_dettagliata_contenuto);

        profiloButton= findViewById(R.id.profiloButton);
        homepageButton= findViewById(R.id.homepageButton);

        account = (Account) getIntent().getExtras().getSerializable("account");
        contenuto = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");
        // aggiungere settaggio dei parametri per visualizzare le informazioni dei contenuti
        Intent i = getIntent();
        int idContenuto= i.getIntExtra("idContenuto", -1);
        Log.d("MyDebug", "idContenutoCliccato -->"+idContenuto);
        setReturnIntent();
    }

    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClickProfilo(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        startActivity(i);
    }

    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        startActivity(i);
    }

    public void onClickAggiungiRecensione(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), PubblicazioneRecensioneActivity.class);
        i.putExtra("account", (Serializable) account);
        i.putExtra("contenuto", (Serializable) contenuto);
        startActivity(i);
    }

    public void onClickAggiungiContenutoAllaLista (View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AggiuntaContenutoListaActivity.class);
        startActivity(i);
    }
    private Account account;
    private ContenutoBean contenuto;
}