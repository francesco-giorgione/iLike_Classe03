package it.unisa.ilike.liste.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.liste.application.AggiuntaContenutoListaAdapter;
import it.unisa.ilike.liste.storage.ListaBean;

public class AggiuntaContenutoListaActivity extends AppCompatActivity {

    private class GsonResultCreaLista extends AsyncTask<Void, Void, ArrayList<ListaBean>> {

        private boolean checkAccount= true;

        @Override
        protected ArrayList<ListaBean> doInBackground(Void... objects) {
            if (account!=null){
                IscrittoBean iscritto= account.getIscrittoBean();
                return (ArrayList<ListaBean>) iscritto.getListe();
            }
            else{
                checkAccount=false;
                return null;
            }
        }


        protected void onPostExecute(ArrayList<ListaBean> listeIscritto) {

            ProgressBar bar= findViewById(R.id.progress_circular);
            bar.setVisibility(View.INVISIBLE);

            TextView t= findViewById(R.id.textView);
            t.setText("Scegli la lista a cui aggiungere il contenuto");

            if (listeIscritto!=null)
                for (ListaBean l: listeIscritto)
                    adapter.add(l);
            else
                if(!checkAccount)
                    Toast.makeText(AggiuntaContenutoListaActivity.this, "Effettua il login per eseguire questa operazione", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AggiuntaContenutoListaActivity.this, "Crea una lista nel tuo profilo prima di eseguire questa operazione", Toast.LENGTH_LONG).show();
        }

    }

    private Account account;
    private ContenutoBean contenuto;
    private AggiuntaContenutoListaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_contenuto_lista);

        Intent i = getIntent();
        setReturnIntent();


        ListView elencoListeIscritto= findViewById(R.id.elencoListeIscritto);

        adapter = new AggiuntaContenutoListaAdapter(this, R.layout.activity_list_element_aggiunta_contenuto_lista,
                new ArrayList<ListaBean>());
        elencoListeIscritto.setAdapter(adapter);

        account = (Account) i.getExtras().getSerializable("account");
        contenuto= (ContenutoBean) i.getExtras().getSerializable("contenuto");

        if (account!=null){
            GsonResultCreaLista g= (GsonResultCreaLista) new GsonResultCreaLista().execute(new Void[0]);
        }
        else{
            Toast.makeText(getApplicationContext(), "Effettua il login per eseguire questa operazione", Toast.LENGTH_LONG).show();
        }

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
}