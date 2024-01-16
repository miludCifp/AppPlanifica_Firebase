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

public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseFirestore bd;
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

        //Recogo los datos del usuario logueado
        bd = FirebaseFirestore.getInstance();
        recogerDatos();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_account) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, new PerfilEstudiante_Fragment()).commit();
        } else if (itemId == R.id.plan_practica) {

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
                        Alumno u = new Alumno();
                        u.setNombre(document.getData().get("Nombre").toString());
                        u.setApellidos(document.getData().get("Apellidos").toString());
                        u.setEmail(document.getData().get("Correo").toString());
                        u.setGrupo(document.getData().get("Grupo").toString());
                        u.setTurno(document.getData().get("Turno").toString());

                        Toast.makeText(WelcomeActivity.this,"Nombre: "+u.getNombre()+" Tu email es : "+u.getEmail(),Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(WelcomeActivity.this,"No se ha podido encontrar al usuario "+FirebaseAuth.getInstance().getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WelcomeActivity.this,"Ha habido un error en la conexion "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}