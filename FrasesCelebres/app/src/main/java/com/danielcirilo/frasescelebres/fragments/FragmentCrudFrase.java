package com.danielcirilo.frasescelebres.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.danielcirilo.frasescelebres.Models.Autor;
import com.danielcirilo.frasescelebres.Models.Categoria;
import com.danielcirilo.frasescelebres.Models.Frase;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.enumerado.TipoAccion;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.rest.RestClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCrudFrase extends Fragment {
    public static final String ID_FRASE_UPDATE = "com.danielcirilo.modificarfrase";


    private TextInputEditText etTexto;
    private TextInputEditText etFechaProgramada;
    private Button btInsertarFrase;
    private Spinner sprAutor;
    private Spinner spCategoria;
    private IAPIFrase iapiFrase;
    private ArrayList<Autor> autores;
    private ArrayList<Categoria> categorias;
    private Categoria categoria;
    private Autor autor;
    private TextView tvCrudFrase;
    private TipoAccion accionRealizar;
    private Frase frase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        accionRealizar = TipoAccion.ADD;
        Log.d("acciion", "casi entro al bundle");
        Bundle data = this.getArguments();
        if (data != null) {
            Log.d("acciion", "no entro");
            if (data.containsKey(ID_FRASE_UPDATE)) {
                accionRealizar = TipoAccion.UPDATE;
                frase = (Frase) data.getSerializable(ID_FRASE_UPDATE);
                Log.d("Vamos a si funciona", frase.getAutor().getNombre());
            } else {
                accionRealizar = TipoAccion.ADD;

            }
        }
        return inflater.inflate(R.layout.fragment_crud_frase, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        autores = new ArrayList<>();
        etTexto = getActivity().findViewById(R.id.tiTextoFrase);
        etFechaProgramada = getActivity().findViewById(R.id.tiFechaProgramada);
        btInsertarFrase = getActivity().findViewById(R.id.btInsertarFrase);
        tvCrudFrase = getActivity().findViewById(R.id.tvTitutloCrudFrase);
        sprAutor = getActivity().findViewById(R.id.spAutores);
        spCategoria = getActivity().findViewById(R.id.spCategorias);
        iapiFrase = RestClient.getInstance(getContext());
        Log.d("acciion", "entro al swithc");
        switch (accionRealizar) {
            case ADD:
                Log.d("acciion", "entro al add fragmentaddfrase");


                String nombre = etTexto.getText().toString().trim();
                String fechaProgramada = etFechaProgramada.getText().toString().trim();
                cargarAutores();
                cargarCategorias();
                if (nombre.isEmpty()) {
                    etTexto.setError("Este campo es requerido");
                }
                if (fechaProgramada.isEmpty()) {
                    etTexto.setError("Este campo es requerido");
                }
                btInsertarFrase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Frase frase = new Frase(etTexto.getText().toString(), etFechaProgramada.getText().toString(), autor, categoria);
                        crudFrase(frase);
                        Toast.makeText(getActivity(), "Frase añadida de manera correcta", Toast.LENGTH_LONG).show();

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
            case UPDATE:
                tvCrudFrase.setText("Modificar Frase");
                cargarAutores();
                cargarCategorias();
                etTexto.setText(frase.getTexto());
                etFechaProgramada.setText(frase.getFechaProgramada());
                Log.d("id fecha", String.valueOf(frase.getId()));
                sprAutor.getSelectedItemPosition();
                spCategoria.setSelection(frase.getId());
                btInsertarFrase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Frase frase2 = new Frase(frase.getId(), etTexto.getText().toString(), etFechaProgramada.getText().toString(), autor, categoria);
                        crudFrase(frase2);
                        Toast.makeText(getActivity(), "Frase modificada de manera correcta", Toast.LENGTH_LONG).show();

                    }
                });
                break;
        }


    }


    public void crudFrase(Frase frase) {

        iapiFrase.addFrase(frase).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {

                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });


    }


    /**
     * Metodo encargado de conectarse a nuestro servicio web(apirest) y lo que hace es obtener todos los autores
     * para luego ser mostrados en un sppiner, ya que al crear una frase. Es necesario escoger el autor de la frase
     */
    public void cargarAutores() {
        iapiFrase.getAutores().enqueue(new Callback<ArrayList<Autor>>() {
            @Override
            //Establecemos la coenxion con el servidor
            public void onResponse(Call<ArrayList<Autor>> call, Response<ArrayList<Autor>> response) {
                //Si todo va bien rellenamos el spinner
                if (response.isSuccessful()) {
                    //Rellenamos el array de autores
                    autores = response.body();
                    ArrayAdapter<Autor> spinnerAutores = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, autores);
                    spinnerAutores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sprAutor.setAdapter(spinnerAutores);
                    //Hacemos un listener para que el usuario escoja el autor al que pertenece la frase.
                    sprAutor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            autor = (Autor) sprAutor.getSelectedItem();

                            sprAutor.getSelectedItemPosition();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    Log.w("errpr", "error");
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Autor>> call, Throwable t) {
                Toast.makeText(getActivity(),"Error al establecer conexion con el servidor",Toast.LENGTH_LONG).show();
            }
        });
    }



    /**
     * Metodo encargado de conectarse a nuestro servicio web(apirest) y lo que hace es obtener todas las categorias
     * para luego ser mostradass en un sppiner, ya que al crear una frase. Es necesario escoger la categoria
     * a la que pertenece dicha frase a hacer creada.
     */
    public void cargarCategorias() {
        iapiFrase.getCategorias().enqueue(new Callback<ArrayList<Categoria>>() {
            @Override
            //Establecemos la conexion con el servidor
            public void onResponse(Call<ArrayList<Categoria>> call, Response<ArrayList<Categoria>> response) {
                //Si la respuesta es correcta cargamos las categorias en el spinner.
                if (response.isSuccessful()) {
                    categorias = response.body();
                    ArrayAdapter<Categoria> spinnerCategorias = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categorias);
                    spinnerCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCategoria.setAdapter(spinnerCategorias);
                    //Establecemos un listener para la posicion escogida dentro del sppinner
                    spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //La posicion del spinner sera igual a la categoria
                            categoria = (Categoria) spCategoria.getSelectedItem();
                            int i = spCategoria.getSelectedItemPosition();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }

            }



            @Override
            //En caso de un error a la conexion le mostraremos un mensaje a el usuario
            //para indicarle el error.
            public void onFailure(Call<ArrayList<Categoria>> call, Throwable t) {
                Toast.makeText(getActivity(),"Error al establecer conexion con el servidor",Toast.LENGTH_LONG).show();
            }
        });
    }
}
