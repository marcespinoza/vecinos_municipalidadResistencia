package com.muni.resistencia.Presentador;

import com.muni.resistencia.Interfaces.RecordatorioInterface;
import com.muni.resistencia.Modelo.RecordatorioModelo;
import com.muni.resistencia.Vista.Servicios_activity;

public class RecordatorioPresentador implements RecordatorioInterface.Presentador {

    RecordatorioInterface.Vista rVista;
    RecordatorioInterface.Modelo rModelo;

    public RecordatorioPresentador(Servicios_activity rVista) {
        this.rVista = rVista;
        rModelo = new RecordatorioModelo(this);
    }

    @Override
    public void enviarResultado(int idReclamo) {
        rModelo.registrarResultado(idReclamo);
    }

    @Override
    public void mostrarResultado(String resultado) {
        rVista.mostrarResultado(resultado);
    }
}
