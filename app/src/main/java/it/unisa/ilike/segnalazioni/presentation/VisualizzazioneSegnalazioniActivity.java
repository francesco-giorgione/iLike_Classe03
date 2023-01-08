package it.unisa.ilike.segnalazioni.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.GestoreBean;
import it.unisa.ilike.account.storage.GestoreDAO;
import it.unisa.ilike.contenuti.presentation.VisualizzazioneHomepageActivity;

public class VisualizzazioneSegnalazioniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_segnalazioni);

        Intent i = getIntent();
        setReturnIntent();
        account = (Account) getIntent().getExtras().getSerializable("account");
        GestoreBean gestoreBean = account.getGestoreBean();
        TextView numerSegnalazioniTextView = (TextView) findViewById(R.id.numerSegnalazioniGestite);
        numerSegnalazioniTextView.setText(gestoreBean.getNumSegnalazioniGestite());
    }

    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }

    public void onClickVisualizzaSegnalazioni(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneSegnalazioniActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }


    public void onClickHomepage(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    public void onClickLogout(View view) {
        AccountService accountService = new AccountImpl();
        account = accountService.logout(account.getGestoreBean());
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    private Account account;
}