package oop.project.library.command;

public class MissingRequiredArgumentException extends CommandException {
    public MissingRequiredArgumentException(String message) {
        super(message);
    }
}
