package com.example.informatica.xabiagame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.informatica.xabiagame.R;
import com.example.informatica.xabiagame.model.Misiones_Compl;

import java.util.List;

/**
 * Created by Informatica on 04/01/2017.
 */

public class MisionesCompletadasDefinitiva <T> extends ArrayAdapter<T> {

    public MisionesCompletadasDefinitiva(Context context, List<T> objects) {
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
            listItemView = inflater.inflate(R.layout.listitem_mision_completada, parent, false);
        }

        //Obteniendo instancias de los elementos
        //TextView vhIdMision = (TextView) listItemView.findViewById(R.id.listIdMisionCompletada);
        TextView vhNombreMision = (TextView) listItemView.findViewById(R.id.listTituloMisionCompletada);
        TextView vhDificultad = (TextView) listItemView.findViewById(R.id.listDificultadMisionCompletada);
        TextView vhTiempo = (TextView) listItemView.findViewById(R.id.listTiempoCompletado);
        //Obteniendo instancia del articulo en la posición actual
        Misiones_Compl item = (Misiones_Compl) getItem(position);

        //vhIdMision.setText(String.valueOf(item.getId()));
        vhNombreMision.setText(item.getTitulo());
        vhDificultad.setText(item.getNivel_dificultad());
        vhTiempo.setText(item.getTiempo_total());

        //Devolver al ListView la fila creada
        return listItemView;
    }
}