package playground.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException error(String message) {
        return new NotFoundException(message);
    }
}
