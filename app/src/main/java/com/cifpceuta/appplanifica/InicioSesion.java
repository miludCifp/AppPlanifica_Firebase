package com.cifpceuta.appplanifica;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicioSesion extends AppCompatActivity {
    private Button btnIniciarSesion, btnVolverInicio;
    private EditText txtEmail, txtContraseña;
    private FirebaseAuth mAuth;
    private FirebaseFirestore bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Localizamos los elementos de la interfaz.
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnVolverInicio = (Button) findViewById(R.id.btnVolverInicio);
        txtEmail = (EditText) findViewById(R.id.txtEmailInicio);
        txtContraseña = (EditText) findViewById(R.id.txtContraseñaInicio);

        //Inicializamos el objeto FirebaseAuth;
        mAuth = FirebaseAuth.getInstance();
        btnVolverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InicioSesion.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = txtEmail.getText().toString();
                String passUser = txtContraseña.getText().toString();
                iniciarSesion(emailUser, passUser);
            }
        });

    }
    private void iniciarSesion(String email, String password){
            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "Sesion inciada correctamente");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                                pasarActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "Ha fallado la autenticación.", task.getException());
                                Toast.makeText(InicioSesion.this, "Ha fallado la autenticación.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
            // [END sign_in_with_email]
    }
    private void pasarActivity(){
        // si el usuario se ha creado volvemos al Activity Principal para que se pueda logear
        Intent intent = new Intent(InicioSesion.this, WelcomeActivity.class);
        startActivity(intent);
    }
}