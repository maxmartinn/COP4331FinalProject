package oop.project.library.command;

public class TooManyPositionalsException extends CommandException {
    public TooManyPositionalsException(String message) {
        super(message);
    }
}
