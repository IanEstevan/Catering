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

        Propuesta mesaDulce = carta.servicio("Mesa dulce");

        Propuesta conMozos = new MozosAdicionales(mesaDulce);

        assertEquals(78000, conMozos.precio());
        assertEquals("Mesa dulce + Mozos adicionales", conMozos.descripcion());
    }

    @Test
    void dadaUnaVajillaPremium_alAplicarlaSobreUnServicio_sumaDoceMilYAgregaSuDescripcion() {

        Propuesta mesaDulce = carta.servicio("Mesa dulce");

        Propuesta conVajilla = new VajillaPremium(mesaDulce);

        assertEquals(72000, conVajilla.precio());
        assertEquals("Mesa dulce + Vajilla premium", conVajilla.descripcion());
    }

    @Test
    void dadoUnServicioNocturno_alAplicarloSobreUnServicio_sumaElVeintePorCientoYAgregaSuDescripcion() {

        Propuesta mesaDulce = carta.servicio("Mesa dulce");

        Propuesta conNocturno = new ServicioNocturno(mesaDulce);

        assertEquals(72000, conNocturno.precio());
        assertEquals("Mesa dulce + Servicio nocturno", conNocturno.descripcion());
    }

    @Test
    void dadosMozosYLuegoNocturno_alCalcularPrecioSobreUnMenu_elNocturnoIncluyeLosMozos() {

        Propuesta parrillero = carta.menu("Menú Parrillero");

        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(parrillero));

        assertEquals(432000, propuesta.precio());
        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno", propuesta.descripcion());
    }

    @Test
    void dadoUnServicioNocturnoAntesQueMozos_alCalcularPrecio_elOrdenAlteraElPrecioFinal() {

        Propuesta parrillero = carta.menu("Menú Parrillero");

        Propuesta propuesta = new MozosAdicionales(new ServicioNocturno(parrillero));

        assertEquals(428400, propuesta.precio());
        assertEquals("Menú Parrillero + Servicio nocturno + Mozos adicionales", propuesta.descripcion());
    }

    @Test
    void dadaUnaVajillaRepetida_alCalcularPrecio_seAcumulaDosVeces() {

        Propuesta brindis = carta.servicio("Brindis de bienvenida");

        Propuesta propuesta = new VajillaPremium(new VajillaPremium(brindis));

        assertEquals(74000, propuesta.precio());
        assertEquals("Brindis de bienvenida + Vajilla premium + Vajilla premium", propuesta.descripcion());
    }

    @Test
    void dadoUnNocturnoRepetido_alCalcularPrecio_seAcumulaSobreElPrecioAcumulado() {

        Propuesta mesaDulce = carta.servicio("Mesa dulce");

        Propuesta propuesta = new ServicioNocturno(new ServicioNocturno(mesaDulce));

        assertEquals(86400, propuesta.precio());
        assertEquals("Mesa dulce + Servicio nocturno + Servicio nocturno", propuesta.descripcion());
    }

    @Test
    void dadoUnAdicionalSobreUnMenuAnidado_alCalcularPrecio_sumaSobreElPrecioDelMenuCompleto() {

        Propuesta evento = carta.menu("Evento Completo");

        Propuesta propuesta = new VajillaPremium(evento);

        assertEquals(530800, propuesta.precio());
        assertEquals("Evento Completo + Vajilla premium", propuesta.descripcion());
    }

    @Test
    void dadaUnaPropuestaConAdicionales_alConsultarCantidadEInclusion_secomportaComoLaOriginal() {

        Propuesta evento = carta.menu("Evento Completo");

        Propuesta propuesta = new ServicioNocturno(new MozosAdicionales(evento));

        assertEquals(6, propuesta.cantidadServicios());
        assertTrue(propuesta.incluye("DJ y sonido"));
        assertFalse(propuesta.incluye("Mozos adicionales"));
    }

    @Test
    void dadaUnaPropuestaConAdicional_alConsultarLaOriginal_sigueConElMismoPrecioYDescripcion() {

        Propuesta parrillero = carta.menu("Menú Parrillero");

        new MozosAdicionales(parrillero);

        assertEquals(342000, parrillero.precio());
        assertEquals("Menú Parrillero", parrillero.descripcion());
    }
}
