package it.unisa.ilike.contenuti.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.Account;
import it.unisa.ilike.profili.presentation.VisualizzazioneProfiloPersonaleActivity;

public class VisualizzazioneHomepageActivity extends Activity {

    ImageButton profiloButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione_homepage);

        profiloButton= findViewById(R.id.profiloButton);

        //getApplicationContext();

        //inizio da login
        Intent i = getIntent();
        setReturnIntent();
        Account account = (Account) getIntent().getExtras().getSerializable("account");
        //fine da login
    }

    //inizio da login
    private void setReturnIntent() {
        Intent data = new Intent();
        setResult(RESULT_OK,data);
    }
    //fine da login

    public void onClickProfilo(View v){
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VisualizzazioneProfiloPersonaleActivity.class);
        startActivityForResult(i, 878);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 878) return;
        if (resultCode != Activity.RESULT_OK) return;
        if (data == null) return;
    }
}