package com.muni.resistencia.Modelo;

import android.util.Log;

import com.muni.resistencia.Interfaces.RecordatorioInterface;

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

public class RecordatorioModelo implements RecordatorioInterface.Modelo {

    RecordatorioInterface.Presentador rPresentador;

    public RecordatorioModelo(RecordatorioInterface.Presentador rPresentador) {
        this.rPresentador = rPresentador;
    }

    @Override
    public void registrarResultado(int idreclamo) {
        post(idreclamo);
    }


    void post(int idReclamo) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("idReclamo", String.valueOf(idReclamo))
                .build();
        Request request = new Request.Builder()
                .url("http://www.mr.gov.ar/v2/sitio/hacienda/vecinos_api/index.php/api/cambiarestado")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                rPresentador.mostrarResultado("Error al registrar respuesta");
            }

            @Override
            public void onResponse(Call call, Response response){
                if (response.isSuccessful()) {
                    int id;
                    rPresentador.mostrarResultado("Se ha registrado su respuesta.");
                } else {
                   rPresentador.mostrarResultado("Error al registrar respuesta");
                }
            }
        });
    }

}
