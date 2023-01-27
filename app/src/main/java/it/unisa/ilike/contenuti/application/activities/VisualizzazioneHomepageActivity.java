package it.unisa.ilike.contenuti.application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.application.activities.LoginActivity;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.FilmDAO;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.moduloFIA.ActivityChatbot;
import it.unisa.ilike.segnalazioni.application.activities.VisualizzazioneSegnalazioniActivity;
import it.unisa.ilike.utils.InternetConnection;

/**
 * Questa classe gestisce il flusso di interazioni tra l'utente e il sistema. Essa permette di effettuare tutte
 * le operazioni relative alla homepage di iLike.
 * @author Simona Lo Conte
 * @version 0.1
 */
public class VisualizzazioneHomepageActivity extends Activity {


    private class GsonResultContenuti extends AsyncTask<Void, Void, Void> {

        /*ContenutoBean c1;
        ContenutoBean c2;
        ContenutoBean c3;*/

        FilmBean c1;
        FilmBean c2;
        FilmBean c3;


        @Override
        protected Void doInBackground(Void... voids) {

            int random1= (int) (Math.random() * 12609);
            Log.d("MyDebug", "r1 -->"+random1);

            //ContenutoService contenutoService= new ContenutoImpl();
            FilmDAO dao= new FilmDAO();
            c1= dao.doRetrieveById(random1);

            //per non caricare contenuti uguali
            int random2= 12609+(int) (Math.random() * 12609);
            Log.d("MyDebug", "r2 -->"+random2);
            //c2= contenutoService.getById(random2);
            c2= dao.doRetrieveById(random2);

            int random3= 25218+(int) (Math.random() * 12609);
            Log.d("MyDebug", "r3 -->"+random3);
            //c3= contenutoService.getById(random3);
            c3= dao.doRetrieveById(random3);

            /*FilmDAO dao= new FilmDAO();
            c1= dao.doRetrieveById(2);
            c2= dao.doRetrieveById(20);
            c3= dao.doRetrieveById(10);*/

            return null;
        }


        @Override
        protected void onPostExecute(Void unused) {
            Log.d("MyDebug", "sono in onPostExecute");

            if (c1==null || c2==null || c3==null) {
                Log.d("MyDebug", "Contenuti null");
                return;
            }

            //CONTENUTO 1
            TextView titoloContenuto1= findViewById(R.id.nomeContenuto1);
            titoloContenuto1.setText(c1.getTitolo());
            titoloContenuto1.setTag(c1.getId());
            TextView ratingFilm1 = findViewById(R.id.valutazioneMediaContenuto1);
            ratingFilm1.setText(String.valueOf(c1.getValutazioneMedia()));
            ImageView icona= findViewById(R.id.imgContenuto1);
            //if (c1 instanceof FilmBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
            /*else if (c1 instanceof SerieTVBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
            else if (c1 instanceof LibroBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
            else
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));*/


            //CONTENUTO 2
            TextView titoloContenuto2= findViewById(R.id.nomeContenuto2);
            titoloContenuto2.setText(c2.getTitolo());
            titoloContenuto2.setTag(c2.getId());
            TextView ratingFilm2 = findViewById(R.id.valutazioneMediaContenuto2);
            ratingFilm2.setText(String.valueOf(c2.getValutazioneMedia()));
            ImageView icona2= findViewById(R.id.imgContenuto2);
            //if (c2 instanceof FilmBean)
                icona2.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
            /*else if (c2 instanceof SerieTVBean)
                icona2.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
            else if (c2 instanceof LibroBean)
                icona2.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
            else
                icona2.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));*/


            //CONTENUTO 3
            TextView titoloContenuto3= findViewById(R.id.nomeContenuto3);
            titoloContenuto3.setText(c3.getTitolo());
            titoloContenuto3.setTag(c3.getId());
            TextView ratingFilm3 = findViewById(R.id.valutazioneMediaContenuto3);
            ratingFilm3.setText(String.valueOf(c3.getValutazioneMedia()));
            ImageView icona3= findViewById(R.id.imgContenuto3);
            //if (c3 instanceof FilmBean)
                icona3.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
            /*else if (c3 instanceof SerieTVBean)
                icona3.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
            else if (c3 instanceof LibroBean)
                icona3.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
            else
                icona3.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));*/
        }
    }

    private class GsonResultLogout extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AccountService accountService = new AccountImpl();
            account = accountService.logout(account.getGestoreBean());

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Log.d("MyDebug", "sono in onPostExecute");

