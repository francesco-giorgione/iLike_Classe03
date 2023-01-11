package it.unisa.ilike.account.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.ContenutoDAO;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.recensioni.storage.RecensioneBean;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class VisualizzazioneProfiloPersonaleRecensioniAdapter extends ArrayAdapter<RecensioneBean> {

    private LayoutInflater inflater;

    public VisualizzazioneProfiloPersonaleRecensioniAdapter(@NonNull Context context, int resource, @NonNull List<RecensioneBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_element_visualizzazione_profilo_personale_recensioni, null);
        }

        RecensioneBean r = getItem(position);

        ContenutoDAO contenutoDAO = new ContenutoDAO();
        ContenutoBean c = contenutoDAO.doRetrieveById(r.getContenuto().getId());

        ImageView iconaContenuto;
        TextView nomeContenuto;
        TextView testoRecensione;

        nomeContenuto = (TextView) view.findViewById(R.id.nomeContenuto);
        testoRecensione = (TextView) view.findViewById(R.id.testoRecensione);
        iconaContenuto = (ImageView) view.findViewById(R.id.iconaContenuto);

        if (c instanceof FilmBean)
            iconaContenuto.setImageDrawable(view.getResources().getDrawable(R.drawable.icona_film));
        else if (c instanceof SerieTVBean)
            iconaContenuto.setImageDrawable(view.getResources().getDrawable(R.drawable.icona_serietv));
        else if (c instanceof LibroBean)
            iconaContenuto.setImageDrawable(view.getResources().getDrawable(R.drawable.icona_libro));
        else
            iconaContenuto.setImageDrawable(view.getResources().getDrawable(R.drawable.icona_musica));

        nomeContenuto.setText(c.getTitolo());
        testoRecensione.setText(r.getTesto());

        nomeContenuto.setTag(position);
        testoRecensione.setTag(position);

        return view;
    }
}
