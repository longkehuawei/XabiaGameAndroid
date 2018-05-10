package com.example.informatica.xabiagame.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.informatica.xabiagame.R;
import com.example.informatica.xabiagame.model.Misiones_Compl;

import java.util.List;

/**
 * Created by Informatica on 01/03/2017.
 */

public class CardViewMisionesCompletadas extends RecyclerView.Adapter<CardViewMisionesCompletadas.PersonViewHolder> {

    List<Misiones_Compl> sellers;
    Context context;
    public CardViewMisionesCompletadas(Context context,List<Misiones_Compl> sellers)
    {
        this.sellers=sellers;
        this.context=context;
    }

    public void setGridData(List<Misiones_Compl> mGridData)
    {
        this.sellers=mGridData;
        notifyDataSetChanged();
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_mision_completada, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {
        holder.Seller.setText(sellers.get(i).getTitulo());
        holder.Address.setText(sellers.get(i).getNivel_dificultad());
        holder.Telp.setText(sellers.get(i).getTiempo_total());
    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView Seller;
        TextView Address;
        TextView Telp;

        PersonViewHolder(View itemView) {
            super(itemView);
            Seller = (TextView)itemView.findViewById(R.id.listTituloMisionCompletada);
            Address = (TextView)itemView.findViewById(R.id.listDificultadMisionCompletada);
            Telp = (TextView)itemView.findViewById(R.id.listTiempoCompletado);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
