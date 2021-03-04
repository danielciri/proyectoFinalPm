package com.danielcirilo.frasescelebres.sqliteconexion;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.danielcirilo.frasescelebres.Models.Usuario;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    private static UsuariosSQLiteHelper sInstance;
    private static final String DB_NAME = "Usuarios.db";
    private static final int DB_VERSION = 1;

    String sqlCreate = "CREATE TABLE usuarios (username TEXT PRIMARY KEY, " +
            "password TEXT NOT null); ";
    String sqlInsert = "INSERT INTO usuarios (username, password) " +
            "VALUES ('ejemplo','secreto');";

    public static synchronized UsuariosSQLiteHelper getInstance(Context context) {
        if(sInstance == null) {
            // Usamos el contexto de la aplicación para asegurarnos que no se pierde
            // el contexto, por ejemplo de una Activity.
            sInstance = new UsuariosSQLiteHelper(context.getApplicationContext());
        }
        return  sInstance;
    }

    //Definimos el constructor privado para asegurarnos que no lo utilice nadie desde fuera
    //Así forzamos a utilizar getInstance()
    private UsuariosSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SharedPreferences sharedPreferences = context.getSharedPreferences("usuario",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString("usuario","admin");
        editor.putString("clave","admin");
        editor.commit();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Sólo se ejecuta si la base de datos no existe
        db.execSQL(sqlCreate);
        db.execSQL(sqlInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Aquí irán las sentencias de actualización de la base de datos
    }

    public Usuario[] getUsuarios() {
        Usuario[] usuarios = null;
        int i = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT username, password FROM usuarios", null);
        if(c.moveToFirst()) {
            usuarios  = new Usuario[c.getCount()];
            do {
                String username = c.getString(0);
                String password = c.getString(1);
                usuarios[i] = new Usuario(username, password);
                i++;
            } while(c.moveToNext());
        }
        return usuarios;
    }

    public boolean deleteUsuario(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] args = new String[] {username};
        return db.delete("usuarios", "username=?", args) == 1;
    }


    public boolean addUsuario(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        //El método insert devuelve el identificador de la fila insertada o
        //-1 en caso de que se haya producido un error
        return db.insert("usuarios",null, values) != -1;
    }

    public boolean updateUsuario(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = new String[] {username};
        ContentValues values = new ContentValues();
        values.put("password", password);
        return db.update("usuarios", values, "username=?", args) == 1;
    }


}
