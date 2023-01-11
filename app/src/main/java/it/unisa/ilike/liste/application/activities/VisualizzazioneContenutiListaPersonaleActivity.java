package it.unisa.ilike.liste.application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.LibroDAO;
import it.unisa.ilike.liste.application.VisualizzazioneContenutiListaPersonaleAdapter;
import it.unisa.ilike.liste.storage.ListaBean;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.liste.storage.ListaDAO;

public class VisualizzazioneContenutiListaPersonaleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_contenuti_lista_personale);
        Intent i= getIntent();


        String nomeLista= (String) i.getExtras().getString("lista");
        account = (Account) i.getExtras().getSerializable("account");
        IscrittoBean iscritto= account.getIscrittoBean();

        ListaDAO listaDAO = new ListaDAO();
        ListaBean lista = listaDAO.doRetrieveByKey(nomeLista, account.getIscrittoBean().getEmail());

        TextView tvNomeLista= findViewById(R.id.textViewLista);
        tvNomeLista.setText(lista.getNome());
        ListView contenutiList = findViewById(R.id.listaContenutiListaPersonale);

        VisualizzazioneContenutiListaPersonaleAdapter adapter = new VisualizzazioneContenutiListaPersonaleAdapter
                (this, R.layout.activity_list_element_ricerca_contenuto,
                new ArrayList<ContenutoBean>());

        contenutiList.setAdapter(adapter);

        ArrayList<ContenutoBean> contenutiLista= (ArrayList<ContenutoBean>) lista.getContenuti();

        for (ContenutoBean c: contenutiLista){
            adapter.add(c);
        }

    }

    public void onClickProfilo(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }
    private Account account;
}