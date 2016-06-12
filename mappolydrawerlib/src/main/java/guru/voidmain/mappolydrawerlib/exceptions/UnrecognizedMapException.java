package guru.voidmain.mappolydrawerlib.exceptions;

/**
 * Created by voidmain on 16/6/12.
 */
public class UnrecognizedMapException extends Exception {
    public UnrecognizedMapException() {
        super();
    }

    public UnrecognizedMapException(String message) {
        super(message);
    }

    public UnrecognizedMapException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UnrecognizedMapException(Throwable throwable) {
        super(throwable);
    }
}
