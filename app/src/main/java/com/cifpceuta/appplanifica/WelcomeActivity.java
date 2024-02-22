package com.cifpceuta.appplanifica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseFirestore bd;
    private Alumno user;
    private ArrayList<Tarea> tareas;
    private ArrayList<ActExtra> listaActExtra;
    private ArrayList<Alumno> listaAlumnosGest;
    private HashMap<String,ArrayList<String>> listaModulos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Localizamos los elementos de la interfaz grafica.
        toolbar = (findViewById(R.id.toolbar));
        setSupportActionBar(findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Recogo los datos del usuario logueado.
        bd = FirebaseFirestore.getInstance();
        recogerDatos();

        recogerModulos();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_account) {
            Fragment_MiCuenta fragMiCuenta = Fragment_MiCuenta.newInstance(user.getNombre(), user.getEmail(), user.getTurno(), user.getGrupo());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, fragMiCuenta).commit();
        } else if (itemId == R.id.plan_practica) {
            Fragment_PlanificarPractica fragPlanPractica = new Fragment_PlanificarPractica(listaModulos);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, fragPlanPractica).commit();
        } else if (itemId == R.id.tareas) {
            // Poner fragmento de las tareas
            Fragment_Tareas fragTareas = new Fragment_Tareas(user);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, fragTareas).commit();
        } else if (itemId == R.id.tareas_semana) {
            // Cargar aqui el fragmento de las tareas por semana
            Fragment_Tareas_Semana n = new Fragment_Tareas_Semana(user);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst,n).commit();
        } else if (itemId == R.id.actividad_extra) {
            // Cargar aqui el fragmento de las actividades extraescolares, pasandole una lista con las Actividades Extra obtenidas de la BD.
            Fragment_ActividadExtra FragActExtra = new Fragment_ActividadExtra(user, listaActExtra);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst,FragActExtra).commit();
        } else if (itemId == R.id.gestion_alumnos) {
            // Cargar aqui el fragmento
            Frag_Gestion_Alumnos FragGestAlumnos = new Frag_Gestion_Alumnos(user, listaAlumnosGest);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst,FragGestAlumnos).commit();
        } else if (itemId == R.id.plan_exam) {

        } else if (itemId == R.id.nav_settings) {

        } else if (itemId == R.id.nav_logout) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void recogerDatos(){
        DocumentReference docRef = bd.collection("usuarios").document( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Creo un objeto Alumno para guardar sus datos despues de logearse.
                        user = new Alumno();
                        user.setNombre(document.getData().get("Nombre").toString());
                        user.setApellidos(document.getData().get("Apellidos").toString());
                        user.setEmail(document.getData().get("Correo").toString());
                        user.setGrupo(document.getData().get("Grupo").toString());
                        user.setTurno(document.getData().get("Turno").toString());

                        //creamos el fragmento por defecto y cargamos el correo logueado.
                        Fragment_PorDefecto fragDefecto = Fragment_PorDefecto.newInstance(user.getEmail());
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, fragDefecto).commit();

                        // recogo  las tareas extras
                        recogerActExtra();

                        // Recogo los datos Gestion Alumnos
                        recogerInfoAlumnos();


                        Toast.makeText(WelcomeActivity.this,"Nombre: "+user.getNombre()+"\n Tu email es : "+user.getEmail(),Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(WelcomeActivity.this,"No existe el usuario en la base de datos "+FirebaseAuth.getInstance().getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WelcomeActivity.this,"Ha habido un error en la conexion "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void recogerModulos(){
        DocumentReference docRef = bd.collection("modulos").document("modulos");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        listaModulos = new HashMap<>();
                        ArrayList<String> modulos1DAM = new ArrayList<>();
                        ArrayList<String> modulos2DAM = new ArrayList<>();
                        ArrayList<String> modulos1DAW = new ArrayList<>();
                        ArrayList<String> modulos2DAW = new ArrayList<>();
                        modulos1DAM = (ArrayList<String>) document.getData().get("1ºDAM");
                        modulos2DAM = (ArrayList<String>) document.getData().get("2ºDAM");
                        modulos1DAW = (ArrayList<String>) document.getData().get("1ºDAW");
                        modulos2DAW = (ArrayList<String>) document.getData().get("2ºDAW");

                        listaModulos.put("1ºDAM",modulos1DAM);
                        listaModulos.put("2ºDAM",modulos2DAM);
                        listaModulos.put("1ºDAW",modulos1DAW);
                        listaModulos.put("2ºDAW",modulos2DAW);

                    } else {
                        Toast.makeText(WelcomeActivity.this,"Modulos no encontrados"+FirebaseAuth.getInstance().getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WelcomeActivity.this,"Error conexion con la bd "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * mediante este metodo obtengo de la bd los datos de la Actividad Extra
     */
    private void recogerActExtra() {
        //String idProfesor = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bd = FirebaseFirestore.getInstance();

        bd.collection("actextra")
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

                            //Toast.makeText(this.getActivity(), "Actividades Extra consultadas correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getContext(), "Error, no se pudo leer las tareas "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Este metodo obtiene la informacion del alumno desde la BD, por el Grupo al que pertenece cada alumno
     */
    private void recogerInfoAlumnos() {
        //String idProfesor = FirebaseAuth.getInstance().getCurrentUser().getUid();
        bd = FirebaseFirestore.getInstance();

        bd.collection("usuarios")
                .whereEqualTo("Grupo", user.getGrupo())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            //Inicializo el array que guardara las tareas asignadas.
                            listaAlumnosGest = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Alumno infoAlumno = new Alumno();

                                infoAlumno.setNombre(document.getString("Nombre"));
                                infoAlumno.setEmail(document.getString("Correo"));
                                //infoAlumno.setGrupo(document.getString("Grupo"));

                                // Esta lista se llena con la info del alumno
                                listaAlumnosGest.add(infoAlumno);
                            }

                            //Toast.makeText(this.getActivity(), "Actividades Extra consultadas correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getContext(), "Error, no se pudo leer las tareas "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //--------------
        /*
        DocumentReference docRef = bd.collection("usuarios").document("usuarios");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    //Inicializo el array que guardara las tareas asignadas.
                    listaAlumnosGest = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Alumno infoAlumno = new Alumno();

                        infoAlumno.setNombre(document.getString("Nombre"));
                        infoAlumno.setEmail(document.getString("Correo"));
                        //infoAlumno.setGrupo(document.getString("Grupo"));

                        // Esta lista se llena con la info del alumno
                        listaAlumnosGest.add(infoAlumno);
                    }

                    //Toast.makeText(this.getActivity(), "Actividades Extra consultadas correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getContext(), "Error, no se pudo leer las tareas "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

         */
    }

}