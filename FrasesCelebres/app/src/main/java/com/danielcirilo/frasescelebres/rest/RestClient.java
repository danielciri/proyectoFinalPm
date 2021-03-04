package com.danielcirilo.frasescelebres.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static IAPIFrase instance;

    private static final String BASE_URL = "http://192.168.1.52:8090";
    private static final String BASE_URL_INSTITUTO = "http://192.168.104.3:8090";

    /**
     * Lo hacemos privado para evitar que se puedan crear instancias de esta forma
     */
    private RestClient() {

    }

    public static synchronized IAPIFrase getInstance(Context context) {
        //Obtengo la direccion ip y el puerto a traves de las preferencias
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String ip = preferences.getString("ip", "192.168.1.52");
        String lisPuerto = preferences.getString("values_encryption", "default list");
        String todaURL = "http://" + ip + ":" + lisPuerto;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(todaURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        instance = retrofit.create(IAPIFrase.class);

        return instance;
    }

}

