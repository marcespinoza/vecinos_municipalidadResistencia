package com.muni.resistencia.Interfaces;

public interface RecordatorioInterface {

    interface Vista{
        void mostrarResultado(String resultado);
    }

    interface Modelo {
        void registrarResultado(int idreclamo);
    }

    interface Presentador{
        void enviarResultado(int idreclamo);
        void mostrarResultado(String resultado);
    }

}
