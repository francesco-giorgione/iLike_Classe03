package it.unisa.ilike.contenuti.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import it.unisa.ilike.account.application.activities.LoginActivity;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.ContenutoImpl;
import it.unisa.ilike.contenuti.application.RicercaContenutoAdapter;
import it.unisa.ilike.contenuti.storage.ContenutoBean;

public class RicercaContenutoActivity extends AppCompatActivity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio
     * contenuto in ContenutoImpl. Questo è necessario in quanto il metodo in questione richiama metodi
     * delle classi FilmDAO, SerieTVDAO, AlbumMusicaleDAO e LibroDAO. In Android non è consentito fare
     * operazioni di accesso alla rete nel main thread; dato che questa activity si trova nel main
     * thread occorre creare questa classe che estende <code>AsyncTask</code> per usufruire dei
     * metodi di cui sopra.
     */
    private class GsonResultRicerca extends AsyncTask<String, Void, List<ContenutoBean>> {

        List<ContenutoBean> contenutoBeans;

        /**
         * Consente di recuperare un array di oggetti <code>ContenutoBean</code> utilizzando
         * il metodo di servizio getContenuti della classe ContenutoImpl.
         * @param string array contenente la stringa presente nella barra di ricerca
         * @return un array di oggetti <code>ContenutoBean</code>
         */
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

        /**
         * Metodo che restituisce l'array contenutoBeans memorizzato nella classe come variabile
         * d'istanza
         * @return il valore della variabile d'istanza contenutoBeans
         */
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_contenuto);
        profiloButton= findViewById(R.id.profiloButton);
        homepageButton= findViewById(R.id.homepageButton);
        barraDiRicercaContenuti = findViewById(R.id.BarraDiRicercaContenuti);
        filtro= findViewById(R.id.filtroRicerca);
        contenutiList= findViewById(R.id.contenutiList);
        bar= findViewById(R.id.progress_circular);

        adapter = new RicercaContenutoAdapter(this, R.layout.activity_list_element_ricerca_contenuto,
                new ArrayList<ContenutoBean>());

        contenutiList.setAdapter(adapter);

        account = (Account) getIntent().getExtras().getSerializable("account");

        filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu= new PopupMenu(RicercaContenutoActivity.this, filtro);

                menu.getMenuInflater().inflate(R.menu.menu_ricerca, menu.getMenu());
                menu.setOnMenuItemClickListener(item -> {
                    adapter.clear();
                    filtroRicerca=item.getTitle().toString();
                    return true;});
                menu.show();
            }
        });

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

    public void onClickProfilo(View v){
        if(account.isIscritto() == Boolean.TRUE) {
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

    public void onClickCercaContenuto(View v){
        adapter.clear();
        String testoBarraDiRicerca= String.valueOf(barraDiRicercaContenuti.getQuery());
        if (testoBarraDiRicerca.length()>3) {
            bar.setVisibility(View.VISIBLE);
            String[] s = {testoBarraDiRicerca, filtroRicerca};
            GsonResultRicerca g;
            g = (GsonResultRicerca) new GsonResultRicerca().execute(s);
        }
        else
            Toast.makeText(getApplicationContext(), "Scrivere almeno 4 caratteri!", Toast.LENGTH_LONG).show();
    }

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
}