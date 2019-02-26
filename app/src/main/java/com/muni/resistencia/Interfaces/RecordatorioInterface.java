package com.muni.resistencia.Interfaces;

public interface RecordatorioInterface {

    interface Vista{
        void mostrarResultado(String resultado);
    }

    interface Modelo {
        void registrarResultado();
    }

    interface Presentador{
        void enviarResultado();
        void mostrarResultado(String resultado);
    }

}
