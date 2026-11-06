package ar.edu.unahur.obj2.catering.contratosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // Given / When
        Contrato contrato = new Contrato("Lucía", new MozosAdicionales(carta.menu("Menú Parrillero")));

        // Then
        assertEquals("Lucía", contrato.getCliente());
        assertEquals(360_000, contrato.getImporte());
        assertEquals("Menú Parrillero + Mozos adicionales", contrato.getDescripcion());
    }

    @Test
    void dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elImporteContratadoNoCambia() {
        // Given
        Contrato contrato = new Contrato("Lucía", carta.menu("Menú Parrillero"));

        // When
        carta.servicio("Parrillada completa").actualizarPrecio(260_000);

        // Then
        assertEquals(360_000, carta.menu("Menú Parrillero").precio());
        assertEquals(342_000, contrato.getImporte());
    }
}
