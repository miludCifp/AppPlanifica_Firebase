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
    private Alumno user;
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


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_account) {
            BlankFragment_MiCuenta fragMiCuenta = BlankFragment_MiCuenta.newInstance(user.getNombre(), user.getEmail(), user.getTurno(), user.getGrupo());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, new BlankFragment_MiCuenta()).commit();
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
                        //Creo un objeto Alumno para guardar sus datos despues de logearse.
                        user = new Alumno();
                        user.setNombre(document.getData().get("Nombre").toString());
                        user.setApellidos(document.getData().get("Apellidos").toString());
                        user.setEmail(document.getData().get("Correo").toString());
                        user.setGrupo(document.getData().get("Grupo").toString());
                        user.setTurno(document.getData().get("Turno").toString());

                        //Para imprimir en el fragmento el correo del usuario logeado.
                        //BlankFragment fragDefecto = BlankFragment.newInstance(user.getEmail());
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, fragDefecto).commit();
                        //if (savedInstanceState == null) {getSupportFragmentManager().beginTransaction().replace(R.id.fragPerfilEst, fragDefecto).commit();}

                        Toast.makeText(WelcomeActivity.this,"Nombre: "+user.getNombre()+" Tu email es : "+user.getEmail(),Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(WelcomeActivity.this,"No existe el usuario en la base de datos "+FirebaseAuth.getInstance().getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WelcomeActivity.this,"Ha habido un error en la conexion "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}