            Intent i = new Intent();
            i.setClass(VisualizzazioneHomepageActivity.this, VisualizzazioneHomepageActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
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
                    ArrayList<ContenutoBean> contenutiLista= (ArrayList<ContenutoBean>) l.getContenuti();
                    for (ContenutoBean c: contenutiLista){
                        list.add(c);
                    }
                }
                Intent i = new Intent();
                i.setClass(getApplicationContext(), ActivityChatbot.class);
                i.putExtra("contenuti", list);
                startActivity(i);
            }
            else{
                Toast.makeText(VisualizzazioneHomepageActivity.this, "Devi essere loggato ed avere almeno una lista per effettuare questa " +
                        "operazione!", Toast.LENGTH_LONG).show();
            }

        }
    }


    /**
     * Primo metodo chiamato alla creazione dell'activity, per le inizializzazioni di avvio necessarie.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_homepage);

        try {
            account = (Account) getIntent().getExtras().getSerializable("account");
        } catch (Exception e) {
            Log.d("MyDebug", "---------- Account ha lanciato l'eccezione");
            // non esiste l'oggetto account quindi lo creo, e non ci sono attori --> Utente non registrato
            account = new Account(null, null);
        }

        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(VisualizzazioneHomepageActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                profiloButton = findViewById(R.id.profiloButton);
                barraDiRicerca = findViewById(R.id.BarraDiRicercaContenutiHomePage);
                visualizzaSegnalazioniButton = findViewById(R.id.VisualizzaSegnalazioniButton);
                chatBotButton = findViewById(R.id.chatBotButton);

                if (account.isIscritto() == Boolean.FALSE) {
                    // se l'attore è un gestore
                    visualizzaSegnalazioniButton.setVisibility(View.VISIBLE);
                    chatBotButton.setVisibility(View.INVISIBLE);
                } else {
                    // se l'attore è un iscritto o un utente non registrato
                    visualizzaSegnalazioniButton.setVisibility(View.INVISIBLE);
                    chatBotButton.setVisibility(View.VISIBLE);
                }

                Intent i = getIntent();
                GsonResultContenuti g = (GsonResultContenuti) new GsonResultContenuti().execute(new Void[0]);
            }
            catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Questo metodo permette all'utente che non ha effettuato l'accesso di andare alla pagina di login di iLike
     * (da cui poter eventualmente andare alla pagina di registrazione se non si è registrati alla piattaforma).
     * All'iscritto che ha effettuato l'accesso, essa permette di andare alla pagina di visualizzazione del proprio
     * profilo personale.
     * @param v
     */
    public void onClickProfilo(View v){
        boolean checkconnessione;
        if (InternetConnection.haveInternetConnection(VisualizzazioneHomepageActivity.this)) {
            checkconnessione = true;
            Log.d("connessione", "Connessione presente!");
        } else {
            checkconnessione = false;
            Log.d("connessione", "Connessione assente!");
        }

        if (checkconnessione) {
            try {
                if (account.isIscritto() == Boolean.TRUE) {
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
                    i.putExtra("account", (Serializable) account);
                    startActivity(i);
                } else {
                    if (account.isIscritto() == Boolean.FALSE) {
                        //logout
                        GsonResultLogout g = (GsonResultLogout) new GsonResultLogout().execute(new Void[0]);
                    } else {
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                }
            }catch(NetworkOnMainThreadException n){
                Toast.makeText(getApplicationContext(), "Verifica la tua connessione ad internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Connessione Internet assente!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Questo metodo permette all'utente di andare alla pagina di ricerca del contenuto.
     * @param v
     */
    public void onClickSearchBar(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RicercaContenutoActivity.class);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    /**
     * Questo metodo permette al gestore di andare alla pagina di visualizzazione delle segnalazioni ricevute
     * e non ancora gestite.
     * @param v
     */
    public void onClickVisualizzaSegnalazioni (View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneSegnalazioniActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    /**
     * Questo metodo permette all'utente di andare alla pagina di visualizzazione del contenuto, insieme ai
     * relativi dettagli.
     * @param v oggetto View usato per ottenere il riferimento al contenuto selezionato
     */
    public void onClickVisualizzaContenuto(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
        TextView titolo = (TextView) v;
        int id = (int) titolo.getTag();
        i.putExtra("idContenuto", id);
        i.putExtra("account", (Serializable) account);
        startActivity(i);
    }

    public void onClickChatBot (View v){
        GsonResultGetListe g= (GsonResultGetListe) new GsonResultGetListe().execute(new Void[0]);
    }


    private Account account;
    private ImageButton profiloButton;
    private ImageButton barraDiRicerca;
    private ImageButton chatBotButton;
    private ImageButton visualizzaSegnalazioniButton;
}