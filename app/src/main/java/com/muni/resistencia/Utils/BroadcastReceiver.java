package com.muni.resistencia.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.muni.resistencia.R;
import com.muni.resistencia.Vista.Servicios_activity;
import com.pixplicity.easyprefs.library.Prefs;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10002";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        initPrefs();
        if (extras != null) {
          int idReclamo = extras.getInt("idreclamo",0);
          String servicio = extras.getString("servicio","");
          String contravencion = extras.getString("contravencion","");
          createNotification(idReclamo, servicio, contravencion);
        }
    }

    public void createNotification(int idReclamo, String servicio, String contravencion) {
        Intent intent = new Intent(VecinosApplication.getAppContext(), Servicios_activity.class);
        intent.putExtra("popup", true);
        intent.putExtra("idComision", Prefs.getString("idComision",""));
        intent.putExtra("idreclamo", idReclamo);
        intent.putExtra("servicio", servicio);
        intent.putExtra("contravencion", contravencion);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                VecinosApplication.getAppContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(VecinosApplication.getAppContext(), NOTIFICATION_CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_alert);
        mBuilder.setContentTitle("Vecinos")
                .setContentText("Su reclamo ha sido atendido?")
                .setAutoCancel(true)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);



        mNotificationManager = (NotificationManager) VecinosApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }

    void initPrefs(){
        new Prefs.Builder()
                .setContext(VecinosApplication.getAppContext())
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(VecinosApplication.getAppContext().getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

}
