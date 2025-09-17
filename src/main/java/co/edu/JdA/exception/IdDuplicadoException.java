package co.edu.JdA.exception;

/**
 * Custom exception thrown when an attempt is made to create or use a duplicate ID.
 * <p>
 * This is an unchecked exception, which indicates that it's typically a result of a
 * programming error or an invalid state in the application, rather than an expected
 * operational condition. It's often used to prevent creating an entity with an ID that
 * already exists in the system.
 * </p>
 * */
public class IdDuplicadoException extends RuntimeException {

    /**
     * Constructs a new {@code IdDuplicadoException} with a detailed message.
     * This constructor is ideal for providing a clear description of the duplicate ID error.
     *
     * @param message The detail message explaining the cause of the exception.
     * */
    public IdDuplicadoException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code IdDuplicadoException} with a message and a cause.
     * This is useful when the exception is thrown as a result of an underlying exception.
     *
     * @param message The detail message for the exception.
     * @param cause The underlying cause (the original exception that triggered this one).
     * */
    public IdDuplicadoException(String message, Throwable cause){super(message, cause);}
}