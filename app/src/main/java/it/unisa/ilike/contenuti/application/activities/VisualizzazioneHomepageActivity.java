package it.unisa.ilike.contenuti.application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.application.activities.LoginActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.ContenutoImpl;
import it.unisa.ilike.contenuti.application.ContenutoService;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.profili.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.segnalazioni.application.activities.VisualizzazioneSegnalazioniActivity;

public class VisualizzazioneHomepageActivity extends Activity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo getTop3
     * della classe <code>ContenutoService</code>. In Android non è consentito
     * fare operazioni di accesso alla rete nel main thread; dato che questa activity si trova
     * nel main thread occorre creare questa classe che estende <code>AsyncTask</code> per
     * usufruire del metodo di cui sopra.
     */
    private class GsonResultContenuti extends AsyncTask<Void, Void, Void> {

        ContenutoBean c1;
        ContenutoBean c2;
        ContenutoBean c3;

        /**
         * Consente di recuperare una lista di oggetti <code>ContenutoBean</code> utilizzando
         * il metodo il metodo getTop3 della classe <code>ContenutoService</code>.
         * @param voids
         * @return una lista di oggetti <code>ContenutoBean</code>
         */
        @Override
        protected Void doInBackground(Void... voids) {

            int random1= (int) (Math.random() * 333);
            Log.d("MyDebug", "r1 -->"+random1);

            ContenutoService contenutoService= new ContenutoImpl();
            c1= contenutoService.getById(random1);

            //per non caricare contenuti uguali
            int random2= 333+(int) (Math.random() * 333);
            Log.d("MyDebug", "r2 -->"+random2);
            c2= contenutoService.getById(random2);

            int random3= 666+(int) (Math.random() * 333);
            Log.d("MyDebug", "r3 -->"+random3);
            c3= contenutoService.getById(random3);

            return null;
        }

        /**
         * Metodo che restituisce la lista di contenuti ottenuta dal metodo doInBackground(...)
         * @return il valore della variabile d'istanza top3
         */
        @Override
        protected void onPostExecute(Void unused) {
            Log.d("MyDebug", "sono in onPostExecute");

            //CONTENUTO 1
            TextView titoloContenuto1= findViewById(R.id.nomeContenuto1);
            titoloContenuto1.setText(c1.getTitolo());
            titoloContenuto1.setTag(c1.getId());
            TextView ratingFilm1 = findViewById(R.id.valutazioneMediaContenuto1);
            ratingFilm1.setText(String.valueOf(c1.getValutazioneMedia()));
            ImageView icona= findViewById(R.id.imgContenuto1);
            if (c1 instanceof FilmBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
            else if (c1 instanceof SerieTVBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
            else if (c1 instanceof LibroBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
            else
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));


            //CONTENUTO 2
            TextView titoloContenuto2= findViewById(R.id.nomeContenuto2);
            titoloContenuto2.setText(c2.getTitolo());
            titoloContenuto2.setTag(c2.getId());
            TextView ratingFilm2 = findViewById(R.id.valutazioneMediaContenuto2);
            ratingFilm2.setText(String.valueOf(c2.getValutazioneMedia()));
            ImageView icona2= findViewById(R.id.imgContenuto2);
            if (c2 instanceof FilmBean)
                icona2.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
            else if (c2 instanceof SerieTVBean)
                icona2.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
            else if (c2 instanceof LibroBean)
                icona2.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
            else
                icona2.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));


            //CONTENUTO 3
            TextView titoloContenuto3= findViewById(R.id.nomeContenuto3);
            titoloContenuto3.setText(c3.getTitolo());
            titoloContenuto3.setTag(c3.getId());
            TextView ratingFilm3 = findViewById(R.id.valutazioneMediaContenuto3);
            ratingFilm3.setText(String.valueOf(c3.getValutazioneMedia()));
            ImageView icona3= findViewById(R.id.imgContenuto3);
            if (c3 instanceof FilmBean)
                icona3.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
            else if (c3 instanceof SerieTVBean)
                icona3.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
            else if (c3 instanceof LibroBean)
                icona3.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
            else
                icona3.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));
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

        try {
            account = (Account) getIntent().getExtras().getSerializable("account");
        }catch (Exception e){
            Log.d("MyDebug","---------- Account ha lanciato l'eccezione");
            // non esiste l'oggetto account quindi lo creo, e non ci sono attori --> Utente non registrato
            account = new Account(null, null);
        }

        if(account.isIscritto() != Boolean.TRUE){
            // se l'attore è un iscritto o utente non registrato
            visualizzaSegnalazioniButton.setVisibility(View.INVISIBLE);
            chatBotButton.setVisibility(View.VISIBLE);
        }else {
            // se l'attore loggato è un gestore
            visualizzaSegnalazioniButton.setVisibility(View.VISIBLE);
            chatBotButton.setVisibility(View.INVISIBLE);
        }

        Intent i = getIntent();
        setReturnIntent();
        //GsonResultTop3Contenuti g= (GsonResultTop3Contenuti) new GsonResultTop3Contenuti().execute(new Void[0]);
        GsonResultContenuti g= (GsonResultContenuti) new GsonResultContenuti().execute(new Void[0]);
    }

    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }

    public void onClickProfilo(View v){
        if(account.isIscritto() == Boolean.TRUE){
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
            i.putExtra("account", (Serializable) account);
            startActivity(i);
        }else
            if (account.isIscritto() == Boolean.FALSE){
                // logout

                AccountService accountService = new AccountImpl();
                account = accountService.logout(account.getGestoreBean());

                Intent i = new Intent();
                i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
                i.putExtra("account", (Serializable) account);
                startActivity(i);
            }
            else{
                Intent i = new Intent();
                i.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
    }

    public void onClickSearchBar(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RicercaContenutoActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    public void onClickVisualizzaSegnalazioni (View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneSegnalazioniActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    public void onClickVisualizzaContenuto(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
        TextView titolo = (TextView) v;
        int id = (int) titolo.getTag();
        i.putExtra("idContenuto", id);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    private Account account;
}