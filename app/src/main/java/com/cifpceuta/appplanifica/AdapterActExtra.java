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
        View miView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea_layout,parent,false);
        return new AdapterActExtra.ViewHolder(miView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*holder.bindData(listItem.get(position));
        holder.miCardViewExtra.setCardBackgroundColor(Color.WHITE);

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fFin = LocalDate.parse(fechFin,formatoFecha);
        long diasPlazo = ChronoUnit.DAYS.between(LocalDate.now(), fFin);

        if (diasPlazo <= 3 && diasPlazo >= 0) {
            holder.miCardViewExtra.setCardBackgroundColor(ContextCompat.getColor(holder.miCardViewExtra.getContext(), R.color.verdeClaro));
        } else if (diasPlazo > 3) {
            holder.miCardViewExtra.setCardBackgroundColor(ContextCompat.getColor(holder.miCardViewExtra.getContext(), R.color.rojoClaro));
        } else {
            holder.miCardViewExtra.setCardBackgroundColor(ContextCompat.getColor(holder.miCardViewExtra.getContext(), R.color.azulClaro));
        }*/

        //holder.bindData(listItem.get(position));
        //holder.miCardViewExtra.setCardBackgroundColor(Color.WHITE);
        //holder.miCardViewExtra.setCardBackgroundColor(ContextCompat.getColor(holder.miCardViewExtra.getContext(), R.color.azulClaro));
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

        }
    }

    public void setList_item(ArrayList<ActExtra> list_item) {
        this.listItem = list_item;
        notifyDataSetChanged();
    }

}
