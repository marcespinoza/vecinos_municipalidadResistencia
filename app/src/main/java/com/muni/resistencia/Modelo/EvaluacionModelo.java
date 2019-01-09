package com.muni.resistencia.Modelo;

import com.muni.resistencia.Interfaces.EvaluacionInterface;
import com.muni.resistencia.Presentador.EvaluacionPresentador;

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
        post(idComision, idServicio, calificacion);
    }

    void post(String idComision, String idServicio, String calificacion) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = "Current Date : " + mdformat.format(calendar.getTime());
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

}
