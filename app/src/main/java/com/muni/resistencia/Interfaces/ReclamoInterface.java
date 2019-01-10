package com.muni.resistencia.Interfaces;

public interface ReclamoInterface {
    interface Vista{
        void mostrarToast(String mensaje);
    }

    interface Presentador{
        void guardarReclamo(String idComision, String idServicio, String idContravencion, String ubicacion);
        void mostrarToast(String mensaje);
    }
    interface Modelo{
        void guardarReclamo(String idComision, String idServicio, String contravencion, String ubicacion);
    }
}
