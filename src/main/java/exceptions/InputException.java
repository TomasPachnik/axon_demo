package exceptions;

public class InputException extends RuntimeException {

    public InputException(String message) {
        super(message);
        System.out.println( "Error: " + message);
    }
}
