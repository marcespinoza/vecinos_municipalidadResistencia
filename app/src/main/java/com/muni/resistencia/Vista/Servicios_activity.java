package com.muni.resistencia.Vista;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muni.resistencia.Interfaces.RecordatorioInterface;
import com.muni.resistencia.Presentador.RecordatorioPresentador;
import com.muni.resistencia.R;
import com.muni.resistencia.Utils.BroadcastReceiver;
import com.muni.resistencia.Utils.PagerAdapter;
import com.muni.resistencia.Utils.VecinosApplication;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Servicios_activity extends AppCompatActivity implements RecordatorioInterface.Vista{

    RecordatorioInterface.Presentador pRecordatorio;
    @BindView(R.id.cerrarSesion)
    TextView cerrarSesion;
    Button botonSi, botonNo;
    boolean flag = false;
    Dialog popup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            flag = getIntent().getExtras().getBoolean("popup");
        }
        if(flag){
            popup = new Dialog(this);
            botonSi = popup.findViewById(R.id.botonsi);
            botonSi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pRecordatorio.enviarResultado();
                }
            });
            botonNo = popup.findViewById(R.id.botonno);
            popup.setContentView(R.layout.popup_reclamos);
            popup.show();
        }
        ButterKnife.bind(this);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Prefs.putBoolean("login",false);
               Intent intent = new Intent(view.getContext(), LoginActivity.class);
               startActivity(intent);
               finish();
            }
        });
        ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter myPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        pRecordatorio = new RecordatorioPresentador(this);
    }

    @Override
    public void mostrarResultado(final String resultado) {
        runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(Servicios_activity.this, resultado,  Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
