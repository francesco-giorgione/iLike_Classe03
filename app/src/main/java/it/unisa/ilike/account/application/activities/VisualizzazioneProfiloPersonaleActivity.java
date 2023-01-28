package it.unisa.ilike.account.application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.application.activities.CreazioneListaActivity;
import it.unisa.ilike.liste.application.activities.VisualizzazioneContenutiListaPersonaleActivity;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.moduloFIA.ActivityChatbot;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;
import it.unisa.ilike.utils.InternetConnection;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare tutte
 * le operazioni relative al profilo personale di un iscritto ad iLike.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class VisualizzazioneProfiloPersonaleActivity extends Activity {

    List<RecensioneBean> recensioniIscritto;
    List<ListaBean> listeIscritto;

    private class GsonResultListe extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("debugProfilo", "doInBackground");
            listeIscritto= iscritto.getListe();
            return null;
        }


        @Override
        protected void onPostExecute(Void unused) {

            Log.d("debugProfilo", "onPostExecute");
            Log.d("debugProfilo", ""+listeIscritto.size());

            for (ListaBean l:listeIscritto) {
                //Log.d("debugProfilo", l.toString());
                adapterListe.add(l);
            }

            ProgressBar barListe= findViewById(R.id.barListe);
            barListe.setVisibility(View.INVISIBLE);
        }
    }

    private class GsonResultRecensioni extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("debugProfilo", "doInBackground");
            recensioniIscritto= iscritto.getRecensioni();
            return null;
        }


        @Override
        protected void onPostExecute(Void unused) {

            Log.d("debugProfilo", "onPostExecute");
            Log.d("debugProfilo", ""+recensioniIscritto.size());

            for (RecensioneBean r: recensioniIscritto)
                adapterRecensioni.add(r);

            ProgressBar barRecensioni= findViewById(R.id.barRecensioni);
            barRecensioni.setVisibility(View.INVISIBLE);
        }


    }

    private class GsonResultGetListe extends AsyncTask<Void, Void, ArrayList<ListaBean>> {

        private Boolean isOk= true;

        @Override
        protected ArrayList<ListaBean> doInBackground(Void... voids) {
            if (account.getIscrittoBean()!=null){
                ArrayList<ListaBean> listeIscritto = (ArrayList<ListaBean>) account.getIscrittoBean().getListe();
                if (listeIscritto==null){
                    isOk= false;
                }
                else
                    return listeIscritto;
            }
            else
                isOk=false;

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ListaBean> listeIscritto) {
            Log.d("chatBot", "sono in onPostExecute");

            if (isOk){
                ArrayList<ContenutoBean> list= new ArrayList<>();
                for (ListaBean l: listeIscritto){
                    List<ContenutoBean> contenutiLista= l.getContenuti();
                    for (ContenutoBean c: contenutiLista){
                        list.add(c);
                    }
                }
                ContenutoBean[] array = list.toArray(new ContenutoBean[list.size()]);
                Intent i = new Intent();
                i.setClass(getApplicationContext(), ActivityChatbot.class);
                i.putExtra("contenuti", array);
                i.putExtra("account", account);
                startActivity(i);
            }
            else{
                Toast.makeText(VisualizzazioneProfiloPersonaleActivity.this, "Devi essere loggato ed avere almeno una lista per effettuare questa " +
                        "operazione!", Toast.LENGTH_LONG).show();
            }

        }
    }


    private IscrittoBean iscritto;
    private Account account;
    private VisualizzazioneProfiloPersonaleRecensioniAdapter adapterRecensioni;
    private VisualizzazioneProfiloPersonaleListeAdapter adapterListe;


    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_profilo_personale);

        Intent i = getIntent();
        account = (Account) i.getExtras().getSerializable("account");
        iscritto = account.getIscrittoBean();

        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(VisualizzazioneProfiloPersonaleActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                ListView listViewListe = findViewById(R.id.elencoListeIscritto);
                ListView listViewRecensioni = findViewById(R.id.recensioniList);

                TextView nicknameTextView = findViewById(R.id.nicknameTextView);
                TextView infoTextView = findViewById(R.id.infoTextView);
                nicknameTextView.setText(iscritto.getNickname());
                infoTextView.setText(iscritto.getBio());

                adapterListe = new VisualizzazioneProfiloPersonaleListeAdapter(
                        this, R.layout.activity_list_element_visualizzazione_profilo_personale_liste,
                        new ArrayList<ListaBean>());
                listViewListe.setAdapter(adapterListe);


                adapterRecensioni = new VisualizzazioneProfiloPersonaleRecensioniAdapter(
                        this, R.layout.activity_list_element_visualizzazione_profilo_personale_recensioni,
                        new ArrayList<RecensioneBean>());
                listViewRecensioni.setAdapter(adapterRecensioni);

                GsonResultListe g = (GsonResultListe) new GsonResultListe().execute(new Void[0]);
                GsonResultRecensioni g1 = (GsonResultRecensioni) new GsonResultRecensioni().execute(new Void[0]);
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Questo metodo viene chiamato quando l'attività ha rilevato la pressione dell'utente del tasto Indietro.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * Questo metodo permette all'iscritto di effettuare il logout dalla piattaforma di iLike.
     * @param v
     */
    public void onClickLogout(View v){
        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(VisualizzazioneProfiloPersonaleActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                Intent i = new Intent();
                AccountService accountService = new AccountImpl();
                account = accountService.logout(account.getIscrittoBean());
                i.setClass(VisualizzazioneProfiloPersonaleActivity.this, VisualizzazioneHomepageActivity.class);
                i.putExtra("account", account);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Questo metodo permette all'iscritto di passare alla pagina di creazione di una nuova lista personale.
     * @param v
     */
    public void onClickAggiungiLista(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), CreazioneListaActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    /**
     * Questo metodo permette all'iscritto di passare alla homepage di iLike.
     * @param v
     */
    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(VisualizzazioneProfiloPersonaleActivity.this, VisualizzazioneHomepageActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    /**
     * Questo metodo permette all'iscritto di andare alla pagina della visualizzazione dei contenuti della lista
     * personale, scelta in base al pulsante premuto.
     * @param v oggetto View usato per ottenere il riferimento al bottone selezionato
     */
    public void onClickInfo(View v){
        Intent i = new Intent();
        ImageButton info= (ImageButton) v;

        String nomeLista = (String) info.getTag();
        i.putExtra("lista", nomeLista);
        i.setClass(VisualizzazioneProfiloPersonaleActivity.this, VisualizzazioneContenutiListaPersonaleActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    public void onClickChatBot (View v){
        GsonResultGetListe g= (GsonResultGetListe) new GsonResultGetListe().execute(new Void[0]);
    }

    private SegnalazioneBean s;
}