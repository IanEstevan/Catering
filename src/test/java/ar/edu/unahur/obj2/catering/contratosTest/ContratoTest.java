package ar.edu.unahur.obj2.catering.contratosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.adicionales.MozosAdicionales;
import ar.edu.unahur.obj2.catering.carta.Carta;
import ar.edu.unahur.obj2.catering.contratos.Contrato;

public class ContratoTest {

    private Carta carta;

    @BeforeEach
    void preparar() {
        carta = new Carta();
    }

    @Test
    void dadoUnContratoDeLuciaSobreMenuParrilleroConMozos_alConsultarImporteYDescripcion_devuelveLosVigentes() {

        Contrato contrato = new Contrato("Lucía", new MozosAdicionales(carta.menu("Menú Parrillero")));

        assertEquals("Lucía", contrato.getCliente());
        assertEquals(360000, contrato.getImporte());
        assertEquals("Menú Parrillero + Mozos adicionales", contrato.getDescripcion());
    }

    @Test
    void dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elImporteContratadoNoCambia() {

        Contrato contrato = new Contrato("Lucía", carta.menu("Menú Parrillero"));

        carta.servicio("Parrillada completa").actualizarPrecio(260000);

        assertEquals(360000, carta.menu("Menú Parrillero").precio());
        assertEquals(342000, contrato.getImporte());
    }
}
