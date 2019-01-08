package com.muni.resistencia.Modelo;


import android.util.Log;

import com.muni.resistencia.Interfaces.LoginInterface;
import com.muni.resistencia.Presentador.LoginPresentador;

import org.json.JSONArray;
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

/**
 * Created by Marceloi7 on 03/04/2018.
 */

public class LoginModelo implements LoginInterface.Modelo {

    private LoginInterface.Presentador lPresentador;


    public LoginModelo(LoginPresentador lPresentador) {
      this.lPresentador=lPresentador;
    }

    @Override
    public void verificarUsuario(String documento, String clave) {
        lPresentador.exitoLogin("marcelo");
       /* post(documento, clave,  new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) {

                    if (response.isSuccessful()) {
                        try {
                            String presidente = response.body().string();
                            JSONObject jObject = new JSONObject(presidente);
                            if (jObject.getString("mensaje").equalsIgnoreCase( "true")) {
                                lPresentador.exitoLogin(jObject.getString("nombre"));
                            } else {
                                lPresentador.usuarioNoEncontrado(); }
                         } catch (JSONException e) {
                            e.printStackTrace();
                         } catch (IOException e) {
                            e.printStackTrace();
                         }
                    }
             }
        });*/
    }

    Call post(String dni, String clave, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = df.format(Calendar.getInstance().getTime());
        RequestBody formBody = new FormBody.Builder()
                .add("dni", dni)
                .add("clave", clave)
                .build();
        Request request = new Request.Builder()
                .url("http://resistencia.gob.ar/vecinosdb/vecinos_api/index.php/api/existepresidente")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
