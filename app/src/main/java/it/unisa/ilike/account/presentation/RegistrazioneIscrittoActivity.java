package it.unisa.ilike.account.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.exceptions.DatiIscrittoVuotiException;
import it.unisa.ilike.account.application.exceptions.EmailVuotaException;
import it.unisa.ilike.account.application.exceptions.PasswordVuotaException;
import it.unisa.ilike.contenuti.presentation.VisualizzazioneHomepageActivity;
import it.unisa.ilike.profili.presentation.VisualizzazioneProfiloPersonaleActivity;

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

    public void onClickRegistrazioneIscritto(View v) throws EmailVuotaException, PasswordVuotaException, DatiIscrittoVuotiException {

        EditText e = findViewById(R.id.email);
        String email = e.toString();
        EditText n = findViewById(R.id.nickname);
        String nickname = n.toString();
        EditText p = findViewById(R.id.password);
        String password = p.toString();
        EditText rp = findViewById(R.id.repeatPassword);
        String repeatPassword = p.toString();
        EditText no = findViewById(R.id.nome);
        String nome = no.toString();
        EditText c = findViewById(R.id.cognome);
        String cognome = c.toString();
        EditText b = findViewById(R.id.bio);
        String bio = b.toString();

        //aggiungere foto

        //controlli eccezioni

        /*
        AccountImpl accountImpl = new AccountImpl();

        Account account = accountImpl.registrazioneIscritto(email, password, nome, cognome, nickname, bio, //foto);
        */


        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        startActivity(i);
        //se la registrazione va a buon fine
        Toast.makeText(this, "Registrazione effettuata", Toast.LENGTH_SHORT).show();
    }

}