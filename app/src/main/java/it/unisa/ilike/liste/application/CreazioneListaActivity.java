package it.unisa.ilike.liste.application;

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
import it.unisa.ilike.contenuti.application.VisualizzazioneHomepageActivity;
import it.unisa.ilike.liste.application.exceptions.InvalidNomeException;
import it.unisa.ilike.liste.application.exceptions.ListaGiaEsistenteException;
import it.unisa.ilike.liste.application.exceptions.NomeVuotoException;
import it.unisa.ilike.profili.application.VisualizzazioneProfiloPersonaleActivity;
import it.unisa.ilike.utils.exceptions.NotIscrittoException;

public class CreazioneListaActivity extends AppCompatActivity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio
     * contenuto in ListaImpl. Questo è necessario in quanto il metodo in questione richiama metodi
     * della classe ListaDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultCreaLista extends AsyncTask<Object, Void, Boolean> {

        Boolean listaCreata;

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
                this.listaCreata= listaImpl.creaLista(i, nome, pubblica);
            } catch (NotIscrittoException e) {
                this.listaCreata=false;
            } catch (NomeVuotoException e) {
                this.listaCreata=false;
            } catch (InvalidNomeException e) {
                this.listaCreata=false;
            } catch (ListaGiaEsistenteException e) {
                this.listaCreata=false;
            }
            return listaCreata;
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
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        startActivity(i);
    }

    public void onClickHomepage(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
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
        }
        else if (visibilitaPrivate.isChecked()){
            pubblica=false;
        }
        else{
            //errore
        }


        Account account = (Account) getIntent().getExtras().getSerializable("account");
        IscrittoBean iscritto= account.getIscrittoBean();

        Object[] objects=new Object[3];
        objects[0]= iscritto;
        objects[1]= nomeLista;
        objects[2] = pubblica;

        GsonResultCreaLista g= (GsonResultCreaLista) new GsonResultCreaLista().execute(objects);

        if (g.isListaCreata()) {
            Toast.makeText(getApplicationContext(), "Lista creata", Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
            startActivity(i);
        }
        else
            Toast.makeText(getApplicationContext(), "Lista NON creata", Toast.LENGTH_LONG).show();
    }
}