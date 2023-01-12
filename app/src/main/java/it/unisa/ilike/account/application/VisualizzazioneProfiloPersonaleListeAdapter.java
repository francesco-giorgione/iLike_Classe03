package it.unisa.ilike.account.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.liste.storage.ListaBean;

public class VisualizzazioneProfiloPersonaleListeAdapter extends ArrayAdapter<ListaBean> {

    private LayoutInflater inflater;

    public VisualizzazioneProfiloPersonaleListeAdapter(Context context, int resource, List<ListaBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_element_visualizzazione_profilo_personale_liste, null);
        }

        ListaBean l = getItem(position);

        TextView nomeLista= view.findViewById(R.id.nomeLista);

        nomeLista.setText(l.getNome());

        nomeLista.setTag(position);

        ImageButton info= view.findViewById(R.id.infoButton);
        info.setTag(l.getNome());

        return view;
    }


}
