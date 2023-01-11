package it.unisa.ilike.account.application.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    //private int SELECT_PICTURE = 200;

    private InputStream foto;
    //private ImageView IVPreviewImage;
    private Button BSelectImage;


    /**
     * Classe interna che consente di creare un nuovo thread per la chiamata al metodo di servizio
     * contenuto in AccountImpl. Questo è necessario in quanto il metodo in questione richiama metodi
     * delle classi IscrittoDAO e GestoreDAO. In Android non è consentito fare operazioni di accesso
     * alla rete nel main thread; dato che questa activity si trova nel main thread occorre creare
     * questa classe che estende <code>AsyncTask</code> per usufruire dei metodi di cui sopra.
     */
    private class GsonResultRegistrazione extends AsyncTask<String, Void, Account> {

        private boolean isValidate = true;
        private String messaggio = null;

        @Override
        protected Account doInBackground(String... string) {
            Log.d("debugRegistrazione", "in doInBackground");
            AccountImpl accountImpl = new AccountImpl();
            try {
                return accountImpl.registrazioneIscritto(string[0], string[1], string[2],
                        string[3], string[4], string[5]);
            } catch (EmailVuotaException e) {
                Log.d("debugRegistrazione", "in EmailVuotaException");
                this.isValidate = false;
                messaggio = "Email non presente";
                return null;
            } catch (PasswordVuotaException e) {
                Log.d("debugRegistrazione", "in PasswordVuotaException");
                this.isValidate = false;
                messaggio = "Password non presente";
                return null;
            } catch (DatiIscrittoVuotiException e) {
                Log.d("debugRegistrazione", "in DatiIscrittoVuotiException");
                this.isValidate = false;
                messaggio = "Dati iscritto non presenti";
                return null;
            }
        }

        @Override
        protected void onPostExecute(Account account) {
            Log.d("debugRegistrazione", "in onPostExecute");
            if (this.isValidate){
                Log.d("debugRegistrazione", "registrazione OK");
                if (account==null)
                    Log.d("debugRegistrazione", "account null");
                if(account.isAttore()){
                    Toast.makeText(RegistrazioneIscrittoActivity.this, "Registrazioen ok", Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.setClass(RegistrazioneIscrittoActivity.this, VisualizzazioneHomepageActivity.class);
                    i.putExtra("account", account);
                    startActivity(i);
                }
            }
            else{
                Log.d("debugLogin", "registrazione NOT OK");
                Toast.makeText(RegistrazioneIscrittoActivity.this, messaggio, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_iscritto);

        //foto e pulsante per selezionare la foto
        /*BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        //da vedere
        Uri uri = Uri.parse("app/src/main/res/drawable/icona_profilo.png");
        IVPreviewImage.setImageURI(uri);

        Intent i = getIntent();
        setReturnIntent();

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });*/
    }

    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }


    // this function is triggered when
    // the Select Image Button is clicked
    /*void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }*/

    public void onClickRegistrazioneIscritto(View v){

        EditText e = findViewById(R.id.email);
        String email = String.valueOf(e.getText());
        EditText n = findViewById(R.id.nickname);
        String nickname = String.valueOf(n.getText());
        EditText p = findViewById(R.id.password);
        String password = String.valueOf(p.getText());
        EditText rp = findViewById(R.id.repeatPassword);
        String repeatPassword = String.valueOf(rp.getText());
        EditText no = findViewById(R.id.nome);
        String nome = String.valueOf(no.getText());
        EditText c = findViewById(R.id.cognome);
        String cognome = String.valueOf(c.getText());
        EditText b = findViewById(R.id.bio);
        String bio = String.valueOf(b.getText());

        Log.d("debugRegistrazione", password+" "+repeatPassword);

        //gestione foto
        /*ImageView image = findViewById(R.id.IVPreviewImage);
        Uri uri = (Uri) image.getTag();
        try {
            foto = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }*/


        //controllo password corrispondenti
        if (!(password.equals(repeatPassword))){
            Toast.makeText(this, "Le password inserite non corrispondono", Toast.LENGTH_LONG).show();
        }
        else{
            String [] s= {email, password, nome, cognome, nickname, bio};
            GsonResultRegistrazione g= (GsonResultRegistrazione) new GsonResultRegistrazione().execute(s);
        }

    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
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


    }*/

}