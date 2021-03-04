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

import com.danielcirilo.frasescelebres.Models.Autor;
import com.danielcirilo.frasescelebres.R;

import com.danielcirilo.frasescelebres.adaptadores.AdapterAutor;
import com.danielcirilo.frasescelebres.enumerado.TipoAccion;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.interfaces.IAutorSeleccionado;
import com.danielcirilo.frasescelebres.rest.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Clase en cargada de mostrar la lista de autores tanto cuando realizamos consultas
 * o cuando deseamos modificar o anadir algun autor
 */


public class FragmentListadoAutor extends Fragment implements IAutorSeleccionado {

    //Declaracion de variables
    private IAPIFrase iapiFrase;
    private RecyclerView rvAutores;
    private static ArrayList<Autor> autores;
    private AdapterAutor adapterAutor;
    public static final String UPDATE_AUTOR = "com.danielcirilo.listadoautor.update";
    private TipoAccion tipo;


    /**
     * Constrcutor por defecto
     */
    public FragmentListadoAutor() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Le asignamos el tipo de accion por defecto a consultar(filtrar)
        tipo = TipoAccion.FILTRAR;
        //Bundle para obteener el tipo de accion que deseemos realizar ya sea consultar(filtrar) o modificar
        Bundle budle = this.getArguments();
        if (budle != null) {
            if (budle.containsKey(UPDATE_AUTOR)) {
                tipo = (TipoAccion) budle.getSerializable(UPDATE_AUTOR);
            }

        }
        //Retornamos el layout a inflar
        return inflater.inflate(R.layout.listado_autor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("entro ", "fragment");
        iapiFrase = RestClient.getInstance(getActivity());
        cargarAutores();

    }
    /**
     * Sobreesbimos el metodo de la interfaz y en este metodo inicializamos otro fragment donde mostraremos los
     * autores, hacemos un switch para saber que accion realizara el usuario
     * @param position le pasamos la posicion al otro fragment escogida por el suaurio
     */
    @Override
    public void onAutorSeleccionado(int position) {
        Bundle bundle;
        switch (tipo) {
            case UPDATE:
                FragmentCrudAutor crearAutor = new FragmentCrudAutor();
                bundle = new Bundle();
                bundle.putSerializable(FragmentCrudAutor.UPDATE_AUTOR, autores.get(position));
                crearAutor.setArguments(bundle);
                Log.d("Fragmen listado autor", "va mas o menos");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, crearAutor).commit();
                break;
            case FILTRAR:
                FagmentDetalleAutorFrase f = new FagmentDetalleAutorFrase();
                bundle = new Bundle();
                bundle.putSerializable(FagmentDetalleAutorFrase.FRASE_AUTOR, autores.get(position).getId());
                f.setArguments(bundle);
                Log.d("Fragmen listado autor", "va mas o menos");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
                break;
        }


    }

    /**
     * Metodo encargar de cargar los autores para pintarlos en un recyclerview para que puedan
     * ser visualizados por el usuario
     */
    public void cargarAutores() {
        //Establecemos la conexion
        iapiFrase.getAutores().enqueue(new Callback<ArrayList<Autor>>() {
            @Override
            public void onResponse(Call<ArrayList<Autor>> call, Response<ArrayList<Autor>> response) {
                //Si la respuesta es correcta realizamos los pasos para pintar el recycler view
                if (response.isSuccessful()) {
                    //Rellamos nuestro arraylist
                    autores = response.body();
                    adapterAutor = new AdapterAutor(autores, getActivity(), FragmentListadoAutor.this);
                    Log.d("Autores", autores.get(0).getNombre());
                    Log.d("Work all good", "Response good");
                    rvAutores = getView().findViewById(R.id.rvAutores);
                    rvAutores.setHasFixedSize(true);
                    rvAutores.setAdapter(adapterAutor);
                    rvAutores.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }


            }

            @Override
            //Si existe un fallo de conexion le mostramos un mensaje a el usuario indicandole el mensaje de error
            public void onFailure(Call<ArrayList<Autor>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
