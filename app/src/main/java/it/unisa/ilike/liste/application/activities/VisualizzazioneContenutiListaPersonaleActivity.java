package it.unisa.ilike.liste.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.application.VisualizzazioneContenutiListaPersonaleAdapter;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.liste.storage.ListaDAO;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare la
 * visualizzazione dei contenuti di una lista personale dell'iscritto.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class VisualizzazioneContenutiListaPersonaleActivity extends AppCompatActivity {

    private class GsonResultContenuti extends AsyncTask<String, Void, ArrayList<ContenutoBean>> {

        @Override
        protected ArrayList<ContenutoBean> doInBackground(String... strings) {
            ListaDAO dao= new ListaDAO();
            lista= dao.doRetrieveByKey(strings[0], iscritto.getEmail());

            ArrayList<ContenutoBean> contenuti= (ArrayList<ContenutoBean>) lista.getContenuti();
            return contenuti;
        }


        @Override
        protected void onPostExecute(ArrayList<ContenutoBean> contenuti) {
            ProgressBar bar= findViewById(R.id.progress_circular);
            bar.setVisibility(View.INVISIBLE);
            TextView tvNomeLista= findViewById(R.id.textViewLista);
            tvNomeLista.setText(lista.getNome());

            for (ContenutoBean c: contenuti){
                adapter.add(c);
            }
        }
    }


    private Account account;
    private IscrittoBean iscritto;
    private ListaBean lista;
    private VisualizzazioneContenutiListaPersonaleAdapter adapter;

    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_contenuti_lista_personale);
        Intent i= getIntent();


        String nomeLista= (String) i.getExtras().getString("lista");
        account = (Account) i.getExtras().getSerializable("account");
        iscritto= account.getIscrittoBean();

        ListView contenutiList = findViewById(R.id.listaContenutiListaPersonale);

        adapter = new VisualizzazioneContenutiListaPersonaleAdapter
                (this, R.layout.activity_list_element_ricerca_contenuto,
                        new ArrayList<ContenutoBean>());

        contenutiList.setAdapter(adapter);

        String[] s= {nomeLista};
        GsonResultContenuti g= (GsonResultContenuti) new GsonResultContenuti().execute(s);

    }

    /**
     * Questo metodo permette all'iscritto di andare alla pagina di visualizzazione del proprio profilo
     * personale.
     * @param v
     */
    public void onClickProfilo(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    /**
     * Questo metodo permette di passare alla homepage di iLike.
     * @param v
     */
    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

}