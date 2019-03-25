package com.muni.resistencia.Modelo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.muni.resistencia.Interfaces.ReclamoInterface;
import com.muni.resistencia.Presentador.ReclamoPresentador;
import com.muni.resistencia.Utils.BroadcastReceiver;
import com.muni.resistencia.Utils.DiffDays;
import com.muni.resistencia.Utils.VecinosApplication;
import com.muni.resistencia.Vista.Reclamo_activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.ALARM_SERVICE;

public class ReclamoModelo implements ReclamoInterface.Modelo{

    ReclamoInterface.Presentador presentador;

    public ReclamoModelo(ReclamoPresentador reclamoPresentador) {
        presentador=reclamoPresentador;
    }

    @Override
    public void guardarReclamo(String idComision, String idServicio, String idContravencion, String ubicacion) {
            post(idComision, idServicio, idContravencion, ubicacion);
    }


    void post(String idComision, final String idServicio, final String idContravencion, String ubicacion) {
        OkHttpClient client = new OkHttpClient();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = df.format(Calendar.getInstance().getTime());
        RequestBody formBody = new FormBody.Builder()
                .add("idComision", idComision)
                .add("idServicio", idServicio)
                .add("idContravencion", idContravencion)
                .add("ubicacion", ubicacion)
                .add("estado", "0")
                .add("fecha", fechaActual)
                .build();
        Request request = new Request.Builder()
                .url("http://www.mr.gov.ar/v2/sitio/hacienda/vecinos_api/index.php/api/reclamo")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                presentador.mostrarToast("Error al registrar reclamo.");
            }

            @Override
            public void onResponse(Call call, Response response){
                if (response.isSuccessful()) {
                    int id;
                    try {
                        String evaluacion = response.body().string();
                        JSONObject jObject = new JSONObject(evaluacion);
                        id= jObject.getInt("id");
                        programarAlarma(id, idServicio, idContravencion);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    presentador.mostrarToast("Reclamo registrado correctamente");
                } else {
                    presentador.mostrarToast("Error al registrar reclamo");
                }
            }
        });
    }


    public void programarAlarma(int idreclamo, String idServicio, String idContravencion) {
        if(!idContravencion.equals("")){
        switch (Integer.parseInt(idContravencion)){
            case 1: idContravencion = "Vehiculos abandonados"; break;
            case 2: idContravencion = "Construcciones en via pública"; break;
            case 3: idContravencion = "Terrenos con falta de mantenimiento"; break;
            case 4: idContravencion = "Pérdida de agua servida"; break;
            case 5: idContravencion = "Arbol en peligro"; break;
            case 6: idContravencion = "Ruidos molestos"; break;
            case 7: idContravencion = "Conexiones ilegales"; break;
            case 8: idContravencion = "Otras contravenciones"; break;
        }
        }
        if(!idServicio.equals("")){
        switch (Integer.parseInt(idServicio)){
            case 1: idServicio = "Barrido calles de pavimento"; break;
            case 2: idServicio = "Mantenimiento calles pavimentadas y bacheo"; break;
            case 3: idServicio = "Mantenimiento calles no pavimentadas"; break;
            case 4: idServicio = "Recolección de residuos"; break;
            case 5: idServicio = "Desmalezado espacios verdes"; break;
            case 6: idServicio = "Limpieza espacios publicos"; break;
            case 7: idServicio = "Zanjas y desagues"; break;
            case 8: idServicio = "Mantenimiento infraestructura"; break;
            case 9: idServicio = "Iluminación espacio público"; break;
            case 10: idServicio = "Señalización espacio público"; break;
        }
        }
        Intent intent = new Intent(VecinosApplication.getAppContext(), BroadcastReceiver.class);
        intent.putExtra("idreclamo", idreclamo);
        intent.putExtra("servicio", idServicio);
        intent.putExtra("contravencion", idContravencion);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(VecinosApplication.getAppContext(), 234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) VecinosApplication.getAppContext().getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000 * 60 * 60 * 24 * 6 , pendingIntent);
    }

}
