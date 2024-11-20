package oop.project.library.command;

public class TooManyPositionalsException extends RuntimeException {
    public TooManyPositionalsException(String message) {
        super(message);
    }
}
