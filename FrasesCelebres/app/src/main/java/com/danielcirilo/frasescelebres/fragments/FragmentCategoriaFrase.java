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
import com.danielcirilo.frasescelebres.adaptadores.AdaptadorCategoriaFrase;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.rest.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment encargado de mostrar las frases filtradas por categorias
 */
public class FragmentCategoriaFrase extends Fragment {
    //Variables a utilizar
    private TextView tv;
    private IAPIFrase iapiFrase;
    public static final String AUTOR_CATEGORIA = "com.danielcirilo.frasescelebres.fagmentDetalleAutor";
    private RecyclerView rvCategoriaFrases;
    private ArrayList<Frase> frases;
    private AdaptadorCategoriaFrase adaptadorCategoriaFrase;
    private int idFrase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Obtenemos los argunmentos
        Bundle data = this.getArguments();
        //Validamos que sea diferente de null
        if (data != null) {
            //Obtenemos el id de la frase
            if (data.containsKey(AUTOR_CATEGORIA)) {
                idFrase = (int) data.getSerializable(AUTOR_CATEGORIA);
                Log.d("si hay datos", String.valueOf(idFrase));
            }
        }
        return inflater.inflate(R.layout.fragment_categoria_frase, container, false);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvCategoriaFrases = getActivity().findViewById(R.id.rvCategoriaFrase);
        //Instanciamos nuestra api
        iapiFrase = RestClient.getInstance(getActivity());

        //Invocamos a el metodo cargar frases
        cargarFrases(idFrase);
    }

    /**
     * Metodo encargado de obtener las frases de la categoria seleccionada por el usuario
     * @param id como parametro el id de la frase escogida
     */
    public void cargarFrases(int id) {
        //Establecemos la conexion
        iapiFrase.getFrases().enqueue(new Callback<ArrayList<Frase>>() {
            @Override
            public void onResponse(Call<ArrayList<Frase>> call, Response<ArrayList<Frase>> response) {
                //Si la respuesta es correcta realizamos  cogemos las frases con ese id en el servidro
                if (response.isSuccessful()) {
                    frases = response.body();
                    ArrayList<Frase> frasesAux = new ArrayList<>();
                    for (int i = 0; i < frases.size(); i++) {
                        if (frases.get(i).getCategoria().getId() == id) {
                            frasesAux.add(frases.get(i));
                        }
                    }
                    adaptadorCategoriaFrase = new AdaptadorCategoriaFrase(frasesAux, getActivity());
                    rvCategoriaFrases.setAdapter(adaptadorCategoriaFrase);
                    rvCategoriaFrases.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rvCategoriaFrases.setHasFixedSize(true);
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
}
