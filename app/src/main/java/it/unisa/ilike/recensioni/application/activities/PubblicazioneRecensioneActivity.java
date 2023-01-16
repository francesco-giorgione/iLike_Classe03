package it.unisa.ilike.recensioni.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import it.unisa.ilike.utils.InternetConnection;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare tutte
 * le operazioni relative alla pubblicazione di una recensione relativa ad un contenuto di iLike.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class PubblicazioneRecensioneActivity extends AppCompatActivity {



    private class GsonResultCreaRecensione extends AsyncTask<String, Void, Boolean> {

        Boolean isValidate = true;
        RecensioneBean recensione;
        String messaggio = null;


        @Override
        protected Boolean doInBackground(String... string) {
            RecensioneService recensioneService = new RecensioneImpl();
            iscrittoBean = account.getIscrittoBean();
            try {
                int valutazioneContenuto = Integer.parseInt(string[1]);
                this.recensione = null;
                try {
                    this.recensione = recensioneService.creaRecensione(string[0], valutazioneContenuto, iscrittoBean, contenuto);

                    // controllo se l'inserimento è andato a buon fine (potrebbero esserci errori a livello di db)
                    if (this.recensione == null) {
                        this.isValidate = false;
                        this.messaggio = "Recensione non pubblicata, riprovare";
                    } else {
                        if (contenuto.aggiungiRecensione(this.recensione) && iscrittoBean.addRecensione(this.recensione))
                            this.messaggio = "Recensione pubblicata correttamente";
                        else
                            this.messaggio = "Recensione non pubblicata, riprovare";
                    }
                } catch (TestoTroppoBreveException e) {
                    // messaggio di errore
                    this.isValidate = false;
                    this.messaggio = "Il testo della recensione non rispetta il numero minimo di 3 caratteri!";
                } catch (InvalidTestoException e) {
                    // messaggio di errore
                    this.isValidate = false;
                    this.messaggio = "Il testo della recensione non può superare i 1000 caratteri!";
                } catch (ValutazioneException e) {
                    // messaggio di errore
                    this.isValidate = false;
                    this.messaggio = "La valutazione inserita non è valida";
                }

                return true;
            }catch(NetworkOnMainThreadException n) {
                messaggio = "Verifica la tua connessione ad internet";
                return false;
            }
        }

        protected void onPostExecute(Boolean b) {
            if (this.isValidate) {
                account.setIscrittoBean(iscrittoBean);

                Intent i = new Intent();
                i.setClass(PubblicazioneRecensioneActivity.this, VisualizzazioneDettagliataContenutoActivity.class);
                i.putExtra("account", (Serializable) account);
                i.putExtra("contenuto", (Serializable) contenuto);
                startActivity(i);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), this.messaggio, Toast.LENGTH_LONG);
                toast.show();
            }
        }


        public Boolean isValidate(){
            while (this.isValidate==null);
            return this.isValidate;
        }
    }


    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubblicazione_recensione);

        account = (Account) getIntent().getExtras().getSerializable("account");
        contenuto = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");

        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(PubblicazioneRecensioneActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                TextView titoloTextView = findViewById(R.id.titoloContenuto);
                TextView descrizioneTextView = findViewById(R.id.descrizioneContenuto);
                TextView stelleTextView = findViewById(R.id.stelleCorrenti);
                titoloTextView.setText(contenuto.getTitolo());
                descrizioneTextView.setText(contenuto.getDescrizione());
                stelleTextView.setText(String.valueOf(contenuto.getValutazioneMedia()));

                ImageView icona = findViewById(R.id.logoContenuto);
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
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }

    /**
     * Questo metodo viene chiamato quando l'attività ha rilevato la pressione dell'utente del tasto Indietro.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Questo metodo permette di aggiungere una recensione al contenuto selezionato.
     * @param view
     */
    public void onClickAggiungiRecensione(View view) {
        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(PubblicazioneRecensioneActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                RatingBar rBar = findViewById(R.id.valutazioneContenuto);
                int valutazioneContenuto = (int) rBar.getRating();
                EditText descTextView = findViewById(R.id.testoRecensione);
                String descrizioneRecensione = String.valueOf(descTextView.getText());

                String s[] = {descrizioneRecensione, String.valueOf(valutazioneContenuto)};
                GsonResultCreaRecensione g = (GsonResultCreaRecensione) new GsonResultCreaRecensione().execute(s);
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Questo metodo permette all'utente che non ha effettuato l'accesso di andare alla pagina di login di iLike
     * (da cui poter eventualmente andare alla pagina di registrazione se non si è registrati alla piattaforma).
     * All'iscritto che ha effettuato l'accesso, essa permette di andare alla pagina di visualizzazione del proprio
     * profilo personale.
     * @param v
     */
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

    /**
     * Questo metodo permette di passare alla homepage di iLike.
     * @param v
     */
    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }


    private Account account;
    private ContenutoBean contenuto;
    private IscrittoBean iscrittoBean;
}