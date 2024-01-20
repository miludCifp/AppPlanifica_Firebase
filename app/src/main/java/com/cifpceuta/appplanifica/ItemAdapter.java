package com.cifpceuta.appplanifica;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Tarea> listItem;
    private int par;

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

        // holder.bindData(list_item.get(position));


        holder.miCardView.setCardBackgroundColor(Color.WHITE);
        if (par == 2){
            if (position % 2 == 0){
                holder.miCardView.setCardBackgroundColor(Color.GRAY);
            }
        }else if (par == 1){
            if(position % 2 != 0){
                holder.miCardView.setCardBackgroundColor(Color.GREEN);
            }
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
            descripcion = itemView.findViewById(R.id.itemDescripcion);
            fechaInicio = itemView.findViewById(R.id.itemFechaInicio);
            fechaFin = itemView.findViewById(R.id.itemFechaFin);
        }
        void bindData(final String item) {
            modulo.setText(item);
        }
    }

    public void setList_item(ArrayList<Tarea> list_item) {
        this.listItem = list_item;
        notifyDataSetChanged();
    }

    public void setPar(int par) {
        if (par != this.par){
            this.par = par;
        }

        notifyDataSetChanged();
    }

    public void limpiar(){
        notifyDataSetChanged();
    }
}