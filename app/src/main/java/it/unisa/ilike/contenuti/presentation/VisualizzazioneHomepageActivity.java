package it.unisa.ilike.contenuti.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import it.unisa.ilike.R;
import it.unisa.ilike.profili.presentation.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.segnalazioni.presentation.VisualizzazioneSegnalazioniActivity;

public class VisualizzazioneHomepageActivity extends Activity {

    ImageButton profiloButton;
    ImageButton barraDiRicerca;
    ImageButton chatBotButton;
    ImageButton visualizzaSegnalazioniButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_homepage);

        profiloButton= findViewById(R.id.profiloButton);
        barraDiRicerca= findViewById(R.id.BarraDiRicercaContenutiHomePage);
        visualizzaSegnalazioniButton= findViewById(R.id.VisualizzaSegnalazioniButton);
        chatBotButton=findViewById(R.id.chatBotButton);

        // se l'utente loggato è un gestore
        //visualizzaSegnalazioniButton.setVisibility(View.VISIBLE);
        //chatBotButton.setVisibility(View.INVISIBLE);
        //altrimenti
        //visualizzaSegnalazioniButton.setVisibility(View.INVISIBLE);
        //chatBotButton.setVisibility(View.VISIBLE);



        //inizio da login
        Intent i = getIntent();
        setReturnIntent();
        //Account account = (Account) getIntent().getExtras().getSerializable("account");
        //fine da login
    }

    //inizio da login
    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }
    //fine da login

    public void onClickProfilo(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        startActivity(i);
    }

    public void onClickSearchBar(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RicercaContenutoActivity.class);
        startActivity(i);
    }

    public void onClickVisualizzaSegnalazioni (View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneSegnalazioniActivity.class);
        startActivity(i);
    }
}