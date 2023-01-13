package it.unisa.ilike.contenuti.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.activities.LoginActivity;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.ContenutoImpl;
import it.unisa.ilike.contenuti.application.ContenutoService;
import it.unisa.ilike.contenuti.application.VisualizzazioneDettagliataContenutoAdapter;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.liste.application.activities.AggiuntaContenutoListaActivity;
import it.unisa.ilike.recensioni.application.activities.AggiuntaSegnalazioneRecensioneActivity;
import it.unisa.ilike.recensioni.application.activities.PubblicazioneRecensioneActivity;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.recensioni.storage.RecensioneDAO;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class VisualizzazioneDettagliataContenutoActivity extends AppCompatActivity {

    ImageButton profiloButton;
    ImageButton homepageButton;
    ContenutoBean c;
    VisualizzazioneDettagliataContenutoAdapter adapter;

    private class GsonResultContenuto extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... id) {

             ContenutoService contenutoService= new ContenutoImpl();
            c= contenutoService.getById(id[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            TextView titoloContenuto= findViewById(R.id.titoloContenuto);
            titoloContenuto.setText(c.getTitolo());

            ImageView icona= findViewById(R.id.imgContenuto);
            if (c instanceof FilmBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_film));
            else if (c instanceof SerieTVBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_serietv));
            else if (c instanceof LibroBean)
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_libro));
            else
                icona.setImageDrawable(getResources().getDrawable(R.drawable.icona_musica));

            TextView descrizione= findViewById(R.id.descrizioneContenuto);
            descrizione.append(c.getDescrizione());

            RatingBar valutazioneMediaContenuto= findViewById(R.id.valutazioneMediaContenuto);
            valutazioneMediaContenuto.setRating((int)c.getValutazioneMedia());
        }
    }

    private class GsonResultRecensioni extends AsyncTask<Void, Void, ArrayList<RecensioneBean>> {

        @Override
        protected ArrayList<RecensioneBean> doInBackground(Void... voids) {
            ArrayList<RecensioneBean> recensioni= (ArrayList<RecensioneBean>) c.getRecensioni();
            return recensioni;
        }

        @Override
        protected void onPostExecute(ArrayList<RecensioneBean> recensioni) {

            ProgressBar bar= findViewById(R.id.progress_circular);
            bar.setVisibility(View.INVISIBLE);

            if (recensioni.size()>0) {
                for (RecensioneBean r : recensioni)
                    adapter.add(r);
                Log.d("MyDebug", "Recensioni trovate -->"+recensioni.toString());
            }
            else {
                Toast.makeText(getApplicationContext(), "Nessuna recensione trovata", Toast.LENGTH_LONG).show();
                Log.d("MyDebug", "nessuna recensione trovata");
            }

        }
    }

    private class GsonResultSegnalazione extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("debugProfilo", "doInBackground");
            RecensioneDAO recensioneDAO = new RecensioneDAO();
            recensioneBean = recensioneDAO.doRetrieveByIdRecensione(idRecensione);
            return null;
        }


        @Override
        protected void onPostExecute(Void unused) {

            s.setRecensione(recensioneBean);
            onClickAggiungiSegnalazione(s);
        }

        private RecensioneBean recensioneBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_dettagliata_contenuto);

        profiloButton= findViewById(R.id.profiloButton);
        homepageButton= findViewById(R.id.homepageButton);

        account = (Account) getIntent().getExtras().getSerializable("account");
        //c = (ContenutoBean) getIntent().getExtras().getSerializable("contenuto");

        Intent i = getIntent();
        int idContenuto= i.getIntExtra("idContenuto", -1);

        Log.d("MyDebug", "idContenutoCliccato -->"+idContenuto);

        GsonResultContenuto g= (GsonResultContenuto) new GsonResultContenuto().execute(idContenuto);
        ListView recensioniList= findViewById(R.id.recensioniList);

        adapter = new VisualizzazioneDettagliataContenutoAdapter(this, R.layout.activity_list_element_visualizzazione_dettagliata_contenuto,
                new ArrayList<RecensioneBean>());

        recensioniList.setAdapter(adapter);

        GsonResultRecensioni gr= (GsonResultRecensioni) new GsonResultRecensioni().execute(new Void[0]);

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
            Toast.makeText(getApplicationContext(), "Effettua il login per eseguire questa operazione", Toast.LENGTH_LONG).show();
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

    public void onClickAggiungiRecensione(View v){
        if(account.isIscritto() == Boolean.TRUE) {
            Intent i = new Intent();
            i.setClass(getApplicationContext(), PubblicazioneRecensioneActivity.class);
            i.putExtra("account", (Serializable) account);
            i.putExtra("contenuto", (Serializable) c);
            startActivity(i);
        }else {
            if(account.isIscritto() == Boolean.FALSE){
                Toast.makeText(getApplicationContext(), "Effettua il login come iscritto per effettuare questa operazione", Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }
    }

    public void onClickAggiungiContenutoAllaLista (View v){
        if(account.isIscritto() == Boolean.TRUE) {
            Intent i = new Intent();
            i.setClass(getApplicationContext(), AggiuntaContenutoListaActivity.class);
            i.putExtra("account", (Serializable) account);
            i.putExtra("contenuto", (Serializable) c);
            startActivity(i);
        }else {
            if(account.isIscritto() == Boolean.FALSE){
                Toast.makeText(getApplicationContext(), "Effettua il login come iscritto per effettuare questa operazione", Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }

        }
    }

    public void onClickAltreSegnalazioni(View v){
        s = new SegnalazioneBean();
        s.setTipo(0);
        s.setIscritto(account.getIscrittoBean());
        ImageButton altreSegnalazioniButton = (ImageButton) v;
        idRecensione = (int) altreSegnalazioniButton.getTag();

        GsonResultSegnalazione g = (GsonResultSegnalazione) new GsonResultSegnalazione().execute(new Void[0]);
    }

    public void onClickSpoilerAlert(View v){
        s = new SegnalazioneBean();
        s.setTipo(1);
        s.setIscritto(account.getIscrittoBean());
        Button spoilerAlertButton = (Button) v;
        idRecensione = (int) spoilerAlertButton.getTag();

        GsonResultSegnalazione g = (GsonResultSegnalazione) new GsonResultSegnalazione().execute(new Void[0]);
    }

    private void onClickAggiungiSegnalazione(SegnalazioneBean s){
        if(account.isIscritto() == Boolean.TRUE){
            Intent i = new Intent();
            i.setClass(getApplicationContext(), AggiuntaSegnalazioneRecensioneActivity.class);
            i.putExtra("segnalazione", s);
            i.putExtra("account", account);
            i.putExtra("contenuto", c);
            startActivity(i);
        }else{
            if(account.isIscritto() == Boolean.FALSE){
                Toast.makeText(getApplicationContext(), "Effettua il login come iscritto per effettuare questa operazione", Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }
    }

    private Account account;
    private int idRecensione;
    private SegnalazioneBean s;
}