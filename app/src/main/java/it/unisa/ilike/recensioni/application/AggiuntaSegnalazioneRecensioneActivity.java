package it.unisa.ilike.recensioni.application;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.VisualizzazioneDettagliataContenutoActivity;
import it.unisa.ilike.recensioni.application.RecensioneBean;
import it.unisa.ilike.recensioni.application.RecensioneImpl;
import it.unisa.ilike.recensioni.application.RecensioneService;
import it.unisa.ilike.recensioni.application.exceptions.InvalidMotivazioneException;
import it.unisa.ilike.recensioni.application.exceptions.InvalidTipoException;
import it.unisa.ilike.recensioni.application.exceptions.MotivazioneVuotaException;
import it.unisa.ilike.utils.exceptions.NotIscrittoException;

public class AggiuntaSegnalazioneRecensioneActivity extends AppCompatActivity {


    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio aggiungiSegnalazione
     * contenuto in RecensioneService. Questo è necessario in quanto il metodo in questione richiama metodi
     * della classe SegnalazioneDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultValidate extends AsyncTask<String, Void, Boolean> {

        Boolean isValidate = true;

        /**
         * Consente di utilizzare il metodo aggiungiSegnalazione di RecensioneService e di memorizzarne
         * l'esito nella variabile di istanza isValidate;
         * @param string array di stringhe contenente il tipo di segnalazione e la motivazione della
         *               segnalazione
         * @return true se l'operazione è andata a buon fine, false altrimenti
         */
        @Override
        protected Boolean doInBackground(String... string) {
            RecensioneService recensioneService = new RecensioneImpl();
            RecensioneBean recensione = (RecensioneBean) getIntent().getExtras().getSerializable("recensione");
            Account account = (Account) getIntent().getExtras().getSerializable("account");
            int tipo= Integer.parseInt(string[0]);
            try {
                recensioneService.aggiungiSegnalazione(tipo, string[1], recensione, account.getIscrittoBean());
            } catch (NotIscrittoException e) {
                //messaggio errore
                isValidate = false;
                e.printStackTrace();
            } catch (InvalidTipoException e) {
                //messaggio errore
                isValidate = false;
                e.printStackTrace();
            } catch (MotivazioneVuotaException e) {
                //messaggio errore
                isValidate = false;
                e.printStackTrace();
            } catch (InvalidMotivazioneException e) {
                //messaggio errore
                isValidate = false;
                e.printStackTrace();
            }
            return isValidate;
        }

        /**
         * Restituisce il valore della variabile di istanza isValidate dopo che il metodo doInBackground
         * ha terminato la sua esecuzione
         * @return il valore della variabile d'istanza isValidate
         */
        public Boolean isValidate(){
            while (this.isValidate==null);
            return this.isValidate;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiunta_segnalazione_recensione);
    }

    public void onClickInviaSegnalazione(View view) {
        TextView tipoSegnalazioneTextView = findViewById(R.id.tipoSegnalazione);
        String tipoSegnalazione = (String) tipoSegnalazioneTextView.getText();
        TextView motivazioneSegnalazioneTextView = findViewById(R.id.motivazioneSegnalazione);
        String motivazioneSegnalazione = (String) motivazioneSegnalazioneTextView.getText();
        String[] s= new String[2];

        int tipo = 3;
        if (tipoSegnalazione.equalsIgnoreCase("Spoiler Alert"))
            tipo = 0;
        else if (tipoSegnalazione.equalsIgnoreCase("Altre Segnalazioni"))
            tipo = 1;

        s[0]=String.valueOf(tipo);
        s[1]=motivazioneSegnalazione;

        GsonResultValidate g= (GsonResultValidate) new GsonResultValidate().execute(s);
        boolean isValidate= g.isValidate();

        if(isValidate){
            RecensioneBean recensione = (RecensioneBean) getIntent().getExtras().getSerializable("recensione");
            Account account = (Account) getIntent().getExtras().getSerializable("account");
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
            i.putExtra("account", (Serializable) account);
            i.putExtra("recensione", (Serializable) recensione);
            startActivityForResult(i, 878);
        }//else in aggiungi segnalazione
    }
}