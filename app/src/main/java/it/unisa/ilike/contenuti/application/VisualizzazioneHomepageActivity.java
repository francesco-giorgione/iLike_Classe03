package it.unisa.ilike.contenuti.application;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.profili.application.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.segnalazioni.application.VisualizzazioneSegnalazioniActivity;

public class VisualizzazioneHomepageActivity extends Activity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo getTop3
     * della classe <code>ContenutoService</code>. In Android non è consentito
     * fare operazioni di accesso alla rete nel main thread; dato che questa activity si trova
     * nel main thread occorre creare questa classe che estende <code>AsyncTask</code> per
     * usufruire del metodo di cui sopra.
     */
    private class GsonResultTop3Contenuti extends AsyncTask<Void, Void, Void> {

        //List<ContenutoBean> top3;
        List<ContenutoBean> top3Film;
        List<ContenutoBean> top3SerieTV;
        List<ContenutoBean> top3Album;
        List<ContenutoBean> top3Libri;

        /**
         * Consente di recuperare una lista di oggetti <code>ContenutoBean</code> utilizzando
         * il metodo il metodo getTop3 della classe <code>ContenutoService</code>.
         * @param voids
         * @return una lista di oggetti <code>ContenutoBean</code>
         */
        /*@Override
        protected List<ContenutoBean> doInBackground(String... string) {
            ContenutoService contenutoService= new ContenutoImpl();
            if (string[0].equalsIgnoreCase("film")) {
                this.top3 = contenutoService.getTop3(0);
            }
            else if (string[0].equalsIgnoreCase("serieTV")){
                this.top3 = contenutoService.getTop3(1);
            }
            else if (string[0].equalsIgnoreCase("libro")){
                this.top3 = contenutoService.getTop3(2);
            }
            else if (string[0].equalsIgnoreCase("album")){
                this.top3 = contenutoService.getTop3(3);
            }
            return this.top3;
        }*/

        @Override
        protected Void doInBackground(Void... voids) {
            ContenutoService contenutoService= new ContenutoImpl();
            this.top3Film=contenutoService.getTop3(0);
            this.top3SerieTV=contenutoService.getTop3(1);
            this.top3Libri=contenutoService.getTop3(2);
            this.top3Album=contenutoService.getTop3(3);
            return null;
        }

        /**
         * Metodo che restituisce la lista di contenuti ottenuta dal metodo doInBackground(...)
         * @return il valore della variabile d'istanza top3
         */
        /*public List<ContenutoBean> getTop3Film(){
            while (top3Film==null);
            return top3Film;
        }

        public List<ContenutoBean> getTop3SerieTV() {
            while (top3SerieTV==null);
            return top3SerieTV;
        }

        public List<ContenutoBean> getTop3Album() {
            while (top3Album==null);
            return top3Album;
        }

        public List<ContenutoBean> getTop3Libri() {
            while (top3Libri==null);
            return top3Libri;
        }*/

        @Override
        protected void onPostExecute(Void unused) {
            Log.d("MyDebug", "sono in onPostExecute");
            TextView titoloFilm1= findViewById(R.id.textFilm1);
            FilmBean film1= (FilmBean) top3Film.get(0);
            titoloFilm1.setText(film1.getTitolo());
            TextView ratingFilm1 = findViewById(R.id.ratingFilm1);
            //Log.d("MyDebug", ""+film1.getValutazioneMedia());
            ratingFilm1.setText(String.valueOf(film1.getValutazioneMedia()));

            //FILM 2
            TextView titoloFilm2= findViewById(R.id.textFilm2);
            FilmBean film2= (FilmBean) top3Film.get(1);
            titoloFilm2.setText(film2.getTitolo());
            TextView ratingFilm2 = findViewById(R.id.ratingFilm2);
            ratingFilm2.setText(String.valueOf(film2.getValutazioneMedia()));
        }
    }


    ImageButton profiloButton;
    ImageButton barraDiRicerca;
    ImageButton chatBotButton;
    ImageButton visualizzaSegnalazioniButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_homepage);

        profiloButton= findViewById(R.id.profiloButton);
        barraDiRicerca= findViewById(R.id.BarraDiRicercaContenutiHomePage);
        visualizzaSegnalazioniButton= findViewById(R.id.VisualizzaSegnalazioniButton);
        chatBotButton=findViewById(R.id.chatBotButton);

        // se l'utente loggato è un gestore
        //visualizzaSegnalazioniButton.setVisibility(View.VISIBLE);
        //chatBotButton.setVisibility(View.INVISIBLE);
        //altrimenti
        //visualizzaSegnalazioniButton.setVisibility(View.INVISIBLE);
        //chatBotButton.setVisibility(View.VISIBLE);



        //inizio da login
        Intent i = getIntent();
        setReturnIntent();
        //Account account = (Account) getIntent().getExtras().getSerializable("account");
        //fine da login

        GsonResultTop3Contenuti g= (GsonResultTop3Contenuti) new GsonResultTop3Contenuti().execute(new Void[0]);
        /*List<ContenutoBean> top3Film= g.getTop3Film();
        List<ContenutoBean> top3Serie= g.getTop3SerieTV();
        List<ContenutoBean> top3Lirbi= g.getTop3Libri();
        List<ContenutoBean> top3Album= g.getTop3Album();

        String[] s= {"film"};
        GsonResultTop3Contenuti g= (GsonResultTop3Contenuti) new GsonResultTop3Contenuti().execute(s);
        List<ContenutoBean> top3Film= g.getContenuti();

        //FILM 1
        TextView titoloFilm1= findViewById(R.id.textFilm1);
        FilmBean film1= (FilmBean) top3Film.get(0);
        titoloFilm1.setText(film1.getTitolo());
        TextView ratingFilm1 = findViewById(R.id.ratingFilm1);
        ratingFilm1.setText(String.valueOf(film1.getValutazioneMedia()));

        //FILM 2
        TextView titoloFilm2= findViewById(R.id.textFilm2);
        FilmBean film2= (FilmBean) top3Film.get(1);
        titoloFilm2.setText(film2.getTitolo());
        TextView ratingFilm2 = findViewById(R.id.ratingFilm2);
        ratingFilm2.setText(String.valueOf(film2.getValutazioneMedia()));*/

        /*s[0]="serieTv";
        g= (GsonResultTop3Contenuti) new GsonResultTop3Contenuti().execute(s);
        List<ContenutoBean> top3SerieTV= g.getContenuti();*/


    }

    //inizio da login
    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }
    //fine da login

    public void onClickProfilo(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        startActivity(i);
    }

    public void onClickSearchBar(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RicercaContenutoActivity.class);
        startActivity(i);
    }

    public void onClickVisualizzaSegnalazioni (View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneSegnalazioniActivity.class);
        startActivity(i);
    }
}