package com.danielcirilo.frasescelebres.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.danielcirilo.frasescelebres.Models.Categoria;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.adaptadores.AdapterFrases;
import com.danielcirilo.frasescelebres.enumerado.TipoAccion;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.interfaces.ICategoriaSeleccionada;
import com.danielcirilo.frasescelebres.rest.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase en cargada de mostrar la lista de categorias tanto cuando realizamos consultas
 * o cuando deseamos modificar o anadir alguna categoria
 */

public class FragmentListadoCategorias extends Fragment implements ICategoriaSeleccionada {

    //Declaracion de variables
    private IAPIFrase iapiFrase;
    private RecyclerView rvFrases;
    private static ArrayList<Categoria> categorias;
    private AdapterFrases adapterFrases;
    public static final String UPDATE_CATEGORIA = "com.danielcirilo.categoriaFrases.update";
    private TipoAccion tipoAccion;

    /**
     * Constrcutor por defecto
     */
    public FragmentListadoCategorias() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //Bundle para obteener el tipo de accion que deseemos realizar ya sea consultar(filtrar) o modificar
        Bundle bundle = this.getArguments();
        //Le asignamos el tipo de accion por defecto a consultar(filtrar)
        tipoAccion = TipoAccion.FILTRAR;
        //Ponemos una condicion para entrar al bundle, ya que si es null nustro programa fallaria
        if (bundle != null) {
            Log.d("Estoy en el Bundle", "hello bundee fragmentlistadocategoria");
            if (bundle.containsKey(UPDATE_CATEGORIA)) {
                //Obtener el dato que nos envia la clase fragment dialogo
                tipoAccion = (TipoAccion) bundle.getSerializable(UPDATE_CATEGORIA);
                Log.d("casi entro bundle", "hello bunde");
            }
        }
        //Retornamos el layout a inflar
        return inflater.inflate(R.layout.fragment_listado_categoria, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Instaciamos nuestra apirest
        iapiFrase = RestClient.getInstance(getActivity());
        //Obtenemos las categorias
        cargarCategorias();


    }

    /**
     * Metodo implementado para obtener todas las categorias disponibles en nuestro servidor, para luego ser mostradas
     * a traves de un recycler view.
     */
    public void cargarCategorias() {
        //Establecemos la conexion
        iapiFrase.getCategorias().enqueue(new Callback<ArrayList<Categoria>>() {
            @Override
            public void onResponse(Call<ArrayList<Categoria>> call, Response<ArrayList<Categoria>> response) {
                //Si la respuesta es correcta realizamos los pasos para pintar el recycler view
                if (response.isSuccessful()) {
                    //Rellamos nuestro arraylist
                    categorias = response.body();
                    adapterFrases = new AdapterFrases(categorias, getActivity(), FragmentListadoCategorias.this);
                    rvFrases = getView().findViewById(R.id.rvFrasesCategorias);
                    rvFrases.setHasFixedSize(true);
                    rvFrases.setAdapter(adapterFrases);
                    rvFrases.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }

            }

            @Override
            //Si existe un fallo de conexion le mostramos un mensaje a el usuario indicandole el mensaje de error
            public void onFailure(Call<ArrayList<Categoria>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sobreesbimos el metodo de la interfaz y en este metodo inicializamos otro fragment donde mostraremos las frs
     * frases por categoria, hacemos un switch para saber que accion realizara el usuario
     * @param position le pasamos la posicion al otro fragment escogida por el suaurio
     */
    @Override
    public void onCategoriaSeleccionada(int position) {
        Bundle bundle;
        switch (tipoAccion) {
            case FILTRAR:
                FragmentCategoriaFrase f = new FragmentCategoriaFrase();
                bundle = new Bundle();
                bundle.putSerializable(FragmentCategoriaFrase.AUTOR_CATEGORIA, categorias.get(position).getId());
                f.setArguments(bundle);
                Log.d("Fragmen listado autor", "va mas o menos");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
                break;
            case UPDATE:
                FragmentCrudCategoria fCategoria = new FragmentCrudCategoria();
                bundle = new Bundle();
                bundle.putSerializable(FragmentCrudCategoria.UPDATE, categorias.get(position));
                fCategoria.setArguments(bundle);
                Log.d("Categoria fragment no", "va mas o menos");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fCategoria).commit();
                break;
        }

    }
}
