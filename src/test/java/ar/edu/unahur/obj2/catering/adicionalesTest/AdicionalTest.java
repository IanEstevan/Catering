package ar.edu.unahur.obj2.catering.adicionalesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.adicionales.MozosAdicionales;
import ar.edu.unahur.obj2.catering.adicionales.ServicioNocturno;
import ar.edu.unahur.obj2.catering.adicionales.VajillaPremium;
import ar.edu.unahur.obj2.catering.carta.Carta;
import ar.edu.unahur.obj2.catering.propuestas.Propuesta;

public class AdicionalTest {

    private Carta carta;

    @BeforeEach
    void preparar() {
        carta = new Carta();
    }

    @Test
    void dadosMozosAdicionales_alAplicarlosSobreUnServicio_sumaDieciochoMilYAgregaSuDescripcion() {
        // Given
        Propuesta mesaDulce = carta.servicio("Mesa dulce");

        // When
        Propuesta conMozos = new MozosAdicionales(mesaDulce);

        // Then
        assertEquals(78_000, conMozos.precio());
        assertEquals("Mesa dulce + Mozos adicionales", conMozos.descripcion());
    }

    @Test
    void dadaUnaVajillaPremium_alAplicarlaSobreUnServicio_sumaDoceMilYAgregaSuDescripcion() {
        // Given
        Propuesta mesaDulce = carta.servicio("Mesa dulce");

        // When
        Propuesta conVajilla = new VajillaPremium(mesaDulce);

        // Then
        assertEquals(72_000, conVajilla.precio());
        assertEquals("Mesa dulce + Vajilla premium", conVajilla.descripcion());
    }

    @Test
    void dadoUnServicioNocturno_alAplicarloSobreUnServicio_sumaElVeintePorCientoYAgregaSuDescripcion() {
        // Given
        Propuesta mesaDulce = carta.servicio("Mesa dulce");

        // When
        Propuesta conNocturno = new ServicioNocturno(mesaDulce);

        // Then
        assertEquals(72_000, conNocturno.precio());
        assertEquals("Mesa dulce + Servicio nocturno", conNocturno.descripcion());
    }

    @Test
    void dadosMozosYLuegoNocturno_alCalcularPrecioSobreUnMenu_elNocturnoIncluyeLosMozos() {
        // Given
        Propuesta parrillero = carta.menu("Menú Parrillero");

        // When
        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(parrillero));

        // Then: (342.000 + 18.000) x 1,20
        assertEquals(432_000, propuesta.precio());
        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno", propuesta.descripcion());
    }

    @Test
    void dadoUnServicioNocturnoAntesQueMozos_alCalcularPrecio_elOrdenAlteraElPrecioFinal() {
        // Given
        Propuesta parrillero = carta.menu("Menú Parrillero");

        // When
        Propuesta propuesta = new MozosAdicionales(new ServicioNocturno(parrillero));

        // Then: 342.000 x 1,20 + 18.000
        assertEquals(428_400, propuesta.precio());
        assertEquals("Menú Parrillero + Servicio nocturno + Mozos adicionales", propuesta.descripcion());
    }

    @Test
    void dadaUnaVajillaRepetida_alCalcularPrecio_seAcumulaDosVeces() {
        // Given
        Propuesta brindis = carta.servicio("Brindis de bienvenida");

        // When
        Propuesta propuesta = new VajillaPremium(new VajillaPremium(brindis));

        // Then
        assertEquals(74_000, propuesta.precio());
        assertEquals("Brindis de bienvenida + Vajilla premium + Vajilla premium", propuesta.descripcion());
    }

    @Test
    void dadoUnNocturnoRepetido_alCalcularPrecio_seAcumulaSobreElPrecioAcumulado() {
        // Given
        Propuesta mesaDulce = carta.servicio("Mesa dulce");

        // When
        Propuesta propuesta = new ServicioNocturno(new ServicioNocturno(mesaDulce));

        // Then: 60.000 x 1,20 x 1,20
        assertEquals(86_400, propuesta.precio());
        assertEquals("Mesa dulce + Servicio nocturno + Servicio nocturno", propuesta.descripcion());
    }

    @Test
    void dadoUnAdicionalSobreUnMenuAnidado_alCalcularPrecio_sumaSobreElPrecioDelMenuCompleto() {
        // Given
        Propuesta evento = carta.menu("Evento Completo");

        // When
        Propuesta propuesta = new VajillaPremium(evento);

        // Then
        assertEquals(530_800, propuesta.precio());
        assertEquals("Evento Completo + Vajilla premium", propuesta.descripcion());
    }

    @Test
    void dadaUnaPropuestaConAdicionales_alConsultarCantidadEInclusion_secomportaComoLaOriginal() {
        // Given
        Propuesta evento = carta.menu("Evento Completo");

        // When
        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(evento));

        // Then
        assertEquals(6, propuesta.cantidadServicios());
        assertTrue(propuesta.incluye("DJ y sonido"));
        assertFalse(propuesta.incluye("Mozos adicionales"));
    }

    @Test
    void dadaUnaPropuestaConAdicional_alConsultarLaOriginal_sigueConElMismoPrecioYDescripcion() {
        // Given
        Propuesta parrillero = carta.menu("Menú Parrillero");

        // When
        new MozosAdicionales(parrillero);

        // Then
        assertEquals(342_000, parrillero.precio());
        assertEquals("Menú Parrillero", parrillero.descripcion());
    }
}
