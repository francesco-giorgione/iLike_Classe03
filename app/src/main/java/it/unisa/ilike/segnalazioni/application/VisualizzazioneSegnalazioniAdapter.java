package it.unisa.ilike.segnalazioni.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.segnalazioni.storage.SegnalazioneBean;

public class VisualizzazioneSegnalazioniAdapter extends ArrayAdapter<SegnalazioneBean> {

    private LayoutInflater inflater;

    public VisualizzazioneSegnalazioniAdapter(Context context, int resource, List<SegnalazioneBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

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

        Button buttonInfo = (Button) view.findViewById(R.id.buttonInfo);
        buttonInfo.setTag(String.valueOf(s.getId()));

        return view;
    }

}
