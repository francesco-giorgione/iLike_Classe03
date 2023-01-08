package it.unisa.ilike.account.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.application.exceptions.CredenzialiErrateException;
import it.unisa.ilike.account.application.exceptions.CredenzialiVuoteException;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.presentation.VisualizzazioneHomepageActivity;
import it.unisa.ilike.profili.presentation.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.segnalazioni.presentation.VisualizzazioneSegnalazioniActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View view) {
        // prendi i campi email/nickname e password
        TextView username = findViewById(R.id.username);
        String email = (String) username.getText();
        TextView passwordText = findViewById(R.id.password);
        String password = (String) passwordText.getText();
        AccountService accountService = new AccountImpl();
        boolean checkLogin = true;
        Account account = null;
        try {
            account = accountService.login(email, password);
        }catch (CredenzialiVuoteException exception){
            //ritorno al login e messaggio
            checkLogin = false;
        }catch (CredenzialiErrateException exception){
            //ritorno alla login e messaggio
            checkLogin = false;
        }

        if (checkLogin){
            if(account.isIscritto()){
                Intent i = new Intent();
                i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
                i.putExtra("account", (Serializable) account);
                startActivityForResult(i, 878);
            }
            else
                if(account.isIscritto() != null){
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
                    i.putExtra("account", (Serializable) account);
                    startActivityForResult(i, 878);
                }
        }
    }

    public void onClickRegistrazioneLogin(View view){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RegistrazioneIscrittoActivity.class);
        startActivityForResult(i, 878);
    }
}