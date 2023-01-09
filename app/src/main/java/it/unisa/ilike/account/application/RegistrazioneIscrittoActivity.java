package it.unisa.ilike.account.application;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Blob;
import java.sql.SQLException;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.exceptions.DatiIscrittoVuotiException;
import it.unisa.ilike.account.application.exceptions.EmailVuotaException;
import it.unisa.ilike.account.application.exceptions.PasswordVuotaException;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.VisualizzazioneHomepageActivity;

public class RegistrazioneIscrittoActivity extends AppCompatActivity {

    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio
     * contenuto in AccountImpl. Questo è necessario in quanto il metodo in questione richiama metodi
     * delle classi IscrittoDAO e GestoreDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultRegistrazione extends AsyncTask<String, Void, Account> {

        Account account;

        /**
         * Consente di recuperare un oggetto Account utilizzando il metodo di servizio registrazioneIscritto
         * della classe AccountImpl
         * @param string array di stringhe contenente email, password, nome, cognome, nickname, bio, foto
         * @return l'account utente se l'operazione è andata a buon fine, null altrimenti
         */
        @Override
        protected Account doInBackground(String... string) {
            AccountImpl accountImpl = new AccountImpl();
            try {
                this.account= accountImpl.registrazioneIscritto(string[0], string[1], string[2],
                        string[3], string[4], string[5], string[6]);
            } catch (EmailVuotaException e) {
                return null;
            } catch (PasswordVuotaException e) {
                return null;
            } catch (DatiIscrittoVuotiException e) {
                return null;
            }
            return account;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_iscritto);

        //inizio da login
        Intent i = getIntent();
        setReturnIntent();
        //fine da login
    }

    //inizio da login
    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }
    //fine da login

    public void onClickRegistrazioneIscritto(View v) throws EmailVuotaException, PasswordVuotaException, DatiIscrittoVuotiException {

        EditText e = findViewById(R.id.email);
        String email = e.toString();
        EditText n = findViewById(R.id.nickname);
        String nickname = n.toString();
        EditText p = findViewById(R.id.password);
        String password = p.toString();
        EditText rp = findViewById(R.id.repeatPassword);
        String repeatPassword = p.toString();
        EditText no = findViewById(R.id.nome);
        String nome = no.toString();
        EditText c = findViewById(R.id.cognome);
        String cognome = c.toString();
        EditText b = findViewById(R.id.bio);
        String bio = b.toString();

        //aggiungere foto
        Blob fotoBlob= null;

        //conversione da blob a stringa
        byte[] bdata = new byte[0];
        try {
            bdata = fotoBlob.getBytes(1, (int) fotoBlob.length());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String foto = new String(bdata);

        //controlli eccezioni

        if (!(password.equals(repeatPassword)));
            //errore

        String [] s= {email, password, nome, cognome, nickname, bio, foto};
        GsonResultRegistrazione g= (GsonResultRegistrazione) new GsonResultRegistrazione().execute(s);
        Account account= g.getAccount();


        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        startActivity(i);
        //se la registrazione va a buon fine
        Toast.makeText(this, "Registrazione effettuata", Toast.LENGTH_SHORT).show();
    }

}