package oop.project.library.parser;


/**
 * An exception that is thrown by RegexStringParsers when they fail.
 */
public class RegexParseException extends ParseException {
    public RegexParseException(String message) {
        super(message);
    }
}
