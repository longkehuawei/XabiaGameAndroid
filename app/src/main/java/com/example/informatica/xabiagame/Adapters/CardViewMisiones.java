package com.example.informatica.xabiagame.Adapters;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.informatica.xabiagame.DialogMostrarLvMisionSeleccionado;
import com.example.informatica.xabiagame.R;
import com.example.informatica.xabiagame.model.Mision;

import java.util.List;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by Informatica on 08/03/2017.
 */

public class CardViewMisiones extends RecyclerView.Adapter<CardViewMisiones.PersonViewHolder> {

    List<Mision> sellers;
    Context context;

    Bitmap bm;

    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;

    static int resID;

    public CardViewMisiones(Context context, List<Mision> sellers, String personName, String personGivenName, String personFamilyName, String personEmail, String personId)
    {
        this.sellers=sellers;
        this.context=context;
        this.personName=personName;
        this.personGivenName=personGivenName;
        this.personFamilyName=personFamilyName;
        this.personEmail=personEmail;
        this.personId=personId;
    }

    public void setGridData(List<Mision> mGridData)
    {
        this.sellers=mGridData;
        notifyDataSetChanged();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_mision, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {

        String bajo = "bajo";
        String medio = "medio";
        String avanzado = "avanzado";


        if(sellers.get(i).getNivelDificultad().equalsIgnoreCase(bajo)){
            String difverde = "difverde";

            int resID = context.getResources().getIdentifier(difverde , "drawable", context.getPackageName());
            bm = BitmapFactory.decodeResource(context.getResources(), resID);
            holder.ivImagenMision.setImageBitmap(bm);

        }else if(sellers.get(i).getNivelDificultad().equalsIgnoreCase(medio)){
            String difama = "difama";
            int resID = context.getResources().getIdentifier(difama , "drawable", context.getPackageName());
            bm = BitmapFactory.decodeResource(context.getResources(), resID);
            holder.ivImagenMision.setImageBitmap(bm);

        }else if(sellers.get(i).getNivelDificultad().equalsIgnoreCase(avanzado)){
            String difrojo = "difrojo";
            int resID = context.getResources().getIdentifier(difrojo , "drawable", context.getPackageName());
            bm = BitmapFactory.decodeResource(context.getResources(), resID);
            holder.ivImagenMision.setImageBitmap(bm);

        }

        holder.nombreMision.setText(String.valueOf(sellers.get(i).getTitulo()));
        holder.dificultadMision.setText(sellers.get(i).getNivelDificultad());

        holder.linearLayoutMision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String algo = "Algo malo ha pasado";
                //view.getContext().startActivity(inte);
                Bundle bundle = new Bundle();
                int tareaActual = sellers.get(i).getId();
                if (tareaActual != 0) {
                    Log.d(TAG, String.valueOf(tareaActual));

                    FragmentManager fragmentManager = ((AppCompatActivity) context).getFragmentManager();

                    bundle.putString("id", String.valueOf(tareaActual));
                    bundle.putString("personName", personName);
                    bundle.putString("personGivenName", personGivenName);
                    bundle.putString("personFamilyName", personFamilyName);
                    bundle.putString("personEmail", personEmail);
                    bundle.putString("personId", personId);

                    DialogFragment newFragment = new DialogMostrarLvMisionSeleccionado();
                    newFragment.setArguments(bundle);
                    newFragment.show(fragmentManager, "TAG");

                }
                else
                    Toast.makeText(context, algo, Toast.LENGTH_SHORT).show();
                /*
                String personName;
                String personGivenName;
                String personFamilyName;
                String personEmail;
                String personId;
                 */
                Log.i(CardViewMisiones.class.getName(), personName + " " + personGivenName + " " + personFamilyName + " " + personEmail + " " + personId);



            }
        });

        //holder.cardView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImagenMision;
        private TextView nombreMision;
        private TextView dificultadMision;
        private LinearLayout linearLayoutMision;

        CardView cardView;

        PersonViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardViewMisiones);

            ivImagenMision = (ImageView) itemView.findViewById(R.id.imageMisionDifi);
            nombreMision = (TextView) itemView.findViewById(R.id.listTituloMision);
            dificultadMision = (TextView) itemView.findViewById(R.id.listDificultadMision);
            linearLayoutMision = (LinearLayout) itemView.findViewById(R.id.llMision);

        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
