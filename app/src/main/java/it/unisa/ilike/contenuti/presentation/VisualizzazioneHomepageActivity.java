package it.unisa.ilike.contenuti.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.ContenutoBean;
import it.unisa.ilike.contenuti.application.FilmBean;
import it.unisa.ilike.contenuti.storage.AlbumMusicaleDAO;
import it.unisa.ilike.contenuti.storage.FilmDAO;
import it.unisa.ilike.contenuti.storage.LibroDAO;
import it.unisa.ilike.contenuti.storage.SerieTVDAO;
import it.unisa.ilike.profili.presentation.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.segnalazioni.presentation.VisualizzazioneSegnalazioniActivity;

public class VisualizzazioneHomepageActivity extends Activity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata ad alcuni metodi
     * delle classi FilmDAO, SerieTVDAO, AlbumMusicaleDAO e LibroDAO. In Android non è consentito
     * fare operazioni di accesso alla rete nel main thread; dato che questa activity si trova
     * nel main thread occorre creare questa classe che estende <code>AsyncTask</code> per
     * usufruire dei metodi di cui sopra.
     */
    private class GsonResultFilm extends AsyncTask<String, Void, List<ContenutoBean>> {

        List<ContenutoBean> top3;

        /**
         * Consente di recuperare una lista di oggetti <code>ContenutoBean</code> utilizzando
         * il metodo i metodi delle classi FilmDAO, SerieTVDAO, AlbumMusicaleDAO e LibroDAO.
         * @param string array di stringhe contenente la categoria di contenuti da recuperare dal DB
         * @return una lista di oggetti <code>ContenutoBean</code>
         */
        @Override
        protected List<ContenutoBean> doInBackground(String... string) {
            if (string[0].equalsIgnoreCase("film")) {
                FilmDAO dao= new FilmDAO();
                this.top3 = dao.doRetrieveTop3();
            }
            else if (string[0].equalsIgnoreCase("serieTV")){
                SerieTVDAO dao= new SerieTVDAO();
                this.top3= dao.doRetrieveTop3();
            }
            else if (string[0].equalsIgnoreCase("album")){
                AlbumMusicaleDAO dao= new AlbumMusicaleDAO();
                this.top3= dao.doRetrieveTop3();
            }
            else if (string[0].equalsIgnoreCase("libro")){
                LibroDAO dao= new LibroDAO();
                this.top3=dao.doRetrieveTop3();
            }
            return this.top3;
        }

        /**
         * Metodo che restituisce la lista di contenuti ottenuta dal metodo doInBackground(...)
         * @return il valore della variabile d'istanza top3
         */
        public List<ContenutoBean> getContenuti(){
            while (this.top3==null);
            return this.top3;
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
        Account account = (Account) getIntent().getExtras().getSerializable("account");
        //fine da login

        String[] s= {"film"};
        GsonResultFilm g= (GsonResultFilm) new GsonResultFilm().execute(s);
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
        ratingFilm2.setText(String.valueOf(film2.getValutazioneMedia()));

        s[0]="serieTv";
        g= (GsonResultFilm) new GsonResultFilm().execute(s);
        List<ContenutoBean> top3SerieTV= g.getContenuti();


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