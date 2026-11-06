package ar.edu.unahur.obj2.catering.adicionales;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class MozosAdicionales extends Adicional {

    private static final double COSTO = 18_000;

    public MozosAdicionales(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    protected String nombreAdicional() {
        return "Mozos adicionales";
    }

    @Override
    protected double aplicarSobre(double precioAcumulado) {
        return precioAcumulado + COSTO;
    }
}