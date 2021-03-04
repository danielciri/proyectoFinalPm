package com.danielcirilo.frasescelebres.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.sqliteconexion.UsuariosSQLiteHelper;
import com.danielcirilo.frasescelebres.interfaces.ILoginAdmin;


/**
 * Esta clase sera la encargada de mostrar la interfaz de registro a el usuario cuando se tratase de un admin
 * le pedira la contrasena y el usuario
 */
public class FragmentDialogoLogin extends DialogFragment implements DialogInterface.OnClickListener {
    //Declaracion de variables
    private Button btEntrar;
    private EditText etUsuario;
    private EditText etPassword;
    private SQLiteDatabase db;
    private ILoginAdmin iLoginAdmin;
    private CheckBox checkBox;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //Inflamos el layout
        View layout = inflater.inflate(R.layout.dialogo_personalizado_login, null);
        builder.setView(layout);
        //Relacionamos los componentes de las vistas
        etUsuario = layout.findViewById(R.id.etUser);
        checkBox = layout.findViewById(R.id.cbRecordar);
        etPassword = layout.findViewById(R.id.etPassword);
        btEntrar = layout.findViewById(R.id.btInicioSesion);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("usuario", Context.MODE_PRIVATE);
        String user = null;
        String clave = null;
        if (sharedPreferences.getBoolean("cbRememberPreferences", false)){
               user = sharedPreferences.getString("usuario", "");
             clave = sharedPreferences.getString("clave", "");
             etPassword.setText(clave);
             etUsuario.setText(user );
             checkBox.setChecked(true);
        }



        final UsuariosSQLiteHelper usqlh = UsuariosSQLiteHelper.getInstance(getActivity());
        db = usqlh.getWritableDatabase();
        if (db == null) {
            Toast.makeText(getActivity(), "Error al conectar con la BD", Toast.LENGTH_SHORT).show();
        } else {
            //Aquí irán las sentencias de consulta y/o actualización
            btEntrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("cbRememberPreferences", true);
                        editor.commit();
                    }
                    String[] args = new String[]{etUsuario.getText().toString(), etPassword.getText().toString()};
                    Cursor c = db.rawQuery("SELECT * FROM usuarios WHERE username = ? and password = ?", args);
                    if (c.getCount() == 1) {
                        Toast.makeText(getActivity(), "Acceso permitido", Toast.LENGTH_SHORT).show();
                        //Interfaz que sera implementada en el main activty para cuando nos logueemos
                        //esta sea true y el navitadordrawable muestra las opciones del admin
                        iLoginAdmin.loginAdmin(true);
                        //Cerramos el dialogo
                        FragmentDialogoLogin.this.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Log.i("Diálogos", "Aceptar");
        dialogInterface.dismiss();
    }

    //Setter de la interfaz
    public void setiLoginAdmin(ILoginAdmin iLoginAdmin) {
        this.iLoginAdmin = iLoginAdmin;
    }
}
