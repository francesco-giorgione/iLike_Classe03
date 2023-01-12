package it.unisa.ilike.contenuti.application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;

public class VisualizzazioneDettagliataContenutoAdapter extends ArrayAdapter<RecensioneBean> {
    private LayoutInflater inflater;

    public VisualizzazioneDettagliataContenutoAdapter(Context context, int resourceId, List<RecensioneBean> recensioni){
        super(context, resourceId, recensioni);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG","Inflating view");
            v = inflater.inflate(R.layout.activity_list_element_visualizzazione_dettagliata_contenuto, null);
        }

        RecensioneBean r = getItem(position);



        TextView nickname= v.findViewById(R.id.nickname);
        IscrittoBean i= r.getIscritto();
        nickname.setText(i.getNickname());

        TextView testoRecensione= v.findViewById(R.id.testoRecensione);
        testoRecensione.setText(r.getTesto());

        RatingBar bar= v.findViewById(R.id.valutazioneRecensione);
        Log.d("valutazioneDebug",""+r.getValutazione());
        bar.setRating(r.getValutazione());

        return v;
    }

}
