package oop.project.library.parser;


/**
 * An exception that is thrown by NumberParsers when they fail.
 */
public class NumberParseException extends ParseException {
    public NumberParseException(String message) {
        super(message);
    }
}
