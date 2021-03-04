package com.danielcirilo.frasescelebres.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.danielcirilo.frasescelebres.Models.Frase;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.adaptadores.AdaptadorFrasesAutor;
import com.danielcirilo.frasescelebres.enumerado.TipoAccion;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.interfaces.IOnFraseSeleccionada;
import com.danielcirilo.frasescelebres.rest.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase implementada para mostrar las frases por autor
 */
public class FagmentDetalleAutorFrase extends Fragment implements IOnFraseSeleccionada {

    //Declaramos las variables a implementar
    private IAPIFrase iapiFrase;
    public static final String UPDATE = "com.danielcirilo.frasescelebres.update";
    public static final String FRASE_AUTOR = "com.danielcirilo.fraseAutor";
    private TextView tv;
    private RecyclerView rvFrasesAutores;
    private ArrayList<Frase> frases;
    private AdaptadorFrasesAutor adaptadorFrasesAutor;
    private int idAutor;
    private TipoAccion tipoAccion;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Bundle para obteener el tipo de accion que deseemos realizar
        Bundle data = this.getArguments();
        if (data != null) {
            if (data.containsKey(UPDATE)) {
                tipoAccion = (TipoAccion) data.getSerializable(UPDATE);
            } else {
                tipoAccion = TipoAccion.FILTRAR;
                idAutor = (int) data.getSerializable(FRASE_AUTOR);
            }
        }
        //retornamos el layout a inflar
        return inflater.inflate(R.layout.detalle_fragment_autor_frases, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        rvFrasesAutores = getActivity().findViewById(R.id.rvAutorFrases);
        iapiFrase = RestClient.getInstance(getActivity());

        //Dependiendo del tipo de accion cargamos uno u otro
        switch (tipoAccion) {
            case UPDATE:
                cargarAllFrases();
                break;
            case FILTRAR:
                cargarFrasesAutor(idAutor);
                break;
        }

    }


    public void cargarFrasesAutor(int posicion) {
        //Establecemos la conexion
        iapiFrase.fraseAutores(posicion).enqueue(new Callback<ArrayList<Frase>>() {
            @Override
            //Si la respuesta es correcta realizamos cogemos en el servidor las frases con ese id
            public void onResponse(Call<ArrayList<Frase>> call, Response<ArrayList<Frase>> response) {
                if (response.isSuccessful()) {

                    frases = response.body();
                    adaptadorFrasesAutor = new AdaptadorFrasesAutor(frases, FagmentDetalleAutorFrase.this, getActivity());
                    rvFrasesAutores.setAdapter(adaptadorFrasesAutor);
                    rvFrasesAutores.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rvFrasesAutores.setHasFixedSize(true);
                } else {
                    Log.d("fallo else", "fallo");
                }
            }

            @Override
            //Si existe un fallo de conexion le mostramos un mensaje a el usuario indicandole el mensaje de error
            public void onFailure(Call<ArrayList<Frase>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * cargamos todas las frases desde el servidor
     */
    public void cargarAllFrases() {
        //Establecemos la conexion
        iapiFrase.getFrases().enqueue(new Callback<ArrayList<Frase>>() {
            @Override
            public void onResponse(Call<ArrayList<Frase>> call, Response<ArrayList<Frase>> response) {
                //Si la respuesta es correcta realizamos los pasos para crear la categori
                if (response.isSuccessful()) {
                    Log.d("si llego", "hello");
                    frases = response.body();
                    adaptadorFrasesAutor = new AdaptadorFrasesAutor(frases, FagmentDetalleAutorFrase.this, getActivity());
                    rvFrasesAutores.setAdapter(adaptadorFrasesAutor);
                    rvFrasesAutores.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rvFrasesAutores.setHasFixedSize(true);
                } else {
                    Log.d("fallo else", "fallo");
                }
            }

            @Override
            //Si existe un fallo de conexion le mostramos un mensaje a el usuario indicandole el mensaje de error
            public void onFailure(Call<ArrayList<Frase>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * Enviamos las frases a el fragment que se encarga de realizar las modificaciones o creaciones
     *
     * @param posicion le enviamos el objeto escogido por el suaurio
     */
    @Override
    public void onFraseSeleccionada(int posicion) {
        Log.d("posicion", String.valueOf(posicion));
        FragmentCrudFrase f = new FragmentCrudFrase();
        Bundle bundle;
        bundle = new Bundle();
        bundle.putSerializable(FragmentCrudFrase.ID_FRASE_UPDATE, frases.get(posicion));
        f.setArguments(bundle);
        Log.d("Fragmen listado autor", "va mas o menos");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
    }
}
