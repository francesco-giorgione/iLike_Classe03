package it.unisa.ilike.contenuti.application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import it.unisa.ilike.R;
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

        Intent i = getIntent();
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
        startActivity(i);
    }

    public void onClickAggiungiContenutoAllaLista (View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AggiuntaContenutoListaActivity.class);
        startActivity(i);
    }

}