package it.unisa.ilike.liste.application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.liste.application.AggiuntaContenutoListaAdapter;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;

public class AggiuntaContenutoListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_contenuto_lista);

        Intent i = getIntent();
        setReturnIntent();


        ListView elencoListeIscritto= findViewById(R.id.elencoListeIscritto);
        AggiuntaContenutoListaAdapter adapter;
        adapter = new AggiuntaContenutoListaAdapter(this, R.layout.activity_list_element_aggiunta_contenuto_lista,
                new ArrayList<ListaBean>());
        elencoListeIscritto.setAdapter(adapter);

        Account account = (Account) i.getExtras().getSerializable("account");
        if (account!=null){
            IscrittoBean iscritto= account.getIscrittoBean();
            ArrayList<ListaBean> listeIscritto= (ArrayList<ListaBean>) iscritto.getListe();
            for (ListaBean l: listeIscritto){
                adapter.add(l);
            }
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