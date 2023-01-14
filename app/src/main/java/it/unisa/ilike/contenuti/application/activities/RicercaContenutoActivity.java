package it.unisa.ilike.contenuti.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.application.activities.LoginActivity;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.ContenutoImpl;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.utils.InternetConnection;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare tutte
 * le operazioni relative alla ricerca di un contenuto di iLike.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class RicercaContenutoActivity extends AppCompatActivity {


    private class GsonResultRicerca extends AsyncTask<String, Void, List<ContenutoBean>> {

        List<ContenutoBean> contenutoBeans;

        @Override
        protected List<ContenutoBean> doInBackground(String... string) {
            ContenutoImpl contenutoImpl = new ContenutoImpl();
            if (string[1].equalsIgnoreCase("noFilter") || string[1].equalsIgnoreCase("tutti i contenuti")) {
                this.contenutoBeans = contenutoImpl.cerca(string[0]);
            }
            else {
                int tipo=-1;
                if (string[1].equalsIgnoreCase("film"))
                    tipo=0;
                else if (string[1].equalsIgnoreCase("serieTV"))
                    tipo=1;
                else if (string[1].equalsIgnoreCase("libro"))
                    tipo=2;
                else if (string[1].equalsIgnoreCase("album musicale"))
                    tipo=3;
                this.contenutoBeans = contenutoImpl.cerca(string[0], tipo);
                filtroRicerca="noFilter";
            }
            return contenutoBeans;
        }


        public List<ContenutoBean> getContenuti(){
            while (this.contenutoBeans==null);
            return this.contenutoBeans;
        }



        @Override
        protected void onPostExecute(List<ContenutoBean> contenutoBeans) {
            Log.d("MyDebug", "sono in onPostExecute RicercaContenutoActivity");
            ArrayList<ContenutoBean> contenutiTrovati= (ArrayList<ContenutoBean>) contenutoBeans;

            if (contenutiTrovati.size()>0) {
                for (ContenutoBean c : contenutiTrovati)
                    adapter.add(c);
                Log.d("MyDebug", "Contenuti trovati -->"+contenutiTrovati.toString());
            }
            else {
                Toast.makeText(getApplicationContext(), "Nessun contenuto trovato", Toast.LENGTH_LONG).show();
                Log.d("MyDebug", "nessun contenuto trovato");
            }
            bar.setVisibility(View.INVISIBLE);
        }
    }

    private class GsonResultLogout extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AccountService accountService = new AccountImpl();
            account = accountService.logout(account.getGestoreBean());

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Log.d("MyDebug", "sono in onPostExecute");

            Intent i = new Intent();
            i.setClass(RicercaContenutoActivity.this, VisualizzazioneHomepageActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }


    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_contenuto);

        boolean checkconnessione;

        if (InternetConnection.haveInternetConnection(RicercaContenutoActivity.this)) {
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
                barraDiRicercaContenuti = findViewById(R.id.BarraDiRicercaContenuti);
                filtro = findViewById(R.id.filtroRicerca);
                contenutiList = findViewById(R.id.contenutiList);
                bar = findViewById(R.id.progress_circular);
                visualizzaSegnalazioniButton = findViewById(R.id.VisualizzaSegnalazioniButton);
                chatBotButton = findViewById(R.id.chatBotButton);

                adapter = new RicercaContenutoAdapter(this, R.layout.activity_list_element_ricerca_contenuto,
                        new ArrayList<ContenutoBean>());

                contenutiList.setAdapter(adapter);

                account = (Account) getIntent().getExtras().getSerializable("account");

                filtro.setOnClickListener(new View.OnClickListener() {
                    /**
                     * Questo metodo permette di effettuare la ricerca del contenuto secondo i filtri selezionati.
                     *
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        PopupMenu menu = new PopupMenu(RicercaContenutoActivity.this, filtro);

                        menu.getMenuInflater().inflate(R.menu.menu_ricerca, menu.getMenu());
                        menu.setOnMenuItemClickListener(item -> {
                            adapter.clear();
                            filtroRicerca = item.getTitle().toString();
                            return true;
                        });
                        menu.show();
                    }
                });

                if (account.isIscritto() == Boolean.FALSE) {
                    // se l'attore è un gestore
                    visualizzaSegnalazioniButton.setVisibility(View.VISIBLE);
                    chatBotButton.setVisibility(View.INVISIBLE);
                } else {
                    // se l'attore è un iscritto o un utente non registrato
                    visualizzaSegnalazioniButton.setVisibility(View.INVISIBLE);
                    chatBotButton.setVisibility(View.VISIBLE);
                }


                Intent i = getIntent();
                setReturnIntent();
            }
            catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        }
        else{
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
        }
        else if (account.isIscritto() == Boolean.FALSE){
            //logout
            GsonResultLogout g= (GsonResultLogout) new GsonResultLogout().execute(new Void[0]);
        }
        else {
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
     * Questo metodo permette di effettuare la ricerca di un contenuto, mostrando una lista di contenuti il cui
     * titolo corrisponde al testo inserito dall'utente.
     * @param v
     */
    public void onClickCercaContenuto(View v){
        adapter.clear();
        boolean checkconnessione;

        if (InternetConnection.haveInternetConnection(RicercaContenutoActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                String testoBarraDiRicerca = String.valueOf(barraDiRicercaContenuti.getQuery());
                if (testoBarraDiRicerca.length() > 3) {
                    bar.setVisibility(View.VISIBLE);
                    String[] s = {testoBarraDiRicerca, filtroRicerca};
                    GsonResultRicerca g;
                    g = (GsonResultRicerca) new GsonResultRicerca().execute(s);
                } else
                    Toast.makeText(getApplicationContext(), "Scrivere almeno 4 caratteri!", Toast.LENGTH_LONG).show();
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Questo metodo permette di passare alla pagina di visualizzazione dettagliata del contenuto cliccato
     * dall'utente.
     * @param v oggetto View usato per ottenere il riferimento al contenuto selezionato
     */
    public void onClickVisualizzaContenuto(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
        TextView titolo= (TextView) v;
        int id= (int)titolo.getTag();
        i.putExtra("idContenuto", id);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    private ImageButton profiloButton;
    private ImageButton homepageButton;
    private SearchView barraDiRicercaContenuti;
    private ImageButton filtro;
    private String filtroRicerca= "noFilter";
    private ListView contenutiList;
    private RicercaContenutoAdapter adapter;
    private ProgressBar bar;
    private Account account;
    private ImageButton chatBotButton;
    private ImageButton visualizzaSegnalazioniButton;
}