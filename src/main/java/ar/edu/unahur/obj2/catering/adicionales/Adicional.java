package ar.edu.unahur.obj2.catering.adicionales;

import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public abstract class Adicional implements Propuesta {

    private final Propuesta propuestaBase;

    protected Adicional(Propuesta propuestaBase) {
        this.propuestaBase = propuestaBase;
    }

    protected abstract String nombreAdicional();

    protected abstract double aplicarSobre(double precioAcumulado);

    @Override
    public double precio() {
        return aplicarSobre(propuestaBase.precio());
    }

    @Override
    public String descripcion() {
        return propuestaBase.descripcion() + " + " + nombreAdicional();
    }

    @Override
    public int cantidadServicios() {
        return propuestaBase.cantidadServicios();
    }

    @Override
    public boolean incluye(String nombreServicio) {
        return propuestaBase.incluye(nombreServicio);
    }
}