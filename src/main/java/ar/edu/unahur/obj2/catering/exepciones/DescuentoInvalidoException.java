package ar.edu.unahur.obj2.catering.exepciones;

/* El descuento fuera de los limites de 0 a 100 es un dato invalido que depende solo de el que llama
*/
public class DescuentoInvalidoException extends RuntimeException {

    public DescuentoInvalidoException(String mensaje) {
        super(mensaje);
    }
}