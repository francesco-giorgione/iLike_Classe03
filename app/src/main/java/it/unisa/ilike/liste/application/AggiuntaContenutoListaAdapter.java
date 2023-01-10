package it.unisa.ilike.liste.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import it.unisa.ilike.R;
import it.unisa.ilike.contenuti.storage.ContenutoBean;
import it.unisa.ilike.contenuti.storage.FilmBean;
import it.unisa.ilike.contenuti.storage.LibroBean;
import it.unisa.ilike.contenuti.storage.SerieTVBean;
import it.unisa.ilike.liste.storage.ListaBean;

public class AggiuntaContenutoListaAdapter extends ArrayAdapter<ListaBean> {



    private LayoutInflater inflater;

    public AggiuntaContenutoListaAdapter(@NonNull Context context, int resource, @NonNull List<ListaBean> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_element_aggiunta_contenuto_lista, null);
        }

        ListaBean l = getItem(position);

        RadioButton lista;

        lista = (RadioButton) view.findViewById(R.id.sceltaListaRadioButton);

        lista.setText(l.getNome());

        return view;

    }


}
