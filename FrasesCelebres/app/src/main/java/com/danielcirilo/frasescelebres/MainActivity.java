package com.danielcirilo.frasescelebres;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.danielcirilo.frasescelebres.Models.Autor;
import com.danielcirilo.frasescelebres.alarma.AlarmReceiver;
import com.danielcirilo.frasescelebres.dialogos.DialogoCrud;
import com.danielcirilo.frasescelebres.dialogos.FragmentDialogoLogin;
import com.danielcirilo.frasescelebres.fragments.FragmentFraseDia;
import com.danielcirilo.frasescelebres.fragments.FragmentListadoCategorias;
import com.danielcirilo.frasescelebres.fragments.FragmentListadoAutor;
import com.danielcirilo.frasescelebres.interfaces.IAPIFrase;
import com.danielcirilo.frasescelebres.interfaces.ILoginAdmin;
import com.danielcirilo.frasescelebres.preferences.PreferencesActivity;
import com.danielcirilo.frasescelebres.rest.RestClient;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ILoginAdmin {

    private static final int ALARM_ID = 1;
    private DrawerLayout drawer;
    private Intent preferencesIntent;
    private IAPIFrase iapiFrase;
    private ArrayList<Autor> autores;
    private NavigationView navigationView;
    private DialogoCrud.TipoListado listado;
    private LinearLayout llBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        autores = new ArrayList<>();
        iapiFrase = RestClient.getInstance(this);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_titulo_opciones_admin).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_titulo_opciones_admin).setVisible(false);
        View hView = navigationView.getHeaderView(0);
        llBackground = hView.findViewById(R.id.llLayout);

        //Obtengo las preferencias
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String horaString = sharedPreferences.getString("horaAlarma", "30");
        String minutosString = sharedPreferences.getString("minutos", "35");

        //Parseamos de String a int, para luego pasarle la hora a la alarma
        int hora = Integer.parseInt(horaString);
        int minutos = Integer.parseInt(minutosString);
        //Creamos una instacian de calendar y le pasamos la hora y los minutos de las preferencias
        Calendar mcurrentTime = Calendar.getInstance();
        mcurrentTime.set(Calendar.HOUR_OF_DAY, hora);
        mcurrentTime.set(Calendar.MINUTE, minutos);
        mcurrentTime.set(Calendar.SECOND,0);
        setAlarm(ALARM_ID, mcurrentTime.getTimeInMillis());

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentFraseDia()).commit();

    }

    /**
     * Metodo implementado donde activamos la alarma la hora que hemos indicado en las preferencias
     * @param i el ID del broadcast
     * @param timestamp hora del calendar
     */
    public void setAlarm(int i, Long timestamp) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(this, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }


    @Override
    public void onBackPressed() {
        /**
         * Si el usuario pulsa el botón atrás mientras está mostrándose el menú del NavigationView,
         * hacemos que se cierre dicho menú, ya que el comportamiento por defecto es cerrar la
         * Activity.
         */
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el menú de la ActionBar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Se ha hecho click en algún item del menú de la ActionBar
        switch (item.getItemId()) {
            case R.id.action_preferencias: {
                preferencesIntent = new Intent(this, PreferencesActivity.class);
                startActivity(preferencesIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Se ha hecho click en algún item del NavigationView
        FragmentManager fragmentManager = getSupportFragmentManager();

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_autores:
                setTitle("Lista de Autores");
                fragmentManager.beginTransaction().replace(R.id.content_frame, new FragmentListadoAutor()).commit();
                break;
            case R.id.nav_frase_dia:
                setTitle("Frase del dia");
                fragmentManager.beginTransaction().replace(R.id.content_frame, new FragmentFraseDia()).commit();
                break;
            case R.id.nav_categorias:
                setTitle("Categorias");
                fragmentManager.beginTransaction().replace(R.id.content_frame, new FragmentListadoCategorias()).commit();
                break;
            case R.id.nav_ModoAdministrador:
                FragmentDialogoLogin fragmentDialogoLogin = new FragmentDialogoLogin();
                fragmentDialogoLogin.setiLoginAdmin(this);
                fragmentDialogoLogin.show(getSupportFragmentManager(), "eero");
                break;
            case R.id.nav_AddOdeleteAutor:
                loadFragment(DialogoCrud.TipoListado.AUTOR);
                break;
            case R.id.nav_AddOdeleteCategoria:
                loadFragment(DialogoCrud.TipoListado.CATEGORIA);
                break;
            case R.id.nav_Add_O_modificar_frase:
                loadFragment(DialogoCrud.TipoListado.FRASE);
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loginAdmin(boolean admin) {
        navigationView.getMenu().findItem(R.id.nav_titulo_opciones_admin).setVisible(admin);

        llBackground.setBackgroundResource(R.drawable.adminbien);

    }

    /**
     * Metodo implementado para cargar el fragment de dialogo
     * @param tipoListado recibe el tipo de listado
     */
    public void loadFragment(DialogoCrud.TipoListado tipoListado) {
        DialogoCrud f = new DialogoCrud();
        Bundle bundle;
        bundle = new Bundle();
        bundle.putSerializable(DialogoCrud.TIPO, tipoListado);
        f.setArguments(bundle);
        f.show(getSupportFragmentManager(), "eero");

    }
}
