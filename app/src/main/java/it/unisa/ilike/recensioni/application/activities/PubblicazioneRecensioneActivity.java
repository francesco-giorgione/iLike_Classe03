package it.unisa.ilike.recensioni.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneDettagliataContenutoActivity;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.profili.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTestoException;
import it.unisa.ilike.recensioni.application.exceptions.TestoTroppoBreveException;
import it.unisa.ilike.recensioni.application.exceptions.ValutazioneException;

public class PubblicazioneRecensioneActivity extends AppCompatActivity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio creaRecensione
     * contenuto in RecensioneService. Questo è necessario in quanto il metodo in questione richiama metodi
     * delle classi RecensioneDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultCreaRecensione extends AsyncTask<String, Void, Boolean> {

        Boolean isValidate = true;

        /**
         * Consente di utilizzare il metodo creaRecensione di RecensioneService e di memorizzarne
         * l'esito nella variabile di istanza isValidate;
         * @param string array di stringhe contenente il testo della recensione e la valutazione del
         *               contenuto
         * @return true se l'operazione è andata a buon fine, false altrimenti
         */
        @Override
        protected Boolean doInBackground(String... string) {
            RecensioneService recensioneService = new RecensioneImpl();
            Account account = (Account) getIntent().getExtras().getSerializable("account");
            ContenutoBean contenuto = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");

            int valutazioneContenuto= Integer.parseInt(string[1]);

            try {
                recensioneService.creaRecensione(string[0], valutazioneContenuto, account.getIscrittoBean(), contenuto);
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

            return isValidate;
        }

        /**
         * Restituisce il valore della variabile di istanza isValidate dopo che il metodo doInBackground
         * ha terminato la sua esecuzione
         * @return il valore della variabile d'istanza isValidate
         */
        public Boolean isValidate(){
            while (this.isValidate==null);
            return this.isValidate;
        }
    }


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
        RatingBar rBar = findViewById(R.id.valutazioneContenuto);
        int valutazioneContenuto = rBar.getNumStars();
        TextView descTextView = findViewById(R.id.testoRecensione);
        String descrizioneRecensione = (String) descTextView.getText();

        String s[]={descrizioneRecensione, String.valueOf(valutazioneContenuto)};
        GsonResultCreaRecensione g= (GsonResultCreaRecensione) new GsonResultCreaRecensione().execute(s);

        boolean isValidate= g.isValidate();

        if(isValidate){
            Account account = (Account) getIntent().getExtras().getSerializable("account");
            ContenutoBean contenuto = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");

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