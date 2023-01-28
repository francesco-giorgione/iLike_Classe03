package it.unisa.ilike.moduloFIA;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.application.activities.VisualizzazioneDettagliataContenutoActivity;

public class MessageListAdapter extends ArrayAdapter<Messaggio> {

    private Activity activity;
    private ArrayList<Messaggio> messaggio;

    public MessageListAdapter(Activity context, int resource, ArrayList<Messaggio> objects) {
        super(context, resource, objects);
        this.activity=context;
        this.messaggio=objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        int layoutResource = 0;

        Messaggio messaggio = getItem(position);
        int viewType = getItemViewType(position);

        //occorre stabilire se l'ultimo messaggio è stato inviato dal bot o dall'utente
        if (messaggio.isMine()) {
            layoutResource = R.layout.activity_messaggio_bot;
        } else {
            layoutResource = R.layout.activity_messaggio_iscritto;
        }
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        
        //inizializzo il messaggio
        holder.msg.setText(messaggio.getTesto());
        holder.ora.setText(messaggio.getOra());
        if (messaggio.getAccount()!=null && messaggio.getId()>0) {
            holder.info.setVisibility(View.VISIBLE);
            holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(activity.getApplicationContext(), VisualizzazioneDettagliataContenutoActivity.class);
                    i.putExtra("idContenuto", messaggio.getId());
                    i.putExtra("account", (Serializable) messaggio.getAccount());
                    activity.startActivity(i);
                }
            });
        }
        else
            holder.info.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // restituisce un valore tra 0 e 1
        return position % 2;
    }

    private class ViewHolder {
        private TextView msg;
        private TextView ora;
        private ImageView info;
        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.textView_messaggio);
            ora= (TextView) v.findViewById(R.id.textView_orario);
            info= (ImageView) v.findViewById(R.id.buttonInfoFilm);
        }

    }

}
