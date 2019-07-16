package com.example.menudigital.entidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {
    final private  String TABLA_PEDIDOS = "CREATE TABLE pedidos (idOrden INTEGER PRIMARY KEY   AUTOINCREMENT, id INTEGER, titulo TEXT, descripcion TEXT, precio DOUBLE, imagen BLOB)";

    public ConexionSQLiteHelper( Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_PEDIDOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS pedidos");

        onCreate(db);
    }
}
