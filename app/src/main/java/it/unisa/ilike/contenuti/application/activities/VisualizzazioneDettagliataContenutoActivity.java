package it.unisa.ilike.contenuti.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.application.ContenutoImpl;
import it.unisa.ilike.contenuti.application.ContenutoService;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.liste.application.activities.AggiuntaContenutoListaActivity;
import it.unisa.ilike.profili.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.recensioni.application.activities.PubblicazioneRecensioneActivity;

public class VisualizzazioneDettagliataContenutoActivity extends AppCompatActivity {

    ImageButton profiloButton;
    ImageButton homepageButton;
    ContenutoBean c;

    private class GsonResultContenuti extends AsyncTask<Integer, Void, Void> {

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_dettagliata_contenuto);

        profiloButton= findViewById(R.id.profiloButton);
        homepageButton= findViewById(R.id.homepageButton);

        Intent i = getIntent();
        int idContenuto= i.getIntExtra("idContenuto", -1);
        Log.d("MyDebug", "idContenutoCliccato -->"+idContenuto);

        GsonResultContenuti g= (GsonResultContenuti) new GsonResultContenuti().execute(idContenuto);
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

    public void onClickAggiungiRecensione(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), PubblicazioneRecensioneActivity.class);
        startActivity(i);
    }

    public void onClickAggiungiContenutoAllaLista (View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AggiuntaContenutoListaActivity.class);
        startActivity(i);
    }

}