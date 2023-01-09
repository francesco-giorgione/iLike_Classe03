package it.unisa.ilike.account.application;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.exceptions.CredenzialiErrateException;
import it.unisa.ilike.account.application.exceptions.CredenzialiVuoteException;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.VisualizzazioneHomepageActivity;

public class LoginActivity extends AppCompatActivity {

    boolean checkLogin=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio
     * contenuto in AccountImpl. Questo è necessario in quanto il metodo in questione richiama metodi
     * delle classi IscrittoDAO e GestoreDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultLogin extends AsyncTask<String, Void, Account> {

        Account account;

        /**
         * Consente di recuperare un oggetto Account utilizzando il metodo di servizio login della
         * classe AccountImpl
         * @param string array di stringhe contenente email e password
         * @return l'account utente se l'operazione è andata a buon fine, null altrimenti
         */
        @Override
        protected Account doInBackground(String... string) {
            AccountImpl accountImpl = new AccountImpl();
            try {
                this.account= accountImpl.login(string[0], string[1]);
                return account;
            } catch (CredenzialiVuoteException e) {
                //ritorno al login e messaggio
                checkLogin=false;
                return null;
            } catch (CredenzialiErrateException e) {
                //ritorno al login e messaggio
                checkLogin=false;
                return null;
            }
        }

        /**
         * Metodo che restituisce l'account utente memorizzato nella classe come variabile d'istanza
         * @return il valore della variabile d'istanza account
         */
        public Account getAccount(){
            while (this.account==null);
            return this.account;
        }
    }


    public void onClickLogin(View view) {

        // prendi i campi email/nickname e password
        TextView username = findViewById(R.id.username);
        String email = (String) username.getText();
        TextView passwordText = findViewById(R.id.password);
        String password = (String) passwordText.getText();
        AccountService accountService = new AccountImpl();
        Account account = null;
        String[] s ={email, password};
        GsonResultLogin g= (GsonResultLogin) new GsonResultLogin().execute(s);
        account= g.getAccount();

        if (checkLogin){
            if(account.isIscritto()){
                Intent i = new Intent();
                i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
                i.putExtra("account", (Serializable) account);
                startActivityForResult(i, 878);
            }
            else
                if(account.isIscritto() != null){
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
                    i.putExtra("account", (Serializable) account);
                    startActivityForResult(i, 878);
                }
        }
    }

    public void onClickRegistrazioneLogin(View view){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RegistrazioneIscrittoActivity.class);
        startActivityForResult(i, 878);
    }
}