package it.unisa.ilike.liste.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.liste.application.ListaImpl;
import it.unisa.ilike.liste.application.exceptions.InvalidNomeException;
import it.unisa.ilike.liste.application.exceptions.ListaGiaEsistenteException;
import it.unisa.ilike.liste.application.exceptions.NomeVuotoException;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.utils.InternetConnection;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare tutte
 * le operazioni relative alla creazione di una lista personale di contenuti di iLike.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class CreazioneListaActivity extends AppCompatActivity {


    private class GsonResultCreaLista extends AsyncTask<Object, Void, Boolean> {

        Boolean listaCreata = true;


        @Override
        protected Boolean doInBackground(Object... objects) {
            ListaImpl listaImpl = new ListaImpl();
            IscrittoBean i= (IscrittoBean) objects[0];
            String nome= (String) objects[1];
            boolean pubblica= (boolean) objects[2];
            try {
                i = listaImpl.creaLista(i, nome, pubblica);
            } catch (NomeVuotoException e) {
                this.listaCreata=false;
            } catch (InvalidNomeException e) {
                this.listaCreata=false;
            } catch (ListaGiaEsistenteException e) {
                this.listaCreata=false;
            }
            if(i != null && this.listaCreata)
                account.setIscrittoBean(i);
            return listaCreata;
        }


        protected void onPostExecute(Boolean check) {
            if (check) {
                Toast.makeText(CreazioneListaActivity.this, "Lista creata", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setClass(CreazioneListaActivity.this, VisualizzazioneProfiloPersonaleActivity.class);
                i.putExtra("account", account);
                startActivity(i);
            }
            else
                Toast.makeText(CreazioneListaActivity.this, "Lista NON creata", Toast.LENGTH_LONG).show();
        }

        public Boolean isListaCreata(){
            while (this.listaCreata==null);
            return this.listaCreata;
        }
    }

    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creazione_lista);
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
        i.setClass(CreazioneListaActivity.this, VisualizzazioneProfiloPersonaleActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    /**
     * Questo metodo permette di passare alla homepage di iLike.
     * @param v
     */
    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(CreazioneListaActivity.this, VisualizzazioneHomepageActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    /**
     * Questo metodo permette all'iscritto di creare una nuova lista personale, specificandone il nome e
     * la visibilita (pubblica o privata).
     * @param v
     */
    public void onClickCreaLista(View v){
        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(CreazioneListaActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {

                TextView nomeLista = findViewById(R.id.nomeLista);
                RadioButton visibilitaPublic = findViewById(R.id.visibilitaPublic);
                RadioButton visibilitaPrivate = findViewById(R.id.visibilitaPrivate);

                Boolean pubblica = null;
                if (visibilitaPublic.isChecked()) {
                    pubblica = true;
                } else if (visibilitaPrivate.isChecked()) {
                    pubblica = false;
                }


                if (pubblica == null) {
                    Toast.makeText(getApplicationContext(), "Inserire la visibilità della lista", Toast.LENGTH_LONG).show();
                } else {
                    account = (Account) getIntent().getExtras().getSerializable("account");
                    IscrittoBean iscritto = account.getIscrittoBean();
                    Object[] objects = new Object[3];
                    objects[0] = iscritto;
                    objects[1] = String.valueOf(nomeLista.getText());
                    objects[2] = pubblica;
                    GsonResultCreaLista g = (GsonResultCreaLista) new GsonResultCreaLista().execute(objects);
                }
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    private Account account;
}