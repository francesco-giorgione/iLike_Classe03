package it.unisa.ilike.contenuti.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import it.unisa.ilike.R;

public class RicercaContenutoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_contenuto);
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
}