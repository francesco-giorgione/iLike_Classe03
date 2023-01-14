package it.unisa.ilike.liste.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.application.ListaImpl;
import it.unisa.ilike.liste.application.ListaService;
import it.unisa.ilike.liste.application.exceptions.ContenutoGiaPresenteException;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.liste.storage.ListaDAO;
import it.unisa.ilike.utils.InternetConnection;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare tutte
 * le operazioni relative all'aggiunta di un contenuto ad una lista personale dell'iscritto.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class AggiuntaContenutoListaActivity extends AppCompatActivity {

    private class GsonResultCreaLista extends AsyncTask<Void, Void, ArrayList<ListaBean>> {

        private boolean checkAccount= true;

        @Override
        protected ArrayList<ListaBean> doInBackground(Void... objects) {
            if (account!=null){
                IscrittoBean iscritto= account.getIscrittoBean();
                return (ArrayList<ListaBean>) iscritto.getListe();
            }
            else{
                checkAccount=false;
                return null;
            }
        }


        protected void onPostExecute(ArrayList<ListaBean> listeIscritto) {

            ProgressBar bar= findViewById(R.id.progress_circular);
            bar.setVisibility(View.INVISIBLE);

            TextView t= findViewById(R.id.textView);
            t.setText("Scegli la lista a cui aggiungere il contenuto");

            if (listeIscritto!=null)
                for (ListaBean l: listeIscritto)
                    adapter.add(l);
            else
                if(!checkAccount)
                    Toast.makeText(AggiuntaContenutoListaActivity.this, "Effettua il login per eseguire questa operazione", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AggiuntaContenutoListaActivity.this, "Crea una lista nel tuo profilo prima di eseguire questa operazione", Toast.LENGTH_LONG).show();
        }

    }

    private class GsonResultAggiungiContenutoLista extends AsyncTask<String, Void, Void> {

        private boolean isOk = true;

        @Override
        protected Void doInBackground(String... strings) {
            if (account!=null){
                IscrittoBean iscritto= account.getIscrittoBean();
                ListaService service= new ListaImpl();
                ListaBean lista = service.getLista(strings[0], iscritto.getEmail());
                Log.d("debugListe", contenuto.toString());
                try {
                    service.aggiungiContenuto(lista, contenuto);
                } catch (ContenutoGiaPresenteException e) {
                    isOk =false;
                }
            }
            else{
                isOk =false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            if(!isOk) {
                Toast.makeText(AggiuntaContenutoListaActivity.this, "Errore", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
            else {
                Toast.makeText(AggiuntaContenutoListaActivity.this, "Contenuto aggiunto alla lista", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setClass(AggiuntaContenutoListaActivity.this, VisualizzazioneProfiloPersonaleActivity.class);
                i.putExtra("account", account);
                startActivity(i);
            }
        }

    }

    private Account account;
    private ContenutoBean contenuto;
    private AggiuntaContenutoListaAdapter adapter;


    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_contenuto_lista);

        Intent i = getIntent();
        account = (Account) i.getExtras().getSerializable("account");
        contenuto = (ContenutoBean) i.getExtras().getSerializable("contenuto");
        setReturnIntent();

        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(AggiuntaContenutoListaActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                ListView elencoListeIscritto = findViewById(R.id.elencoListeIscritto);

                adapter = new AggiuntaContenutoListaAdapter(this, R.layout.activity_list_element_aggiunta_contenuto_lista,
                        new ArrayList<ListaBean>());
                elencoListeIscritto.setAdapter(adapter);

                if (account != null) {
                    GsonResultCreaLista g = (GsonResultCreaLista) new GsonResultCreaLista().execute(new Void[0]);
                } else {
                    Toast.makeText(getApplicationContext(), "Effettua il login per eseguire questa operazione", Toast.LENGTH_LONG).show();
                }
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
     * Questo metodo permette all'iscritto di andare alla pagina di visualizzazione del proprio profilo
     * personale.
     * @param v
     */
    public void onClickProfilo(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        startActivity(i);
    }

    /**
     * Questo metodo permette di passare alla homepage di iLike.
     * @param v
     */
    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        startActivity(i);
    }

    /**
     * Questo metodo permette di aggiungere il contenuto selezionato alla lista personale scelta.
     * @param v oggetto View usato per ottenere il riferimento alla lista personale selezionata
     */
    public void onClickAggiungiContenutoAllaLista(View v){
        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(AggiuntaContenutoListaActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                TextView t = (TextView) v;
                String nomeLista = String.valueOf(t.getText());
                String[] s = {nomeLista};
                GsonResultAggiungiContenutoLista g = (GsonResultAggiungiContenutoLista) new GsonResultAggiungiContenutoLista().execute(s);
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }
}