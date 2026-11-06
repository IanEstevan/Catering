package ar.edu.unahur.obj2.catering.carta;

import ar.edu.unahur.obj2.catering.exepciones.PropuestaNoIncluidaException;
import ar.edu.unahur.obj2.catering.propuestas.Menu;
import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class QuitarPropuesta implements Modificacion {

    private final Menu menu;
    private final Propuesta propuesta;

    public QuitarPropuesta(Menu menu, Propuesta propuesta) {
        this.menu = menu;
        this.propuesta = propuesta;
    }

    @Override
    public void aplicar() throws PropuestaNoIncluidaException {
        menu.quitar(propuesta);
    }

    @Override
    public void revertir() {
        menu.agregar(propuesta);
    }
}