package it.unisa.ilike.account.application.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import it.unisa.ilike.R;
import it.unisa.ilike.account.application.AccountImpl;
import it.unisa.ilike.account.application.exceptions.DatiIscrittoVuotiException;
import it.unisa.ilike.account.application.exceptions.EmailVuotaException;
import it.unisa.ilike.account.application.exceptions.PasswordVuotaException;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneHomepageActivity;

public class RegistrazioneIscrittoActivity extends AppCompatActivity {

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    InputStream foto = null;

    ImageView IVPreviewImage;
    Button BSelectImage;

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
                        string[3], string[4], string[5], foto);
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

        //foto e pulsante per selezionare la foto
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        //da vedere
        Uri uri = Uri.parse("app/src/main/res/drawable/icona_profilo.png");
        IVPreviewImage.setImageURI(uri);


        //inizio da login
        Intent i = getIntent();
        setReturnIntent();
        //fine da login

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    //inizio da login
    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }
    //fine da login


    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onClickRegistrazioneIscritto(View v, Intent intent) throws EmailVuotaException, PasswordVuotaException, DatiIscrittoVuotiException {

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

        //gestione foto

        ImageView image = findViewById(R.id.IVPreviewImage);
        Uri uri = (Uri) image.getTag();

        InputStream inputStream = null;

        try {
            inputStream = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }



        //controlli eccezioni

        if (!(password.equals(repeatPassword)));
            //errore

        String [] s= {email, password, nome, cognome, nickname, bio};
        GsonResultRegistrazione g= (GsonResultRegistrazione) new GsonResultRegistrazione().execute(s);
        Account account= g.getAccount();


        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneHomepageActivity.class);
        startActivity(i);
        //se la registrazione va a buon fine
        Toast.makeText(this, "Registrazione effettuata", Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();

                IVPreviewImage.setTag(selectedImageUri);

                Bitmap bitmap = null;
                InputStream inputStream = null;

                try {
                    inputStream = getContentResolver().openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                bitmap = BitmapFactory.decodeStream(bufferedInputStream);


                if (bitmap != null) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageBitmap(bitmap);

                }
            }
        }


    }

}