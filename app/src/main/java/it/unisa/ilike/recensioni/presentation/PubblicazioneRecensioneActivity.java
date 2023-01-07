package it.unisa.ilike.recensioni.presentation;

import static it.unisa.ilike.R.id.valutazioneContenuto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.presentation.VisualizzazioneDettagliataContenutoActivity;
import it.unisa.ilike.contenuti.presentation.VisualizzazioneHomepageActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.presentation.AggiuntaContenutoListaActivity;
import it.unisa.ilike.profili.presentation.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTestoException;
import it.unisa.ilike.recensioni.application.exceptions.TestoTroppoBreveException;
import it.unisa.ilike.recensioni.application.exceptions.ValutazioneException;
import it.unisa.ilike.utils.exceptions.NotIscrittoException;

public class PubblicazioneRecensioneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubblicazione_recensione);

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


    public void onClickAggiungiRecensione(View view) {

//        TextView username = findViewById(R.id.valutazioneContenuto);
        RatingBar rBar = findViewById(R.id.valutazioneContenuto);
        int valutazioneContenuto = rBar.getNumStars();
        TextView descTextView = findViewById(R.id.testoRecensione);
        String descrizioneRecensione = (String) descTextView.getText();
        RecensioneService recensioneService = new RecensioneImpl();
        Account account = (Account) getIntent().getExtras().getSerializable("account");
        ContenutoBean contenuto = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");
        boolean isValidate = true;
        try {
            recensioneService.creaRecensione(descrizioneRecensione, valutazioneContenuto, account.getIscrittoBean(), contenuto);
        } catch (NotIscrittoException e) {
            // messaggio di errore
            isValidate = false;
            e.printStackTrace();
        } catch (TestoTroppoBreveException e) {
            // messaggio di errore
            isValidate = false;
            e.printStackTrace();
        } catch (InvalidTestoException e) {
            // messaggio di errore
            isValidate = false;
            e.printStackTrace();
        } catch (ValutazioneException e) {
            // messaggio di errore
            isValidate = false;
            e.printStackTrace();
        }

        if(isValidate){
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
            i.putExtra("account", (Serializable) account);
            i.putExtra("contenuto", (Serializable) contenuto);
            startActivityForResult(i, 878);
        }//else go to pubblicazione recensione
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