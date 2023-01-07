package it.unisa.ilike.segnalazioni.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;

public class VisualizzazioneSegnalazioniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_segnalazioni);

        //inizio da login
        Intent i = getIntent();
        setReturnIntent();
        Account account = (Account) getIntent().getExtras().getSerializable("account");
        //fine da login
    }

    //inizio da login
    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }
    //fine da login
}