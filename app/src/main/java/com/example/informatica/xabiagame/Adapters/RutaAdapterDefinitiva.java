package com.example.informatica.xabiagame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.informatica.xabiagame.R;
import com.example.informatica.xabiagame.model.Ruta;

import java.util.List;

/**
 * Created by Informatica on 13/12/2016.
 */

public class RutaAdapterDefinitiva<T> extends ArrayAdapter<T> {

    public RutaAdapterDefinitiva(Context context, List<T> objects) {
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
            listItemView = inflater.inflate(R.layout.listitem_ruta, parent, false);
        }

        /*
         private int id;
        private String nombre;
        private String descripcion;
        private double distancia;
        private String tiempo_efectivo;
        private String nivel_dificultad;
        private int desnivel_acumulado_ascenso;
         private int id_admin;
         */
        //Obteniendo instancias de los elementos
        TextView vhNombreRuta = (TextView) listItemView.findViewById(R.id.listNombre);
        TextView vhKm = (TextView) listItemView.findViewById(R.id.listKm);
        TextView vhDificultad = (TextView) listItemView.findViewById(R.id.listDificultad);
        TextView vhTiempoTotal = (TextView) listItemView.findViewById(R.id.listTiempo);
        TextView vhDesnivel = (TextView) listItemView.findViewById(R.id.listDesnivel);


        //Obteniendo instancia del articulo en la posición actual
        Ruta item = (Ruta) getItem(position);

        vhNombreRuta.setText(item.getNombre());
        vhKm.setText(String.valueOf(item.getDistancia()));
        vhDificultad.setText(item.getNivel_dificultad());
        vhTiempoTotal.setText(item.getTiempo_efectivo());
        vhDesnivel.setText(String.valueOf(item.getDesnivel_acumulado_ascenso()));


        //Devolver al ListView la fila creada
        return listItemView;
    }



}
