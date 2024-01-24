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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Tarea> listItem;
    private String fechInicio,fechFin;

    public ItemAdapter(){}

    public ItemAdapter(ArrayList<Tarea> listaTareas) {
        this.listItem = listaTareas;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View miView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea_layout,parent,false);
        return new ItemAdapter.ViewHolder(miView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listItem.get(position));
        holder.miCardView.setCardBackgroundColor(Color.WHITE);

        /*DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fInicio = LocalDate.parse(fechInicio,formatoFecha);
        LocalDate fFin = LocalDate.parse(fechFin,formatoFecha);
        long diasPlazo = ChronoUnit.DAYS.between(fInicio, fFin);*/

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fFin = LocalDate.parse(fechFin,formatoFecha);
        long diasPlazo = ChronoUnit.DAYS.between(LocalDate.now(), fFin);

        if (diasPlazo <= 3 && diasPlazo >= 0) {
            holder.miCardView.setCardBackgroundColor(ContextCompat.getColor(holder.miCardView.getContext(), R.color.verdeClaro));
        } else if (diasPlazo > 3) {
            holder.miCardView.setCardBackgroundColor(ContextCompat.getColor(holder.miCardView.getContext(), R.color.rojoClaro));
        } else {
            holder.miCardView.setCardBackgroundColor(ContextCompat.getColor(holder.miCardView.getContext(), R.color.azulClaro));
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView miCardView;
        TextView curso, modulo, fechaInicio, fechaFin, descripcion;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            miCardView = itemView.findViewById(R.id.miCardView);
            curso = itemView.findViewById(R.id.itemCurso);
            modulo = itemView.findViewById(R.id.itemModulo);
            descripcion = itemView.findViewById(R.id.itemTitulo);
            fechaInicio = itemView.findViewById(R.id.itemFechaInicio);
            fechaFin = itemView.findViewById(R.id.itemFechaFin);
        }
        void bindData(Tarea tarea) {
            fechInicio = tarea.getFechaInicio();
            fechFin = tarea.getFechaFin();

            fechaInicio.setText(fechInicio);
            fechaFin.setText(fechFin);
            descripcion.setText(tarea.getDescripcionTarea());
            curso.setText(tarea.getCurso());
            modulo.setText(tarea.getModulo());
        }
    }

    public void setList_item(ArrayList<Tarea> list_item) {
        this.listItem = list_item;
        notifyDataSetChanged();
    }

    public void limpiar(){
        notifyDataSetChanged();
    }
}