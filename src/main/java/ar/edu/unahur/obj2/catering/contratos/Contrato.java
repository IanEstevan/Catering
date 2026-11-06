package ar.edu.unahur.obj2.catering.contratos;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class Contrato {

    private final String cliente;
    private final String descripcion;
    private final double importe;

    public Contrato(String cliente, Propuesta propuesta) {
        this.cliente = cliente;
        this.descripcion = propuesta.descripcion();
        this.importe = propuesta.precio();
    }

    public String getCliente() {
        return cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getImporte() {
        return importe;
    }
}