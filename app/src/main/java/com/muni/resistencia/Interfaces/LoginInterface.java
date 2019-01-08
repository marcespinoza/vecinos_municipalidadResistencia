package com.muni.resistencia.Interfaces;

/**
 * Created by Marceloi7 on 03/04/2018.
 */

public interface LoginInterface {

    interface Modelo{
        void verificarUsuario(String documento, String clave);
    }
    interface Vista{
        void enviarUsuario();
        void campoVacio();
        void errorUsuario();
        void exitoLogin();
    }
    interface Presentador{
        void enviarUsuario(String documento, String clave);
        void usuarioNoEncontrado();
        void exitoLogin(String apynom);
    }

}
