package ar.edu.unahur.obj2.catering.propuestas;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.catering.exepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.exepciones.PropuestaNoIncluidaException;

public class Menu implements Propuesta {

    private final String nombre;
    private final double descuento;
    private final List<Propuesta> componentes = new ArrayList<>();

    public Menu(String nombre, double descuento) {
        if (descuento < 0 || descuento > 100) {
            throw new DescuentoInvalidoException("El descuento debe estar entre 0 y 100, se recibio: " + descuento);
        }
        this.nombre = nombre;
        this.descuento = descuento;
    }

    public void agregar(Propuesta propuesta) {
        componentes.add(propuesta);
    }

    public void quitar(Propuesta propuesta) throws PropuestaNoIncluidaException {
        if (!componentes.remove(propuesta)) {
            throw new PropuestaNoIncluidaException(
                    "'" + propuesta.descripcion() + "' no es componente directo de '" + nombre + "'");
        }
    }

    @Override
    public double precio() {
        double suma = componentes.stream().mapToDouble(Propuesta::precio).sum();
        return suma * (1 - descuento / 100);
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    @Override
    public int cantidadServicios() {
        return componentes.stream().mapToInt(Propuesta::cantidadServicios).sum();
    }

    @Override
    public boolean incluye(String nombreServicio) {
        return componentes.stream().anyMatch(componente -> componente.incluye(nombreServicio));
    }
}