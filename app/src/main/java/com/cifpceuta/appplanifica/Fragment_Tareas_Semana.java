package com.cifpceuta.appplanifica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Tareas_Semana#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Tareas_Semana extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private RecyclerView miRecyclerView;
    private  ItemAdapter miAdapter;
    private Alumno alumno;
    private ArrayList<Tarea> listaTareas;
    private FirebaseFirestore db;

    public Fragment_Tareas_Semana() {
        // Required empty public constructor
    }
    public Fragment_Tareas_Semana(Alumno alumno) {
        this.alumno = alumno;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Tareas_Semana.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Tareas_Semana newInstance(String param1, String param2) {
        Fragment_Tareas_Semana fragment = new Fragment_Tareas_Semana();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View miView = inflater.inflate(R.layout.fragment__tareas__semana, container, false);
        TabLayout miTableLyt = miView.findViewById(R.id.miTableLyt);

        // Recogo las tareas
        recogerTareas(miView);
        miTableLyt.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Dependiendo de la semana que se seleccione se mostraran tareas segun la semana a que pertenecen.
                if(tab.getPosition() == 0){
                    miAdapter.setList_item(tareasSemana(1));
                } else if (tab.getPosition() == 1) {
                    miAdapter.setList_item(tareasSemana(2));
                } else if (tab.getPosition() == 2) {
                    miAdapter.setList_item(tareasSemana(3));
                } else if (tab.getPosition() == 3) {
                    miAdapter.setList_item(tareasSemana(4));
                } else if (tab.getPosition() == 4) {
                    miAdapter.setList_item(tareasSemana(5));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //ViewPager2 para cambiar haciendo deslizando
        return miView;
    }
    /**
     * Este metodo me devuelve una lista con las tareas de la semana seleccionada
     * @param semana
     * @return
     */
    private ArrayList<Tarea> tareasSemana(int semana){
        ArrayList<Tarea> Tareas_Semana = new ArrayList<>();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaTarea;

        int semanaTarea;

        for (Tarea tarea : listaTareas){
            fechaTarea = LocalDate.parse(tarea.getFechaFin(),formato);

            if (fechaTarea.getYear() == LocalDate.now().getYear() &&  fechaTarea.getMonth() == LocalDate.now().getMonth()){
                semanaTarea = fechaTarea.get(WeekFields.of(DayOfWeek.MONDAY,1).weekOfMonth());
                if (semana == semanaTarea){
                    Tareas_Semana.add(tarea);
                }
            }
        }
        return Tareas_Semana;
    }

    /**
     * Este metodo recoge de la bd las tareas
     * @param miView
     */
    private void recogerTareas(View miView) {
        //String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("practicas")
                .whereEqualTo("Grupo", alumno.getGrupo())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listaTareas = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tarea tarea = new Tarea();
                                tarea.setIdUser(document.getString("ProfesorID"));
                                tarea.setTituloTarea(document.getString("Titulo"));
                                tarea.setDescripcionTarea(document.getString("Descripcion"));
                                tarea.setFechaInicio(document.getString("FechaInicio"));
                                tarea.setFechaFin(document.getString("FechaFin"));
                                tarea.setCurso(document.getString("Grupo"));
                                tarea.setModulo(document.getString("Modulo"));
                                // Añado las tareas a la lista
                                listaTareas.add(tarea);
                            }
                            // Le paso al adapter la primera semana por defecto
                            miAdapter = new ItemAdapter(tareasSemana(1));
                            miRecyclerView = (RecyclerView) miView.findViewById(R.id.TareasSemanaRecycler);
                            miRecyclerView.setAdapter(miAdapter);
                            miRecyclerView.setLayoutManager(new LinearLayoutManager(miView.getContext()));
                        } else {
                            Toast.makeText(getActivity(), "Error en la consulta de datos " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}