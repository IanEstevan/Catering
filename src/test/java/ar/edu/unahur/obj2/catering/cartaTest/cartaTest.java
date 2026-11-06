package ar.edu.unahur.obj2.catering.cartaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.carta.ActualizarPrecio;
import ar.edu.unahur.obj2.catering.carta.AgregarPropuesta;
import ar.edu.unahur.obj2.catering.carta.Carta;
import ar.edu.unahur.obj2.catering.carta.GestorCarta;
import ar.edu.unahur.obj2.catering.carta.QuitarPropuesta;
import ar.edu.unahur.obj2.catering.carta.Modificacion;
import ar.edu.unahur.obj2.catering.exepciones.NadaParaRevertirException;
import ar.edu.unahur.obj2.catering.exepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.exepciones.PropuestaNoIncluidaException;
import ar.edu.unahur.obj2.catering.propuestas.Menu;
import ar.edu.unahur.obj2.catering.propuestas.Servicio;

public class cartaTest {

    private Carta carta;
    private GestorCarta gestor;
    private Menu parrillero;
    private Servicio parrillada;
    private Servicio mesaDulce;
    private Servicio barraDeTragos;
    private Servicio fotografo;

    @BeforeEach
    void preparar() {
        carta = new Carta();
        gestor = new GestorCarta();
        parrillero = carta.menu("Menú Parrillero");
        parrillada = carta.servicio("Parrillada completa");
        mesaDulce = carta.servicio("Mesa dulce");
        barraDeTragos = carta.servicio("Barra de tragos");
        fotografo = new Servicio("Fotógrafo", 100_000);
    }

    @Test
    void dadaUnaModificacionAgregar_alAplicarlaYRevertirla_elMenuAgregaYLuegoQuitaLaPropuesta() throws Exception {

        gestor.aplicar(new AgregarPropuesta(parrillero, fotografo));

        assertTrue(parrillero.incluye("Fotógrafo"));
        assertEquals(4, parrillero.cantidadServicios());

        gestor.revertirUltima();

        assertFalse(parrillero.incluye("Fotógrafo"));
        assertEquals(3, parrillero.cantidadServicios());
    }

    @Test
    void dadaUnaModificacionQuitar_alAplicarlaYRevertirla_elMenuQuitaYLuegoVuelveAAgregarLaPropuesta() throws Exception {

        gestor.aplicar(new QuitarPropuesta(parrillero, mesaDulce));

        assertFalse(parrillero.incluye("Mesa dulce"));

        gestor.revertirUltima();

        assertTrue(parrillero.incluye("Mesa dulce"));
        assertEquals(342000, parrillero.precio());
    }

    @Test
    void dadaUnaActualizacionDePrecio_alRevertirla_devuelveElPrecioAnterior() throws Exception {

        gestor.aplicar(new ActualizarPrecio(parrillada, 260000));

        assertEquals(260000, parrillada.precio());

        gestor.revertirUltima();

        assertEquals(240000, parrillada.precio());
    }

    @Test
    void dadaUnaSecuenciaDeModificacionesInmediatas_alRevertirlas_seDeshacenEnOrdenInverso() throws Exception {
        assertEquals(342000, parrillero.precio());

        gestor.aplicar(new AgregarPropuesta(parrillero, fotografo));
        assertEquals(432000, parrillero.precio());

        gestor.aplicar(new ActualizarPrecio(parrillada, 260000));
        assertEquals(450000, parrillero.precio());

        gestor.aplicar(new QuitarPropuesta(parrillero, mesaDulce));
        assertEquals(396000, parrillero.precio());

        gestor.revertirUltima();
        assertEquals(450000, parrillero.precio());

        gestor.revertirUltima();
        assertEquals(432000, parrillero.precio());

        gestor.revertirUltima();
        assertEquals(342000, parrillero.precio());

        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    @Test
    void dadasDosActualizacionesDelMismoServicioCreadasAntesDeAplicar_alRevertirlas_seDeshacenEnCadena() throws Exception {

        Modificacion a260 = new ActualizarPrecio(parrillada, 260000);
        Modificacion a300 = new ActualizarPrecio(parrillada, 300000);

        gestor.aplicar(a260);
        gestor.aplicar(a300);

        assertEquals(300000, parrillada.precio());

        gestor.revertirUltima();
        assertEquals(260000, parrillada.precio());

        gestor.revertirUltima();
        assertEquals(240000, parrillada.precio());
    }

    @Test
    void dadoUnLoteConUnaModificacionInvalida_alAplicarlo_dejaElCatalogoComoEstaba() throws Exception {

        gestor.aplicar(new AgregarPropuesta(parrillero, fotografo));

        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicarLote(List.of(
                new ActualizarPrecio(parrillada, 260000),
                new QuitarPropuesta(parrillero, barraDeTragos))));

        assertEquals(432000, parrillero.precio());
        assertEquals(240000, parrillada.precio());

        gestor.revertirUltima();
        assertEquals(342000, parrillero.precio());
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    @Test
    void dadoUnLoteConUnPrecioNegativoAlFinal_alAplicarlo_deshaceLoAnteriorYPropagaPrecioNegativoException() {

        List<Modificacion> lote = List.of(
                new AgregarPropuesta(parrillero, fotografo),
                new ActualizarPrecio(parrillada, -1));

        assertThrows(PrecioNegativoException.class, () -> gestor.aplicarLote(lote));

        assertFalse(parrillero.incluye("Fotógrafo"));
        assertEquals(342000, parrillero.precio());
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    @Test
    void dadoUnLoteVacio_alAplicarlo_noModificaNadaNiRegistraHistorial() throws Exception {

        gestor.aplicarLote(List.of());

        assertEquals(342000, parrillero.precio());
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    @Test
    void dadoUnMenu_alAplicarQuitarDeUnaPropuestaNoIncluida_lanzaPropuestaNoIncluidaYNoAlteraElMenu() {

        assertThrows(PropuestaNoIncluidaException.class,
                () -> gestor.aplicar(new QuitarPropuesta(parrillero, barraDeTragos)));

        assertEquals(3, parrillero.cantidadServicios());
        assertEquals(342000, parrillero.precio());
    }

    @Test
    void dadaUnaActualizacionDePrecioNegativa_alAplicarla_lanzaPrecioNegativoYNoQuedaEnElHistorial() {

        assertThrows(PrecioNegativoException.class,
                () -> gestor.aplicar(new ActualizarPrecio(parrillada, -1)));

        assertEquals(240000, parrillada.precio());
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    @Test
    void dadoUnHistorialVacio_alRevertir_lanzaNadaParaRevertirException() {
        assertThrows(NadaParaRevertirException.class, () -> gestor.revertirUltima());
    }

    @Test
    void dadoUnAgregadoCuyaPropuestaYaFueQuitadaPorFuera_alRevertirlo_lanzaIllegalStateException() throws Exception {

        gestor.aplicar(new AgregarPropuesta(parrillero, fotografo));
        parrillero.quitar(fotografo);

        assertThrows(IllegalStateException.class, () -> gestor.revertirUltima());
    }
}