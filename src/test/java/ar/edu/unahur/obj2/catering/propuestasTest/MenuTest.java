package ar.edu.unahur.obj2.catering.propuestasTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.carta.Carta;
import ar.edu.unahur.obj2.catering.exepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.exepciones.PropuestaNoIncluidaException;
import ar.edu.unahur.obj2.catering.propuestas.Menu;
import ar.edu.unahur.obj2.catering.propuestas.Servicio;

public class MenuTest {

    private Carta carta;

    @BeforeEach
    void preparar() {
        carta = new Carta();
    }

    @Test
    void dadoUnMenuConDescuentoMayorACien_alCrearlo_lanzaDescuentoInvalidoException() {
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Invalido", 100.1));
    }

    @Test
    void dadoUnMenuConDescuentoNegativo_alCrearlo_lanzaDescuentoInvalidoException() {
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Invalido", -0.1));
    }

    @Test
    void dadoUnMenuConDescuentosLimite_alCrearlo_noLanzaExcepcion() {
        assertDoesNotThrow(() -> new Menu("Sin descuento", 0));
        assertDoesNotThrow(() -> new Menu("Gratis", 100));
    }

    @Test
    void dadoPackFiesta_alCalcularPrecio_aplicaElDescuentoDelMenu() {
        assertEquals(256_500, carta.menu("Pack Fiesta").precio());
    }

    @Test
    void dadoMenuParrillero_alContarServicios_devuelveTres() {
        assertEquals(3, carta.menu("Menú Parrillero").cantidadServicios());
    }

    @Test
    void dadoMenuParrillero_alConsultarInclusion_respondeSegunLosServiciosQueContiene() {

        Menu parrillero = carta.menu("Menú Parrillero");

        assertTrue(parrillero.incluye("Parrillada completa"));
        assertFalse(parrillero.incluye("DJ y sonido"));
    }

    @Test
    void dadoUnMenu_alPedirSuDescripcion_devuelveSuNombre() {
        assertEquals("Menú Parrillero", carta.menu("Menú Parrillero").descripcion());
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alContarServicios_sumaLosDeTodosLosNiveles() {

        assertEquals(6, carta.menu("Evento Completo").cantidadServicios());
    }

    @Test
    void dadoUnMenuAnidado_alConsultarInclusion_encuentraServiciosDeCualquierNivel() {

        Menu evento = carta.menu("Evento Completo");

        assertTrue(evento.incluye("DJ y sonido"));
        assertTrue(evento.incluye("Parrillada completa"));
        assertFalse(evento.incluye("Fotógrafo"));
    }

    @Test
    void dadoUnMenuNuevoAgregadoAlMenuExterno_alCalcularPrecio_loConsideraSinCambiarElCodigo() {

        Menu evento = carta.menu("Evento Completo");
        Menu extra = new Menu("Extra", 0);
        extra.agregar(new Servicio("Fotógrafo", 100_000));

        evento.agregar(extra);

        assertEquals(598_800, evento.precio());
        assertEquals(7, evento.cantidadServicios());
    }

    @Test
    void dadoUnMenu_alQuitarUnaPropuestaQueNoEsComponenteDirecto_lanzaPropuestaNoIncluidaYNoAlteraElMenu() {

        Menu parrillero = carta.menu("Menú Parrillero");

        assertThrows(PropuestaNoIncluidaException.class,
                () -> parrillero.quitar(carta.servicio("DJ y sonido")));

        assertEquals(3, parrillero.cantidadServicios());
        assertEquals(342_000, parrillero.precio());
    }

    @Test
    void dadoUnMenuAnidado_alQuitarUnServicioDeUnSubmenu_lanzaPropuestaNoIncluidaPorNoSerComponenteDirecto() {

        Menu evento = carta.menu("Evento Completo");

        assertThrows(PropuestaNoIncluidaException.class,
                () -> evento.quitar(carta.servicio("Mesa dulce")));
        }
}
