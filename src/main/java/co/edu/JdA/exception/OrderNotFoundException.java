package co.edu.JdA.exception;

/**
 * Custom exception thrown when an order is not found in the system.
 * <p>
 * This exception is used to handle situations where an operation, such as retrieving
 * or updating an order, fails because the order with the specified ID does not exist.
 * As a checked exception, it signals an anticipated error that requires explicit handling
 * by the calling code.
 * </p>
 * */
public class OrderNotFoundException extends Exception {

    /**
     * Constructs a new {@code OrderNotFoundException} with a detailed message.
     * This constructor provides a clear description of the specific order that could not be found.
     *
     * @param message The detail message explaining the cause of the exception.
     * */
    public OrderNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code OrderNotFoundException} with a message and a cause.
     * This is useful when the exception is thrown as a result of an underlying exception
     * (e.g., from a database query).
     *
     * @param message The detail message for the exception.
     * @param cause The underlying cause (the original exception that triggered this one).
     * */
    public OrderNotFoundException(String message, Throwable cause) {super(message, cause);}
}