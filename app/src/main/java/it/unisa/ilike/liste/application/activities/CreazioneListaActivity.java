package it.unisa.ilike.liste.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;
import it.unisa.ilike.liste.application.ListaImpl;
import it.unisa.ilike.liste.application.exceptions.InvalidNomeException;
import it.unisa.ilike.liste.application.exceptions.ListaGiaEsistenteException;
import it.unisa.ilike.liste.application.exceptions.NomeVuotoException;
import it.unisa.ilike.account.application.activities.VisualizzazioneProfiloPersonaleActivity;

public class CreazioneListaActivity extends AppCompatActivity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio
     * contenuto in ListaImpl. Questo è necessario in quanto il metodo in questione richiama metodi
     * della classe ListaDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultCreaLista extends AsyncTask<Object, Void, Boolean> {

        Boolean listaCreata = true;

        /**
         * Consente di utilizzare il metodo di servizio creaLista della classe ListaImpl e di
         * memorizzarne l'esito in una variabile d'istanza listaCreata.
         * @param objects array di oggetti contenente un iscrittoBean, il nome della lista da
         *                creare e un boolean che indica se la lista da creare deve essere pubblica
         *                o privata.
         * @return true se è andata a buon fine, false altrimenti.
         */
        @Override
        protected Boolean doInBackground(Object... objects) {
            ListaImpl listaImpl = new ListaImpl();
            IscrittoBean i= (IscrittoBean) objects[0];
            String nome= (String) objects[1];
            boolean pubblica= (boolean) objects[2];
            try {
                i = listaImpl.creaLista(i, nome, pubblica);
            } catch (NomeVuotoException e) {
                this.listaCreata=false;
            } catch (InvalidNomeException e) {
                this.listaCreata=false;
            } catch (ListaGiaEsistenteException e) {
                this.listaCreata=false;
            }
            if(i != null && this.listaCreata)
                account.setIscrittoBean(i);
            return listaCreata;
        }


        protected void onPostExecute(Boolean check) {
            if (check) {
                Toast.makeText(CreazioneListaActivity.this, "Lista creata", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setClass(CreazioneListaActivity.this, VisualizzazioneProfiloPersonaleActivity.class);
                i.putExtra("account", account);
                startActivity(i);
            }
            else
                Toast.makeText(CreazioneListaActivity.this, "Lista NON creata", Toast.LENGTH_LONG).show();
        }


        /**
         * Metodo che restituisce un boolean listaCreata memorizzato nella classe come variabile d'istanza.
         * @return il valore della variabile d'istanza listaCreata.
         */
        public Boolean isListaCreata(){
            while (this.listaCreata==null);
            return this.listaCreata;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creazione_lista);
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
        i.setClass(CreazioneListaActivity.this, VisualizzazioneProfiloPersonaleActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(CreazioneListaActivity.this, VisualizzazioneHomepageActivity.class);
        i.putExtra("account", account);
        startActivity(i);
    }

    //da finire
    public void onClickCreaLista(View v){
        TextView nomeLista= findViewById(R.id.nomeLista);
        RadioButton visibilitaPublic= findViewById(R.id.visibilitaPublic);
        RadioButton visibilitaPrivate= findViewById(R.id.visibilitaPrivate);

        Boolean pubblica = null;
        if (visibilitaPublic.isChecked()){
            pubblica=true;
        }else if (visibilitaPrivate.isChecked()){
            pubblica=false;
        }


        if(pubblica == null){
            Toast.makeText(getApplicationContext(), "Inserire la visibilità della lista", Toast.LENGTH_LONG).show();
        }else {
            account = (Account) getIntent().getExtras().getSerializable("account");
            IscrittoBean iscritto= account.getIscrittoBean();
            Object[] objects=new Object[3];
            objects[0]= iscritto;
            objects[1]= String.valueOf(nomeLista.getText());
            objects[2] = pubblica;
            GsonResultCreaLista g= (GsonResultCreaLista) new GsonResultCreaLista().execute(objects);
        }
    }

    private Account account;
}