package com.example.informatica.xabiagame.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.informatica.xabiagame.ActMostrarRutas;
import com.example.informatica.xabiagame.R;
import com.example.informatica.xabiagame.model.Ruta;

import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by Informatica on 06/03/2017.
 */

public class CardViewRutas extends RecyclerView.Adapter<CardViewRutas.PersonViewHolder> {

    List<Ruta> sellers;
    Context context;


    Bitmap bm;
    String fotoCreada = "foto";

    static int resID;
    public CardViewRutas(Context context, List<Ruta> sellers)
    {
        this.sellers=sellers;
        this.context=context;
    }

    public void setGridData(List<Ruta> mGridData)
    {
        this.sellers=mGridData;
        notifyDataSetChanged();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_ruta, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {
        

        //COMPROBACION DE IMAGEN
        String fotoCreada11 = "foto";
        fotoCreada = fotoCreada.concat(String.valueOf(sellers.get(i).getId()));

        int checkExistence = context.getResources().getIdentifier(fotoCreada, "drawable", context.getPackageName());
        if ( checkExistence != 0 ) {  // the resouce exists...
            int resID = context.getResources().getIdentifier(fotoCreada , "drawable", context.getPackageName());
            bm = BitmapFactory.decodeResource(context.getResources(), resID);
            holder.ivImagenRuta.setImageBitmap(bm);
            fotoCreada = "foto";
        }
        else {  // checkExistence == 0  // the resouce does NOT exist!!
            String noAvailable = "no_image_available";
            int resIDADA = context.getResources().getIdentifier(noAvailable , "drawable", context.getPackageName());
            bm = BitmapFactory.decodeResource(context.getResources(), resIDADA);
            holder.ivImagenRuta.setImageBitmap(bm);
            fotoCreada = "foto";
        }


        holder.nombreRuta.setText(sellers.get(i).getNombre());
        holder.kmRuta.setText(String.valueOf(sellers.get(i).getDistancia()));
        holder.dificultadRuta.setText(sellers.get(i).getNivel_dificultad());
        holder.tiempoTotalRuta.setText(sellers.get(i).getTiempo_efectivo());
        holder.desnivelRuta.setText(String.valueOf(sellers.get(i).getDesnivel_acumulado_ascenso()));

        holder.linearLayoutRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String algo = "Algo malo ha pasado";
                //view.getContext().startActivity(inte);
                int tareaActual = sellers.get(i).getId();
                if (tareaActual != 0) {
                    Intent intent3 = new Intent(context, ActMostrarRutas.class);
                    intent3.putExtra("id", String.valueOf(tareaActual));
                    v.getContext().startActivity(intent3);
                    Log.d(TAG, String.valueOf(tareaActual));
                }
                else
                    Toast.makeText(context, algo, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagenRuta;
        TextView nombreRuta;
        TextView kmRuta;
        TextView dificultadRuta;
        TextView tiempoTotalRuta;
        TextView desnivelRuta;
        LinearLayout linearLayoutRutas;

        PersonViewHolder(View itemView) {
            super(itemView);

            ivImagenRuta = (ImageView) itemView.findViewById(R.id.imageRuta);
            nombreRuta = (TextView) itemView.findViewById(R.id.listNombre);
            kmRuta = (TextView) itemView.findViewById(R.id.listKm);
            dificultadRuta = (TextView) itemView.findViewById(R.id.listDificultad);
            tiempoTotalRuta = (TextView) itemView.findViewById(R.id.listTiempo);
            desnivelRuta = (TextView) itemView.findViewById(R.id.listDesnivel);
            linearLayoutRutas = (LinearLayout) itemView.findViewById(R.id.llRuta);

        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}