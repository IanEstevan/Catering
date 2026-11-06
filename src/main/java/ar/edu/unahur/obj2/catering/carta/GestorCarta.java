package ar.edu.unahur.obj2.catering.carta;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import ar.edu.unahur.obj2.catering.exepciones.NadaParaRevertirException;
import ar.edu.unahur.obj2.catering.exepciones.PropuestaNoIncluidaException;

public class GestorCarta {

    private final Deque<Modificacion> historial = new ArrayDeque<>();

    public void aplicar(Modificacion modificacion) throws PropuestaNoIncluidaException {
        aplicarLote(List.of(modificacion));
    }

    /** Todo o nada: si alguna falla se deshacen las ya aplicadas y se propaga el error. */
    public void aplicarLote(List<Modificacion> lote) throws PropuestaNoIncluidaException {
        Deque<Modificacion> aplicadas = new ArrayDeque<>();
        try {
            for (Modificacion modificacion : lote) {
                modificacion.aplicar();
                aplicadas.push(modificacion);
            }
        } catch (RuntimeException | PropuestaNoIncluidaException e) {
            aplicadas.forEach(Modificacion::revertir);
            throw e;
        }
        lote.forEach(historial::push);
    }

    public void revertirUltima() throws NadaParaRevertirException {
        if (historial.isEmpty()) {
            throw new NadaParaRevertirException("No hay modificaciones aplicadas para revertir");
        }
        historial.pop().revertir();
    }
}