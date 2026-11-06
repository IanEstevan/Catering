package ar.edu.unahur.obj2.catering.carta;

import ar.edu.unahur.obj2.catering.exepciones.PropuestaNoIncluidaException;

public interface Modificacion {

    void aplicar() throws PropuestaNoIncluidaException;

    void revertir();
}