package com.muni.resistencia.Modelo;

import android.util.Log;


import com.muni.resistencia.Interfaces.Login;
import com.muni.resistencia.Presentador.LoginPresentador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Marceloi7 on 03/04/2018.
 */

public class LoginModelo implements Login.Modelo {

    private Login.Presentador lPresentador;


    public LoginModelo(LoginPresentador lPresentador) {
      this.lPresentador=lPresentador;
    }

    @Override
    public void verificarUsuario(String documento, String clave) {

        lPresentador.exitoLogin("marcelo");
    }

    private void makeRequest(String documento, String clave){
        /*String url= "http://my-json-server.typicode.com/marcespinoza/JSONserver/posts?dni="+documento+"&clave="+clave;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String jsonData = response.body().string();

                try {
                    JSONArray jsonarray = new JSONArray(jsonData);
                    if(jsonarray.length()!=0){
                        JSONObject jsonobject = jsonarray.getJSONObject(0);
                        Log.i("json","json"+jsonobject.getString("apynom"));
                        lPresentador.exitoLogin(jsonobject.getString("apynom"));
                    }else{
                        lPresentador.usuarioNoEncontrado();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }
}
