package ar.edu.unahur.obj2.catering.adicionales;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class ServicioNocturno extends Adicional {

    private static final double PORCENTAJE = 0.20;

    public ServicioNocturno(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    protected String nombreAdicional() {
        return "Servicio nocturno";
    }

    @Override
    protected double aplicarSobre(double precioAcumulado) {
        return precioAcumulado * (1 + PORCENTAJE);
    }
}