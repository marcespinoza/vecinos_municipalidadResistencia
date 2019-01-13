package com.muni.resistencia.Modelo;

import android.util.Log;

import com.muni.resistencia.Interfaces.EvaluacionInterface;
import com.muni.resistencia.Presentador.EvaluacionPresentador;
import com.muni.resistencia.Utils.DiffDays;

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

public class EvaluacionModelo implements EvaluacionInterface.Modelo {

    EvaluacionInterface.Presentador presentador;

    public EvaluacionModelo(EvaluacionPresentador evaluacionPresentador) {
        presentador=evaluacionPresentador;
    }

    @Override
    public void guardarEvaluacion(String idComision, String idServicio, String calificacion) {
        ultimaEvaluacion(idComision, idServicio, calificacion);
    }

    void post(String idComision, String idServicio, String calificacion) {

        OkHttpClient client = new OkHttpClient();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = df.format(Calendar.getInstance().getTime());
        RequestBody formBody = new FormBody.Builder()
                .add("idComision", idComision)
                .add("idServicio", idServicio)
                .add("calificacion", calificacion)
                .add("fecha", fechaActual)
                .build();
        Request request = new Request.Builder()
                .url("http://resistencia.gob.ar/vecinosdb/vecinos_api/index.php/api/evaluacion")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                presentador.mostrarToast("Error al registrar evaluación.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    presentador.mostrarToast("Evaluación registrada correctamente.");
                } else {
                    presentador.mostrarToast("Error al registrar evaluación.");
                }
            }
        });

    }

    void ultimaEvaluacion(final String idComision, final String idServicio, final String calificacion) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("idComision", idComision)
                .add("idServicio", idServicio)
                .build();
        Request request = new Request.Builder()
                .url("http://resistencia.gob.ar/vecinosdb/vecinos_api/index.php/api/ultimaevaluacion")
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
                        int diffs =DiffDays.daysDiff(jObject.getString("fecha"));
                        if(diffs>6){
                            post(idComision, idServicio, calificacion);
                        }else{
                            presentador.mostrarToast("Debe esperar mas de 7 días para una nueva evaluación");
                        }
                        }
                        else{
                            post(idComision, idServicio, calificacion);
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

}
