package co.edu.poli.exception;

public class IdDuplicadoException extends RuntimeException {
    public IdDuplicadoException(String message) {
        super(message);
    }
    public IdDuplicadoException(String message, Throwable cause){super(message, cause);}
}