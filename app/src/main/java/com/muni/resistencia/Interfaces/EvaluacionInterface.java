package com.muni.resistencia.Interfaces;

public interface EvaluacionInterface {

    interface Vista{
        void exitoRegistro();
        void errorRegistro();
    }
    interface Presentador{
        void enviarEvaluacion(int idServicio, String calificacion);
        void exitoRegistro();
        void errorRegistro();
    }
    interface Modelo{
        void guardarEvaluacion(int idServicio, String calificacion);
    }

}
