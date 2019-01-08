package com.muni.resistencia.Modelo;

import com.muni.resistencia.Interfaces.EvaluacionInterface;
import com.muni.resistencia.Presentador.EvaluacionPresentador;

public class EvaluacionModelo implements EvaluacionInterface.Modelo {

    EvaluacionInterface.Presentador presentador;

    public EvaluacionModelo(EvaluacionPresentador evaluacionPresentador) {
        presentador=evaluacionPresentador;
    }

    @Override
    public void guardarEvaluacion(String idServicio, String calificacion) {

    }
}
