package it.unisa.ilike.account.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    }


    private class GsonResultLogin extends AsyncTask<String, Void, Account> {

        String messaggio= null;


        @Override
        protected Account doInBackground(String... string) {
            Log.d("debugLogin", "in doInBackground");

            AccountService accountService = new AccountImpl();
            try {
                Log.d("debugLogin", "login ok doInBackground");
                return accountService.login(string[0], string[1]);
            } catch (CredenzialiVuoteException e) {
                //ritorno al login e messaggio
                Log.d("debugLogin", "CredenzialiVuoteException");
                checkLogin=false;
                messaggio="Formato credenziali non corretto";
                return null;
            } catch (CredenzialiErrateException e) {
                //ritorno al login e messaggio
                Log.d("debugLogin", "CredenzialiErrateException");
                checkLogin=false;
                messaggio="Credenziali errate";
                return null;
            }
        }


        @Override
        protected void onPostExecute(Account account) {
            Log.d("debugLogin", "in onPostExecute");
            if (checkLogin){
                Log.d("debugLogin", "loginOK");
                if(account.isAttore()){
                    messaggio="Login effettuata";
                    Toast.makeText(LoginActivity.this, messaggio, Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
                    i.putExtra("account", account);
                    startActivity(i);
                }
            }
            else{
                Log.d("debugLogin", "login not ok");
                Toast.makeText(LoginActivity.this, messaggio, Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onClickLogin(View view) {
        Log.d("debugLogin", "in onClickLogin");
        checkLogin=true;

        // prendi i campi email/nickname e password
        EditText username = findViewById(R.id.username);
        EditText passwordText = findViewById(R.id.password);

        String email = String.valueOf(username.getText());
        String password = String.valueOf(passwordText.getText());
        Log.d("debugLogin", "username--> "+email+"\npassword--> "+password);

        String[] s ={email, password};
        GsonResultLogin g= (GsonResultLogin) new GsonResultLogin().execute(s);
    }

    public void onClickRegistrazioneLogin(View view){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RegistrazioneIscrittoActivity.class);
        startActivity(i);
    }
}