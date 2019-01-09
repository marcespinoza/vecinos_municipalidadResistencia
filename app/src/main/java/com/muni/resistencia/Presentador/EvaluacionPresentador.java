package com.muni.resistencia.Presentador;

import com.muni.resistencia.Interfaces.EvaluacionInterface;
import com.muni.resistencia.Modelo.EvaluacionModelo;
import com.muni.resistencia.Vista.FragmentServicios;

public class EvaluacionPresentador implements EvaluacionInterface.Presentador {

    EvaluacionInterface.Modelo modelo;
    EvaluacionInterface.Vista vista;

    public EvaluacionPresentador(FragmentServicios fragmentServicios) {
        vista = fragmentServicios;
        modelo = new EvaluacionModelo(this);
    }

    @Override
    public void enviarEvaluacion(String idComision, String idServicio, String calificacion) {
        modelo.guardarEvaluacion(idComision, idServicio, calificacion);
    }

    @Override
    public void mostrarToast(String mensaje) {
        vista.mostrarToast(mensaje);
    }

}
