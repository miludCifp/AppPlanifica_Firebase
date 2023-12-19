package com.cifpceuta.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView nombre, apellidos, telefono, email;
    private EditText txtNombre, txtApellidos, txtTelefono, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Localizamos los elementos de la interfaz.
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        //Hacemos la conexion con la BD mediante la clase manejadora.
        MyOpenHelper dbHelper = new MyOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null) {
            // Hacer las operaciones que queramos sobre la base de datos
            try {
                db.execSQL("INSERT INTO Usuario (nombre, apellidos, telefono, email) VALUES ('"+txtNombre.getText()+"','"+txtApellidos.getText()+"', '"+txtTelefono.getText()+"', '"+txtEmail.getText()+"')");
                Toast.makeText(this, "Usuario insertado correctamente",Toast.LENGTH_LONG);
            }catch (SQLiteConstraintException e){
                Toast.makeText(this, "No se ha podido insertar el usuario",Toast.LENGTH_LONG);
                System.out.println(e.toString());
            }



        }

    }
}
