package oop.project.library.command;

public class MissingRequiredArgumentException extends RuntimeException {
    public MissingRequiredArgumentException(String message) {
        super(message);
    }
}
