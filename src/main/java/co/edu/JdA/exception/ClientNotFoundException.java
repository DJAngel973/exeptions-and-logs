package co.edu.JdA.exception;

/**
 * Custom exception thrown when a client is not found in the system.
 * <p>
 * This exception is useful for handling scenarios where an operation, such as a search or a deletion,
 * fails because the client with the specified ID does not exist in the data layer. By being a "checked exception"
 * (since it extends {@link Exception}), the compiler enforces that it must be either handled
 * or declared in the method signature, leading to more robust code.
 * </p>
 */
public class ClientNotFoundException extends Exception {

    /**
     * Constructs a new {@code ClientNotFoundException} with a detailed message.
     * This constructor is ideal for providing a clear description of the error.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public ClientNotFoundException(String message) {super(message);}

    /**
     * Constructs a new {@code ClientNotFoundException} with a message and a cause.
     * This is useful when the exception is thrown as a result of an underlying exception.
     *
     * @param message The detail message for the exception.
     * @param cause The underlying cause (the original exception that triggered this one).
     */
    public ClientNotFoundException(String message, Throwable cause){super(message, cause);}
}