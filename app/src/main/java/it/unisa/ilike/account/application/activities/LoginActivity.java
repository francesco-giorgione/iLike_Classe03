package it.unisa.ilike.account.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.AccountService;
import it.unisa.ilike.account.application.exceptions.CredenzialiErrateException;
import it.unisa.ilike.account.application.exceptions.CredenzialiVuoteException;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;

public class LoginActivity extends AppCompatActivity {

    boolean checkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkLogin=false;
    }

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio
     * contenuto in AccountImpl. Questo è necessario in quanto il metodo in questione richiama metodi
     * delle classi IscrittoDAO e GestoreDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultLogin extends AsyncTask<String, Void, Account> {

        String messaggio= null;

        /**
         * Consente di recuperare un oggetto Account utilizzando il metodo di servizio login della
         * classe AccountImpl
         * @param string array di stringhe contenente email e password
         * @return l'account utente se l'operazione è andata a buon fine, null altrimenti
         */
        @Override
        protected Account doInBackground(String... string) {
            Log.d("debugLogin", "in doInBackground");

            AccountService accountService = new AccountImpl();
            try {
                return accountService.login(string[0], string[1]);
            } catch (CredenzialiVuoteException e) {
                //ritorno al login e messaggio
                checkLogin=false;
                messaggio="Credenziali vuote";
                return null;
            } catch (CredenzialiErrateException e) {
                //ritorno al login e messaggio
                checkLogin=false;
                messaggio="Credenziali errate";
                return null;
            }
        }

        public String getMessaggio(){
            while (this.messaggio==null);
            return messaggio;
        }

        @Override
        protected void onPostExecute(Account account) {
            Log.d("debugLogin", "in onPostExecute");
            if (checkLogin){
                Log.d("debugLogin", "loginOK");
                if(account.isIscritto() == Boolean.TRUE){
                    messaggio="Login iscritto ok";
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
                    i.putExtra("account", (Serializable) account);
                    startActivity(i);
                }
            }
            else{
                Log.d("debugLogin", "login not ok");
                messaggio="Login not ok";
            }
        }
    }


    public void onClickLogin(View view) {
        Log.d("debugLogin", "in onClickLogin");
        checkLogin=false;

        // prendi i campi email/nickname e password
        TextView username = findViewById(R.id.username);
        String email = String.valueOf(username.getText());
        TextView passwordText = findViewById(R.id.password);
        String password = String.valueOf(passwordText.getText());
        String[] s ={email, password};
        GsonResultLogin g= (GsonResultLogin) new GsonResultLogin().execute(s);
        while (g.getMessaggio()==null);
        Toast.makeText(this, g.getMessaggio(), Toast.LENGTH_LONG).show();
    }

    public void onClickRegistrazioneLogin(View view){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RegistrazioneIscrittoActivity.class);
        startActivity(i);
    }
}