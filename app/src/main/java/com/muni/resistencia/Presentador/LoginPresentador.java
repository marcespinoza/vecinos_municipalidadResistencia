package com.muni.resistencia.Presentador;

import android.text.TextUtils;

import com.muni.resistencia.Interfaces.LoginInterface;
import com.muni.resistencia.Modelo.LoginModelo;
import com.muni.resistencia.Vista.LoginActivity;


/**
 * Created by Marceloi7 on 03/04/2018.
 */

public class LoginPresentador implements LoginInterface.Presentador {

    private LoginInterface.Vista lVista;
    private LoginInterface.Modelo lModelo;

    public LoginPresentador(LoginActivity lVista) {
        this.lVista=lVista;
        lModelo = new LoginModelo(this);
    }


    @Override
    public void enviarUsuario(String documento, String clave) {
        if(TextUtils.isEmpty(documento) || TextUtils.isEmpty(clave)){
            lVista.campoVacio();
        }else{
            lModelo.verificarUsuario(documento, clave);
        }
    }

    @Override
    public void usuarioNoEncontrado() {
        lVista.errorUsuario();
    }

    @Override
    public void exitoLogin(String idComision) {
        lVista.exitoLogin(idComision);
    }
}
