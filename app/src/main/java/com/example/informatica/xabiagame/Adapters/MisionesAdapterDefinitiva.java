package com.example.informatica.xabiagame.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.informatica.xabiagame.R;
import com.example.informatica.xabiagame.model.Mision;

import java.util.List;

/**
 * Created by Informatica on 20/12/2016.
 */

public class MisionesAdapterDefinitiva <T> extends ArrayAdapter<T> {

    public MisionesAdapterDefinitiva(Context context, List<T> objects) {
        super(context,0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Salvando la referencia del View de la fila
        View listItemView = convertView;
        //Comprobando si el View no existe
        if (null == convertView) {
            listItemView = inflater.inflate(R.layout.listitem_mision, parent, false);
        }

        //Obteniendo instancias de los elementos
        //TextView vhIdMision = (TextView) listItemView.findViewById(R.id.listIdMision);
        TextView vhNombreMision = (TextView) listItemView.findViewById(R.id.listTituloMision);
        TextView vhDificultad = (TextView) listItemView.findViewById(R.id.listDificultadMision);

        //Obteniendo instancia del articulo en la posición actual
        Mision item = (Mision) getItem(position);

        //vhIdMision.setText(String.valueOf(item.getId()));
        vhNombreMision.setText(item.getTitulo());
        vhDificultad.setText(item.getNivelDificultad());
        Log.i("TAG",item.info());

        //Devolver al ListView la fila creada
        return listItemView;
    }
}
