package it.unisa.ilike.contenuti.application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.storage.ContenutoBean;

public class RicercaContenutoAdapter extends ArrayAdapter<ContenutoBean> {
    private LayoutInflater inflater;

    public RicercaContenutoAdapter (Context context, int resourceId, List<ContenutoBean> contenuti){
        super(context, resourceId, contenuti);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG","Inflating view");
            v = inflater.inflate(R.layout.activity_list_element_ricerca_contenuto, null);
        }

        ContenutoBean c = getItem(position);

        Log.d("DEBUG","contact c="+c);


        TextView nomeContenuto= v.findViewById(R.id.nomeContenuto);
        TextView valutazioneMedia= v.findViewById(R.id.valutazioneMedia);

        nomeContenuto.setText(c.getTitolo());
        valutazioneMedia.setText(String.valueOf(c.getValutazioneMedia()));

        nomeContenuto.setTag(position);
        valutazioneMedia.setTag(position);

        return v;
    }

}
