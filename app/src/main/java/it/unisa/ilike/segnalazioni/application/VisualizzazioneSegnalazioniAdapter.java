package it.unisa.ilike.segnalazioni.application;

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
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.liste.application.VisualizzazioneContenutiListaPersonaleAdapter;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class VisualizzazioneSegnalazioniAdapter extends ArrayAdapter<SegnalazioneBean> {

    private LayoutInflater inflater;

    public VisualizzazioneSegnalazioniAdapter(@NonNull Context context, int resource, @NonNull List<SegnalazioneBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_element_visualizzazione_segnalazioni, null);
        }

        SegnalazioneBean s = getItem(position);


        TextView nicknameIscritto;
        TextView tipoSegnalazione;

        nicknameIscritto = (TextView) view.findViewById(R.id.nickname);
        tipoSegnalazione = (TextView) view.findViewById(R.id.tipologia);

        nicknameIscritto.setText(s.getIscritto().getNickname());
        if(s.getTipo() == 1){
            tipoSegnalazione.setText("spoiler alert");
        }else {
            tipoSegnalazione.setText("altre segnalazioni");
        }

        nicknameIscritto.setTag(position);
        tipoSegnalazione.setTag(position);

        return view;
    }

}
