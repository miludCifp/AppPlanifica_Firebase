package com.cifpceuta.appplanifica;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarCuenta extends AppCompatActivity {
    private Button btnRegistrarse, btnVolverRegistro;
    private TextView nombre, apellidos, telefono, email;
    private EditText txtNombre, txtApellidos, txtEmail, txtContraseña;
    private Spinner spGrupo;
    private RadioButton opMañana, opTarde;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cuenta);

        //Localizamos los elementos de la interfaz.
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContraseña = (EditText) findViewById(R.id.txtContraseña);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnVolverRegistro = (Button) findViewById(R.id.btnVolverRegistro);
        spGrupo = (Spinner) findViewById(R.id.spinnerGrupo);
        opMañana = (RadioButton) findViewById(R.id.opcionMañana);
        opTarde = (RadioButton) findViewById(R.id.opcionTarde);

        //Inicializamos el objeto FirebaseAuth;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //damos accion a los botones
        btnVolverRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrarCuenta.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insertarUsuario();
                String emailUser = txtEmail.getText().toString();
                String passUser = txtContraseña.getText().toString();
                registrarUsuario(emailUser, passUser);
            }
        });

    }
    private void registrarUsuario(String email, String password){
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registro realizado correctamente!",
                                            Toast.LENGTH_LONG)
                                    .show();
                            //Guardo los datos del usuario en la BD.
                            guardarDatosUser();
                            // si el usuario se ha creado volvemos al Activity Principal para que se pueda logear
                            Intent intent
                                    = new Intent(RegistrarCuenta.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // En este punto algo ha fallado, lo notificaremos
                            Toast.makeText(
                                            getApplicationContext(),
                                            "El registro ha fallado!!"
                                                    + " Intentelo mas tarde...",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
    private void guardarDatosUser(){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Nombre", txtNombre.getText().toString());
        user.put("Apellidos", txtApellidos.getText().toString());
        user.put("Grupo", spGrupo.getSelectedItem().toString());
        if (opMañana.isSelected()){
            user.put("Turno", "Mañana");
        }else {
            user.put("Turno", "Tarde");
        }

        // Add a new document with a generated ID
        db.collection("Usuarios")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(RegistrarCuenta.this, "Datos guardados correctamente en la BD.",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(RegistrarCuenta.this, "Error, no se pudo guardar los datos en la BD.",Toast.LENGTH_LONG).show();
                    }
                });
    }


}