package ar.edu.unahur.obj2.catering.carta;

import ar.edu.unahur.obj2.catering.exepciones.PropuestaNoIncluidaException;
import ar.edu.unahur.obj2.catering.propuestas.Menu;
import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class AgregarPropuesta implements Modificacion {

    private final Menu menu;
    private final Propuesta propuesta;

    public AgregarPropuesta(Menu menu, Propuesta propuesta) {
        this.menu = menu;
        this.propuesta = propuesta;
    }

    @Override
    public void aplicar() {
        menu.agregar(propuesta);
    }

    @Override
    public void revertir() {
        try {
            menu.quitar(propuesta);
        } catch (PropuestaNoIncluidaException e) {
            throw new IllegalStateException("No se pudo revertir el agregado de " + propuesta.descripcion(), e);
        }
    }
}