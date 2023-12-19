package com.cifpceuta.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnInicio, btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Localizamos los elementos de la interfaz.
        btnInicio = (Button) findViewById(R.id.btnInicio);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrarse);

        //Hacemos la conexion con la BD mediante la clase manejadora.


    }
}
