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


    void post(String idComision, String idServicio, String idContravencion, String ubicacion) {
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
                .url("http://resistencia.gob.ar/vecinosdb/vecinos_api/index.php/api/reclamo")
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
                        Log.i("idreclamo","idreclamo"+id);
                        programarAlarma(id);
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

    void ultimoReclamo(final String idComision, final String idServicio, final String idContravencion, final String calificacion) {
        String url = "";
        RequestBody formBody;
        if(idContravencion==""){
            url = "http://resistencia.gob.ar/vecinosdb/vecinos_api/index.php/api/ultimoreclamo";
           formBody = new FormBody.Builder()
                    .add("idComision", idComision)
                    .add("idServicio", idServicio)
                    .build();
        }else{
            url = "http://resistencia.gob.ar/vecinosdb/vecinos_api/index.php/api/ultimacontravencion";
           formBody = new FormBody.Builder()
                    .add("idComision", idComision)
                    .add("idContravencion", idContravencion)
                    .build();
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                presentador.mostrarToast("Error al obtener datos, intente nuevamente.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String evaluacion = response.body().string();
                        JSONObject jObject = new JSONObject(evaluacion);
                        String flag= jObject.getString("mensaje");
                        if(flag.equals("true")){
//                            int diffs = DiffDays.daysDiff(jObject.getString("fecha"));
//                            if(diffs>6){
                                post(idComision, idServicio, idContravencion, calificacion);
//                            }else{
//                                presentador.mostrarToast("Debe esperar mas de 7 días para una nueva evaluación");
//                            }
                        } else{
                            presentador.mostrarToast("No se pudo guardar el reclamo. Intente nuevamente.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    presentador.mostrarToast("Error al obtener datos, intente nuevamente.");
                }
            }
        });
    }

    void registrarReclamo(String id){

    }

    public void programarAlarma(int idreclamo) {

        Intent intent = new Intent(VecinosApplication.getAppContext(), BroadcastReceiver.class);
        intent.putExtra("IDRECLAMO", idreclamo);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(VecinosApplication.getAppContext(), 234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) VecinosApplication.getAppContext().getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5 * 1000 , pendingIntent);
    }

}
