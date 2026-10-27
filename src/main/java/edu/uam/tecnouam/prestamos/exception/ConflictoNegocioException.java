package edu.uam.tecnouam.prestamos.exception;


public class ConflictoNegocioException extends RuntimeException {
    public ConflictoNegocioException(String mensaje) {
        super(mensaje);
    }
}

