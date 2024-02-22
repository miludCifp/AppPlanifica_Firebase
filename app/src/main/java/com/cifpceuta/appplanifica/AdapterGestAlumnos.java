package com.cifpceuta.appplanifica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterGestAlumnos extends RecyclerView.Adapter<AdapterGestAlumnos.ViewHolder>{
    private ArrayList<Alumno> listItem;

    public AdapterGestAlumnos(){}

    @NonNull
    @Override
    public AdapterGestAlumnos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View miView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestion_alumnos,parent,false);
        return new AdapterGestAlumnos.ViewHolder(miView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGestAlumnos.ViewHolder holder, int position) {
        holder.bindData(listItem.get(position));

    }

    public AdapterGestAlumnos(ArrayList<Alumno> listaAlumnosGest) {
        this.listItem = listaAlumnosGest;
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardViewGestAlumnos;
        TextView tvLetraAlumno, tvNombreAlumno, tvEmailAlumno;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cardViewGestAlumnos = (CardView) itemView.findViewById(R.id.cardViewGestAlumnos);
            tvLetraAlumno = itemView.findViewById(R.id.tvLetraAlumno);
            tvNombreAlumno = itemView.findViewById(R.id.tvNombreAlumno);
            tvEmailAlumno = itemView.findViewById(R.id.tvEmailAlumno);

            //miCardViewExtra.setCardBackgroundColor(Color.WHITE);
            //miCardViewExtra.setCardBackgroundColor(ContextCompat.getColor(miCardViewExtra.getContext(), R.color.azulClaro));
        }
        void bindData(Alumno infoAlumno) {
            if (!listItem.isEmpty()) {
                // Coger la primera letra del nombre y mostrarla como inicial
                char primeraLetra = infoAlumno.getNombre().charAt(0);
                String primeraLetraComoString = String.valueOf(primeraLetra);
                tvLetraAlumno.setText(primeraLetraComoString);

                tvNombreAlumno.setText(infoAlumno.getNombre());
                tvEmailAlumno.setText(infoAlumno.getEmail());
            }


        }
    }

    public void setList_item(ArrayList<Alumno> list_item) {
        this.listItem = list_item;
        notifyDataSetChanged();
    }
}
