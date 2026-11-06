package ar.edu.unahur.obj2.catering.propuestas;

import ar.edu.unahur.obj2.catering.exepciones.PrecioNegativoException;

public class Servicio implements Propuesta {

    private final String nombre;
    private double precio;

    public Servicio(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = validarPrecio(precio);
    }

    public void actualizarPrecio(double nuevoPrecio) {
        this.precio = validarPrecio(nuevoPrecio);
    }

    private static double validarPrecio(double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException("El precio no puede ser negativo, se recibio: " + precio);
        }
        return precio;
    }

    @Override
    public double precio() {
        return precio;
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    @Override
    public int cantidadServicios() {
        return 1;
    }

    @Override
    public boolean incluye(String nombreServicio) {
        return nombre.equals(nombreServicio);
    }
}