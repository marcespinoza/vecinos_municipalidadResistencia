package com.muni.resistencia.Interfaces;

public interface EvaluacionInterface {

    interface Vista{
        void mostrarToast(String mensaje);
    }
    interface Presentador{
        void enviarEvaluacion(String idComision, String idServicio, String calificacion);
        void mostrarToast(String mensaje);
    }
    interface Modelo{
        void guardarEvaluacion(String idComision, String idServicio, String calificacion);
    }

}
