package ar.edu.unahur.obj2.catering.carta;

import ar.edu.unahur.obj2.catering.propuestas.Servicio;

public class ActualizarPrecio implements Modificacion {

    private final Servicio servicio;
    private final double nuevoPrecio;
    private double precioAnterior;

    public ActualizarPrecio(Servicio servicio, double nuevoPrecio) {
        this.servicio = servicio;
        this.nuevoPrecio = nuevoPrecio;
    }

    @Override
    public void aplicar() {
        // El precio anterior se toma al aplicar (no al crear la modificacion):
        // asi dos actualizaciones del mismo servicio se revierten bien en cadena.
        precioAnterior = servicio.precio();
        servicio.actualizarPrecio(nuevoPrecio);
    }

    @Override
    public void revertir() {
        servicio.actualizarPrecio(precioAnterior);
    }
}
