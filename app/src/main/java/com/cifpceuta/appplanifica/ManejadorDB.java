package com.cifpceuta.appplanifica;

import android.database.sqlite.SQLiteDatabase;

public class ManejadorDB {
    private SQLiteDatabase miBD;

    // Metodo para INSERTAR
    private void insertar(String consulta){
        miBD.execSQL(consulta);
    }
    // Metodo para LEER

    // Metodo para Actualizar

    // Metodo para Borrar

}
