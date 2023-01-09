package it.unisa.ilike.contenuti.presentation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.application.ContenutoImpl;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.profili.presentation.VisualizzazioneProfiloPersonaleActivity;

public class RicercaContenutoActivity extends AppCompatActivity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio
     * contenuto in ContenutoImpl. Questo è necessario in quanto il metodo in questione richiama metodi
     * delle classi FilmDAO, SerieTVDAO, AlbumMusicaleDAO e LibroDAO. In Android non è consentito fare
     * operazioni di accesso alla rete nel main thread; dato che questa activity si trova nel main
     * thread occorre creare questa classe che estende <code>AsyncTask</code> per usufruire dei
     * metodi di cui sopra.
     */
    private class GsonResultRicerca extends AsyncTask<String, Void, ContenutoBean[]> {

        ContenutoBean[] contenutoBeans;

        /**
         * Consente di recuperare un array di oggetti <code>ContenutoBean</code> utilizzando
         * il metodo di servizio getContenuti della classe ContenutoImpl.
         * @param string array contenente la stringa presente nella barra di ricerca
         * @return un array di oggetti <code>ContenutoBean</code>
         */
        @Override
        protected ContenutoBean[] doInBackground(String... string) {
            ContenutoImpl contenutoImpl = new ContenutoImpl();
            //this.contenutoBeans= contenutoImpl.search(string[0], string[1]);
            return contenutoBeans;
        }

        /**
         * Metodo che restituisce l'array contenutoBeans memorizzato nella classe come variabile
         * d'istanza
         * @return il valore della variabile d'istanza contenutoBeans
         */
        public ContenutoBean[] getContenuti(){
            while (this.contenutoBeans==null);
            return this.contenutoBeans;
        }
    }

    ImageButton profiloButton;
    ImageButton homepageButton;
    SearchView barraDiRicercaContenuti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_contenuto);
        profiloButton= findViewById(R.id.profiloButton);
        homepageButton= findViewById(R.id.homepageButton);
        barraDiRicercaContenuti = findViewById(R.id.BarraDiRicercaContenuti);


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

        GsonResultRicerca g= (GsonResultRicerca) new GsonResultRicerca().execute(testoBarraDiRicerca);
        ContenutoBean[] list= g.getContenuti();
        ArrayList<ContenutoBean> contenutiTrovati= new ArrayList<>();
        for (ContenutoBean c: list){
            contenutiTrovati.add(c);
        }
    }
}