package com.cifpceuta.appplanifica;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class AdapterActExtra extends RecyclerView.Adapter<AdapterActExtra.ViewHolder>{
    private ArrayList<ActExtra> listItem;

    public AdapterActExtra(){}

    @NonNull
    @Override
    public AdapterActExtra.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View miView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_act_extra,parent,false);
        return new AdapterActExtra.ViewHolder(miView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listItem.get(position));

    }

    public AdapterActExtra(ArrayList<ActExtra> listaTareas) {
        this.listItem = listaTareas;
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView miCardViewExtra;
        TextView titulo, grupo, fecha;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            miCardViewExtra = itemView.findViewById(R.id.miCardViewExtra);
            titulo = itemView.findViewById(R.id.itemTituloExtra);
            grupo = itemView.findViewById(R.id.itemGrupoExtra);
            fecha = itemView.findViewById(R.id.itemFechaExtra);

            //miCardViewExtra.setCardBackgroundColor(Color.WHITE);
            //miCardViewExtra.setCardBackgroundColor(ContextCompat.getColor(miCardViewExtra.getContext(), R.color.azulClaro));
        }
        void bindData(ActExtra actExtra) {
            if(!listItem.isEmpty()){
                titulo.setText(actExtra.getTitulo());
                grupo.setText(actExtra.getGrupo());
                fecha.setText(actExtra.getFecha());
            }


        }
    }

    public void setList_item(ArrayList<ActExtra> list_item) {
        this.listItem = list_item;
        notifyDataSetChanged();
    }

}
