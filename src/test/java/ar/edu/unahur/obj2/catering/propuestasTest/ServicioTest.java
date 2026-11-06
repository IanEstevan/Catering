package ar.edu.unahur.obj2.catering.propuestasTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.exepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.propuestas.Servicio;

public class ServicioTest {

    @Test
    void dadoUnServicioConPrecioNegativo_alCrearlo_lanzaPrecioNegativoException() {
        assertThrows(PrecioNegativoException.class, () -> new Servicio("DJ y sonido", -1));
    }

    @Test
    void dadoUnServicioConPrecioCero_alCrearlo_secreaConPrecioCero() {
        assertEquals(0, new Servicio("Cortesia", 0).precio());
    }

    @Test
    void dadoUnServicio_alConsultarPrecioYDescripcion_devuelveSuPrecioYSuNombre() {
        // Given
        Servicio servicio = new Servicio("Mesa dulce", 60000);

        // When / Then
        assertEquals(60000, servicio.precio());
        assertEquals("Mesa dulce", servicio.descripcion());
    }

    @Test
    void dadoUnServicioSuelto_alContarServicios_devuelveUno() {
        assertEquals(1, new Servicio("Mesa dulce", 60000).cantidadServicios());
    }

    @Test
    void dadoUnServicio_alConsultarInclusionPorNombre_soloIncluyeSuPropioNombre() {
        // Given
        Servicio servicio = new Servicio("Mesa dulce", 60000);

        // When / Then
        assertTrue(servicio.incluye("Mesa dulce"));
        assertFalse(servicio.incluye("DJ y sonido"));
    }

    @Test
    void dadoUnServicio_alActualizarSuPrecioANegativo_lanzaPrecioNegativoYConservaElAnterior() {
        // Given
        Servicio servicio = new Servicio("Mesa dulce", 60000);

        // When
        assertThrows(PrecioNegativoException.class, () -> servicio.actualizarPrecio(-5));

        // Then
        assertEquals(60000, servicio.precio());
    }
}
