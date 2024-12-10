package oop.project.library.parser;


/**
 * In order to parse user-defined classes, you must extend this class with T set to the user-defined class.
 * There are no restrictions to what may be done.
 * Please throw ParseExceptions or an extension of ParseException if your parser fails.
 */
public interface Parser<T> {
    T parse(String input);
}
