package com.cifpceuta.appplanifica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_Gestion_Alumnos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Gestion_Alumnos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Alumno user;
    private Spinner spGestAlumnos;
    private AdapterGestAlumnos miAdapterGestAlumnos;
    private ArrayList<Alumno> listaAlumnos;

    private RecyclerView miRecyclerView;

    public Frag_Gestion_Alumnos() {
        // Required empty public constructor
    }

    public Frag_Gestion_Alumnos(Alumno user, ArrayList<Alumno> listaAlumnos) {
        // Required empty public constructor
        this.user = user;
        this.listaAlumnos = listaAlumnos;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Gestion_Alumnos.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_Gestion_Alumnos newInstance(String param1, String param2) {
        Frag_Gestion_Alumnos fragment = new Frag_Gestion_Alumnos();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View miView =  inflater.inflate(R.layout.fragment_frag__gestion__alumnos, container, false);

        crearComp(miView);

        return miView;
    }
    public void crearComp(View miView){

        // localizamos el spinner
        spGestAlumnos = miView.findViewById(R.id.spGestAlumnos);

        //Relleno el spinner de los grupos de cursos
        List<String> listaGrupos = new ArrayList<>();
        listaGrupos.add("1ºDAM");
        listaGrupos.add("1ºDAW");
        listaGrupos.add("1ºASIR");
        listaGrupos.add("1ºSMT");
        listaGrupos.add("2ºDAM");
        listaGrupos.add("2ºDAW");
        listaGrupos.add("2ºASIR");
        listaGrupos.add("2ºSMT");

        ArrayAdapter<String> spGruposAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaGrupos);
        spGruposAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spGestAlumnos.setAdapter(spGruposAdapter);


        // Ahora localizamos el item, creando el Reycler View,

        String grupoSeleccionado = spGestAlumnos.getSelectedItem().toString();

        miAdapterGestAlumnos = new AdapterGestAlumnos(listaAlumnos);

        miRecyclerView = (RecyclerView) miView.findViewById(R.id.recyclerGestAlumnos);
        miRecyclerView.setAdapter(miAdapterGestAlumnos);
        miRecyclerView.setLayoutManager(new LinearLayoutManager(miView.getContext()));

        // Actualizo el Reycler View,
        /*
        spGestAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grupoSeleccionado.equalsIgnoreCase(user.getGrupo())){
                    miAdapterGestAlumnos.setList_item(listaAlumnos);
                    //miAdapterGestAlumnos.setList_item(listaAlumnos.removeAll());

                }
            }
        });
        */
        if(grupoSeleccionado.equalsIgnoreCase(user.getGrupo())){
            miAdapterGestAlumnos.setList_item(listaAlumnos);
            //miAdapterGestAlumnos.setList_item(listaAlumnos.removeAll());

        }
    }
}