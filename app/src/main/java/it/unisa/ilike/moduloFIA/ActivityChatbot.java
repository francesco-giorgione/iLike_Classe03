package it.unisa.ilike.moduloFIA;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.storage.ContenutoBean;

public class ActivityChatbot extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText messaggioDigitato;
    boolean isMine =true;
    private ArrayList<Messaggio> messaggi;
    private ArrayAdapter<Messaggio> adapter;
    private String utterances;
    private List<ContenutoBean> contenuti;
    public InputStream inputStream;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        messaggi =new ArrayList<>();
        listView =(ListView)findViewById(R.id.messaggiList);
        btnSend =findViewById(R.id.sendButton);
        messaggioDigitato =(EditText)findViewById(R.id.editTest_message);

        contenuti = (List<ContenutoBean>) getIntent().getExtras().getSerializable("contenuti");

        //List<ContenutoBean> contenutiList = new ArrayList<>(Arrays.asList(contenuti));

        for (ContenutoBean c: contenuti){
            Log.d("chatbot", c.toString());
        }

        //inputStream = getResources().openRawResource(R.raw.film_cluster_kmeans);


        adapter = new MessageListAdapter(this, R.layout.activity_messaggio_bot, messaggi);

        listView.setAdapter(adapter);


        //inizializzazione giorno chat
        GregorianCalendar g= new GregorianCalendar();
        int numGiorno= g.get(Calendar.DAY_OF_MONTH);
        int numGiornoSettimana= g.get(Calendar.DAY_OF_WEEK);
        String giornoSettimana="";
        switch (numGiornoSettimana){
            case 1: giornoSettimana="Domenica";break;
            case 2:  giornoSettimana="Lunedi";break;
            case 3:  giornoSettimana="Martedi";break;
            case 4:  giornoSettimana="Mercoledi";break;
            case 5:  giornoSettimana="Giovedi";break;
            case 6:  giornoSettimana="Venerdi";break;
            case 7:  giornoSettimana="Sabato";break;
        }

        String giorno=giornoSettimana+" "+numGiorno;

        TextView data= findViewById(R.id.textViewData);
        data.setText(giorno);


        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py= Python.getInstance();
        //nome del nostro file python
        final PyObject pyobj= py.getModule("trainingCA");


        String dateTime = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.now());
        //messaggio di benvenuto chatBot
        Messaggio messaggio = new Messaggio("Ciao, sono il chatbot di iLike. Come posso aiutarti?","iLike chatbot", dateTime,isMine);
        messaggi.add(messaggio);
        adapter.notifyDataSetChanged();
        messaggioDigitato.setText("");
        if (isMine) {
            isMine = false;
        } else {
            isMine = true;
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(messaggioDigitato.getText().toString().trim().equals("")){
                    Toast.makeText(ActivityChatbot.this,"Nessun testo inserito..",Toast.LENGTH_SHORT).show();
                }else {

                    //messaggio iscritto
                    String dateTime = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.now());
                    Messaggio messaggio = new Messaggio(messaggioDigitato.getText().toString(),null,dateTime,isMine);
                    utterances= messaggioDigitato.getText().toString();
                    messaggi.add(messaggio);
                    adapter.notifyDataSetChanged();
                    messaggioDigitato.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }



                    //risposta chatBot al messaggio


                    PyObject obj= pyobj.callAttr("main", utterances, contenuti); //PyObject obj= pyobj.callAttr(function name, first argument, second argument, ...);

                    String risposta;

                    if (obj==null)
                        risposta= "Non ho capito la domanda";
                    else if (obj.toString().equalsIgnoreCase("Certo! Scegli l'algoritmo che preferisci utilizzare.")) {
                        risposta = obj.toString();
                        risposta+="\n\nDigita:\nkms per k-means\ndbs per DBScan";
                    }
                    else
                        risposta=obj.toString();
                    dateTime = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.now());
                    Messaggio rispostaChatBot = new Messaggio(risposta,"iLike chatbot",dateTime,isMine);
                    messaggi.add(rispostaChatBot);
                    adapter.notifyDataSetChanged();
                    messaggioDigitato.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }
                }
            }
        });
    }

    public void onClickBack (View v){
        super.onBackPressed();
    }

}