package oop.project.library.command;

public class UnknownFlagException extends RuntimeException {
    public UnknownFlagException(String message) {
        super(message);
    }
}
