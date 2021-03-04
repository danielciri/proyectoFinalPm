package com.danielcirilo.frasescelebres.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.danielcirilo.frasescelebres.Models.Categoria;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.enumerado.TipoAccion;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.rest.RestClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Clase encargada de anadir o modificar una categoria
 */

public class FragmentCrudCategoria extends Fragment {

    //Declaracion de variblaes a implementar
    public static String UPDATE = "com.danielcirilo.frasescelebres.fragmentcategoriacrud.update";
    private TextInputEditText etNombre;
    private Button btInsertarCategoria;
    private IAPIFrase iapiFrase;
    private TipoAccion tipoAccion;
    private Categoria categoria;
    private TextView tvCrudCategoria;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Le asignamos el tipo de accion por defecto anadir
        tipoAccion = TipoAccion.ADD;
        //Bundle para obteener el tipo de accion que deseemos realizar Y ademas cogemos la posicion de la categoria seleccionada
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey(UPDATE)) {
                tipoAccion = TipoAccion.UPDATE;
                //Obtenemos el objeto seleccionada
                categoria = (Categoria) bundle.getSerializable(UPDATE);

            }
        }
        //Retornamos el layout a inflar
        return inflater.inflate(R.layout.fragment_crud_categoria, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Hacemos referencia a los componentes de nuestras  vistas
        etNombre = getActivity().findViewById(R.id.tiNombreCategoria);
        tvCrudCategoria = getActivity().findViewById(R.id.tvTituloCrudCategoria);
        btInsertarCategoria = getActivity().findViewById(R.id.btInsertarCategoria);
        iapiFrase = RestClient.getInstance(getContext());
        //Switch implementado para el tipo de accion que deseemos realizar
        //En caso de que queremos actuaizar cogemos el nombre de la categoria seleccionada y lo pintamos en un textInput
        switch (tipoAccion) {
            case UPDATE:
                //Cambios el texto de la vista
                tvCrudCategoria.setText("Modificar Categoria");
                etNombre.setText(categoria.getNombre());
                //Cambiamos el texto del buutton
                btInsertarCategoria.setText("Update");

                btInsertarCategoria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos un objeto categoria pero en este caso le pasamos el id y el se encarga de buscar el id en la base de datos ya atualiza los campos
                        Categoria categoria1 = new Categoria(categoria.getId(), etNombre.getText().toString());
                        crearCategoria(categoria1);
                    }
                });
                break;
            case ADD:

                //Mostramos un mensaje de error en caso de que este vacio
                String nombre = etNombre.getText().toString().trim();
                if (nombre.isEmpty()) {
                    etNombre.setError("Este campo es requerido");
                }
                btInsertarCategoria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Creamos una nueva categoria pero en este caso no le pasamos el id ya que es autoincrementativo
                        Categoria categoria = new Categoria(etNombre.getText().toString());
                        crearCategoria(categoria);

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
     * Metodo encargado de crear una categoria
     * @param categoria recibe la categoria
     */
    public void crearCategoria(Categoria categoria) {
        //Establecemos la conexion
        iapiFrase.addCategoria(categoria).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                //Si la respuesta es correcta realizamos los pasos para crear la categoria

                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Categoria añadida de manera correcta", Toast.LENGTH_LONG).show();

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
