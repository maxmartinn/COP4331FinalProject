package oop.project.library.command;

public class UnknownFlagException extends CommandException {
    public UnknownFlagException(String message) {
        super(message);
    }
}
