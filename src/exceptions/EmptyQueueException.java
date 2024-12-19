package exceptions;

/**
 * Exception thrown when an operation is attempted on an empty queue.
 * This exception extends {@link RuntimeException}, allowing unchecked usage.
 */
public class EmptyQueueException extends RuntimeException {

    private static final long serialVersionUID = -256313342472876629L;

    /**
     * Constructs a new {@code EmptyQueueException} with a default message.
     * The default message is "Queue is empty."
     */
    public EmptyQueueException() {
        super("Queue is empty.");
    }

    /**
     * Constructs a new {@code EmptyQueueException} with a custom message.
     *
     * @param message the custom message for the exception.
     */
    public EmptyQueueException(String message) {
        super(message);
    }
}
