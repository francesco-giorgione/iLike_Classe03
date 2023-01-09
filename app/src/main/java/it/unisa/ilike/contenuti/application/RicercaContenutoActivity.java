package it.unisa.ilike.contenuti.application;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.profili.application.VisualizzazioneProfiloPersonaleActivity;

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
            if (string[0].equalsIgnoreCase("noFilter")) {
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
            ArrayList<ContenutoBean> contenutiTrovati= (ArrayList<ContenutoBean>) contenutoBeans;

            if (contenutiTrovati.size()>0) {
                for (ContenutoBean c : contenutiTrovati)
                    adapter.add(c);
                Log.d("TestContenutiRicerca", contenutiTrovati.toString());
            }
            else
                Toast.makeText(getApplicationContext(), "Nessun contenuto trovato", Toast.LENGTH_LONG).show();

        }
    }

    ImageButton profiloButton;
    ImageButton homepageButton;
    SearchView barraDiRicercaContenuti;
    ImageButton filtro;
    String filtroRicerca= "noFilter";
    ListView contenutiList;
    RicercaContenutoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_contenuto);
        profiloButton= findViewById(R.id.profiloButton);
        homepageButton= findViewById(R.id.homepageButton);
        barraDiRicercaContenuti = findViewById(R.id.BarraDiRicercaContenuti);
        filtro= findViewById(R.id.filtroRicerca);
        contenutiList= findViewById(R.id.contenutiList);

        adapter = new RicercaContenutoAdapter(this, R.layout.activity_list_element_ricerca_contenuto,
                new ArrayList<ContenutoBean>());

        contenutiList.setAdapter(adapter);

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

        //Intent i = getIntent();
        //setReturnIntent();

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
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        startActivity(i);
    }

    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        startActivity(i);
    }

    public void onClickCercaContenuto(View v){

        String testoBarraDiRicerca= String.valueOf(barraDiRicercaContenuti.getQuery());
        String[] s={testoBarraDiRicerca, filtroRicerca};
        GsonResultRicerca g;
        g= (GsonResultRicerca) new GsonResultRicerca().execute(s);

    }
}