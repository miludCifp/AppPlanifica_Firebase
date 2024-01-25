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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Tareas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Tareas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView miRecyclerView;
    private ArrayList<Tarea> tareas;
    private FirebaseFirestore db;
    private Alumno user;

    public Fragment_Tareas(){}

    public Fragment_Tareas(Alumno alumno) {
        // Required empty public constructor
        this.user = alumno;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Tareas.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Tareas newInstance(String param1, String param2) {
        Fragment_Tareas fragment = new Fragment_Tareas();
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
        View miView =  inflater.inflate(R.layout.fragment__tareas, container, false);
        // utilizo el metodo recogerTareas()
        recogerTareas(miView);
        return miView;
    }
    private void recogerTareas(View miView) {
        //String idProfesor = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        db.collection("practicas")
                .whereEqualTo("Grupo", user.getGrupo())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //Inicializo el array que guardara las tareas asignadas.
                            tareas = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tarea unaTarea = new Tarea();
                                unaTarea.setIdUser(document.getString("ProfesorID"));
                                unaTarea.setTituloTarea(document.getString("Titulo"));
                                unaTarea.setCurso(document.getString("Grupo"));
                                unaTarea.setModulo(document.getString("Modulo"));
                                unaTarea.setDescripcionTarea(document.getString("Descripcion"));
                                unaTarea.setFechaInicio(document.getString("FechaInicio"));
                                unaTarea.setFechaFin(document.getString("FechaFin"));
                                tareas.add(unaTarea);
                            }
                            ItemAdapter miItemAdapter = new ItemAdapter(tareas);
                            miRecyclerView = (RecyclerView) miView.findViewById(R.id.miRecyclerView);
                            miRecyclerView.setAdapter(miItemAdapter);
                            miRecyclerView.setLayoutManager(new LinearLayoutManager(miView.getContext()));
                            Toast.makeText(getActivity(), "Tareas consultadas correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error, no se pudo leer las tareas "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}