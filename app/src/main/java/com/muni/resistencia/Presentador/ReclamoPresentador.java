package com.muni.resistencia.Presentador;

import com.muni.resistencia.Interfaces.ReclamoInterface;
import com.muni.resistencia.Modelo.ReclamoModelo;
import com.muni.resistencia.Vista.Reclamo_activity;

public class ReclamoPresentador implements ReclamoInterface.Presentador {

    ReclamoInterface.Vista vista;
    ReclamoInterface.Modelo modelo;

    public ReclamoPresentador(Reclamo_activity vista) {
        this.vista=vista;
        modelo = new ReclamoModelo(this);
    }

    @Override
    public void guardarReclamo(String idComision, String idServicio, String ubicacion) {
        modelo.guardarReclamo(idComision, idServicio, ubicacion);
    }

    @Override
    public void mostrarToast(String mensaje) {
        vista.mostrarToast(mensaje);
    }
}
