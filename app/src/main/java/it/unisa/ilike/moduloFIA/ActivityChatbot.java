package it.unisa.ilike.moduloFIA;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import it.unisa.ilike.R;

public class ActivityChatbot extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText messaggioDigitato;
    boolean isMine =true;
    private ArrayList<Messaggio> messaggi;
    private ArrayAdapter<Messaggio> adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        messaggi =new ArrayList<>();
        listView =(ListView)findViewById(R.id.messaggiList);
        btnSend =findViewById(R.id.sendButton);
        messaggioDigitato =(EditText)findViewById(R.id.editTest_message);

        adapter = new MessageListAdapter(ActivityChatbot.this, R.layout.activity_messaggio_bot, messaggi);

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
                    String dateTime = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.now());

                    Messaggio messaggio = new Messaggio(messaggioDigitato.getText().toString(),null,dateTime,isMine);
                    messaggi.add(messaggio);
                    adapter.notifyDataSetChanged();
                    messaggioDigitato.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }

                    //risposta chatBot al messaggio
                    dateTime = DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.now());
                    Messaggio rispostaChatBot = new Messaggio("Risposta chatbot","iLike chatbot",dateTime,isMine);
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
}