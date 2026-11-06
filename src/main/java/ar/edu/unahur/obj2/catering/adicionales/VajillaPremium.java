package ar.edu.unahur.obj2.catering.adicionales;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class VajillaPremium extends Adicional {

    private static final double COSTO = 12_000;

    public VajillaPremium(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    protected String nombreAdicional() {
        return "Vajilla premium";
    }

    @Override
    protected double aplicarSobre(double precioAcumulado) {
        return precioAcumulado + COSTO;
    }
}
