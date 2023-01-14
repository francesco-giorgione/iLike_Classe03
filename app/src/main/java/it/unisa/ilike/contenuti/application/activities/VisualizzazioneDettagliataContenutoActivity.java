package it.unisa.ilike.contenuti.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.activities.LoginActivity;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.ContenutoImpl;
import it.unisa.ilike.contenuti.application.ContenutoService;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.liste.application.activities.AggiuntaContenutoListaActivity;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.application.activities.AggiuntaSegnalazioneRecensioneActivity;
import it.unisa.ilike.recensioni.application.activities.PubblicazioneRecensioneActivity;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;
import it.unisa.ilike.utils.InternetConnection;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare tutte
 * le operazioni relative alla visualizzazione dettagliata di un contenuto di iLike.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class VisualizzazioneDettagliataContenutoActivity extends AppCompatActivity {

    ImageButton profiloButton;
    ImageButton homepageButton;
    ContenutoBean c;
    VisualizzazioneDettagliataContenutoAdapter adapter;

    private class GsonResultContenuto extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... id) {

            ContenutoService contenutoService= new ContenutoImpl();
            c= contenutoService.getById(id[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            TextView titoloContenuto= findViewById(R.id.titoloContenuto);
            titoloContenuto.setText(c.getTitolo());

            ImageView icona= findViewById(R.id.imgContenuto);
            if (c instanceof FilmBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
            else if (c instanceof SerieTVBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
            else if (c instanceof LibroBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
            else
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));

            TextView descrizione= findViewById(R.id.descrizioneContenuto);
            descrizione.append(c.getDescrizione());

            RatingBar valutazioneMediaContenuto= findViewById(R.id.valutazioneMediaContenuto);
            valutazioneMediaContenuto.setRating((int)c.getValutazioneMedia());
        }
    }

    private class GsonResultRecensioni extends AsyncTask<Void, Void, ArrayList<RecensioneBean>> {

        @Override
        protected ArrayList<RecensioneBean> doInBackground(Void... voids) {
            ArrayList<RecensioneBean> recensioni= (ArrayList<RecensioneBean>) c.getRecensioni();
            return recensioni;
        }

        @Override
        protected void onPostExecute(ArrayList<RecensioneBean> recensioni) {

            ProgressBar bar= findViewById(R.id.progress_circular);
            bar.setVisibility(View.INVISIBLE);

            if (recensioni.size()>0) {
                for (RecensioneBean r : recensioni)
                    adapter.add(r);
                Log.d("MyDebug", "Recensioni trovate -->"+recensioni.toString());
            }
            else {
                Toast.makeText(getApplicationContext(), "Nessuna recensione trovata", Toast.LENGTH_LONG).show();
                Log.d("MyDebug", "nessuna recensione trovata");
            }

        }
    }

    private class GsonResultSegnalazione extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("debugProfilo", "doInBackground");
            RecensioneService recensioneService = new RecensioneImpl();
            recensioneBean = recensioneService.getRecensione(idRecensione);
            return null;
        }


        @Override
        protected void onPostExecute(Void unused) {

            s.setRecensione(recensioneBean);
            onClickAggiungiSegnalazione(s);
        }

        private RecensioneBean recensioneBean;
    }

    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_dettagliata_contenuto);

        account = (Account) getIntent().getExtras().getSerializable("account");

        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(VisualizzazioneDettagliataContenutoActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                profiloButton = findViewById(R.id.profiloButton);
                homepageButton = findViewById(R.id.homepageButton);

                //c = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");

                Intent i = getIntent();
                int idContenuto = i.getIntExtra("idContenuto", -1);

                Log.d("MyDebug", "idContenutoCliccato -->" + idContenuto);

                GsonResultContenuto g = (GsonResultContenuto) new GsonResultContenuto().execute(idContenuto);
                ListView recensioniList = findViewById(R.id.recensioniList);

                adapter = new VisualizzazioneDettagliataContenutoAdapter(this, R.layout.activity_list_element_visualizzazione_dettagliata_contenuto,
                        new ArrayList<RecensioneBean>());

                recensioniList.setAdapter(adapter);

                GsonResultRecensioni gr = (GsonResultRecensioni) new GsonResultRecensioni().execute(new Void[0]);

                setReturnIntent();
            }
            catch(NetworkOnMainThreadException n){
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
     * Questo metodo permette all'utente che non ha effettuato l'accesso di andare alla pagina di login di iLike
     * (da cui poter eventualmente andare alla pagina di registrazione se non si è registrati alla piattaforma).
     * All'iscritto che ha effettuato l'accesso, essa permette di andare alla pagina di visualizzazione del proprio
     * profilo personale.
     * @param v
     */
    public void onClickProfilo(View v){
        if(account.isIscritto() == Boolean.TRUE) {
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
            i.putExtra("account", (Serializable) account);
            startActivity(i);
        }else {
            Toast.makeText(getApplicationContext(), "Effettua il login per eseguire questa operazione", Toast.LENGTH_LONG).show();
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

    /**
     * Questo metodo permette all'iscritto di passare alla pagina per la pubblicazione di una recensione
     * relativa al contenuto che si sta visualizzando. Se l'utente non ha effettuato l'accesso viene mandato
     * alla pagina di login.
     * @param v
     */
    public void onClickAggiungiRecensione(View v){
        if(account.isIscritto() == Boolean.TRUE) {
            Intent i = new Intent();
            i.setClass(getApplicationContext(), PubblicazioneRecensioneActivity.class);
            i.putExtra("account", (Serializable) account);
            i.putExtra("contenuto", (Serializable) c);
            startActivity(i);
        }else {
            if(account.isIscritto() == Boolean.FALSE){
                Toast.makeText(getApplicationContext(), "Effettua il login come iscritto per effettuare questa operazione", Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }
    }

    /**
     * Questo metodo permette all'iscritto di passare alla pagina che permette di selezionare la lista personale
     * a cui aggiungere il contenuto che si sta visualizzando. Se l'utente non ha effettuato l'accesso viene
     * mandato alla pagina di login.
     * @param v
     */
    public void onClickAggiungiContenutoAllaLista (View v){
        if(account.isIscritto() == Boolean.TRUE) {
            Intent i = new Intent();
            i.setClass(getApplicationContext(), AggiuntaContenutoListaActivity.class);
            i.putExtra("account", (Serializable) account);
            i.putExtra("contenuto", (Serializable) c);
            startActivity(i);
        }else {
            if(account.isIscritto() == Boolean.FALSE){
                Toast.makeText(getApplicationContext(), "Effettua il login come iscritto per effettuare questa operazione", Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }

        }
    }

    /**
     * Questo metodo permette all'iscritto di passare alla pagina di iLike che permette di effettuare una
     * segnalazione di tipo AltreSegnalazioni.
     * @param v oggetto View usato per ottenere il riferimento al bottone selezionato
     */
    public void onClickAltreSegnalazioni(View v){

        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(VisualizzazioneDettagliataContenutoActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                s = new SegnalazioneBean();
                s.setTipo(0);
                s.setIscritto(account.getIscrittoBean());
                ImageButton altreSegnalazioniButton = (ImageButton) v;
                idRecensione = (int) altreSegnalazioniButton.getTag();

                GsonResultSegnalazione g = (GsonResultSegnalazione) new GsonResultSegnalazione().execute(new Void[0]);
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Questo metodo permette all'iscritto di passare alla pagina di iLike che permette di effettuare una
     * segnalazione di tipo SpoilerAlert.
     * @param v oggetto View usato per ottenere il riferimento al bottone selezionato
     */
    public void onClickSpoilerAlert(View v){
        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(VisualizzazioneDettagliataContenutoActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                s = new SegnalazioneBean();
                s.setTipo(1);
                s.setIscritto(account.getIscrittoBean());
                Button spoilerAlertButton = (Button) v;
                idRecensione = (int) spoilerAlertButton.getTag();

                GsonResultSegnalazione g = (GsonResultSegnalazione) new GsonResultSegnalazione().execute(new Void[0]);
            }
            catch(NetworkOnMainThreadException n){
                    Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
                }
            }
        else {
                Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    private void onClickAggiungiSegnalazione(SegnalazioneBean s){
        if(account.isIscritto() == Boolean.TRUE){
            Intent i = new Intent();
            i.setClass(getApplicationContext(), AggiuntaSegnalazioneRecensioneActivity.class);
            i.putExtra("segnalazione", s);
            i.putExtra("account", account);
            i.putExtra("contenuto", c);
            startActivity(i);
        }else{
            if(account.isIscritto() == Boolean.FALSE){
                Toast.makeText(getApplicationContext(), "Effettua il login come iscritto per effettuare questa operazione", Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }
    }

    private Account account;
    private int idRecensione;
    private SegnalazioneBean s;
}