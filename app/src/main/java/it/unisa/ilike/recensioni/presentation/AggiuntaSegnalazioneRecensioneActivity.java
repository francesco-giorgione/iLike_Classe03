package it.unisa.ilike.recensioni.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.presentation.VisualizzazioneDettagliataContenutoActivity;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTipoException;
import it.unisa.ilike.recensioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.recensioni.application.RecensioneBean;
import it.unisa.ilike.utils.exceptions.NotIscrittoException;

public class AggiuntaSegnalazioneRecensioneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_segnalazione_recensione);
    }

    public void onClickInviaSegnalazione(View view) {
        TextView tipoSegnalazioneTextView = findViewById(R.id.tipoSegnalazione);
        String tipoSegnalazione = (String) tipoSegnalazioneTextView.getText();
        TextView motivazioneSegnalazioneTextView = findViewById(R.id.motivazioneSegnalazione);
        String motivazioneSegnalazione = (String) motivazioneSegnalazioneTextView.getText();

        int tipo = 3;
        boolean isValidate = true;
        if (tipoSegnalazione.equalsIgnoreCase("Spoiler Alert"))
            tipo = 0;
        else if (tipoSegnalazione.equalsIgnoreCase("Altre Segnalazioni"))
            tipo = 1;

        RecensioneBean recensione = (RecensioneBean) getIntent().getExtras().getSerializable("recensione");
        Account account = (Account) getIntent().getExtras().getSerializable("account");

        RecensioneService recensioneService = new RecensioneImpl();
        try {
            recensioneService.aggiungiSegnalazione(tipo, motivazioneSegnalazione, recensione, account.getIscrittoBean());
        } catch (NotIscrittoException e) {
            //messaggio errore
            isValidate = false;
            e.printStackTrace();
        } catch (InvalidTipoException e) {
            //messaggio errore
            isValidate = false;
            e.printStackTrace();
        } catch (MotivazioneVuotaException e) {
            //messaggio errore
            isValidate = false;
            e.printStackTrace();
        } catch (InvalidMotivazioneException e) {
            //messaggio errore
            isValidate = false;
            e.printStackTrace();
        }

        if(isValidate){
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
            i.putExtra("account", (Serializable) account);
            i.putExtra("recensione", (Serializable) recensione);
            startActivityForResult(i, 878);
        }//else in aggiungi segnalazione
    }
}