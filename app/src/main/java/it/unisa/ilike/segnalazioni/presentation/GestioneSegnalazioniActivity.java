package it.unisa.ilike.segnalazioni.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.presentation.VisualizzazioneHomepageActivity;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.segnalazioni.application.SegnalazioneImpl;
import it.unisa.ilike.segnalazioni.application.SegnalazioneService;
import it.unisa.ilike.segnalazioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.segnalazioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;
import it.unisa.ilike.utils.exceptions.NotGestoreException;

public class GestioneSegnalazioniActivity extends AppCompatActivity {

    private RecensioneBean recensione;
    private SegnalazioneBean segnalazione;
    private Account account;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_segnalazioni);


        Intent i = getIntent();
        setReturnIntent();
        account = (Account) getIntent().getExtras().getSerializable("account");
        recensione = (RecensioneBean) getIntent().getExtras().getSerializable("recensione");
        segnalazione = (SegnalazioneBean)  getIntent().getExtras().getSerializable("segnalazione");

        TextView recensioneText = findViewById(R.id.textViewTestoRecensione);
        recensioneText.setText(recensione.getTesto());

        TextView segnalazioneText = findViewById(R.id.textViewSegnalazione);
        segnalazioneText.setText(segnalazione.getMotivazione());

    }


    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }

    public void onClickRifiutaSegnalazione(View view) {
        SegnalazioneService segnalazioneService = new SegnalazioneImpl();
        boolean isValidate = true;
        try {
            segnalazioneService.rifiutaSegnalazione(segnalazione, account.getGestoreBean());
        } catch (NotGestoreException e) {
            // messaggio errore
            isValidate = false;
            e.printStackTrace();
        }

        if (isValidate){
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
            i.putExtra("account", (Serializable) account);
            startActivityForResult(i, 878);
        }
    }

    public void onClickAccettaSegnalazione(View view) {

        SegnalazioneService segnalazioneService = new SegnalazioneImpl();
        boolean isValidate = true;

        EditText editText = findViewById(R.id.motivazioneCancellazione);
        String motivazione = editText.toString();

        try {
            segnalazioneService.cancellaRecensione(segnalazione, motivazione, account.getGestoreBean());
        } catch (NotGestoreException e) {
            e.printStackTrace();
        } catch (MotivazioneVuotaException e) {
            // messaggio errore
            e.printStackTrace();
        } catch (InvalidMotivazioneException e) {
            // messaggio errore
            e.printStackTrace();
        }

        if (isValidate){
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
            i.putExtra("account", (Serializable) account);
            startActivityForResult(i, 878);
        }
    }
}
