package ar.edu.unahur.obj2.catering.exepciones;

/* Es una situación esperable pedir “deshacer” cuando ya no queda nada
*/
public class NadaParaRevertirException extends Exception {

    public NadaParaRevertirException(String mensaje) {
        super(mensaje);
    }
}