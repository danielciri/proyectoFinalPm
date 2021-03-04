package com.danielcirilo.frasescelebres.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.danielcirilo.frasescelebres.Models.Autor;
import com.danielcirilo.frasescelebres.Models.Frase;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.rest.RestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFraseDia extends Fragment {
    private TextView tvFrase;
    private IAPIFrase iapiFrase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Retornamos el layout a inflar
        return inflater.inflate(R.layout.fragment_frase_dia, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvFrase = getActivity().findViewById(R.id.tvFraseDia);
        iapiFrase = RestClient.getInstance(getContext());
        findFrase();
    }


    /**
     * Metodo implementado para que busque en el servidor la frase del dia
     */
    private void findFrase() {
        //Establecemos la conexion
        iapiFrase.fraseProgramada().enqueue(new Callback<Frase>() {
            @Override
            public void onResponse(Call<Frase> call, Response<Frase> response) {
                //Si la respuesta es correcta realizamos los pasos para pintar el recycler view
                if (response.isSuccessful()) {
                    Log.i("Entro issuceful", "fechacorrecta");
                    //Cogemos la frase
                    Frase frase = response.body();
                    Log.d("frase es:", frase.getTexto());
                    //La mostramos
                    tvFrase.setText(frase.getTexto());
                }
            }

            @Override
            //Si existe un fallo de conexion le mostramos un mensaje a el usuario indicandole el mensaje de error
            public void onFailure(Call<Frase> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al establecer conexion con el servidor", Toast.LENGTH_SHORT).show();


            }
        });


    }
}
