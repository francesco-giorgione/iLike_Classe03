package it.unisa.ilike.profili.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import it.unisa.ilike.R;

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
}