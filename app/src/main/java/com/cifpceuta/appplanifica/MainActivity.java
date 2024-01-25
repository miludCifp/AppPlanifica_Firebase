package com.cifpceuta.appplanifica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btnInicio, btnRegistrar;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Localizamos los elementos de la interfaz.
        btnRegistrar = (Button) findViewById(R.id.btnRegistro);
        btnInicio = (Button) findViewById(R.id.btnInicio);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrarCuenta.class);
                startActivity(i);
            }
        });
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InicioSesion.class);
                startActivity(i);
            }
        });
        // Establezco la conexion con la bd.
        db = FirebaseFirestore.getInstance();

        // Cargo los modulos una vez en la bd, y lo comento.
        //modulos();

    }
    private void modulos(){
        // Create a new user with a first and last name
        List<String> listaModulosDam1 = new ArrayList<>();
        listaModulosDam1.add("P");
        listaModulosDam1.add("FOL");
        listaModulosDam1.add("ED");
        listaModulosDam1.add("LM");
        listaModulosDam1.add("SMI");
        listaModulosDam1.add("BD");

        List<String> listaModulosDam2 = new ArrayList<>();
        listaModulosDam2.add("DI");
        listaModulosDam2.add("SGE");
        listaModulosDam2.add("PSP");
        listaModulosDam2.add("EIE");
        listaModulosDam2.add("AD");
        listaModulosDam2.add("PMDM");
        listaModulosDam2.add("FCT");

        List<String> listaModulosDaw1 = new ArrayList<>();
        listaModulosDaw1.add("P");
        listaModulosDaw1.add("FOL");
        listaModulosDaw1.add("ED");
        listaModulosDaw1.add("LM");
        listaModulosDaw1.add("SMI");
        listaModulosDaw1.add("BD");

        List<String> listaModulosDaw2 = new ArrayList<>();
        listaModulosDaw2.add("DWEC");
        listaModulosDaw2.add("DWES");
        listaModulosDaw2.add("DAW");
        listaModulosDaw2.add("DIW");
        listaModulosDaw2.add("PDAW");
        listaModulosDaw2.add("EIE");
        listaModulosDaw2.add("FCT");

        Map<String, Object> modulos = new HashMap<>();
        modulos.put("1ºDAM", listaModulosDam1);
        modulos.put("2ºDAM", listaModulosDam2);
        modulos.put("1ºDAW", listaModulosDaw1);
        modulos.put("2ºDAW", listaModulosDaw2);

        // Add a new document with a generated ID
        db.collection("modulos").document("modulos")
                .set(modulos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        Toast.makeText(MainActivity.this, "Modulos guardados correctamente en la BD.",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error, no se han podido guardar los modulos en la BD.",Toast.LENGTH_LONG).show();
                    }
                });

    }
}
