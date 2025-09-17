package co.edu.JdA.exception;

/**
 * Custom exception thrown when data provided for an operation is invalid or malformed.
 * <p>
 * This is an unchecked exception, which indicates that it's a result of an invalid state
 * that should have been prevented by a programming check. Examples include providing a null
 * or empty string where a non-null value is expected, or using a number outside of an acceptable
 * range.
 * </p>
 * */
public class InvalidDataException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidDataException} with a detailed message.
     * This constructor is ideal for providing a clear description of why the data is invalid.
     *
     * @param message The detail message explaining the cause of the exception.
     * */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code InvalidDataException} with a message and a cause.
     * This is useful when the exception is thrown as a result of an underlying exception.
     *
     * @param message The detail message for the exception.
     * @param cause The underlying cause (the original exception that triggered this one).
     * */
    public InvalidDataException(String message, Throwable cause){super(message, cause);}
}