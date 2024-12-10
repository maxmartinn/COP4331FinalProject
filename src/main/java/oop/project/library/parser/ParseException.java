package oop.project.library.parser;

/**
 * An exception that is thrown by Parsers when they fail.
 */
public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}
