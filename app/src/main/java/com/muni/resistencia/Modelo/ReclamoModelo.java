package com.muni.resistencia.Modelo;

import android.widget.Toast;

import com.muni.resistencia.Interfaces.ReclamoInterface;
import com.muni.resistencia.Presentador.ReclamoPresentador;
import com.muni.resistencia.Utils.VecinosApplication;
import com.muni.resistencia.Vista.Reclamo_activity;

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

public class ReclamoModelo implements ReclamoInterface.Modelo{

    ReclamoInterface.Presentador presentador;

    public ReclamoModelo(ReclamoPresentador reclamoPresentador) {
        presentador=reclamoPresentador;
    }


    void post(String idComision, String idServicio, String idContravencion, String ubicacion) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = "Current Date : " + mdformat.format(calendar.getTime());
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
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    presentador.mostrarToast("Reclamo registrado correctamente");
                } else {
                    presentador.mostrarToast("Error al registrar reclamo");
                }
            }
        });

    }

    @Override
    public void guardarReclamo(String idComision, String idServicio, String idContravencion, String ubicacion) {
        post(idComision, idServicio, idContravencion, ubicacion);
    }
}
