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

import com.muni.resistencia.R;
import com.muni.resistencia.Utils.BroadcastReceiver;
import com.muni.resistencia.Utils.PagerAdapter;
import com.muni.resistencia.Utils.VecinosApplication;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Servicios_activity extends AppCompatActivity {

    @BindView(R.id.cerrarSesion)
    TextView cerrarSesion;
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
        startAlert();
    }

    public void startAlert() {

        Intent intent = new Intent(VecinosApplication.getAppContext(), BroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(VecinosApplication.getAppContext(), 234, intent, 0);
        AlarmManager alarmManager = (AlarmManager) VecinosApplication.getAppContext().getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10*60000 , pendingIntent);
        Toast.makeText(VecinosApplication.getAppContext(), "Alarm set",Toast.LENGTH_LONG).show();
    }

}
