package it.unisa.ilike.account.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import it.unisa.ilike.R;

public class RegistrazioneIscrittoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_iscritto);

        //inizio da login
        Intent i = getIntent();
        setReturnIntent();
        //fine da login
    }

    //inizio da login
    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }
    //fine da login
}