package com.danielcirilo.frasescelebres.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.enumerado.TipoAccion;
import com.danielcirilo.frasescelebres.fragments.FagmentDetalleAutorFrase;
import com.danielcirilo.frasescelebres.fragments.FragmentCrudAutor;
import com.danielcirilo.frasescelebres.fragments.FragmentCrudCategoria;
import com.danielcirilo.frasescelebres.fragments.FragmentCrudFrase;
import com.danielcirilo.frasescelebres.fragments.FragmentListadoAutor;
import com.danielcirilo.frasescelebres.fragments.FragmentListadoCategorias;

/**
 * Clase impleementada para mostrar las acciones que vas a realizar ya sea anadir o modificar
 */
public class DialogoCrud extends DialogFragment implements DialogInterface.OnClickListener {
    //Enum con los lsitados a mostrar
    public enum TipoListado {
        AUTOR, CATEGORIA, FRASE
    }
    //Declaramos las  variables a utilizar
    public static final String TIPO = "com.danielcirilo.frasescelebres.tipo";
    private Button fbAdd;
    private Button fbUpdate;
    private TextView tvMensaje;
    private SQLiteDatabase db;
    private View.OnClickListener lister;
    private TipoListado tipoListado;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Declaramos el dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //Relaciones las vistas
        View layout = inflater.inflate(R.layout.dialog_crud, null);
        builder.setView(layout);
        tvMensaje = layout.findViewById(R.id.tvMensaje);
        fbAdd = layout.findViewById(R.id.btAdd);
        fbUpdate = layout.findViewById(R.id.btUpdate);
        //Obtenemos los argumentos que recibimos desde el mainactivy
        Bundle bundle = this.getArguments();
        //Validamos que no sea null
        if (bundle != null) {
            //Asignamos el tipo de listado correspondiente
            if (bundle.containsKey(TIPO)) {
                tipoListado = (TipoListado) bundle.getSerializable(TIPO);
            }
        }

        //Implementamos un listener con las acciones que va arealiazar
        lister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    //Al entrar en update cargara los diferentes fragments
                    case R.id.btUpdate:
                        switch (tipoListado) {
                            case AUTOR:
                                loadFragmentaUpdateAutor(TipoAccion.UPDATE);
                                DialogoCrud.this.dismiss();
                                break;
                            case FRASE:
                                loadFragmentaUpdateFrase(TipoAccion.UPDATE);
                                DialogoCrud.this.dismiss();
                                break;
                            case CATEGORIA:
                                loadFragmentaUpdateCategoria(TipoAccion.UPDATE);
                                DialogoCrud.this.dismiss();
                                break;

                        }
                        break;
                    case R.id.btAdd:
                        //Al entrar en e add cargara en los diferentes fragments para anadir objetos
                        switch (tipoListado) {
                            case AUTOR:
                                getActivity().getSupportFragmentManager().beginTransaction().
                                        replace(R.id.content_frame, new FragmentCrudAutor()).commit();
                                DialogoCrud.this.dismiss();
                                break;
                            case FRASE:
                                getActivity().getSupportFragmentManager().beginTransaction().
                                        replace(R.id.content_frame, new FragmentCrudFrase()).commit();
                                DialogoCrud.this.dismiss();
                                break;
                            case CATEGORIA:
                                getActivity().getSupportFragmentManager().beginTransaction().
                                        replace(R.id.content_frame, new FragmentCrudCategoria()).commit();
                                DialogoCrud.this.dismiss();
                                break;

                        }

                }
            }

        };
        //Listener
        fbAdd.setOnClickListener(lister);
        fbUpdate.setOnClickListener(lister);


        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Log.i("Diálogos", "Aceptar");
        dialogInterface.dismiss();
    }

    /**
     * Metodo implementado para cargar el fragment de pendienteo del tippo de accion
     * @param tipoAccion recibe el tipo de accion
     */

    public void loadFragmentaUpdateFrase(TipoAccion tipoAccion) {
        FagmentDetalleAutorFrase f = new FagmentDetalleAutorFrase();
        Bundle bundle;
        bundle = new Bundle();
        bundle.putSerializable(FagmentDetalleAutorFrase.UPDATE, tipoAccion);
        f.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();

    }


    /**
     * Metodo implementado para cargar el fragment de pendienteo del tippo de accion
     * @param tipoAccion recibe el tipo de accion
     */
    public void loadFragmentaUpdateCategoria(TipoAccion tipoAccion) {
        FragmentListadoCategorias f = new FragmentListadoCategorias();
        Bundle bundle;
        bundle = new Bundle();
        bundle.putSerializable(FragmentListadoCategorias.UPDATE_CATEGORIA, tipoAccion);
        f.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();

    }
    /**
     * Metodo implementado para cargar el fragment de pendienteo del tippo de accion
     * @param tipoAccion recibe el tipo de accion
     */
    public void loadFragmentaUpdateAutor(TipoAccion tipoAccion) {
        FragmentListadoAutor f = new FragmentListadoAutor();
        Bundle bundle;
        bundle = new Bundle();
        bundle.putSerializable(FragmentListadoAutor.UPDATE_AUTOR, tipoAccion);
        f.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();

    }

}
