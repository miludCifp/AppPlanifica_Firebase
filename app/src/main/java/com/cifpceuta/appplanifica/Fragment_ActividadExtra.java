package com.cifpceuta.appplanifica;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ActividadExtra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ActividadExtra extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton fabAñadir;
    private Spinner gruposDialogo;
    private FirebaseFirestore db;
    private EditText etTituloActExtra, etFechaActExtra;
    private Button btnDialogAñadir;

    private ArrayList<ActExtra> listaActExtra;
    private Alumno user;
    private RecyclerView miRecyclerView;

    public Fragment_ActividadExtra() {
        // Required empty public constructor
    }
    public Fragment_ActividadExtra(Alumno alumno) {
        // Required empty public constructor
        this.user = alumno;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ActividadExtra.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ActividadExtra newInstance(String param1, String param2) {
        Fragment_ActividadExtra fragment = new Fragment_ActividadExtra();
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
        View miView =  inflater.inflate(R.layout.fragment__actividad_extra, container, false);
        // Doy accion al icono fab
        iconoFabAñadir(miView);

        return miView;
    }
    private void iconoFabAñadir(View miView){
        fabAñadir = (FloatingActionButton) miView.findViewById(R.id.fabAñadir);

        fabAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creamos el DIALOG asociado al ActivityMAIN
                Dialog dialog = new Dialog(getActivity());

                //Le asociamos el layout correspondiente
                dialog.setContentView(R.layout.dialogo_actividad_extra);

                //Recuperamos los views dentro de dicho layout para recuperar sus valores posteriormente
                etTituloActExtra = dialog.findViewById(R.id.etTituloActExtra);
                gruposDialogo = dialog.findViewById(R.id.spGrupoDialogo);
                etFechaActExtra = dialog.findViewById(R.id.etFechaActExtra);
                btnDialogAñadir = dialog.findViewById(R.id.btnDialogAñadir);

                //Relleno el spinner de los grupos de cursos
                List<String> listaGrupos = new ArrayList<>();
                listaGrupos.add("1ºDAM");
                listaGrupos.add("1ºDAW");
                //listaGrupos.add("1ºASIR");
                //listaGrupos.add("1ºSMT");
                listaGrupos.add("2ºDAM");
                listaGrupos.add("2ºDAW");
                //listaGrupos.add("2ºASIR");
                //listaGrupos.add("2ºSMT");

                ArrayAdapter<String> spGruposAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaGrupos);
                spGruposAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                gruposDialogo.setAdapter(spGruposAdapter);

                //damos accion al boton
                btnDialogAñadir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent i = new Intent(RegistrarCuenta.this, MainActivity.class);
                        //startActivity(i);
                    }
                });

                //Establecemos el listener para capturar datos y realizar acción de añadir
                btnDialogAñadir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tituloActExtra = etTituloActExtra.getText().toString();
                        String fechaDialogo = etFechaActExtra.getText().toString();
                        String grupoSeleccionado = gruposDialogo.getSelectedItem().toString();

                        // Se guarda la informacion en la bd
                        guardarDatos(tituloActExtra, fechaDialogo, grupoSeleccionado);

                        //Esta llamada cierra el dialogo
                        dialog.dismiss();

                        //Aqui se añadira la tarea al fragment llamando al recogerActExtra
                        recogerActExtra(miView);
                    }
                });

                //Esta llamada abre el diálogo
                dialog.show();
            }
        });
    }
    private void guardarDatos(String titulo, String fecha, String grupo){
        // Create a new user with a first and last name
        db = FirebaseFirestore.getInstance();
        String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> actExtra = new HashMap<>();
        actExtra.put("Fecha_ini", fecha);
        actExtra.put("Titulo", titulo);
        actExtra.put("Grupo", grupo);

        // Add a new document with a generated ID
        db.collection("actextra")
                .add(actExtra)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Acticidad Extra creada y guardada en la bd correctamente",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Error, no se ha podido guardar la actividad extra en la bd",Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void recogerActExtra(View miView) {
        //String idProfesor = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        db.collection("actextra")
                .whereEqualTo("Grupo", user.getGrupo())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            //Inicializo el array que guardara las tareas asignadas.
                            listaActExtra = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ActExtra unaActExtra = new ActExtra();
                                unaActExtra.setTitulo(document.getString("Titulo"));
                                unaActExtra.setFecha(document.getString("Fecha_ini"));
                                unaActExtra.setGrupo(document.getString("Grupo"));

                                listaActExtra.add(unaActExtra);
                            }
                            AdapterActExtra miItemAdapterExtra = new AdapterActExtra(listaActExtra);
                            miRecyclerView = (RecyclerView) miView.findViewById(R.id.recyclerActExtra);
                            miRecyclerView.setAdapter(miItemAdapterExtra);
                            miRecyclerView.setLayoutManager(new LinearLayoutManager(miView.getContext()));
                            Toast.makeText(getActivity(), "Actividades Extra consultadas correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error, no se pudo leer las tareas "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}