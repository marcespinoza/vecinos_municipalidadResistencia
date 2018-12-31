package com.muni.resistencia.Interfaces;

public interface ReclamoInterface {
    interface Vista{
        void mostrarToast();
    }

    interface Presentador{
        void guardarReclamo();
        void mostrarToast();
    }
    interface Modelo{
        void guardarReclamo();
    }
}
