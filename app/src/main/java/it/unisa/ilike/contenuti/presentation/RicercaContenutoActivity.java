package it.unisa.ilike.contenuti.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import it.unisa.ilike.R;
import it.unisa.ilike.profili.presentation.VisualizzazioneProfiloPersonaleActivity;

public class RicercaContenutoActivity extends AppCompatActivity {

    ImageButton profiloButton;
    ImageButton homepageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_contenuto);
        profiloButton= findViewById(R.id.profiloButton);
        homepageButton= findViewById(R.id.homepageButton);

        Intent i = getIntent();
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
}