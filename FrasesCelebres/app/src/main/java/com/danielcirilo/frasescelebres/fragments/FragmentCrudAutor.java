package com.danielcirilo.frasescelebres.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.danielcirilo.frasescelebres.Models.Autor;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.enumerado.TipoAccion;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.rest.RestClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Clase implementada para modificar o anadir autores
 */
public class FragmentCrudAutor extends Fragment {
    //Variables a usar
    public static String UPDATE_AUTOR = "com.danielcirilo.fragmentcrearautor.update";
    private TextInputEditText etMuerte;
    private TextInputEditText etNacimiento;
    private TextInputEditText etNombre;
    private TextInputEditText etProfesion;
    private Button btInsertarAutor;
    private IAPIFrase iapiFrase;
    private TipoAccion tipoAccion;
    private Autor autor;
    private TextView tvCrudAutor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Le asignamos el tipo de accion por defecto es anadir
        tipoAccion = TipoAccion.ADD;
        //Bundle para obteener el tipo de accion que deseemos realizar Y ademas cogemos la posicion de la categoria seleccionada
        Bundle data = this.getArguments();
        if (data != null) {
            Log.d("acciion", "no entro");
            if (data.containsKey(UPDATE_AUTOR)) {
                tipoAccion = TipoAccion.UPDATE;
                //Obtenemos el objeto seleccionada
                autor = (Autor) data.getSerializable(UPDATE_AUTOR);

            }
        }
        //Retornamos el layout a inflar

        return inflater.inflate(R.layout.fragment_crud_autor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Obtenemos los componentes de nuestras vista
        etMuerte = getActivity().findViewById(R.id.tiMuerte);
        etNacimiento = getActivity().findViewById(R.id.tiNacimiento);
        etNombre = getActivity().findViewById(R.id.tiNombre);
        tvCrudAutor = getActivity().findViewById(R.id.tvTitutloCrudAutor);
        etProfesion = getActivity().findViewById(R.id.tiProfesion);
        btInsertarAutor = getActivity().findViewById(R.id.btInsertarAutor);
        //Instacioamos a la api
        iapiFrase = RestClient.getInstance(getContext());
        //Switch implementado para el tipo de accion que deseemos realizar
        //En caso de que queremos actuaizar cogemos los datos del autor seleccionado y lo pintamos en un textInput
        switch (tipoAccion) {
            case UPDATE:
                tvCrudAutor.setText("Modificar Autor");
                etMuerte.setText(autor.getMuerte());
                etNacimiento.setText(autor.getNacimiento());
                etNombre.setText(autor.getNombre());
                etProfesion.setText(autor.getProfesion());
                btInsertarAutor.setText("Update");
                btInsertarAutor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos un objeto autor pero en este caso le pasamos el id y el se encarga de buscar el id en la base de datos ya atualiza los campos
                        Autor auto1 = new Autor(autor.getId(), etMuerte.getText().toString(), etNacimiento.getText().toString(), etNombre.getText().toString(), etProfesion.getText().toString());
                        //Invocamos a el metodo
                        crearAutor(auto1);

                        try {
                            Thread.sleep(1000);
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame)).commit();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
            case ADD:
                //Mostramos  mensajes de error en caso de que los campos esten vacios
                String muerte = etMuerte.getText().toString().trim();
                String nacimiento = etNacimiento.getText().toString().trim();
                String profesion = etProfesion.getText().toString().trim();
                String nombre = etNombre.getText().toString().trim();
                if (muerte.isEmpty()) {
                    etMuerte.setError("Este campo es requerido");
                }
                if (nacimiento.isEmpty()) {
                    etNacimiento.setError("Este campo es requerido");
                }
                if (profesion.isEmpty()) {
                    etProfesion.setError("Este campo es requerido");
                }
                if (nombre.isEmpty()) {
                    etNombre.setError("Este campo es requerido");
                }
                btInsertarAutor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Autor autor = new Autor(etMuerte.getText().toString(), etNacimiento.getText().toString(), etNombre.getText().toString(), etProfesion.getText().toString());
                        crearAutor(autor);

                        try {
                            Thread.sleep(1000);
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame)).commit();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;
        }
    }


    /**
     * Metodo implementado para crear un autor
     * @param autor recibe el autor a crear
     */
    public void crearAutor(Autor autor) {
        //Establecemos la conexion

        iapiFrase.addAutor(autor).enqueue(new Callback<Boolean>() {
            @Override
            //Si la respuesta es correcta realizamos los pasos para crear la categoria
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Autor añadido de manera correcta", Toast.LENGTH_LONG).show();

                } else {
                    Log.i(FragmentCrudAutor.class.getSimpleName(), "Error al añadir la frase");
                }
            }

            @Override
            //Si existe un fallo de conexion le mostramos un mensaje a el usuario indicandole el mensaje de error

            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al establecer conexion con el servidor", Toast.LENGTH_SHORT).show();


            }
        });


    }
}
