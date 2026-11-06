package ar.edu.unahur.obj2.catering.propuestas;

public interface Propuesta {

    double precio();

    String descripcion();

    int cantidadServicios();

    boolean incluye(String nombreServicio);
}