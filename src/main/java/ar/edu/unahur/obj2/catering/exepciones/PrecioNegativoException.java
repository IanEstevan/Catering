package ar.edu.unahur.obj2.catering.exepciones;

/*quien llama controla el precio que pasa y podía evitar el error..
*/
public class PrecioNegativoException extends RuntimeException {

    public PrecioNegativoException(String mensaje) {
        super(mensaje);
    }
}