package com.cifpceuta.appplanifica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PlanificarPractica#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PlanificarPractica extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseFirestore db;

    private EditText etTituloPractica, etFechaInicio, etFechaFin, etDescPractica;
    private Spinner grupos, modulos;
    private Button btnGuardar;

    public Fragment_PlanificarPractica() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_PlanificarPractica.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_PlanificarPractica newInstance(String param1, String param2) {
        Fragment_PlanificarPractica fragment = new Fragment_PlanificarPractica();
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
        View miView = inflater.inflate(R.layout.fragment__planificar_practica, container, false);

        etTituloPractica = miView.findViewById(R.id.etTituloPractica);
        etFechaInicio = miView.findViewById(R.id.etFechaInicio);
        etFechaInicio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mostrarFecha(etFechaInicio);
                    //fechaIni = dateString;
                }
                return false;
            }
        });


        etFechaFin = miView.findViewById(R.id.etFechaFin);
        etFechaFin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mostrarFecha(etFechaFin);
                    //fechaIni = dateString;
                }
                return false;
            }
        });
        etDescPractica = miView.findViewById(R.id.etDescPractica);

        grupos = miView.findViewById(R.id.spGrupo);
        modulos = miView.findViewById(R.id.spModulo);

        btnGuardar = miView.findViewById(R.id.btnGuardarTarea);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
                //Reseteo los campos de la interfaz
                etTituloPractica.setText("");
                etDescPractica.setText("");
                etFechaInicio.setText("");
                etFechaFin.setText("");

            }
        });

        // Rellenamos los spinners y le creamos el ArrayAdapter correspondiente.
        List<String> listaGrupos = new ArrayList<>();
        listaGrupos.add("1ºDAM");
        listaGrupos.add("1ºDAW");
        listaGrupos.add("1ºASIR");
        listaGrupos.add("1ºSMT");
        listaGrupos.add("2ºDAM");
        listaGrupos.add("2ºDAW");
        listaGrupos.add("2ºASIR");
        listaGrupos.add("2ºSMT");

        ArrayAdapter<String> spGruposAdp = new ArrayAdapter<>(miView.getContext(), android.R.layout.simple_spinner_item, listaGrupos);
        spGruposAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grupos.setAdapter(spGruposAdp);

        List<String> listaModulos = new ArrayList<>();
        listaModulos.add("Sistemas");
        listaModulos.add("FOL");
        listaModulos.add("Empresas");
        listaModulos.add("Acceso a datos");
        ArrayAdapter<String> spModulosAdp = new ArrayAdapter<>(miView.getContext(), android.R.layout.simple_spinner_item, listaModulos);
        spModulosAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modulos.setAdapter(spModulosAdp);


        return miView;
    }
    private void guardarDatos(){
        // Create a new user with a first and last name
        db = FirebaseFirestore.getInstance();
        String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> practica = new HashMap<>();
        practica.put("ProfesorID", idUsuario);
        practica.put("Titulo", etTituloPractica.getText().toString());
        practica.put("FechaInicio", etFechaInicio.getText().toString());
        practica.put("FechaFin", etFechaFin.getText().toString());
        practica.put("Descripcion", etDescPractica.getText().toString());
        practica.put("Grupo", grupos.getSelectedItem().toString());
        practica.put("Modulo", modulos.getSelectedItem().toString());

        // Add a new document with a generated ID
        db.collection("practicas")
                .add(practica)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Tarea creada y guardada en la bd correctamente",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Error, no se ha podido guardar la tarea en la bd",Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void mostrarFecha(EditText fecha){
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                // Establecemos el formato de la fecha
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                // Convertimos la fecha en String
                String fechaString = formatoFecha.format(new Date(selection));

                // Cargamos la fecha en el TextView
                fecha.setText(fechaString);

            }
        });

        if (getActivity() != null) {
            datePicker.show(getActivity().getSupportFragmentManager(), datePicker.toString());
        }
    }
}