package it.unisa.ilike.contenuti.application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.account.storage.IscrittoBean;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
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
        ContenutoBean c= r.getContenuto();

        Log.d("DEBUG","recensione r="+r);

        ImageView icona= v.findViewById(R.id.icona);
        if (c instanceof FilmBean)
            icona.setImageDrawable(v.getResources().getDrawable(R.drawable.icona_film));
        else if (c instanceof SerieTVBean)
            icona.setImageDrawable(v.getResources().getDrawable(R.drawable.icona_serietv));
        else if (c instanceof LibroBean)
            icona.setImageDrawable(v.getResources().getDrawable(R.drawable.icona_libro));
        else
            icona.setImageDrawable(v.getResources().getDrawable(R.drawable.icona_musica));

        TextView nickname= v.findViewById(R.id.nickname);
        IscrittoBean i= r.getIscritto();
        nickname.setText(i.getNickname());

        TextView testoRecensione= v.findViewById(R.id.testoRecensione);
        testoRecensione.setText(r.getTesto());

        return v;
    }

}
