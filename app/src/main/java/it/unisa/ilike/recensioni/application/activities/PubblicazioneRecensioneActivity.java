package it.unisa.ilike.recensioni.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.activities.LoginActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneDettagliataContenutoActivity;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTestoException;
import it.unisa.ilike.recensioni.application.exceptions.TestoTroppoBreveException;
import it.unisa.ilike.recensioni.application.exceptions.ValutazioneException;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

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
        RecensioneBean recensione;

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

            int valutazioneContenuto= Integer.parseInt(string[1]);
            recensione = null;
            try {
                recensione = recensioneService.creaRecensione(string[0], valutazioneContenuto, account.getIscrittoBean(), contenuto);
            } catch (TestoTroppoBreveException e) {
                // messaggio di errore
                isValidate = false;
                Toast toast = Toast.makeText(getApplicationContext(), "Il testo della recensione non rispetta il numero minimo di 3 caratteri!", Toast.LENGTH_LONG);
                toast.show();
            } catch (InvalidTestoException e) {
                // messaggio di errore
                isValidate = false;
                Toast toast = Toast.makeText(getApplicationContext(), "Il testo della recensione non può superare i 1000 caratteri!", Toast.LENGTH_LONG);
                toast.show();
            } catch (ValutazioneException e) {
                // messaggio di errore
                isValidate = false;
                Toast toast = Toast.makeText(getApplicationContext(), "La valutazione inserita non è valida", Toast.LENGTH_LONG);
                toast.show();
            }

            return true;
        }

        protected void onPostExecute(Boolean b) {

            if (this.isValidate) {
                contenuto.aggiungiRecensione(recensione);
                IscrittoBean iscrittoBean = account.getIscrittoBean();
                iscrittoBean.addRecensione(recensione);
                account.setIscrittoBean(iscrittoBean);

                Intent i = new Intent();
                i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
                i.putExtra("account", (Serializable) account);
                i.putExtra("contenuto", (Serializable) contenuto);
                startActivity(i);
            }//else go to pubblicazione recensione
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

        account = (Account) getIntent().getExtras().getSerializable("account");
        contenuto = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");

        TextView titoloTextView = findViewById(R.id.titoloContenuto);
        TextView descrizioneTextView = findViewById(R.id.descrizioneContenuto);
        TextView stelleTextView = findViewById(R.id.stelleCorrenti);
        titoloTextView.setText(contenuto.getTitolo());
        descrizioneTextView.setText(contenuto.getDescrizione());
        stelleTextView.setText(String.valueOf(contenuto.getValutazioneMedia()));

        ImageView icona= findViewById(R.id.logoContenuto);
        if (contenuto instanceof FilmBean)
            icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
        else if (contenuto instanceof SerieTVBean)
            icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
        else if (contenuto instanceof LibroBean)
            icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
        else
            icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));


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
        String descrizioneRecensione = String.valueOf(descTextView.getText());

        String s[] = {descrizioneRecensione, String.valueOf(valutazioneContenuto)};
        GsonResultCreaRecensione g = (GsonResultCreaRecensione) new GsonResultCreaRecensione().execute(s);

    }

    public void onClickProfilo(View v){
        if(account.isIscritto()) {
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
            i.putExtra("account", (Serializable) account);
            startActivity(i);
        }else {
            Intent i = new Intent();
            i.setClass(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }

    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }


    private Account account;
    private ContenutoBean contenuto;
}