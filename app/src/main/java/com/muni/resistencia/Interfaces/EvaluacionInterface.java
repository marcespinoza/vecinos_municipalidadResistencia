package com.muni.resistencia.Interfaces;

public interface EvaluacionInterface {

    interface Vista{
        void exitoRegistro();
        void errorRegistro();
    }
    interface Presentador{
        void enviarEvaluacion(String idServicio, String calificacion);
        void exitoRegistro();
        void errorRegistro();
    }
    interface Modelo{
        void guardarEvaluacion(String idServicio, String calificacion);
    }

}
