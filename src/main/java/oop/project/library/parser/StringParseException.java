package oop.project.library.parser;


/**
 * An exception that is thrown by StringParsers when they fail.
 */
public class StringParseException extends ParseException {
  public StringParseException(String message) {
    super(message);
  }
}
