package com.danielcirilo.frasescelebres.alarma;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import com.danielcirilo.frasescelebres.MainActivity;
import com.danielcirilo.frasescelebres.Models.Frase;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.rest.RestClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FraseService extends Service {

    //Variabls a utilizar
    public static final String CHANNEL_ID = "CH_01";
    public static final int ID_ALERTA_NOTIFICACION = 1;
    private IAPIFrase iapiFrase;
    private Frase frase;
    private static final int ALARM_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        iapiFrase = RestClient.getInstance(getApplication());

        fraseDia();


        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Metodo implementado para obtener la frase del dia
     */
    public void fraseDia() {
        Log.d("entro al metodofrasedia", "sdasd");
        //Conecto con el servidor para obtener la frase del dia
        iapiFrase.fraseProgramada().enqueue(new Callback<Frase>() {
            @Override
            public void onResponse(Call<Frase> call, Response<Frase> response) {
                //Si todo va bien obtengo la frase y la pinto en la notifiacion
                if (response.isSuccessful()) {
                    frase = response.body();
                    Log.d("funciona el response", "sdasd");
                    Log.d("la frase es", frase.getTexto());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence nombre = "Mi canal";
                        String descripcion = "Mi canal de notificación ";
                        int importancia = NotificationManager.IMPORTANCE_DEFAULT;
                        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, nombre,
                                importancia);
                        channel.setDescription(descripcion);
                        NotificationManager notificationManager =
                                getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);

                    }
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setContentTitle("Frase del dia")
                            .setContentText(frase.getTexto())
                            .setSmallIcon(android.R.drawable.sym_def_app_icon)
                            .setContentInfo("4");
                    //Creaco el intent
                    Intent intent1 = new Intent(getApplication(), MainActivity.class);
                    //Creo el pending intent
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                            0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(ID_ALERTA_NOTIFICACION, mBuilder.build());

                }
            }

            @Override
            public void onFailure(Call<Frase> call, Throwable t) {
                Log.d("caigo al onfailure", "sdasd");
            }
        });
    }


}
