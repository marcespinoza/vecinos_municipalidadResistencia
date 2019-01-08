package com.muni.resistencia.Interfaces;

public interface EvaluacionInterface {

    interface Vista{
        void exitoRegistro();
        void errorRegistro();
    }
    interface Presentador{
        void enviarEvaluacion();
        void exitoRegistro();
        void errorRegistro();
    }
    interface Modelo{
        void guardarEvaluacion();
    }

}
